package models.game;

public class GameStatisticsDisplay implements Observer {
    private Game game;

    public GameStatisticsDisplay(Game game) {
        this.game = game;
        this.game.registerObserver(this); // Register itself as an observer
    }

    @Override
    public void update(String message) {
    }

    @Override
    public void update() {
        // Display the updated game statistics
        System.out.println("\nTurn: " + game.getCurrentTurnNumber() + " - " + game.getCurrentCountry().getName());
        System.out.println("\nAI turn for " + game.getCurrentCountry().getName());
    }
}
