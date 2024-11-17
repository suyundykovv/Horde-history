package models.factory;

import models.country.Country;
import models.general.IRegion;
import models.general.Region;

public interface ISoldierFactory {
    Soldier createSoldier(String type, int Amount, IRegion region, Country country);

}
