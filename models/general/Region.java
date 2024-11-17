package models.general;

import models.AbstractFactory.IBuilding;
import models.country.Country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static models.general.RelationshipStatus.ALLIED;
import static models.general.RelationshipStatus.AT_WAR;

public class Region implements IRegion, Cloneable {
    private String name;
    private int developmentLevel;
    private String capital;
    private List<IBuilding> buildings;
    private Country owner;
    private Country temporaryController; // New field for temporary controller

    // Soldier containers: separate for owner and temporary controller
    private Map<String, Integer> ownerSoldiers;
    private Map<String, Integer> tempControllerSoldiers;

    private List<Region> neighboringRegions;

    public Region(String name, int developmentLevel, String capital) {
        this.name = name;
        this.developmentLevel = developmentLevel;
        this.capital = capital;
        this.buildings = new ArrayList<>();
        this.ownerSoldiers = new HashMap<>(); // Initialize owner soldier container
        this.tempControllerSoldiers = new HashMap<>(); // Initialize temp controller soldier container
        this.neighboringRegions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getDevelopmentLevel() {
        return developmentLevel;
    }

    public String getCapital() {
        return capital;
    }

    public void upgradeDevelopmentLevel() {
        if (developmentLevel < 10) {
            developmentLevel++;
            System.out.println(name + " upgraded to development level " + developmentLevel);
        } else {
            System.out.println(name + " is already at maximum development level.");
        }
    }

    public void addBuilding(IBuilding building) {
        buildings.add(building);
    }

    // Soldier management for owner and temporary controller
    public void addSoldiers(String soldierType, int count, boolean isTemporaryController) {
        Map<String, Integer> targetSoldiers = isTemporaryController ? tempControllerSoldiers : ownerSoldiers;
        targetSoldiers.put(soldierType, targetSoldiers.getOrDefault(soldierType, 0) + count);
    }

    public boolean conquerRegion(Country attacker, IRegion targetRegion, Country defender, String soldierType) {
        int attackerArmySize = getSoldierCount(soldierType, true);
        int defenderArmySize = getSoldierCount(soldierType, false);

        System.out.println(attacker.getName() + " army size: " + attackerArmySize);
        System.out.println(defender.getName() + " army size: " + defenderArmySize);

        if (attackerArmySize > defenderArmySize) {
            System.out.println(attacker.getName() + " conquers the region: " + targetRegion.getName());

            defender.removeRegion(targetRegion);
            attacker.addRegion(targetRegion);

            targetRegion.setOwner(attacker);
            int soldiersLost = defenderArmySize;
            reduceSoldiers(soldierType, soldiersLost, true);
            targetRegion.addSoldiers(soldierType, getSoldierCount(soldierType, true), false);
            targetRegion.clearTemporaryController();



            System.out.println("Remaining " + soldierType + " soldiers for " + attacker.getName() + ": " + getSoldierCount(soldierType, true));

            return true;
        } else if (attackerArmySize < defenderArmySize) {
            System.out.println(defender.getName() + " defends the region: " + targetRegion.getName());

            clearTemporaryControllerSoldiers(soldierType);

            return false;
        } else {
            System.out.println("Both sides lost their soldiers in a draw!");

            clearTemporaryControllerSoldiers(soldierType);
            clearOwnerSoldiers(soldierType);

            return false;
        }
    }

    public boolean moveSoldier(String soldierType,IRegion sourceRegion, IRegion targetRegion, Country attacker, Country defender) {
        int soldierCount = sourceRegion.getSoldierCount(soldierType, false);

        if (soldierCount <= 0) {
            System.out.println("No soldiers available to move.");
            return false;
        }

        if (targetRegion.isNeutral(attacker, targetRegion) || targetRegion.isOwnedByAlly(attacker, defender)) {
            sourceRegion.removeSoldiers(soldierType, soldierCount, false);
            targetRegion.addSoldiers(soldierType, soldierCount, false);
            System.out.println("Soldier moved to " + targetRegion.getName() + ".");
            return true;
        } else if (targetRegion.isEnemy(attacker, defender)) {
            System.out.println(attacker.getName() + " is moving soldiers to attack " + targetRegion.getName());

            targetRegion.setTemporaryController(attacker);
            sourceRegion.removeSoldiers(soldierType, soldierCount, false);
            targetRegion.addSoldiers(soldierType, soldierCount, true);
            System.out.println("Soldier moved to enemy region: " + targetRegion.getName() + " for battle.");

            boolean conquered = targetRegion.conquerRegion(attacker, targetRegion, defender, soldierType);

            if (conquered) {
                System.out.println(attacker.getName() + " has conquered " + targetRegion.getName() + "!");
            } else {
                targetRegion.setOwner(defender);
                System.out.println(attacker.getName() + " failed to conquer " + targetRegion.getName() + ". Ownership reverted to " + defender.getName());
            }

            return true;
        } else {
            System.out.println("Cannot move soldier. Invalid target region.");
            return false;
        }
    }

    // Get soldier count from either owner or temporary controller map
    public int getSoldierCount(String soldierType, boolean isTemporaryController) {
        Map<String, Integer> targetSoldiers = isTemporaryController ? tempControllerSoldiers : ownerSoldiers;
        return targetSoldiers.getOrDefault(soldierType, 0);
    }

    // Remove soldiers from either owner or temporary controller map
    public void removeSoldiers(String soldierType, int count, boolean isTemporaryController) {
        Map<String, Integer> targetSoldiers = isTemporaryController ? tempControllerSoldiers : ownerSoldiers;
        int currentCount = targetSoldiers.getOrDefault(soldierType, 0);
        if (currentCount >= count) {
            targetSoldiers.put(soldierType, currentCount - count);
        } else {
            System.out.println("Not enough soldiers to remove.");
        }
    }

    // Clear soldiers for temporary controller or owner after the battle
    public void clearTemporaryControllerSoldiers(String soldierType) {
        tempControllerSoldiers.remove(soldierType);
    }

    public void clearOwnerSoldiers(String soldierType) {
        ownerSoldiers.remove(soldierType);
    }

    // Setters for owner and temporary controller
    public void setOwner(Country newOwner) {
        this.owner = newOwner;
        this.temporaryController = null; // Clear temporary controller after region is conquered
    }

    public void setTemporaryController(Country controller) {
        this.temporaryController = controller;
    }

    public void clearTemporaryController() {
        this.temporaryController = null;
        this.tempControllerSoldiers.clear(); // Clear temporary soldiers after battle
    }

    public Country getOwner() {
        return owner;
    }

    public Country getTemporaryController() {
        return temporaryController;
    }
    // Border management methods

    // Add a neighboring region
    public void addNeighbor(Region neighbor) {
        if (!neighboringRegions.contains(neighbor)) {
            neighboringRegions.add(neighbor);
            neighbor.neighboringRegions.add(this); // Add this region to the neighbor's list as well
        }
    }
    public boolean reduceSoldiers(String soldierType, int count, boolean isTemporaryController) {
        Map<String, Integer> targetSoldierMap = isTemporaryController ? tempControllerSoldiers : ownerSoldiers;

        // Get the current count of soldiers of the specified type
        int currentCount = targetSoldierMap.getOrDefault(soldierType, 0);

        // Check if there are enough soldiers to reduce
        if (currentCount < count) {
            System.out.println("Not enough " + soldierType + " soldiers to reduce in this region.");
            return false;
        }

        // Reduce the count of soldiers
        targetSoldierMap.put(soldierType, currentCount - count);

        System.out.println("Reduced " + count + " " + soldierType + " soldiers in the region.");
        return true;
    }
    public int getRegionSoldier(String soldierType, boolean isTemporaryController) {
        Map<String, Integer> targetSoldierMap = isTemporaryController ? tempControllerSoldiers : ownerSoldiers;
        return targetSoldierMap.getOrDefault(soldierType, 0);
    }
    // Remove a neighboring region
    public void removeNeighbor(Region neighbor) {
        neighboringRegions.remove(neighbor);
        neighbor.neighboringRegions.remove(this); // Remove this region from the neighbor's list
    }

    // Check if two regions are neighbors
    public boolean isNeighbor(Region region) {
        return neighboringRegions.contains(region);
    }

    // Get neighboring regions
    public List<Region> getNeighbors() {
        return new ArrayList<>(neighboringRegions); // Return a copy of the list to prevent modification
    }
    @Override
    public boolean isNeutral(Country myCountry, IRegion targetReginion) {
        return myCountry.getRegions().contains(targetReginion);// A region is neutral if it has no owner
    }

    @Override
    public boolean isEnemy(Country attacker, Country  defender) {
        // Logic to determine if the region's owner is an enemy
        return attacker.getRelationshipManager().getRelationship(defender) == AT_WAR;
    }

    @Override
    public boolean isOwnedByAlly(Country attacker, Country  defender) {
        return attacker.getRelationshipManager().getRelationship(defender) == ALLIED;
    }


    // Prototype method
//    @Override
//    protected Region clone() {
//        try {
//            Region cloned = (Region) super.clone();
//            // Deep copy the buildings list
//            cloned.buildings = new ArrayList<>(this.buildings.size());
//            for (IBuilding building : this.buildings) {
//                cloned.buildings.add(building); // Assuming IBuilding also implements Cloneable
//            }
//
//            // Deep copy the soldiers map
//            cloned.soldiers = new HashMap<>(this.soldiers);
//
//            // Do not copy neighboring regions (since they are references to other regions)
//
//            return cloned;
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError(); // Can never happen
//        }
//    }
}
