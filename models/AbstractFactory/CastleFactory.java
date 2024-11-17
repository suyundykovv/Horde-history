package models.AbstractFactory;

public class CastleFactory implements BuildingFactory {
    @Override
    public IBuilding createBuilding() {
        return new Castle(1000);
    }
}

