package models.general;

import models.country.Country;

public class Diplomacy implements IDiplomacy {
    private int diplomacyPoints;
    private IRelationship relationship;
    private IEconomy economy; // Reference to the Economy class
    private Country country;

    public Diplomacy(int diplomacyPoints, IEconomy economy) {
        this.diplomacyPoints = diplomacyPoints;
        this.relationship = relationship; // Default opinion value
        this.economy = economy;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    public int getDiplomacyPoints(Country myCountry) {
        // Total diplomacy points = base diplomacy points + leader's diplomacy skill
        int finalDiplomacyPoints = diplomacyPoints + myCountry.getLeader().getDiplomacySkill();
        System.out.println("Diplomacy points for " + myCountry.getName() + ": " + diplomacyPoints);
        return finalDiplomacyPoints;
    }
    public Country getCountry(Country targetCountry) {
        return targetCountry;
    }
    public int getOpinion(Country myCountry, Country targetCountry) {
        IRelationship myRelationshipManager = myCountry.getRelationshipManager();
        // Get the opinion of myCountry towards targetCountry
        int opinion = myRelationshipManager.getOpinion(targetCountry);
        System.out.println("Opinion of " + myCountry.getName() + " about " + targetCountry.getName() + ": " + opinion);
        return opinion;
    }
    public boolean canFormAlliance(Country myCountry, Country targetCountry) {
        IRelationship myRelationshipManager = myCountry.getRelationshipManager();
        int opinion = myRelationshipManager.getOpinion(targetCountry);
        return diplomacyPoints >= 2 && opinion > 0;
    }

    public boolean canFormNonAggressionPact(Country myCountry, Country targetCountry) {
        IRelationship myRelationshipManager = myCountry.getRelationshipManager();
        // Get the opinion of myCountry towards targetCountry
        int opinion = myRelationshipManager.getOpinion(targetCountry);
        return diplomacyPoints >= 1 && opinion > 0;
    }
    public boolean canEndWar(Country myCountry, Country targetCountry) {
        RelationshipStatus relationshipStatus = myCountry.getRelationshipManager().getRelationship(targetCountry);
        int opinion = myCountry.getRelationshipManager().getOpinion(targetCountry);

        // Check if they are at war and if the opinion is higher than -20
        if (relationshipStatus == RelationshipStatus.AT_WAR && opinion > -20) {
            return true;
        } else {
            System.out.println("Countries cannot end the war. Status: " + relationshipStatus + ", Opinion: " + opinion);
            return false;
        }
    }
    public boolean canDeclareWar(Country myCountry, Country targetCountry) {
        RelationshipStatus relationshipStatus = myCountry.getRelationshipManager().getRelationship(targetCountry);
        int opinion = myCountry.getRelationshipManager().getOpinion(targetCountry);
        if (relationshipStatus == RelationshipStatus.NEUTRAL && opinion < -20) {
            return true;
        } else {
            System.out.println("Countries cannot declare war. Status: " + relationshipStatus + ", Opinion: " + opinion);
            return false;
        }
    }
    public boolean canMakeAction(Country myCountry, Country thisCountry) {
        RelationshipStatus relationshipStatus = myCountry.getRelationshipManager().getRelationship(thisCountry);
        if (relationshipStatus == RelationshipStatus.NEUTRAL) {
            return true;
        } else {
            System.out.println("Countries cannot make action. Status: " + relationshipStatus);
            return false;
        }
    }
    public void formAlliance(Country myCountry, Country targetCountry) {
        if (canFormAlliance(myCountry, targetCountry) && canMakeAction(targetCountry, myCountry)) {
            diplomacyPoints -= 2;
            IRelationship myRelationshipManager = myCountry.getRelationshipManager();
            myRelationshipManager.modifyOpinion(targetCountry, +50);
            System.out.println("Alliance formed with " + targetCountry.getName() + "!");
            // Set relationship status
            myCountry.getRelationshipManager().setRelationship(this.getCountry(targetCountry), RelationshipStatus.ALLIED);
            this.getCountry(targetCountry).getRelationshipManager().setRelationship(targetCountry, RelationshipStatus.ALLIED);
        } else {
            System.out.println("Not enough diplomacy points or opinion is too low to form an alliance.");
        }
    }

    public void formNonAggressionPact(Country myCountry, Country targetCountry) {
        if (canFormNonAggressionPact(myCountry, targetCountry) && canMakeAction(targetCountry, myCountry)) {
            diplomacyPoints -= 1;
            IRelationship myRelationshipManager = myCountry.getRelationshipManager();
            myRelationshipManager.modifyOpinion(targetCountry, +25);
            System.out.println("Non-aggression pact formed with " + targetCountry.getName() + "!");
            // Set relationship status
            myCountry.getRelationshipManager().setRelationship(this.getCountry(targetCountry), RelationshipStatus.PACT);
            this.getCountry(targetCountry).getRelationshipManager().setRelationship(targetCountry, RelationshipStatus.PACT);
        } else {
            System.out.println("Not enough diplomacy points or opinion is too low to form a non-aggression pact.");
        }
    }

    public void sendGift(Country myCountry, Country targetCountry, int amount) {
        // Check if there is enough money to send the gift
        if (economy.subtractMoney(amount)) {
            int opinionIncrease = amount / 10; // Each 10 ducats increases opinion by 1
            IRelationship myRelationshipManager = myCountry.getRelationshipManager();
            myRelationshipManager.modifyOpinion(targetCountry, +opinionIncrease);
            System.out.println("Gift sent to " + targetCountry.getName() + "! Opinion increased by " + opinionIncrease);
        } else {
            System.out.println("Not enough money to send a gift!");
        }
    }

    public void humiliate(Country myCountry,Country targetCountry, int amount) {
        // Check if there is enough money to humiliate the target
        if (economy.subtractMoney(amount)) {
            int opinionDecrease = amount / 10; // Decrease opinion by 1 for each 10 ducats of insult
            IRelationship myRelationshipManager = myCountry.getRelationshipManager();
            myRelationshipManager.modifyOpinion(targetCountry, -opinionDecrease);
            System.out.println("You humiliated " + targetCountry.getName() + "! Opinion decreased by " + opinionDecrease);
        } else {
            System.out.println("Not enough money to humiliate " + targetCountry.getName() + "!");
        }
    }
    public void breakPact(Country myCountry,Country targetCountry) {
        IRelationship myRelationshipManager = myCountry.getRelationshipManager();
        myRelationshipManager.modifyOpinion(targetCountry, -25);
        myCountry.getRelationshipManager().setRelationship(this.getCountry( targetCountry), RelationshipStatus.NEUTRAL);
        this.getCountry( targetCountry).getRelationshipManager().setRelationship(targetCountry, RelationshipStatus.NEUTRAL);
        diplomacyPoints += 2; // Return diplomacy points upon breaking an alliance
        System.out.println("Pact broken with " + targetCountry.getName() + ".");
    }
    public void breakAlliance(Country myCountry,Country targetCountry) {
        IRelationship myRelationshipManager = myCountry.getRelationshipManager();
        myRelationshipManager.modifyOpinion(targetCountry, -50);
        myCountry.getRelationshipManager().setRelationship(this.getCountry( targetCountry), RelationshipStatus.NEUTRAL);
        this.getCountry( targetCountry).getRelationshipManager().setRelationship(targetCountry, RelationshipStatus.NEUTRAL);
        diplomacyPoints += 2; // Return diplomacy points upon breaking an alliance
        System.out.println("Alliance broken with " + targetCountry.getName() + ".");
    }

    public void declareWar(Country myCountry,Country targetCountry) {
        if (canDeclareWar(myCountry, targetCountry)) {
            IRelationship myRelationshipManager = myCountry.getRelationshipManager();
            diplomacyPoints -= 2;
            myRelationshipManager.modifyOpinion(targetCountry, -60);
            myCountry.getRelationshipManager().setRelationship(this.getCountry(targetCountry), RelationshipStatus.AT_WAR);
            this.getCountry(targetCountry).getRelationshipManager().setRelationship(targetCountry, RelationshipStatus.AT_WAR);
            System.out.println("Declared war on " + targetCountry.getName() + "!");
        }else{
            System.out.println("Not enough money to end war!");
        }

    }
    public void EndWar(Country myCountry,Country targetCountry) {
        if (canEndWar(myCountry, targetCountry)) {
            IRelationship myRelationshipManager = myCountry.getRelationshipManager();
            diplomacyPoints += 2;
            myRelationshipManager.modifyOpinion(targetCountry, +40);
            myCountry.getRelationshipManager().setRelationship(this.getCountry(targetCountry), RelationshipStatus.NEUTRAL);
            this.getCountry(targetCountry).getRelationshipManager().setRelationship(targetCountry, RelationshipStatus.NEUTRAL);
            System.out.println("End war with " + targetCountry.getName() + "!");
        } else{
            System.out.println("You can not declare War in Country!");
        }

    }
    public void ShowInfo(Country targetCountry){
        System.out.println("Name of Country: " + targetCountry.getName() + "!");
        System.out.println("Name of Ledear: " + targetCountry.getLeader().getName() + "!");
        System.out.println("Number of Ducats: " + targetCountry.getEconomy().getMoney(targetCountry) + "!");
        System.out.println("Number of soldier: " + targetCountry.getMilitary().getSoldiers() + "!");
        System.out.println("Number of recruits: " + targetCountry.getMilitary().getAvailableRecruits() + "!");

    }

}
