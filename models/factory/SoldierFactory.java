package models.factory;

import models.country.Country;
import models.general.IRegion;
import models.general.Region;

public class SoldierFactory implements ISoldierFactory {
    @Override
    public Soldier createSoldier(String type, int amount, IRegion region, Country country) {
        switch (type.toLowerCase()) {
            case "infantry":
                return new Infantry(amount);  // Pass amount to Infantry
            case "cavalry":
                return new Cavalry(amount);   // Pass amount to Cavalry
            default:
                throw new IllegalArgumentException("Unknown soldier type: " + type);
        }
    }
}
