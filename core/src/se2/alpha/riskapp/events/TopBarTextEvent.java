package se2.alpha.riskapp.events;

public class TopBarTextEvent {
    private String text;

    public TopBarTextEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
