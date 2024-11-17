package models.EventTemplate;

import models.country.Country;

public class WarDeclarationEvent extends GameEvent {
    private Country attacker;
    private Country defender;

    public WarDeclarationEvent(Country attacker, Country defender) {
        this.attacker = attacker;
        this.defender = defender;
    }

    @Override
    protected void executeEvent() {
        System.out.println(attacker.getName() + " declares war on " + defender.getName());
        System.out.println(attacker.getName() + " receives an Military boost.");
        attacker.getDiplomacy().humiliate(attacker,defender,350);
        attacker.getDiplomacy().declareWar(attacker, defender);
//        String type = "cavalry";
//        attacker.getMilitary().recruitSoldier(type,100); // Attacker gains military boost
    }
}
