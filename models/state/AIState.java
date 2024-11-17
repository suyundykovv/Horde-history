package models.state;

import models.country.Country;
import models.game.Game;
import models.strategy.AIController;
import models.strategy.AggressiveStrategy;
import models.strategy.DiplomaticStrategy;
import models.strategy.EconomicStrategy;

class AIState implements TurnState {
    @Override
    public void manageTurn(Game context) {
        Country currentCountry = context.getCurrentCountry();

        // AI logic for choosing actions based on strategy
        switch (currentCountry.getType()) {
            case AGGRESSIVE:
                AIController.setStrategy(new AggressiveStrategy());
                break;
            case DIPLOMATIC:
                AIController.setStrategy(new DiplomaticStrategy());
                break;
            case ECONOMIC:
                AIController.setStrategy(new EconomicStrategy());
                break;
            default:
                System.out.println(currentCountry.getName() + " is neutral or player-controlled.");
                return;
        }
        // Execute AI strategy
        AIController.performAction(currentCountry,context);  // The country reacts based on its assigned strategy

        // Example: AI takes actions based on its strategy
        // The strategy (aggressive, diplomatic, economic) will decide what the AI does
        // This could be managing military, economy, or diplomacy
    }

    @Override
    public void nextState(Game context) {
        if (context.isLastCountryInTurn()) {
            context.setState(new EndTurnState());  // Move to End Turn state
        } else {
            context.moveToNextCountry();  // Move to the next country
            context.setState(new EventState());  // Start with Diplomacy for the next country
        }
    }
}
