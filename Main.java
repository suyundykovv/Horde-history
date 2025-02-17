import models.game.Game;
import models.game.GameStatisticsDisplay;

public class Main {
    public static void main(String[] args) {
        Game context = new Game();
        GameStatisticsDisplay display = new GameStatisticsDisplay(context); // Observer

        while (!context.isGameOver()) {
            context.nextTurn();
            if (context.isLastCountryInTurn()) {
                context.startNewTurn();
            } else {
                context.moveToNextCountry();
            }
        }
        System.out.println("Game Over!");
    }
}
