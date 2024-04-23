package Network.Client;

import Network.Server.VirtualServer;
import model.cards.CardPlaying;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientModel{

    VirtualServer server;

    private HashMap<ArrayList<Integer>, CardPlaying> table;
    private ArrayList<CardPlaying> hand;

    public ClientModel() {
        table = new HashMap<>();
        hand = new ArrayList<>();
    }


    public void addCardToTable(CardPlaying card, Integer X, Integer Y) {
        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(X);
        coordinates.add(Y);
        table.put(coordinates, card);
    }

    public void addCardToHand(CardPlaying card) {
        hand.add(card);
    }



}
