package controller;

import Network.Client.ClientController;
import model.Player;
import model.PlayingBoard;
import model.PlayingStation;
import model.cards.*;
import model.deck.Decktemplates;
import model.enums.TokenEnum;
import model.enums.GameState;
import exception.*;


import java.io.Serializable;
import java.util.*;
import java.util.List;

public class GameController extends ClientController implements Serializable {
    private PlayingBoard board;
    private GameState gameState;

    //costructor
    public GameController(PlayingBoard board, GameState gameState) {
        this.board = board;
        this.gameState = gameState;
    }


    /**
     * this method is used to generate the board of a game after that we can add players to the board
     */
    public void initGameController() {
        // creating decks
        LinkedList<CardStarting> deckStarting = Decktemplates.StartingCardDeck(); //ID : 0 -> 5
        LinkedList<CardObjective> deckObjective = Decktemplates.ObjectiveCardDeck(); //ID : 6 -> 21
        LinkedList<CardResource> deckResource = Decktemplates.ResourceCardDeck(); //ID : 22 -> 61
        LinkedList<CardGold> deckGold = Decktemplates.GoldCardDeck(); //ID : 62 -> 101


        //shuffleing the decks
        Collections.shuffle(deckGold);
        Collections.shuffle(deckResource);
        Collections.shuffle(deckStarting);
        Collections.shuffle(deckObjective);

        // popping objective
        CardObjective firstCardObj = deckObjective.pop();
        CardObjective secondCardObj = deckObjective.pop();

        //creating the board
        this.board = new PlayingBoard(firstCardObj, secondCardObj, 0, new ArrayList<Player>(), deckStarting, deckResource, deckObjective, deckGold, null, new ArrayList<CardResource>(), new ArrayList<CardGold>());

        board.getCentralCardsGold().add(board.getDeckCardGold().pop());
        board.getCentralCardsGold().add(board.getDeckCardGold().pop());

        board.getCentralCardsResource().add(board.getDeckCardResource().pop());
        board.getCentralCardsResource().add(board.getDeckCardResource().pop());

        gameState = GameState.SET_PLAYER_NUMBER;
    }

    public void setPlayerNumber(int playernumber) throws NotValidMoveException {
        //check right time of the game
        if(playernumber<1) throw new NotValidMoveException("player must be at least two");
        if(playernumber>4) throw new NotValidMoveException("player must be at least four");
        assertGameState(GameState.SET_PLAYER_NUMBER);
        board.setPlayernumber(playernumber);
        gameState = GameState.SET_NAME_AND_TOKEN;
    }

    public int getPlayerNumber() throws NotValidMoveException {
        return board.getPlayernumber();
    }

    /**
     * @return the list of token that are not already been choosen
     * @throws NotValidMoveException if i am in another state of the game
     */
    public List<TokenEnum> getAvailableToken() throws NotValidMoveException {
        return Arrays.stream(TokenEnum.values())
                .filter(t -> board.getPlayers()
                        .stream()
                        .noneMatch(p -> p.getToken().equals(t)))
                .toList();
    }

    public boolean checkTokenAvailability(TokenEnum token) throws NotValidMoveException {
        return getAvailableToken().contains(token);
    }


    /**
     * @param nickname that i want to set
     * @return if the name has already been choosen
     * @throws NotValidMoveException if i am in another state of the game
     */
    public boolean checkNicknameAvailability(String nickname) throws NotValidMoveException {
        return board.getPlayers()
                .stream()
                .filter(p -> p.getNickname().equals(nickname))
                .toList()
                .isEmpty();
    }

    /**
     * it add a player to the game and its token
     *
     * @param nickname
     * @param token
     * @throws NotValidMoveException if i am in another state of the game
     */
    public void addPlayer(String nickname, TokenEnum token) throws NotValidMoveException {
        assertGameState(GameState.SET_NAME_AND_TOKEN);
        if(board.getPlayernumber() < board.getPlayers().size()) throw new NotValidMoveException("numero di player massimo raggiunto");
        if(!checkNicknameAvailability(nickname)) throw new NotValidMoveException("nickname already been choosen");
        if(!checkTokenAvailability(token)) throw new NotValidMoveException("token already been choosen");
        this.board.addPlayer(new Player(nickname, token, new PlayingStation(null, new HashMap<>()), 0, new ArrayList<>()));
        if(board.getPlayers().size() == board.getPlayernumber()){
            shufflePlayerAndPopulateHands();
        }
    }

    /**
     * it shuffle the player and set the game state to the selection of the Starting card face and the Objectives
     *
     * @throws NotValidMoveException
     */
    public void shufflePlayerAndPopulateHands() throws NotValidMoveException {
        assertGameState(GameState.SET_NAME_AND_TOKEN);
        board.shufflePlayer();
        board.getPlayers().stream().forEach(player -> {
            player.getHand().add(board.getDeckCardResource().pop());
            player.getHand().add(board.getDeckCardResource().pop());
            player.getHand().add(board.getDeckCardGold().pop());
        });
        gameState = GameState.SELECT_STARTINGCARDFACE_AND_OBJECTIVE;
        setupOfStartingCardAndPersonalObjectives();
    }


    /**
     * It is used after setStartingCardForPlayers() for selecting the face of the StartingCard
     *
     * @param playedback
     * @param nickname
     * @throws NotValidMoveException
     */
    public void setCentralCardPlayedBack(boolean playedback, String nickname) throws NotValidMoveException {
        assertGameState(GameState.SELECT_STARTINGCARDFACE_AND_OBJECTIVE);
        Player player = board.getPlayers().stream()
                .filter(x -> x.getNickname().equals(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("player " + nickname + " not found"));
    }


    /**
     * The getter for displaying the starting card
     *
     * @param nickname
     * @return
     */
    public CardStarting getStartingCard(String nickname) throws NotValidMoveException {
        assertGameState(GameState.SELECT_STARTINGCARDFACE_AND_OBJECTIVE);
        return (CardStarting) board.getPlayer(nickname).getStation().getMap().get(creatingCordinatesArray(40,40));
    }

    /**
     * Set the personal objectives and the starting card for each player
     * @throws NotValidMoveException
     */
    public void setupOfStartingCardAndPersonalObjectives() throws NotValidMoveException {
        //giving each player his two personal objectives
        assertGameState(GameState.SELECT_STARTINGCARDFACE_AND_OBJECTIVE);
        ArrayList<CardObjective> cardObjectives = new ArrayList<>();
        board.getPlayers().forEach(p -> {
            ArrayList<CardObjective> secretObjectivesToChoose = new ArrayList<CardObjective>();
            secretObjectivesToChoose.add(board.getDeckCardObjective().pop());
            secretObjectivesToChoose.add(board.getDeckCardObjective().pop());
            p.setSelectibleObjectives(secretObjectivesToChoose);
        });
        //now i give for each player its Starting card
        for (Player player: board.getPlayers()){
            player.getStation().setCardStarting(board.getDeckCardStarting().pop());
        };
    }
    public ArrayList<CardObjective> getObjectiveToChoose(String nickname) {
        return board.getPlayer(nickname).getSelectibleObjectives();
    }

    /**
     * Sets the PlayerObjective
     * @param nickname
     * @throws NotValidMoveException
     */
    public void setObjectiveOfPlayer(String nickname,int id) throws NotValidMoveException {

        //check if i am in the right state
        assertGameState(GameState.SELECT_STARTINGCARDFACE_AND_OBJECTIVE);
        Player p = board.getPlayer(nickname);

        //i check if the objective is selectible
        if (p.getSelectibleObjectives().stream()
                .map(card -> card.getId())
                .filter(idcardobjectivetochoose -> idcardobjectivetochoose.equals(id))
                .findFirst()
                .isEmpty()) throw  new NotValidMoveException("the objective is not selectible");
        ArrayList<CardObjective> selectibleObjectives = p.getSelectibleObjectives();
        p.getStation().setSecretObjective(selectibleObjectives.stream()
                .filter(card -> card.getId().equals(id))
                .findFirst()
                .orElseThrow());

        //if all player has setted the objective i can start the game
        if( board.getPlayers().stream()
                .map(player -> player.getStation())
                .filter(station -> station.getSecretObjective() == null)
                .findFirst()
                .isEmpty())
         startGame();
    }

    /**
     * We check if all the selection has been made so we can start the game,
     * this methods checks if we are in the right state of the game and it set the current Player as the
     * first player in the player list
     * then it adds to heach hand of each player 2 card resource and a cardGold
     *
     * @throws NotValidMoveException
     */
    public void startGame() throws NotValidMoveException {
        assertGameState(GameState.SELECT_STARTINGCARDFACE_AND_OBJECTIVE);
        for (Player player : board.getPlayers()){
            if (player.getStation().getSecretObjective().equals(null)) throw new NotValidMoveException("player has not selected the objective");
        }
        gameState = GameState.IN_GAME;
        board.setCurrentPlayer(board.getPlayers().getFirst().getNickname());
        board.getPlayers().forEach(player -> {
        });
    }

    /**
     * this method returns the hand of a player
     * @param nickname of the player we want to see the hand
     * @return
     * @throws NotValidMoveException
     */
    public ArrayList<CardResource> getPlayerHand(String nickname) throws NotValidMoveException {
        return board.getPlayer(nickname).getHand();
    }

    public void addCardToPlayerHand(String nickname, int cardId) throws NotValidMoveException, NotMyTurnException {
        assertIsMyTurn(nickname);
        assertGameState(GameState.IN_GAME);
        CardResource card = board.getCardResource(cardId)
                .orElse(board.getCardGold(cardId)
                        .orElseThrow(() -> new IllegalStateException("Central card " + cardId + " not found")));
        Player player = board.getPlayer(nickname);
        if(player.getNumberOfCardInHand() == 2) {
            player.addCardToHand(card);
        }
        else throw new NotValidMoveException("you can't have another card");
    }


    public void getCurrentPlayerTurn() throws NotValidMoveException {
        assertGameState(GameState.IN_GAME);
        board.getCurrentPlayer();
    }


    public void changeTurn() throws NotValidMoveException {
        assertGameState(GameState.IN_GAME);
        if (!isGamefinished()){
            board.setCurrentPlayer(board.getnextPlayer());
        }
        else {
            gameState = GameState.FINISHED;
            return;
        }
    }



    /**
     * This method adds a card to the playing station
     *
     * @param card the card to add
     * @param X    the x coordinate
     * @param Y    the y coordinate
     */
    public void addCardToPlayingStation(String nickname, int id, Integer X, Integer Y) throws Exception {
        int points = 0;
        Player player = board.getPlayer(nickname);
        CardResource card = player.removeCardFromHand(id);

        try {
            if (player.getStation().isPlayable(card, X, Y)) {
                // Check if the card can be placed
                throw new InvalidPlacingCondition("Non puoi piazzare la carta qua!");
            }
        } catch (InvalidPlacingCondition e) {
            System.out.println(e.getMessage());
            throw new InvalidPlacingCondition("the card cant't be blaced at the coordinates " + X + " " + Y);
        }

        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(0, X);
        coordinates.add(1, Y);
        player.getStation().getMap().put(coordinates, card);


        //calculating the points that the card generates
        if (!(card.getPlayingBack())) {
            points = card.getObjective().countObjectivePoints(player.getStation(), card, X, Y);
        } else points = 0;

        player.setPoints(player.getPoints() + points);
    }

    public boolean isGamefinished() {
        for (Player player : board.getPlayers()) {
            if (player.getPoints() > 20) {
                return true;
            }
        }
        return false;
    }

    public String winner() {
        int maxpoint = 0;
        String maxpointHolder = "";
        for (Player player : board.getPlayers()) {
            if (maxpoint < player.getPoints()) {
                maxpoint = player.getPoints();
                maxpointHolder = player.getNickname();
            }
        }
        return maxpointHolder;
    }


    public void assertGameState(GameState gameState) throws NotValidMoveException {
        if (this.gameState != gameState) {
            throw new NotValidMoveException();
        }
    }

    public void assertIsMyTurn(String nickname) throws NotMyTurnException{
        if (!board.getCurrentPlayer().equals(nickname)) throw new NotMyTurnException();
    }


    private ArrayList<Integer> creatingCordinatesArray(int x, int y) {
        ArrayList<Integer> coordinates = new ArrayList<Integer>();
        coordinates.add(x);
        coordinates.add(y);
        return coordinates;
    }

}

