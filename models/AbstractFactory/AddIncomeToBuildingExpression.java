package models.AbstractFactory;

public class AddIncomeToBuildingExpression implements Expression {
    private IBuilding building;

    public AddIncomeToBuildingExpression(IBuilding building) {
        this.building = building;
    }

    @Override
    public int interpret() {
        int income = building.getIncome();
        building.addMoneytoBuilding(income);
        return income;
    }
}