package model.client;

import Socket.Messages.ChatMessage;
import Socket.Messages.PrivateChatMessage;
import exception.NonePlayerFoundException;
import model.Chat;
import model.Player;
import model.PrivateChat;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.enums.GameState;
import model.enums.SuitEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ClientBoard implements Serializable {

    private ArrayList<CardGold> centralCardsGold;
    private ArrayList<CardResource> centralCardsResource;
    private CardObjective firstObjective;
    private CardObjective secondObjective;
    private GameState gameState;
    private String currentPlayer;
    private Player myplayer;
 //   private String currentPlayerName;
    private ArrayList<ReductPlayer> otherplayers;
    private SuitEnum backOfResourceDeck;
    private SuitEnum backOfGoldDeck;
    private Chat globalChat;
    private ArrayList<PrivateChat> privateChatArrayList;

    public ClientBoard(CardObjective firstObjective, CardObjective secondObjective, ArrayList<ReductPlayer> otherplayers, Player myplayer, ArrayList<CardResource> centralCardsResource, ArrayList<CardGold> centralCardsGold, GameState gameState) {
        this.firstObjective = firstObjective;
        this.secondObjective = secondObjective;
        this.otherplayers = otherplayers;
        this.myplayer = myplayer;
        this.centralCardsResource = centralCardsResource;
        this.centralCardsGold = centralCardsGold;
        this.gameState = gameState;
        globalChat = new Chat();
        privateChatArrayList = new ArrayList<>();
    }

    // public ClientBoard(CardObjective firstObjective, CardObjective secondObjective, GameState gameState) {
    //     this.firstObjective = firstObjective;
    //     this.secondObjective = secondObjective;
    // }


    public Chat getGlobalChat() {
        return globalChat;
    }

    public ArrayList<PrivateChatMessage> getPrivateChat(String nickname1, String nickname2) {
        for (PrivateChat p : privateChatArrayList) {
            if ((p.getNickname1().equals(nickname1) && p.getNickname2().equals(nickname2)) || (p.getNickname1().equals(nickname2) && p.getNickname2().equals(nickname1))) {
                return p.getMessage();
            }
        }
        return null;
    }

    public ArrayList<CardGold> getCentralCardsGold() {
        return centralCardsGold;
    }

    public ArrayList<CardResource> getCentralCardsResource() {
        return centralCardsResource;
    }

    public CardObjective getFirstObjective() {
        return firstObjective;
    }

    public Player getMyplayer() {
        return myplayer;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<ReductPlayer> getOtherplayers() {
        return otherplayers;
    }

    public CardObjective getSecondObjective() {
        return secondObjective;
    }

    public GameState getGameState() {
        return gameState;
    }

    public SuitEnum getBackOfResourceDeck() {
        return backOfResourceDeck;
    }

    public SuitEnum getBackOfGoldDeck() {
        return backOfGoldDeck;
    }

    public void setCentralCardsGold(ArrayList<CardGold> centralCardsGold) {
        this.centralCardsGold = centralCardsGold;
    }

    public void setCentralCardsResource(ArrayList<CardResource> centralCardsResource) {
        this.centralCardsResource = centralCardsResource;
    }

    public void setMyplayer(Player myplayer) {
        this.myplayer = myplayer;
    }

    public void setOtherplayers(ArrayList<ReductPlayer> otherplayers) {
        this.otherplayers = otherplayers;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ReductPlayer getOtherPlayer(String nickname) throws NonePlayerFoundException {
        return otherplayers.stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findFirst()
                .orElseThrow(() ->
                        new NonePlayerFoundException("Player not found"));
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setFirstObjective(CardObjective firstObjective) {
        this.firstObjective = firstObjective;
    }

    public void setSecondObjective(CardObjective secondObjective) {
        this.secondObjective = secondObjective;
    }

    public void setBackOfResourceDeck(SuitEnum backOfResourceDeck) {
        this.backOfResourceDeck = backOfResourceDeck;
    }

    public void setBackOfGoldDeck(SuitEnum backOfGoldDeck) {
        this.backOfGoldDeck = backOfGoldDeck;
    }

    public void addNewPrivateChat(String nickname1, String nickname2) {
        privateChatArrayList.add(new PrivateChat(nickname1, nickname2));
    }

    public void updateChat(String chat, String nicknameSender, String nicknameReceiver, String message) {
        if (chat.equals("GLOBAL")) {
            globalChat.addMessage(new ChatMessage("GLOBAL", message, nicknameSender));
        }
        else if (chat.equals("PRIVATE")) {
                privateChatArrayList.stream()
                        .filter(p -> (p.getNickname1().equals(nicknameSender) && p.getNickname2().equals(nicknameReceiver)) || (p.getNickname1().equals(nicknameReceiver) && p.getNickname2().equals(nicknameSender)))
                        .findFirst()
                        .ifPresentOrElse(
                                p -> p.addMessage(new PrivateChatMessage( message,nicknameSender,nicknameReceiver)),
                                () -> System.out.println("Chat not found")
                        );
        }
    }
}
