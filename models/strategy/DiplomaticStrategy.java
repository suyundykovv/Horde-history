package models.strategy;

import models.country.Country;
import models.game.Game;

public class DiplomaticStrategy implements  AIActionStrategy {
    @Override
    public void executeAction(Country country, Game game) {
        System.out.println( "\n" + country.getName() + " is focusing on diplomacy with all countries.");
        country.getEconomy().calculateIncome();
        country.getMilitary().addRecruits();
        // 1. Check diplomatic relations with all countries in the game
        for (Country otherCountry : game.getAllCountries()) {
            if (otherCountry == country) continue; // Skip the AI's own country

            int opinion = country.getDiplomacy().getOpinion(country,otherCountry); // Check opinion of the other country

            // 2. Improve relations or form an alliance if beneficial
            if (opinion < 50) {
                if (country.getDiplomacy().canFormAlliance(country,otherCountry)) {
                    country.getDiplomacy().formAlliance(country,otherCountry); // Form an alliance
                    System.out.println(country.getName() + " forms an alliance with " + otherCountry.getName() + ".");
                } else {
                    country.getDiplomacy().sendGift(country,otherCountry,50); // Improve relations by sending a gift
                    System.out.println(country.getName() + " sends a gift to " + otherCountry.getName() + " to improve relations.");
                }
            }
        }

        // 3. Avoid war, focus on diplomacy and maintaining peace
        System.out.println(country.getName() + " avoids conflict and strengthens diplomatic ties.");
    }
}
