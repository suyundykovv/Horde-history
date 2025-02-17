package models.db;

import org.bson.Document;
import models.country.Country;
import models.country.CountryType;
import models.general.Economy;
import models.general.IEconomy;
import models.general.IMilitary;
import models.general.ILeader;
import models.general.IRegion;
import models.general.Leader;
import models.general.Military;
import models.general.Region;

import java.util.ArrayList;
import java.util.List;

public class GameDataLoader {
    public static ArrayList<Country> loadGameData(List<Document> countryDocs, List<Document> regionDocs, List<Document> leaderDocs) {
        ArrayList<Country> countries = new ArrayList<>();

        for (Document doc : countryDocs) {
            String name = doc.getString("name");
            CountryType type = CountryType.valueOf(doc.getString("type").toUpperCase()); // Convert string to enum

            // Load leader
            ILeader leader = loadLeader(doc.getString("name"), leaderDocs);

            // Load regions belonging to this country
            List<IRegion> regions = loadRegions(name, regionDocs);

            // Load economy with initial money, regions, and leader
            int initialMoney = doc.getInteger("initialMoney", 0); // Default to 0 if null
            IEconomy economy = new Economy(initialMoney, regions, leader);

            // Load military
            int militaryPoints = doc.getInteger("military", 0); // Default to 0 if null
            IMilitary military = new Military(militaryPoints);

            // Create and add the country
            Country country = new Country(name, leader, economy, military, regions, type);
            countries.add(country);
        }

        return countries;
    }

    private static ILeader loadLeader(String countryName, List<Document> leaderDocs) {
        for (Document leaderDoc : leaderDocs) {
            if (leaderDoc.getString("countryName").equals(countryName)) {
                return new Leader.Builder()
                        .setName(leaderDoc.getString("name"))
                        .setMilitarySkill(leaderDoc.getInteger("military"))
                        .setEconomySkill(leaderDoc.getInteger("economy"))
                        .setDiplomacySkill(leaderDoc.getInteger("diplomacy"))
                        .build();
            }
        }
        System.out.println("Error: Leader not found for " + countryName);
        return null; // Or handle appropriately
    }
    

    private static List<IRegion> loadRegions(String countryName, List<Document> regionDocs) {
        List<IRegion> regions = new ArrayList<>();
        for (Document regionDoc : regionDocs) {
            if (regionDoc.getString("country").equals(countryName)) {
                regions.add(new Region(regionDoc.getString("name"), regionDoc.getInteger("development", 1),regionDoc.getString("name")));
            }
        }
        return regions;
    }
    
}
