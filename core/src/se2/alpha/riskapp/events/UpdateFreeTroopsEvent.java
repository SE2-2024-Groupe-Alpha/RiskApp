package se2.alpha.riskapp.events;

public class UpdateFreeTroopsEvent {
    private final int freeTroops;
    public UpdateFreeTroopsEvent(int freeTroops){
        this.freeTroops = freeTroops;
    }

    public int getFreeTroops() {
        return freeTroops;
    }
}
