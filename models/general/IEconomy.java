package models.general;

import models.country.Country;
import models.game.Visitor;

public interface IEconomy {
    void calculateIncome();
    boolean subtractMoney(int amount);
    void addMoney(int amount);
    int getMoney(Country targetCountry) ;
    void spentMoney(int amount);

    void accept(Visitor visitor);
}