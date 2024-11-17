package models.country;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import models.AbstractFactory.*;
import models.general.*;
import models.game.Visitor;


public class Country {
    private String name;
    private IEconomy economy;
    private IMilitary military;
    private CountryType type;
    private IRelationship relationshipManager;
    private IDiplomacy diplomacy;
    private ILeader leader;
    private List<IRegion> regions;
    private Scanner scanner;

    BuildingFactory castleFactory = new CastleFactory();
    IBuilding castle = castleFactory.createBuilding();
    BuildingFactory farmFactory = new FarmFactory();
    IBuilding farm = farmFactory.createBuilding();
    BuildingFactory marketFactory = new MarketFactory();
    IBuilding market = marketFactory.createBuilding();
    Expression addFarmIncome = new AddIncomeToBuildingExpression(farm);
    Expression addMarketIncome = new AddIncomeToBuildingExpression(market);
    Expression addCastleIncome = new AddIncomeToBuildingExpression(castle);

    // Constructor is now private to enforce Factory Method usage
    private Country(String name, ILeader leader, IEconomy economy, IMilitary military, List<IRegion> regions,CountryType type) {
        this.name = name;
        this.leader = leader;
        this.economy = economy;
        this.type = type;
        this.military = military;
        this.relationshipManager = RelationshipManager.getInstance(); // Use Singleton
        this.diplomacy = new Diplomacy(3, economy);
        this.regions = regions;
        this.scanner = new Scanner(System.in);

    }

    // Factory Method to create Country instances
    public static Country createCountry(String name, ILeader leader, IEconomy economy, IMilitary military, List<IRegion> regions,CountryType type) {
        return new Country(name, leader, economy, military, regions,type);
    }

    public String getName() {
        return name;
    }

    public ILeader getLeader() {
        return leader;
    }

    public IEconomy getEconomy() {
        return economy;
    }

    public IMilitary getMilitary() {
        return military;
    }

    public IRelationship getRelationshipManager() {
        return relationshipManager;
    }

    public IDiplomacy getDiplomacy() {
        return diplomacy;
    }

    public List<IRegion> getRegions() {
        return regions;
    }
    public void manageDiplomacy(Country myCountry, Country targetCountry) {
        System.out.println("\nDiplomatic Actions with: " + targetCountry.getName());
        System.out.println("Diplomacy Points: " + myCountry.getDiplomacy().getDiplomacyPoints(myCountry));
        System.out.println("Opinion of " + targetCountry.getName() + ": " + myCountry.getRelationshipManager().getOpinion(targetCountry));
        System.out.println("Status of " + targetCountry.getName() + ": " + myCountry.getRelationshipManager().getRelationship(targetCountry));
        System.out.println("\nManage Diplomacy of a Country:");
        System.out.println("1. Form Alliance");
        System.out.println("2. Form Non-Aggression Pact");
        System.out.println("3. Send Gift");
        System.out.println("4. Humilate Country");
        System.out.println("5. Break Alliance");
        System.out.println("6. Declaining WAR");
        System.out.println("7. End War");
        System.out.println("8. Break Pact");
        System.out.println("9. End War");
        System.out.println("0. Back to Menu");
        int actionChoice = scanner.nextInt();

        boolean running = true;
        while (running) {
            if (actionChoice == 1) {
                myCountry.getDiplomacy().formAlliance(myCountry,targetCountry); // Form alliance
                break;
            } else if (actionChoice == 2) {
                myCountry.getDiplomacy().formNonAggressionPact(myCountry,targetCountry);// Form pact
                break;
            } else if (actionChoice == 3) {
                System.out.println("How many money spent?");
                int actionGift = scanner.nextInt();
                myCountry.getDiplomacy().sendGift(myCountry,targetCountry,actionGift);
                break;
            }else if (actionChoice == 4) {
                System.out.println("How many money spent?");
                int actionHumilate = scanner.nextInt();
                myCountry.getDiplomacy().humiliate(myCountry,targetCountry,actionHumilate);
                break;
            }else if (actionChoice == 5) {
                myCountry.getDiplomacy().breakAlliance(myCountry,targetCountry);
                break;
            }else if (actionChoice == 6) {
                myCountry.getDiplomacy().declareWar(myCountry,targetCountry);
                break;
            }else if (actionChoice == 7) {
                myCountry.getDiplomacy().EndWar(myCountry,targetCountry);
                break;
            }else if (actionChoice == 8) {
                myCountry.getDiplomacy().breakPact(myCountry,targetCountry);
                break;
            }else if (actionChoice == 9) {
                myCountry.getDiplomacy().ShowInfo(targetCountry);
                break;
            }else{
                running = false;
                System.out.println("Invalid choice. Please try again.");
            }
        }


    }
    public void manageMilitary(Country myCountry, List<Country> allCountries) {
        IMilitary military = myCountry.getMilitary();
        List<IRegion> regions = myCountry.getRegions();

        System.out.println("Manage Military of " + myCountry.getName() + ":");

        // Display current military stats
        System.out.println("Total Soldiers: " + military.getSoldierCount());
        System.out.println("Available Recruits: " + military.getAvailableRecruits());

        // Display soldier counts
        Map<String, Integer> soldierCounts = military.getSoldierCount();
        System.out.println("Soldier Counts:");
        for (Map.Entry<String, Integer> entry : soldierCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Ask how many recruits to add
        System.out.print("Enter the amount of soldiers to recruit: ");
        Scanner scanner = new Scanner(System.in);
        int soldierAmount = scanner.nextInt();

        if (soldierAmount <= military.getAvailableRecruits()) {
            System.out.println("Select the region where you want to recruit soldiers:");
            for (int i = 0; i < regions.size(); i++) {
                System.out.println((i + 1) + ". " + regions.get(i).getName() + " (Development Level: " + regions.get(i).getDevelopmentLevel() + ")");
            }

            int regionIndex = -1;
            boolean validRegion = false;

            while (!validRegion) {
                try {
                    regionIndex = scanner.nextInt();
                    if (regionIndex > 0 && regionIndex <= regions.size()) {
                        validRegion = true;
                    } else {
                        System.out.println("Invalid region selection. Please enter a number between 1 and " + regions.size() + ".");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.next(); // Clear invalid input
                }
            }

            IRegion selectedRegion = regions.get(regionIndex - 1);
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Select type of soldier to recruit (infantry, cavalry): ");
                String soldierType = scanner.next();

                // Check if the input matches the expected soldier types
                if (soldierType.equalsIgnoreCase("infantry") || soldierType.equalsIgnoreCase("cavalry")) {
                    selectedRegion.addSoldiers(soldierType, soldierAmount, false);
                    validInput = true; // Valid input, break the loop
                } else {
                    System.out.println("Invalid input. Please enter 'infantry' or 'cavalry'.");
                }
            }

            military.spendRecruits(soldierAmount);

            // Display updated stats
            System.out.println("Updated Military Stats:");
            System.out.println("Total Soldiers: " + military.getSoldierCount());
            System.out.println("Available Recruits: " + military.getAvailableRecruits());

            // Display updated soldier counts
            soldierCounts = military.getSoldierCount();
            System.out.println("Updated Soldier Counts:");
            for (Map.Entry<String, Integer> entry : soldierCounts.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            // Ask the player if they want to move soldiers
            System.out.print("Do you want to move soldiers? (yes/no): ");
            String moveChoice = scanner.next();

            if (moveChoice.equalsIgnoreCase("yes")) {
                String soldierTypeToMove = "";
                System.out.println("Select the type of soldier to move (infantry, cavalry): ");
                soldierTypeToMove = scanner.next();
                System.out.println("Regions with soldiers:");
                for (int i = 0; i < regions.size(); i++) {
                    IRegion region = regions.get(i);
                    if (region.getRegionSoldier(soldierTypeToMove,false) > 0) {
                        System.out.println((i + 1) + ". " + region.getName() + " - Soldiers: " + region.getRegionSoldier(soldierTypeToMove,false));
                    }
                }

                // Select the region to move soldiers from
                System.out.print("Select the region from which to move soldiers: ");
                regionIndex = -1;
                validRegion = false;
                while (!validRegion) {
                    try {
                        regionIndex = scanner.nextInt();
                        if (regionIndex > 0 && regionIndex <= regions.size() && regions.get(regionIndex - 1).getRegionSoldier(soldierTypeToMove,false) > 0) {
                            validRegion = true;
                        } else {
                            System.out.println("Invalid region selection or no soldiers in the selected region.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next(); // Clear invalid input
                    }
                }

                IRegion sourceRegion = regions.get(regionIndex - 1);

                // Select the target country
                System.out.println("Select the target country to move soldiers to:");
                for (int i = 0; i < allCountries.size(); i++) {

                        System.out.println((i + 1) + ". " + allCountries.get(i).getName());

                }

                int countryIndex = -0;
                boolean validCountry = false;
                while (!validCountry) {
                    try {
                        countryIndex = scanner.nextInt();
                        if (countryIndex > 0 && countryIndex <= allCountries.size() && !allCountries.get(countryIndex - 1).equals(myCountry)) {
                            validCountry = true;
                        } else {
                            System.out.println("Invalid country selection.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next(); // Clear invalid input
                    }
                }

                Country targetCountry = allCountries.get(countryIndex - 1);

                // Select the target region within the chosen country
                System.out.println("Select the target region to move soldiers to within " + targetCountry.getName() + ":");
                List<IRegion> targetRegions = targetCountry.getRegions();
                for (int i = 0; i < targetRegions.size(); i++) {
                    System.out.println((i + 1) + ". " + targetRegions.get(i).getName() + " (Development Level: " + targetRegions.get(i).getDevelopmentLevel() + ")");
                }

                regionIndex = -1;
                validRegion = false;
                while (!validRegion) {
                    try {
                        regionIndex = scanner.nextInt();
                        if (regionIndex > 0 && regionIndex <= targetRegions.size()) {
                            validRegion = true;
                        } else {
                            System.out.println("Invalid region selection. Please enter a number between 1 and " + targetRegions.size() + ".");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next(); // Clear invalid input
                    }
                }

                IRegion targetRegion = targetRegions.get(regionIndex - 1);

                // Move soldiers
                sourceRegion.moveSoldier(soldierTypeToMove, sourceRegion, targetRegion,myCountry,targetCountry);

                // Display confirmation
                System.out.println("Soldiers moved from " + sourceRegion.getName() + " to " + targetRegion.getName() + " in " + targetCountry.getName());
            }
        } else {
            System.out.println("Not enough recruits available.");
        }
    }


    public void manageEconomy(Country myCountry) {
        int castleResult = addCastleIncome.interpret();
        int marketResult = addMarketIncome.interpret();
        int farmResult = addFarmIncome.interpret();

        Scanner scanner = new Scanner(System.in);
        // Adding money to the market
        System.out.println("Manage Economy of a Country:");
        myCountry.getEconomy().calculateIncome(); // Calculating income
        System.out.println("Current Money: " + myCountry.getEconomy().getMoney(myCountry) + " ducats");

        System.out.println("\nManage Buildings of a Country:");
        System.out.println("1. Adding money to the building");
        System.out.println("2. Take Income from buildings");
        System.out.println("3. Exit");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Enter amount to add:");
                int amountToAdd = scanner.nextInt();
                myCountry.getEconomy().subtractMoney(amountToAdd / 3);
                castle.addMoneytoBuilding(amountToAdd / 3);
                market.addMoneytoBuilding(amountToAdd / 3);
                farm.addMoneytoBuilding(amountToAdd / 3);

            case 2:
                myCountry.getEconomy().addMoney(castle.getIncome()+market.getIncome()+farm.getIncome());
            case 3:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice, please try again.");
                break;


        }

        System.out.println("Current Money: " + myCountry.getEconomy().getMoney(myCountry) + " ducats");
    }
    public void accept(Visitor visitor) {
        visitor.visit(this);
        economy.accept(visitor);
//      military.accept(visitor);
    }

    public CountryType getType() {
        return type;
    }

    public void setType(CountryType type) {
        this.type = type;
    }

    public boolean isEnemy(CountryType type) {
        if (this.type == type) {
            return true;
        }
        return false;
    }



    public void removeRegion(IRegion region) {
        // Check if the region belongs to the country
        if (getRegions().contains(region)) {
            getRegions().remove(region);
            System.out.println("Region " + region.getName() + " has been removed from " + getName() + ".");
        } else {
            System.out.println("The region " + region.getName() + " does not belong to " + getName() + ".");
        }
    }

    public void addRegion(IRegion region) {
        // Check if the region is not already part of the country's regions
        if (!getRegions().contains(region)) {
            getRegions().add(region);
            System.out.println("Region " + region.getName() + " has been added to " + getName() + ".");
            // Update the region's owner to this country
            region.setOwner(this);
        } else {
            System.out.println("The region " + region.getName() + " is already controlled by " + getName() + ".");
        }
    }





}
