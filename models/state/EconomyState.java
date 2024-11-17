package models.state;

import models.country.Country;
import models.country.CountryType;
import models.game.Game;

import java.util.Objects;

class EconomyState implements TurnState {
    @Override
    public void manageTurn(Game context) {
        Country currentCountry = context.getCurrentCountry();
        if (Objects.requireNonNull(currentCountry.getType()) == CountryType.NEUTRAL) {
            System.out.println("\nEconomy phase for " + currentCountry.getName());
            currentCountry.manageEconomy(currentCountry);
        }

    }

    @Override
    public void nextState(Game context) {
        context.setState(new MilitaryState());  // Move to the Military phase
    }
}

