package se2.alpha.riskapp.model.dol;

public class Troop {
    private TroopType type;
    private Country location;
    private Player owner;

    public Troop(TroopType type, Country location, Player owner) {
        this.type = type;
        this.location = location;
        this.owner = owner;
    }

    public Troop() {
    }

    public TroopType getType() {
        return type;
    }

    public void setType(TroopType type) {
        this.type = type;
    }

    public Country getLocation() {
        return location;
    }

    public void setLocation(Country location) {
        this.location = location;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
