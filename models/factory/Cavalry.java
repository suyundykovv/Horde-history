package models.factory;

public class Cavalry extends Soldier {
    public Cavalry(int amount) {
        this.type = "Cavalry";
        this.strength = 20;
        this.cost = 2;
    }

    @Override
    public void fight() {
        System.out.println( " Cavalry soldiers charging with total strength: " + getStrength());
    }
}
