package models.general;

import java.util.Map;

import models.country.Country;

public interface IMilitary {
    void recruitSoldiers(Country targetCountry, int numberOfRecruits);
    int getAvailableRecruits();
    int getSoldiers();
    void setMilitaryPoints(int militaryPoints);
    Map<String, Integer> getSoldierCount();
    void spendRecruits(int amount) ;// Returns a map of soldier type and count

    boolean canRecruitSoldiers();
    void addRecruits();
}
