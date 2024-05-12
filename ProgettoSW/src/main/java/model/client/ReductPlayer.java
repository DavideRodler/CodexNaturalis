package model.client;

import model.PlayingStation;
import model.enums.SuitEnum;
import model.enums.TokenEnum;

import java.util.ArrayList;

public class ReductPlayer {
    private final String nickname;
    private int points;
    private final TokenEnum token;
    private PlayingStation station;
    private ArrayList<SuitEnum> hand;


    public ReductPlayer(int points, ArrayList<SuitEnum> hand, String nickname, PlayingStation station, TokenEnum token) {
        this.points = points;
        this.hand = hand;
        this.nickname = nickname;
        this.station = station;
        this.token = token;
    }

    public ReductPlayer(String nickname, TokenEnum token) {
        this.nickname = nickname;
        this.token = token;
    }

    public ArrayList<SuitEnum> getHand() {
        return hand;
    }

    public String getNickname() {
        return nickname;
    }

    public int getPoints() {
        return points;
    }

    public PlayingStation getStation() {
        return station;
    }

    public TokenEnum getToken() {
        return token;
    }

    public void setHand(ArrayList<SuitEnum> hand) {
        this.hand = hand;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setStation(PlayingStation station) {
        this.station = station;
    }
}
