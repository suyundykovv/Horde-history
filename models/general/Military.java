package models.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.country.Country;
import models.factory.ISoldierFactory;
import models.factory.Soldier;
import models.factory.SoldierFactory;

public class Military implements IMilitary {

    private int militaryPoints;
    private int recruits;
    private static final int RECRUITS_PER_MILITARY_POINT = 30;
    private List<Soldier> army;  // List to hold recruited soldiers
    private ISoldierFactory soldierFactory;

    public Military( int initialMilitaryPoints) {

        this.militaryPoints = initialMilitaryPoints;
        this.recruits = militaryPoints * RECRUITS_PER_MILITARY_POINT;
        this.army = new ArrayList<>();
        this.soldierFactory = new SoldierFactory(); // Factory for soldier creation
    }
    public void addRecruits() {
        int recruitsToAdd = militaryPoints * RECRUITS_PER_MILITARY_POINT;
        recruits += recruitsToAdd;
        System.out.println(recruitsToAdd + " recruits added. Total recruits: " + recruits);
    }
    public int recruitSoldier(String type, int amount, IRegion region,Country country) {
        Soldier newSoldier = soldierFactory.createSoldier(type, amount, region,country);
        if (recruits >= newSoldier.getCost()) {
            recruits -= newSoldier.getCost();
            for (int i = 0; i < amount; i++) {
                army.add(newSoldier); // Add the soldiers to the army
            }
            region.addSoldiers(type,amount,false);
            System.out.println(amount + " " + newSoldier.getType() + "(s) recruited in" + region.getName());
        } else {
            System.out.println("Not enough recruits to create " + newSoldier.getType());
        }
        return amount;
    }

    public Map<String, Integer> getSoldierCount() {
        Map<String, Integer> soldierCount = new HashMap<>();
        for (Soldier soldier : army) {
            soldierCount.put(soldier.getType(), soldierCount.getOrDefault(soldier.getType(), 0) + 1);
        }
        return soldierCount;
    }

    @Override
    public int getAvailableRecruits() {
        return recruits;
    }
    public void spendRecruits(int amount) {
        if (recruits >= amount) {
            recruits -= amount;
            System.out.println(amount + " recruits spent. Remaining recruits: " + recruits);
        } else {
            System.out.println("Not enough recruits available!");
        }
    }
    @Override
    public int getSoldiers() {
        return army.size();
    }

    @Override
    public void setMilitaryPoints(int militaryPoints) {
        this.militaryPoints = militaryPoints;
    }



    @Override
    public void recruitSoldiers(Country targetCountry, int numberOfRecruits) {
        // Implementation for recruiting soldiers for a specific country
    }
    public boolean canRecruitSoldiers(){
        return true;
    }
}
