package models.general;

import models.country.Country;

public interface IRegion {
    String getName();
    int getDevelopmentLevel();
    void upgradeDevelopmentLevel();
    void addSoldiers(String soldierType, int count, boolean isTemporaryController);
    boolean isNeutral(Country myCountry, IRegion targetReginion);
    boolean isEnemy(Country attacker, Country  defender);
    boolean isOwnedByAlly(Country attacker, Country  defender);
    void removeSoldiers(String soldierType, int count, boolean isTemporaryController);
    int getRegionSoldier(String soldierType, boolean isTemporaryController);
    boolean moveSoldier(String soldierType,IRegion sourceRegion, IRegion targetRegion, Country attacker, Country defender);
    void setOwner(Country country);
    boolean reduceSoldiers(String soldierType, int count, boolean isTemporaryController);
    boolean conquerRegion(Country attacker, IRegion targetRegion, Country defender, String soldierType);
    int getSoldierCount(String soldierType, boolean isTemporaryController);
    void clearTemporaryController();
    void setTemporaryController(Country controller);
}
