package models.AbstractFactory;

import models.AbstractFactory.BuildingFactory;
import models.AbstractFactory.Farm;
import models.AbstractFactory.IBuilding;

public class FarmFactory implements BuildingFactory {
    @Override
    public IBuilding createBuilding() {
        return new Farm(1000);
    }
}

