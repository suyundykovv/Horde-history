package models.state;

import models.game.Game;

public class EndTurnState implements TurnState {
    @Override
    public void manageTurn(Game context) {
        System.out.println("\nEnd of turn: Updating resources and resolving battles.");
//        context.updateResources();
//        context.resolveBattles();
    }

    @Override
    public void nextState(Game context) {
        context.startNewTurn();  // Move to the next turn and reset state to Diplomacy
    }
}

