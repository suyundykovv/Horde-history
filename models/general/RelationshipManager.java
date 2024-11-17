package models.general;

import java.util.HashMap;
import java.util.Map;

import models.country.Country;

public class RelationshipManager implements IRelationship {
    private static RelationshipManager instance; // Singleton instance
    private Map<Country, RelationshipStatus> relationships;
    private Map<Country, Integer> opinions;

    private RelationshipManager() {
        relationships = new HashMap<>();
        opinions = new HashMap<>();
    }

    public static RelationshipManager getInstance() {
        if (instance == null) {
            instance = new RelationshipManager();
        }
        return instance;
    }

    public void setRelationship(Country targetCountry, RelationshipStatus status) {
        relationships.put(targetCountry, status);
    }

    public RelationshipStatus getRelationship(Country targetCountry) {
        return relationships.getOrDefault(targetCountry, RelationshipStatus.NEUTRAL);
    }

    public void setOpinion(Country targetCountry, int opinion) {
        opinions.put(targetCountry, opinion);
    }

    public void modifyOpinion(Country targetCountry, int delta) {
        int newOpinion = opinions.getOrDefault(targetCountry, 0) + delta;
        opinions.put(targetCountry, newOpinion);
    }

    public int getOpinion(Country targetCountry) {
        return opinions.getOrDefault(targetCountry, 0);
    }
}
