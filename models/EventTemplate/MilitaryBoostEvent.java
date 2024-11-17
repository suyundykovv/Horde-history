package models.EventTemplate;

import models.country.Country;
import models.general.IRegion;
import models.general.Region;

import java.util.List;

import static models.game.Game.myCountry;

public class MilitaryBoostEvent extends GameEvent {
    private Country country;


    public MilitaryBoostEvent(Country country) {
        this.country = country;
    }
    @Override
    protected void executeEvent() {
        List<IRegion> regions = myCountry.getRegions();
        IRegion selectedRegion = regions.get(1);
        System.out.println(country.getName() + " receives an Military boost.");
        String type = "cavalry";
        selectedRegion.addSoldiers(type,200, false);
        // Boost the economy
    }
}
