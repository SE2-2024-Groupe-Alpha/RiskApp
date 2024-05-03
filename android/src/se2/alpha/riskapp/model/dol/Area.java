package se2.alpha.riskapp.model.dol;

public abstract class Area {
    protected String name;
    protected Player owner;

    public Area(String name, Player owner) {
        this.name = name;
        this.owner = owner;
    }

    public Area() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
