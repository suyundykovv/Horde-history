package models.general;

import models.country.Country;

public interface IRelationship {
    void setRelationship(Country targetCountry, RelationshipStatus status);
    RelationshipStatus getRelationship(Country targetCountry);
    void setOpinion(Country targetCountry, int opinion);
    void modifyOpinion(Country targetCountry, int delta);
    int getOpinion(Country targetCountry);
}