package models.state;

import java.util.List;
import java.util.Objects;

import models.country.Country;
import models.country.CountryType;
import models.game.Game;

class MilitaryState implements TurnState {
    @Override
    public void manageTurn(Game context) {
        Country currentCountry = context.getCurrentCountry();
        List<Country> allCountries = context.getAllCountries();
        if (Objects.requireNonNull(currentCountry.getType()) == CountryType.NEUTRAL) {
            System.out.println("\nMilitary phase for " + currentCountry.getName());
            currentCountry.manageMilitary(currentCountry, allCountries);
        }
        currentCountry.getMilitary().addRecruits();
    }

    @Override
    public void nextState(Game context) {
        context.setState(new AIState());  // Move to the Military phase
    }
}



