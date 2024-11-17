package models.factory;

import models.country.Country;
import models.general.IRegion;

public abstract class Soldier {
    protected String type;
    protected int strength;
    protected int cost;
    protected IRegion currentRegion;
    protected Country country;

    public String getType() {
        return type;
    }

    public int getStrength() {
        return strength;
    }

    public int getCost() {
        return cost;
    }

    public abstract void fight();

    // Getter for current region
    public IRegion getCurrentRegion() {
        return currentRegion;
    }

    // Method to set the soldier's region
    public void setRegion(IRegion newRegion) {
        if (newRegion != null) {
            // Optionally: Logic to handle soldier presence in the new region
            // For example, you might want to check if the soldier can be placed there

            // Update the soldier's current region
            this.currentRegion = newRegion;
            System.out.println("Soldier moved to region: " + newRegion.getName());
        } else {
            System.out.println("Invalid region. Soldier remains in the current region.");
        }
    }
}

