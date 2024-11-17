package models.general;

public class Leader implements ILeader {
    private String name;
    private int militarySkill;
    private int economySkill;
    private int diplomacySkill;

    // Private constructor
    private Leader(Builder builder) {
        this.name = builder.name;
        this.militarySkill = builder.militarySkill;
        this.economySkill = builder.economySkill;
        this.diplomacySkill = builder.diplomacySkill;
    }

    public static class Builder {
        private String name;
        private int militarySkill;
        private int economySkill;
        private int diplomacySkill;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setMilitarySkill(int militarySkill) {
            this.militarySkill = militarySkill;
            return this;
        }

        public Builder setEconomySkill(int economySkill) {
            this.economySkill = economySkill;
            return this;
        }

        public Builder setDiplomacySkill(int diplomacySkill) {
            this.diplomacySkill = diplomacySkill;
            return this;
        }

        public Leader build() {
            return new Leader(this);
        }
    }

    // Implementing the methods from the interface
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMilitarySkill() {
        return militarySkill;
    }

    @Override
    public int getEconomySkill() {
        return economySkill;
    }

    @Override
    public int getDiplomacySkill() {
        return diplomacySkill;
    }
}
