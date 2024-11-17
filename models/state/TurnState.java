package models.state;

import models.game.Game;

public interface TurnState {
    void manageTurn(Game context);
    void nextState(Game context);
}

