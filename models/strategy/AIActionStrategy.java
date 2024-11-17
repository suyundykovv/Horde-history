package models.strategy;

import models.country.Country;
import models.game.Game;

public interface AIActionStrategy{
    void executeAction(Country country, Game game);
}
