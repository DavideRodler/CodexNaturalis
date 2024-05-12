package controller;

import model.Player;
import model.PlayingBoard;
import model.PlayingStation;
import model.cards.*;
import model.deck.Decktemplates;
import model.enums.DeckEnum;
import model.enums.TokenEnum;
import model.enums.GameState;
import exception.*;


import java.io.Serializable;
import java.util.*;
import java.util.List;

public class GameController implements Serializable {
    private PlayingBoard board;

    //getter
    public PlayingBoard getBoard() {
        return board;
    }


    //costructor
    public GameController() {
        initGameController();
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


        //creating the board
        this.board = new PlayingBoard(null, null, 0, new ArrayList<Player>(), deckStarting, deckResource, deckObjective, deckGold, null, new ArrayList<CardResource>(), new ArrayList<CardGold>(),null);

        board.setGameState(GameState.SET_PLAYER_NUMBER);
    }

    public void setPlayerNumber(int playernumber) throws NotValidMoveException, ChangedStateException {
        //check right time of the game
        if(playernumber<2) throw new NotValidMoveException("player must be at least two");
        if(playernumber>4) throw new NotValidMoveException("player must less than four");
        assertGameState(GameState.SET_PLAYER_NUMBER);
        board.setPlayernumber(playernumber);
        board.setGameState(GameState.SET_NAME_AND_TOKEN);
    }

    public int getPlayerNumber() throws NotValidMoveException {
        return board.getPlayernumber();
    }

    /**
     * @return the list of token that are not already been choosen
     * @throws NotValidMoveException if i am in another state of the game
     */
    public List<TokenEnum> getAvailableToken() {
        return Arrays.stream(TokenEnum.values())
                .filter(t -> board.getPlayers()
                        .stream()
                        .noneMatch(p -> p.getToken().equals(t)))
                .toList();
    }

    public boolean checkTokenAvailability(TokenEnum token) {
        return getAvailableToken().contains(token);
    }


    /**
     * @param nickname that i want to set
     * @return if the name has already been choosen
     * @throws NotValidMoveException if i am in another state of the game
     */
    public boolean checkNicknameAvailability(String nickname) {
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
    public void addPlayer(String nickname, TokenEnum token) throws NotValidMoveException, ChangedStateException {
        assertGameState(GameState.SET_NAME_AND_TOKEN);
        if(board.getPlayernumber() < board.getPlayers().size()) throw new NotValidMoveException("numero di player massimo raggiunto");
        if(!checkNicknameAvailability(nickname)) throw new NotValidMoveException("nickname already been choosen");
        if(!checkTokenAvailability(token)) throw new NotValidMoveException("token already been choosen");
        this.board.addPlayer(new Player(nickname, token, new PlayingStation(null, new HashMap<>()), 0, new ArrayList<>()));
        if(board.getPlayers().size() == board.getPlayernumber()){
            board.setGameState(GameState.INITIALIZE_GAME);
            InitializeGame();
        }
    }

    /**
     * it shuffle the player and set the game state to the selection of the Starting card face and the Objectives
     *
     * @throws NotValidMoveException
     */
    public void InitializeGame() throws NotValidMoveException, ChangedStateException {
        assertGameState(GameState.INITIALIZE_GAME);

        //shuffle the player
        board.shufflePlayer();

        // generate common objectives objective
        board.setFirstObjective(board.getDeckCardObjective().pop());
        board.setFirstObjective(board.getDeckCardObjective().pop());

        //set the first player
        board.setCurrentPlayer(board.getPlayers().getFirst().getNickname());

        //populating hands
        board.getPlayers().stream().forEach(player -> {
            player.getHand().add(board.getDeckCardResource().pop());
            player.getHand().add(board.getDeckCardResource().pop());
            player.getHand().add(board.getDeckCardGold().pop());
        });

        //populating the central board
        board.getCentralCardsGold().add(board.getDeckCardGold().pop());
        board.getCentralCardsGold().add(board.getDeckCardGold().pop());

        board.getCentralCardsResource().add(board.getDeckCardResource().pop());
        board.getCentralCardsResource().add(board.getDeckCardResource().pop());

        //giving each player his two personal objectives
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

        board.setGameState(GameState.SELECT_STARTINGCARDFACE_AND_OBJECTIVE);
    }


    /**
     * It is used after setStartingCardForPlayers() for selecting the face of the StartingCard
     *
     * @param playedback
     * @param nickname
     * @throws NotValidMoveException
     */
    public void setCentralCardPlayedBack(boolean playedback, String nickname) throws NotValidMoveException, ChangedStateException {
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
    public CardStarting getStartingCard(String nickname) throws NotValidMoveException, ChangedStateException {
        assertGameState(GameState.SELECT_STARTINGCARDFACE_AND_OBJECTIVE);
        return (CardStarting) board.getPlayer(nickname).getStation().getMap().get(creatingCordinatesArray(40,40));
    }


    public ArrayList<CardObjective> getObjectiveToChoose(String nickname) {
        return board.getPlayer(nickname).getSelectibleObjectives();
    }

    /**
     * Sets the PlayerObjective
     * @param nickname
     * @throws NotValidMoveException
     */
    public void setObjectiveOfPlayer(String nickname,int id) throws NotValidMoveException, ChangedStateException {

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
                .isEmpty()){
            board.setGameState(GameState.PLACING_CARD);
        }
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

    public synchronized void addCardFromCentralCardsToPlayerHand(String nickname, int cardId) throws NotValidMoveException, NotMyTurnException, ChangedStateException {
        assertGameState(GameState.ADDING_CARD_TO_HAND);
        assertIsMyTurn(nickname);
        CardResource card = board.getCardResource(cardId)
                .orElse(board.getCardGold(cardId)
                        .orElseThrow(() -> new IllegalStateException("Central card " + cardId + " not found")));
        Player player = board.getPlayer(nickname);
        if(player.getNumberOfCardInHand() == 2) {
            player.addCardToHand(card);
        }
        else throw new NotValidMoveException("you can't have another card");
        board.setGameState(GameState.CHANGING_TURN);
        changeTurn();
    }
    public synchronized void addCardFromDeckToPlayerHand(String nickname, DeckEnum deck) throws NotValidMoveException, NotMyTurnException, ChangedStateException {
        assertGameState(GameState.ADDING_CARD_TO_HAND);
        assertIsMyTurn(nickname);
        Player p = board.getPlayer(nickname);
        if(p.getNumberOfCardInHand() == 2) {
            if(deck.equals(DeckEnum.DECK_GOLD)){
                p.addCardToHand(board.getDeckCardGold().pop());
            }
            else p.addCardToHand(board.getDeckCardResource().pop());
        }
        else throw new NotValidMoveException("you can't have another card");
        board.setGameState(GameState.CHANGING_TURN);
        changeTurn();
    }


    public String getCurrentPlayer() throws NotValidMoveException {
        return board.getCurrentPlayer();
    }


    public synchronized void changeTurn() throws NotValidMoveException, ChangedStateException {
        assertGameState(GameState.CHANGING_TURN);
        if (!isGamefinished()){
            board.setCurrentPlayer(board.getnextPlayer());
            board.setGameState(GameState.PLACING_CARD);
        }
        else {
            board.setGameState(GameState.FINISHED);
        }
    }



    /**
     * This method adds a card to the playing station
     *
     * @param X    the x coordinate
     * @param Y    the y coordinate
     */
    public synchronized void addCardToPlayingStation(String nickname, int id, Integer X, Integer Y) throws Exception {
        assertGameState(GameState.PLACING_CARD);
        assertIsMyTurn(nickname);
        int points = 0;
        Player player = board.getPlayer(nickname);

        //check if the card is in your hand
        CardResource card = player.getHand().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(()-> new NotValidMoveException("card not in your hand"));
        //check if the card is Playable
        player.getStation().isPlayable(card, X, Y);

        // Check if the card can be placed
        player.removeCardFromHand(id);

        //putting the card in the map
        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(0, X);
        coordinates.add(1, Y);
        player.getStation().getMap().put(coordinates, card);

        //updating the counters
        //TODO: non va fatto cosi' devo cambiare metodo
        player.getStation().updateCounters(card);


        //calculating the points that the card generates
        if (!(card.getPlayingBack())) {
            points = card.getObjective().countObjectivePoints(player.getStation(), card, X, Y);
        } else points = 0;

        player.setPoints(player.getPoints() + points);
        repopulatePlayingBoard();
        board.setGameState(GameState.ADDING_CARD_TO_HAND);
    }

    public boolean isGamefinished() {
        //controllo che sia il turno dell'ultimo player ad aver giocato
        if (board.getPlayers().get(board.getPlayernumber()-1).getNickname().equals(board.getCurrentPlayer())) {
            //controllo ci sia almeno un player che ha fatto piu' di 20 punti
            for (Player player : board.getPlayers()) {
                if (board.getPlayers().get(1).equals(player) && player.getPoints() > 20) {
                    return true;
                }
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


    public void assertGameState(GameState gameState) throws NotValidMoveException, ChangedStateException {
        if (board.getGameState() != gameState) {
            throw new ChangedStateException("not valid move, game state is " + board.getGameState() + " but expected " + gameState );
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

    private void repopulatePlayingBoard(){
        for (int i = 0; i < 2; i++) {
            if (board.getCentralCardsResource().get(i) == null){
                board.getCentralCardsResource().add(i, board.getDeckCardResource().pop());
            }
        }
        for (int i = 0; i < 2; i++) {
            if (board.getCentralCardsGold().get(i) == null){
                board.getCentralCardsGold().add(i, board.getDeckCardGold().pop());
            }
        }
    }

}

