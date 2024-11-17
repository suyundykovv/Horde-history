package models.factory;

public class Infantry extends Soldier {
    public Infantry(int amount) {
        this.type = "Infantry";
        this.strength = 10;
        this.cost = 1;
    }

    @Override
    public void fight() {
        System.out.println( " Infantry soldiers fighting with total strength: " + getStrength());
    }
}
