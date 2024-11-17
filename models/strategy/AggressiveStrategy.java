package models.strategy;

import models.country.Country;
import models.game.Game;
import models.general.IRegion;

import java.util.List;

import static models.game.Game.myCountry;

public class AggressiveStrategy implements  AIActionStrategy {
    @Override
    public void executeAction(Country country, Game game) {
        System.out.println("\n" + country.getName() + " is taking aggressive actions.");

        // 1. Recruit soldiers if possible
        if (country.getMilitary().canRecruitSoldiers()) {
            List<IRegion> regions = myCountry.getRegions();
            IRegion selectedRegion = regions.getFirst();
            int count = 200;
            selectedRegion.addSoldiers("infantry",count,false);
            // Method to recruit based on available resources
            System.out.println(country.getName() + " recruits " + count + " soldiers.");
        }
        country.getEconomy().calculateIncome();
        country.getMilitary().addRecruits();

        // 2. Find a weak neighboring country to attack
//        Country weakestNeighbor = findWeakestNeighbor(country, game);
//
//        if (weakestNeighbor != null) {
//            // 3. Declare war and attack
//            System.out.println(country.getName() + " declares war on " + weakestNeighbor.getName() + ".");
//            country.declareWar(weakestNeighbor); // Declare war method
//            attackRegion(country, weakestNeighbor); // Attack a region of the weak neighbor
//        } else {
//            // No weak neighbors, build military power
//            System.out.println(country.getName() + " is preparing for future wars.");
//            // Focus on further military development
//        }
    }
}

    // Find the weakest neighboring country by comparing military strength
//    private Country findWeakestNeighbor(Country country, Game game) {
//        Country weakest = null;
//        int minStrength = Integer.MAX_VALUE;
//
//        // Loop through neighboring countries
//        for (Country neighbor : game.getNeighboringCountries(country)) {
//            if (country.isAtWar(neighbor)) continue; // Skip if already at war
//            int neighborStrength = neighbor.getMilitaryStrength(); // Method to get military strength
//            if (neighborStrength < minStrength) {
//                minStrength = neighborStrength;
//                weakest = neighbor;
//            }
//        }
//
//        return weakest;
//    }

    // Execute an attack on one of the enemy's regions
//    private void attackRegion(Country country, Country enemy) {
//        Region targetRegion = findWeakestRegion(enemy); // Find the weakest region of the enemy
//        if (targetRegion != null) {
//            country.attackRegion(targetRegion); // Method to execute an attack
//            System.out.println(country.getName() + " attacks " + enemy.getName() + " in the region of " + targetRegion.getName() + ".");
//        }
//    }
//
//    // Find the weakest region of the enemy country
//    private Region findWeakestRegion(Country enemy) {
//        Region weakestRegion = null;
//        int minDevelopment = Integer.MAX_VALUE;
//
//        for (Region region : enemy.getRegions()) {
//            if (region.getDevelopmentLevel() < minDevelopment) {
//                minDevelopment = region.getDevelopmentLevel();
//                weakestRegion = region;
//            }
//        }
//
//        return weakestRegion;
//    }
//}


