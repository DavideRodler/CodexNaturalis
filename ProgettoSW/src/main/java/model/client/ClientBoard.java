package model.client;

import Messages.Chat.GlobalChatMessage;
import Messages.Chat.PrivateChatMessage;
import model.chats.GlobalChat;
import model.Player;
import model.chats.PrivateChat;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.enums.GameState;
import model.enums.SuitEnum;
import model.enums.TokenEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the model of a client
 * has all the information that the client needs to know
 * It has the central cards, the objectives, the players, the current player, the game state, the back of the decks and the chats
 * It also has the player of the client
 * it has a list of reduct players that are the other players
 */
public class ClientBoard implements Serializable {


    private ArrayList<CardGold> centralCardsGold;
    private ArrayList<CardResource> centralCardsResource;
    private CardObjective firstObjective;
    private CardObjective secondObjective;
    private GameState gameState;
    private String currentPlayer;
    private Player myplayer;
    private ArrayList<ReductPlayer> otherplayers;
    private SuitEnum backOfResourceDeck;
    private SuitEnum backOfGoldDeck;
    private GlobalChat globalChat;
    private final ArrayList<PrivateChat> privateChats;

    public ClientBoard(CardObjective firstObjective, CardObjective secondObjective, ArrayList<ReductPlayer> otherplayers, Player myplayer, ArrayList<CardResource> centralCardsResource, ArrayList<CardGold> centralCardsGold, GameState gameState) {
        this.firstObjective = firstObjective;
        this.secondObjective = secondObjective;
        this.otherplayers = otherplayers;
        this.myplayer = myplayer;
        this.centralCardsResource = centralCardsResource;
        this.centralCardsGold = centralCardsGold;
        this.gameState = gameState;
        this.privateChats = new ArrayList<>();
        this.globalChat = new GlobalChat();
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




    /**
     * Get the player with the given nickname or null if the player is not found
     * @param nickname the nickname of the player
     * @return the player with the given nickname or null if the player is not found
     */
    public ReductPlayer getOtherPlayer(String nickname) {
        return otherplayers.stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);
    }



    /**
     * Get the available tokens for the player
     * @return the available tokens for the player
     */
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




    /**
     * Update a specific private chat between two players
     * @param typeOfChat the type of chat
     * @param nicknameSender the nickname of the sender
     * @param nicknameReceiver the nickname of the receiver
     * @param privateChatMessage the message of the private chat
     */
    public void updatePrivateChat(String typeOfChat, String nicknameSender, String nicknameReceiver, String privateChatMessage) {
        synchronized (privateChats) {
            if (typeOfChat.equals("PRIVATE")) {
                for (PrivateChat p : privateChats) {
                    if ((p.getNickname1().equals(nicknameSender) && p.getNickname2().equals(nicknameReceiver))) {
                        p.addMessage(new PrivateChatMessage(privateChatMessage, nicknameSender, nicknameReceiver));
                        return;
                    } else if ((p.getNickname1().equals(nicknameReceiver) && p.getNickname2().equals(nicknameSender))) {
                        p.addMessage(new PrivateChatMessage(privateChatMessage, nicknameSender, nicknameReceiver));
                        return;
                    }
                }

            }
        }
    }




    /**
     * Update the global chat
     * @param typeOfChat the type of chat
     * @param nickname the nickname of the player
     * @param globalChatMessage     the message of the global chat
     */
    public void updateGlobalChat (String typeOfChat, String nickname, String globalChatMessage){
        if (typeOfChat.equals("GLOBAL")) {
            globalChat.addMessage(new GlobalChatMessage("GLOBAL", globalChatMessage, nickname));
        }
    }




    /**
     * Add a new private chat between two players
     * @param nickname1 the nickname of the first player
     * @param nickname2 the nickname of the second player
     */
    public void addNewPrivateChat (String nickname1, String nickname2){
            privateChats.add(new PrivateChat(nickname1, nickname2));
        }


    public GlobalChat getGlobalChat() {
        return globalChat;
    }




    /**
     * Get a private chat between two players
     * @param nickname the nickname of the first player
     * @param nickname1 the nickname of the second player
     * @return the private chat between two players
     */
    public ArrayList<PrivateChatMessage> getPrivateChat(String nickname, String nickname1) {
        return privateChats.stream()
                .filter(p -> (p.getNickname1().equals(nickname) && p.getNickname2().equals(nickname1)) || (p.getNickname1().equals(nickname1) && p.getNickname2().equals(nickname)))
                .findFirst()
                .map(PrivateChat::getMessage)
                .orElse(null);
    }


    public List<PrivateChat> getPrivateChats() {
        return this.privateChats;
    }

    public void setGlobalChat(GlobalChat globalChat) {
        this.globalChat = globalChat;
    }
}