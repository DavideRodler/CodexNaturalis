package model;

import java.io.Serializable;

public class ReducedBoard implements Serializable {
    private Player player;
    private PlayingStation station;

    public ReducedBoard(Player player, PlayingStation station) {
        this.player = player;
        this.station = station;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayingStation getStation() {
        return station;
    }
}