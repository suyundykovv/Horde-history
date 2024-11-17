package models.state;

import models.game.Game;


public class EventState implements TurnState {
    @Override
    public void manageTurn(Game context) {
        context.initializeGameEvents();
    }

    @Override
    public void nextState(Game context) {
        context.setState(new DiplomacyState());  // Move to the Economy phase
    }
}

