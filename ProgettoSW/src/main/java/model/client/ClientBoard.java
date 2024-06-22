package model.client;

import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
import model.GlobalChat;
import model.Player;
import model.PrivateChat;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.enums.GameState;
import model.enums.SuitEnum;
import model.enums.TokenEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ClientBoard implements Serializable {

    private ArrayList<CardGold> centralCardsGold;
    private ArrayList<CardResource> centralCardsResource;
    private CardObjective firstObjective;
    private CardObjective secondObjective;
    private GameState gameState;
    private Player myplayer;
    private ArrayList<ReductPlayer> otherplayers;
    private SuitEnum backOfResourceDeck;
    private SuitEnum backOfGoldDeck;
    private GlobalChat globalChat;
    private ArrayList<PrivateChat> privateChats;

    public ClientBoard(CardObjective firstObjective, CardObjective secondObjective, ArrayList<ReductPlayer> otherplayers, Player myplayer, ArrayList<CardResource> centralCardsResource, ArrayList<CardGold> centralCardsGold, GameState gameState) {
        this.firstObjective = firstObjective;
        this.secondObjective = secondObjective;
        this.otherplayers = otherplayers;
        this.myplayer = myplayer;
        this.centralCardsResource = centralCardsResource;
        this.centralCardsGold = centralCardsGold;
        this.gameState = gameState;
    }

    // public ClientBoard(CardObjective firstObjective, CardObjective secondObjective, GameState gameState) {
    //     this.firstObjective = firstObjective;
    //     this.secondObjective = secondObjective;
    // }

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

    public ReductPlayer getOtherPlayer(String nickname) {
        return otherplayers.stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);
    }

    //TODO: metodo giusto ma inutile perche' il messaggio dei giocatori arriva dopo
    public ArrayList<TokenEnum> getAvailableTokens() {
        ArrayList<TokenEnum> availableTokens = new ArrayList<>();
        for (TokenEnum token : TokenEnum.values()) {
            if (otherplayers.stream().noneMatch(p -> p.getToken() == token)) {
                availableTokens.add(token);
            }
        }
        return availableTokens;
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

    public void updatePrivateChat(String typeOfChat, String nicknameSender, String nicknameReceiver, String privateChatMessage) {

        if (typeOfChat.equals("PRIVATE")) {

            privateChats.stream()
                    .filter(p -> (p.getNickname1().equals(nicknameSender) && p.getNickname2().equals(nicknameReceiver)) || (p.getNickname1().equals(nicknameReceiver) && p.getNickname2().equals(nicknameSender)))
                    .findFirst()
                    .ifPresentOrElse(
                            p -> p.addMessage(new PrivateChatMessage( privateChatMessage,nicknameSender,nicknameReceiver)),
                            () -> System.out.println("Chat not found")
                    );

        }
    }

        public void updateGlobalChat (String typeOfChat, String nickname, String globalChatMessage){
            if (typeOfChat.equals("GLOBAL")) {
                globalChat.addMessage(new GlobalChatMessage("GLOBAL", globalChatMessage, nickname));
            }
        }

        public void addNewPrivateChat (String nickname1, String nickname2){

            privateChats.add(new PrivateChat(nickname1, nickname2));
        }


    public GlobalChat getGlobalChat() {
        return globalChat;
    }

    public ArrayList<PrivateChatMessage> getPrivateChat(String nickname, String nickname1) {
        return privateChats.stream()
                .filter(p -> (p.getNickname1().equals(nickname) && p.getNickname2().equals(nickname1)) || (p.getNickname1().equals(nickname1) && p.getNickname2().equals(nickname)))
                .findFirst()
                .map(PrivateChat::getMessage)
                .orElse(null);
    }


}