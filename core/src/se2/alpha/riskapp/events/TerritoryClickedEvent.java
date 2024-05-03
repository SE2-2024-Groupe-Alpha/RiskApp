package se2.alpha.riskapp.events;

public class TerritoryClickedEvent {
    String name;

    public TerritoryClickedEvent(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
