package models.strategy;

import models.country.Country;
import models.game.Game;

public class EconomicStrategy implements  AIActionStrategy {
    @Override
    public void executeAction(Country country, Game game) {
        System.out.println("\n" +  country.getName() + " is focusing on economic growth.");
        country.getEconomy().calculateIncome();
        country.getMilitary().addRecruits();
        // 1. Invest in regional development
//        Region lowestRegion = findLowestDevelopmentRegion(country);
//        if (lowestRegion != null && country.canUpgradeRegion(lowestRegion)) {
//            country.upgradeRegion(lowestRegion); // Upgrade region method
//            System.out.println(country.getName() + " upgrades the development of " + lowestRegion.getName() + ".");
//        }
//
//        // 2. Avoid military action, focus on generating income
//        System.out.println(country.getName() + " focuses on increasing income.");
    }

    // Find the region with the lowest development level
//    private Region findLowestDevelopmentRegion(Country country) {
//        Region lowestRegion = null;
//        int minDevelopment = Integer.MAX_VALUE;
//
//        for (Region region : country.getRegions()) {
//            if (region.getDevelopmentLevel() < minDevelopment) {
//                minDevelopment = region.getDevelopmentLevel();
//                lowestRegion = region;
//            }
//        }
//
//        return lowestRegion;
//    }
}
