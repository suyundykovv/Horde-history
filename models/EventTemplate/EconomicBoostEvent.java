package models.EventTemplate;

import models.country.Country;

public class EconomicBoostEvent extends GameEvent {
    private Country country;

    public EconomicBoostEvent(Country country) {
        this.country = country;
    }

    @Override
    protected void executeEvent() {
        System.out.println(country.getName() + " receives an economic boost.");
        country.getEconomy().addMoney(200);  // Boost the economy
    }
}
