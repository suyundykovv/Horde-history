package models.game;

import models.country.Country;
import models.general.Economy;
import models.general.Military;

public interface Visitor {
    void visit(Country country);
    void visit(Economy economy);
    void visit(Military military);
}


