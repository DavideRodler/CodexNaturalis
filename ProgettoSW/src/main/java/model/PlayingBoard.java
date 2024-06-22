package model;

import Socket.Messages.Chat.AddPrivateChatMessage;
import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
import model.cards.*;
import model.enums.GameState;
import model.enums.SuitEnum;
import model.enums.TokenEnum;
import observers.ObservableModel;
import Socket.Messages.*;
//import socket.Messages.CommonObjectivesMessage;
//import socket.Messages.PlayersInfoMessage;

import java.rmi.RemoteException;
import java.util.*;

public class PlayingBoard extends ObservableModel {

    private LinkedList<CardGold> deckCardGold;
    private LinkedList<CardResource> deckCardResource;
    private LinkedList<CardObjective> deckCardObjective;
    private LinkedList<CardStarting> deckCardStarting;
    private ArrayList<CardGold> centralCardsGold;
    private ArrayList<CardResource> centralCardsResource;
    private CardObjective firstObjective;
    private CardObjective secondObjective;
    private SuitEnum firstCardOfResourceDeck;
    private SuitEnum firstCardOfGoldDeck;
    private GlobalChat globalChat;
    private ArrayList<PrivateChat> privateChat;


    //the current player playing
    private String currentPlayer;
    //saving the number of player
    private int playernumber;
    //the player map that for each nickname has a player
    private ArrayList<Player> players;
    //gameState
    private GameState gameState;

    public PlayingBoard(CardObjective firstObjective, CardObjective secondObjective, int playernumber, ArrayList<Player> playerList, LinkedList<CardStarting> deckCardStarting, LinkedList<CardResource> deckCardResource, LinkedList<CardObjective> deckCardObjective, LinkedList<CardGold> deckCardGold, String currentPlayer, ArrayList<CardResource> centralCardsResource, ArrayList<CardGold> centralCardsGold, GameState gameState) {
        this.firstObjective = firstObjective;
        this.secondObjective = secondObjective;
        this.playernumber = playernumber;
        this.players = playerList;
        this.deckCardStarting = deckCardStarting;
        this.deckCardResource = deckCardResource;
        this.deckCardObjective = deckCardObjective;
        this.deckCardGold = deckCardGold;
        this.currentPlayer = currentPlayer;
        this.centralCardsResource = centralCardsResource;
        this.centralCardsGold = centralCardsGold;
        this.gameState = gameState;

        this.globalChat = new GlobalChat();
        this.privateChat = new ArrayList<>();
    }

    public PlayingBoard() {
        this.playernumber = 0;
        this.players = new ArrayList<>();
        this.deckCardStarting = new LinkedList<>();
        this.deckCardResource = new LinkedList<>();
        this.deckCardObjective = new LinkedList<>();
        this.deckCardGold = new LinkedList<>();
        this.centralCardsResource = new ArrayList<>();
        this.centralCardsGold = new ArrayList<>();
    }


    //-------------------GETTER-----------------------------
    public CardObjective getFirstObjective() {
        return firstObjective;
    }

    public CardObjective getSecondObjective() {
        return secondObjective;
    }

    public int getPlayernumber() {
        return playernumber;
    }

    public ArrayList<CardGold> getCentralCardsGold() {
        return centralCardsGold;
    }

    public ArrayList<CardResource> getCentralCardsResource() {
        return centralCardsResource;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public LinkedList<CardObjective> getDeckCardObjective() {
        return deckCardObjective;
    }
    public LinkedList<CardStarting> getDeckCardStarting() {
        return deckCardStarting;
    }

    public CardGold getCardFromGoldDeck() {
        CardGold card = deckCardGold.pop();
        try {
            notifyObservers(new BackOfFirstCardOfDeckMessage(deckCardGold.getFirst().getSymbol(),true));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return card;
    }
    public CardResource getCardFromResourceDeck() {
        CardResource card = deckCardResource.pop();
        try {
            notifyObservers(new BackOfFirstCardOfDeckMessage(deckCardResource.getFirst().getSymbol(),false));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return card;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public GameState getGameState() {
        return gameState;
    }

    //--------------------SETTER----------------------------


    public void addPlayer(Player p) {
        players.add(p);
    }

    public void setPlayernumber(int playernumber) {
        this.playernumber = playernumber;
    }

    public void setCentralCardsGold(ArrayList<CardGold> centralCardsGold) {
        this.centralCardsGold = centralCardsGold;
    }

    public void setCentralCardsResource(ArrayList<CardResource> centralCardsResource) {
        this.centralCardsResource = centralCardsResource;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
        try {
            notifyObservers(new CurrentPlayerMessage(currentPlayer));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDeckCardObjective(LinkedList<CardObjective> deckCardObjective) {
        this.deckCardObjective = deckCardObjective;
    }

    public void setDeckCardResource(LinkedList<CardResource> deckCardResource) {
        this.deckCardResource = deckCardResource;
    }

    public void setDeckCardStarting(LinkedList<CardStarting> deckCardStarting) {
        this.deckCardStarting = deckCardStarting;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        try {
            notifyObservers(new ChangeStateMessage("ChangeState", gameState));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    public void setCommonObjectives( CardObjective firstObjective, CardObjective secondObjective) {
        this.firstObjective = firstObjective;
        this.secondObjective = secondObjective;
        try {
            notifyObservers(new CommonObjectivesMessage(firstObjective,secondObjective));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
//--------------------SETTING FASE ENDED----------------------------


    public Player getPlayer(String nickname) throws IllegalStateException {
        return players.stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Player " + nickname + " not found"));
    }
    private int getPlayerPositon(String nickname){
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getNickname().equals(nickname)){
                return i;
            }
        }
        return -1;
    }


    public CardResource getCardResource(int id) {
        for(var c : centralCardsResource){
            if(c.getId() == id){
                return c;
            }
        }
        return null;
    }

    public CardGold getCardGold(int id) {
        for(var c : centralCardsGold){
            if(c.getId() == id){
                return c;
            }
        }
        return null;
    }

    public void addCardResource(CardResource card) {
        centralCardsResource.add(card);
        try {
            notifyObservers(new CentralCardResourceMessage(card));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }



    public void addCardGold(CardGold card) {
        centralCardsGold.add(card);
        try {
            notifyObservers(new CentralCardGoldMessage(card));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeCentralCard(int id) {
        centralCardsResource.removeIf(c -> c.getId() == id);
        centralCardsGold.removeIf(c -> c.getId() == id);
        try {
            notifyObservers(new CentralCardResourceRemovedMessage(id));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    // shuffle players
    public void shufflePlayer() {
        Collections.shuffle(players);
        ArrayList<String> playerslist = new ArrayList<>();
        for (Player p : players) {
            playerslist.add(p.getNickname());
        }

        try {
            notifyObservers(new PlayersInfoMessage(playerslist));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public String getnextPlayer(){
        int indexOfCurrentPlayer = players.indexOf(getPlayer(currentPlayer));
        if (indexOfCurrentPlayer == players.size()-1){
            return players.get(0).getNickname();
        }
        else {
            return players.get(indexOfCurrentPlayer+1).getNickname();
        }
    }

    public void addNewPrivateChat(String nickname1, String nickname2) {
        try{
            getPrivateChat(nickname1, nickname2);
        }catch(IllegalStateException e){
            privateChat.add(new PrivateChat(nickname1, nickname2));
            try {
                notifySpecificObserver(nickname1, new AddPrivateChatMessage(nickname1, nickname2));
                notifySpecificObserver(nickname2, new AddPrivateChatMessage(nickname1, nickname2));
            } catch (RemoteException  f) {
                throw new RuntimeException(f);
            }
        }
    }

    public PrivateChat getPrivateChat(String nickname1, String nickname2) {
        return privateChat.stream()
                .filter(p -> (p.getNickname1().equals(nickname1) && p.getNickname2().equals(nickname2)) || (p.getNickname1().equals(nickname2) && p.getNickname2().equals(nickname1)))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Chat not found"));
    }

    public void addMessageToGlobalChat(String nickname, String message) {
        globalChat.addMessage(new GlobalChatMessage("GLOBAL", message, nickname));
        try {
            notifyObservers(new GlobalChatMessage("GLOBAL", message, nickname));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMessageToPrivateChat(String nickname, String nickname1, String message2) {
        try {
            getPrivateChat(nickname, nickname1).addMessage(new PrivateChatMessage(message2, nickname, nickname1));
            notifySpecificObserver(nickname, new PrivateChatMessage(message2, nickname, nickname1));
            notifySpecificObserver(nickname1, new PrivateChatMessage(message2, nickname, nickname1));
        } catch (IllegalStateException | RemoteException e) {
            throw new RuntimeException(e);
        }
    }


}
