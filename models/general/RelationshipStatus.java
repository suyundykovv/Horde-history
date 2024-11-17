package models.general; // or use enums if you have that package

public enum RelationshipStatus {
    NEUTRAL,     // Default state, no active diplomacy
    ALLIED,      // Allied with the country
    AT_WAR,      // At war with the country
    PACT         // Non-aggression pact
}