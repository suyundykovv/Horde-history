package models.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import models.EventTemplate.EconomicBoostEvent;
import models.EventTemplate.GameEvent;
import models.EventTemplate.WarDeclarationEvent;
import models.country.Country;
import models.country.CountryType;
import models.db.GameDataLoader;
import models.db.MongoDBService;
import models.general.Economy;
import models.general.IEconomy;
import models.general.IMilitary;
import models.general.IRegion;
import models.general.Leader;
import models.general.Military;
import models.general.Region;
import models.state.EndTurnState;
import models.state.EventState;
import models.state.TurnState;

public class Game implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private static ArrayList<Country> countries; // List of countries in the game
    private Scanner scanner; // Scanner for user input
    public static Country myCountry;
    private int currentCountryIndex = 0;
    private boolean gameOver = false;
    private int turnNumber = 1;
    private TurnState state;

    public Game() {
        this.countries = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        MongoDBService dbService = new MongoDBService();
        List<Document> countryDocs = dbService.loadCountries();
        List<Document> regionDocs = dbService.loadRegions();
        List<Document> leaderDocs = dbService.loadLeaders();

        this.countries = GameDataLoader.loadGameData(countryDocs, regionDocs, leaderDocs);
        choosePlayerCountry();
        moveMyCountryToFirst();
        this.state = new EventState();  // Start with Diplomacy State
    }

    public void initializeGameEvents() {
        Country russia = getCountryByName("Russian Empire");
        Country zhungars = getCountryByName("Zhungar Khanate");

        // Trigger a war declaration event
        if (turnNumber == 2){
            System.out.println("\nEvent:");
            System.out.println("Russian Empire diplomats was killed by Zhungars army");
            System.out.println("War will come?");
            GameEvent warEvent = new WarDeclarationEvent(russia, zhungars);
            warEvent.triggerEvent();
        }


        // Trigger an economic boost event
        Country middleJuz = getCountryByName("Middle Juz");
        if (turnNumber == 3){
            System.out.println("Middle Juz has a golden era");
            GameEvent economicBoost = new EconomicBoostEvent(middleJuz);
            economicBoost.triggerEvent();
        }

    }
    public void setState(TurnState state) {
        this.state = state;
    }

    public Country getCurrentCountry() {
        return countries.get(currentCountryIndex);
    }

    public boolean isLastCountryInTurn() {
        return currentCountryIndex == countries.size() - 1;
    }

    public void nextTurn() {
        notifyObservers();
        for (int i = 0; i < 4; i++) {
            state.manageTurn(this);
            state.nextState(this);
        }
        state.manageTurn(this);
        if (state instanceof EndTurnState) {
            calculateEconomicStrength();
            startNewTurn();  // Reset for the new turn
        }
    }





    public void startNewTurn() {
        turnNumber++;
        currentCountryIndex = 0;  // Reset to the first country
        state = new EventState();  // Reset state to the first phase
    }

    public void moveToNextCountry() {
        currentCountryIndex++;
        if (currentCountryIndex < countries.size()) {
            state = new EventState();  // Start with Diplomacy for the next country
        }
    }

    //    public void updateResources() {
//        for (Country country : countries) {
//            country.getEconomy().calculateIncome();
//            country.addRecruits();
//        }
//    }
    private void displayCountries() {
        for (int i = 0; i < countries.size(); i++) {
            System.out.println(i + 1 + ". " + countries.get(i).getName());
        }
    }
    public void choosePlayerCountry() {
        System.out.println("Choose a country to play:");

        // Display available countries to the player
        for (int i = 0; i < countries.size(); i++) {
            System.out.println((i + 1) + ". " + countries.get(i).getName());
        }

        // Get player's choice
        int choice = scanner.nextInt();
        myCountry = countries.get(choice - 1); // Assign the chosen country to myCountry

        // Set the chosen country type to neutral, indicating it's controlled by the player
        myCountry.setType(CountryType.NEUTRAL);

        System.out.println("You have chosen " + myCountry.getName() + " as your country.");
    }
    public static Country chooseTargetCountry() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available countries for diplomatic interaction:");

        // Exclude the chosen country from the diplomatic target list
        ArrayList<Country> availableCountries = new ArrayList<>(countries);
        availableCountries.remove(myCountry);

        for (int i = 0; i < availableCountries.size(); i++) {
            System.out.println((i + 1) + ". " + availableCountries.get(i).getName());
        }

        int choice = scanner.nextInt();
        return availableCountries.get(choice - 1); // Return the chosen country for diplomacy
    }
    private void moveMyCountryToFirst() {
        // Remove the chosen country from its current position
        countries.remove(myCountry);

        // Add it back at the first position
        countries.add(0, myCountry);
    }
    public void endGame() {
        System.out.println("\nGame Over.");
        System.out.println("Final statistics:");

        // Display stats for each country (like regions controlled, economy, military, etc.)
        for (Country country : countries) {
            System.out.println("------------------------------");
            System.out.println("Country: " + country.getName());
            System.out.println("Regions controlled: " + country.getRegions().size());
        }

        // Optionally, you could calculate and display the winner (if there is one)
        Country winner = determineWinner();
        if (winner != null) {
            System.out.println("\nThe winner of the game is: " + winner.getName() + "!");
        } else {
            System.out.println("\nNo clear winner.");
        }

        System.out.println("\nThank you for playing!");
        gameOver = true;  // Mark the game as over
    }

    public boolean isGameOver() {
        return gameOver;
    }

    // Example method to determine the winner (could be based on regions, economy, etc.)
    private Country determineWinner() {
        Country winner = null;
        int maxRegions = 0;

        for (Country country : countries) {
            if (country.getRegions().size() > maxRegions) {
                maxRegions = country.getRegions().size();
                winner = country;
            }
        }

        return winner;
    }

    public List<Country> getAllCountries() {
        return countries;
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public int getCurrentTurnNumber() {
        return turnNumber;
    }

    public void calculateEconomicStrength() {
        Visitor economicVisitor = new EconomicStrengthVisitor();
        for (Country country : countries) {
            country.accept(economicVisitor);
        }
//       System.out.println("Total economic strength: " + ((EconomicStrengthVisitor) economicVisitor).getTotalStrength());
    }
    public Country getCountryByName(String name) {
        // Search through the list of countries for a match
        for (Country country : countries) {
            if (country.getName().equalsIgnoreCase(name)) {
                return country;
            }
        }
        // If no country is found, return null or throw an exception
        return null;
    }


}