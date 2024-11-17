package models.AbstractFactory;

public class Castle implements IBuilding {
    private int money;
    private int income;

    public Castle(int money) {
        this.money = money;
        this.income = (int) (money * 0.25);  // 25% от денег — доход
    }

    @Override
    public void addMoneytoBuilding(int amount) {
        money += amount;
        System.out.println("Castle: Added " + amount + " money. Total: " + money);
    }

    @Override
    public int getIncome(){
        return income;
    }

    public void updateIncome() {
        income = (int) (money * 0.25); // Пересчитываем доход как 25% от текущего количества денег
    }
}
