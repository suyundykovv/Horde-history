package models.state;


import models.country.Country;
import models.country.CountryType;
import models.game.Game;

import java.util.Objects;

public class DiplomacyState implements TurnState {
    @Override
    public void manageTurn(Game context) {
        Country currentCountry = context.getCurrentCountry();
        if (Objects.requireNonNull(currentCountry.getType()) == CountryType.NEUTRAL) {
            Country targetCountry = Game.chooseTargetCountry();
            System.out.println("\nDiplomacy phase for " + currentCountry.getName());
            currentCountry.manageDiplomacy(currentCountry, targetCountry);
        }


    }

    @Override
    public void nextState(Game context) {
        context.setState(new EconomyState());  // Move to the Economy phase
    }
}
