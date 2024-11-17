package models.game;

import models.country.Country;
import models.general.Economy;
import models.general.Military;
import models.game.Visitor;

public class EconomicStrengthVisitor implements Visitor {
    private int Strength = 0;

    @Override
    public void visit(Country country) {
        System.out.println("Visiting country: " + country.getName());
    }

    @Override
    public void visit(Economy economy) {
        Strength = economy.getTotalWealth();
        System.out.println("Current economy strength: " + Strength);
    }

    @Override
    public void visit(Military military) {
    }

    public int getTotalStrength() {
        return Strength;
    }
}

