package models.AbstractFactory;

import models.AbstractFactory.BuildingFactory;
import models.AbstractFactory.IBuilding;
import models.AbstractFactory.Market;


public class MarketFactory implements BuildingFactory {
    @Override
    public IBuilding createBuilding() {
        return new Market(1000);
    }
}
