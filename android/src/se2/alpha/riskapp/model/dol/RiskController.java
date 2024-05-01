package se2.alpha.riskapp.model.logic;

import java.util.ArrayList;

public class RiskController {
    private ArrayList<Player> players;
    private Board board;

    public RiskController(ArrayList<Player> players, Board board) {
        this.players = players;
        this.board = board;
    }

    public RiskController() {
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
