package controller;

import model.GlobalChat;
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
import java.util.stream.Collectors;

public class GameController implements Serializable {
    private PlayingBoard board;
    private int NumberOfPlayerThatHasSettedTheStartingCardFace = 0;
    private  int NumberOfPlayerThatHasSettedToken = 0;
    private boolean lastTurn = false;
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
     * and start the game
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





    /**
     * this method is used to set the number of player of the game
     * @param playernumber the number of player of the game
     * @throws NotValidMoveException if the number of player is not valid
     * @throws ChangedStateException if the game state is not the right one
     */
    public void setPlayerNumber(int playernumber) throws NotValidMoveException, ChangedStateException {
        //check right time of the game
        if(playernumber<2) throw new NotValidMoveException("player must be at least two");
        if(playernumber>4) throw new NotValidMoveException("player must less than four");
        assertGameState(GameState.SET_PLAYER_NUMBER);
        board.setPlayernumber(playernumber);
        board.setGameState(GameState.ADD_PLAYERS);
    }






    /**
     * @return the list of token that are not already been choosen
     * @throws NotValidMoveException if i am in another state of the game
     */
    public ArrayList<TokenEnum> getAvailableToken() {
        return Arrays.stream(TokenEnum.values())
                .filter(t -> board.getPlayers()
                        .stream()
                        .noneMatch(p -> p.getToken() == t))
                .collect(Collectors.toCollection(ArrayList::new));
    }





    /**
     * this method is used to check if a token is available
     * @param token the token that i want to check
     * @return if the token is available
     */
    public boolean checkTokenAvailability(TokenEnum token) {
        return getAvailableToken().contains(token);
    }





    /**
     * this method is used to check if a nickname is available
     * @param nickname the nickname that i want to check
     * @return if the nickname is available
     */
    public boolean checkNicknameAvailability(String nickname) {
        return board.getPlayers()
                .stream()
                .filter(p -> p.getNickname().equals(nickname))
                .toList()
                .isEmpty();
    }





    /**
     * this method is used to add a player to the game
     * @param nickname the nickname of the player
     * @throws NotValidMoveException if the nickname is not available
     * @throws ChangedStateException if the game state is not the right one
     */
    public void addPlayer(String nickname) throws NotValidMoveException, ChangedStateException {
        assertGameState(GameState.ADD_PLAYERS);
        if(board.getPlayernumber() < board.getPlayers().size()) throw new NotValidMoveException("numero di player massimo raggiunto");
        if(!checkNicknameAvailability(nickname)) throw new NotValidMoveException("Nickname already taken");
        this.board.addPlayer(new Player(nickname, new PlayingStation( new HashMap<>()), 0, new ArrayList<>()));
        if(board.getPlayers().size() == board.getPlayernumber()){
            board.setGameState(GameState.INITIALIZE_GAME);
        }
    }





    /**
     * this method is used to set the token of a player
     * @param nickname  the nickname of the player
     * @param token the token that the player has choosen
     * @throws NotValidMoveException if the token is not available
     * @throws ChangedStateException if the game state is not the right one
     */
    public void selectToken(String nickname, TokenEnum token) throws NotValidMoveException, ChangedStateException {
        assertGameState(GameState.SELECT_TOKEN);
        if(!checkTokenAvailability(token)) {
            throw new NotValidMoveException("Token already taken");
        }
        board.getPlayer(nickname).setToken(token);
        NumberOfPlayerThatHasSettedToken++;
        if(NumberOfPlayerThatHasSettedToken == board.getPlayernumber()){
            board.setGameState(GameState.SELECT_STARTINGCARDFACE);
        }
    }





    /**
     * this method is used to set the starting card of a player
     * @throws NotValidMoveException if the game state is not the right one
     * @throws ChangedStateException if the game state is not the right one
     */
    public void InitializeGame() throws NotValidMoveException, ChangedStateException {
        assertGameState(GameState.INITIALIZE_GAME);

        //shuffle the player
        board.shufflePlayer();

        // generate common objectives objective
        board.setCommonObjectives(board.getDeckCardObjective().pop(),board.getDeckCardObjective().pop());

        //set the first player
        board.setCurrentPlayer(board.getPlayers().getFirst().getNickname());

        //populating hands
        board.getPlayers().stream().forEach(player -> {
            player.addCardToHandWithObserver(board.getCardFromResourceDeck());
            player.addCardToHandWithObserver(board.getCardFromResourceDeck());
            player.addCardToHandWithObserver(board.getCardFromGoldDeck());
        });

        //populating the central board
        board.addCardResource(board.getCardFromResourceDeck());
        board.addCardResource(board.getCardFromResourceDeck());

        board.addCardGold(board.getCardFromGoldDeck());
        board.addCardGold(board.getCardFromGoldDeck());

        //giving each player his two personal objectives
        ArrayList<CardObjective> cardObjectives = new ArrayList<>();
        board.getPlayers().forEach(p -> {
            ArrayList<CardObjective> secretObjectivesToChoose = new ArrayList<CardObjective>();
            secretObjectivesToChoose.add(board.getDeckCardObjective().pop());
            secretObjectivesToChoose.add(board.getDeckCardObjective().pop());
            p.setSelectibleObjectivesWithObserver(secretObjectivesToChoose);
        });

        //now i give for each player its Starting card
        for (Player player: board.getPlayers()){
            player.getStation().setCardStarting(board.getDeckCardStarting().pop(),player.getNickname());
        };


        board.setGameState(GameState.SELECT_TOKEN);

    }






    /**
     * this method is used to set the starting card of a player
     * @param playedback if the card is played back
     * @param nickname the nickname of the player
     * @param ID the id of the card
     * @throws NotValidMoveException if the card is not the one that the player has setted
     * @throws ChangedStateException if the game state is not the right one
     */
    public void setCentralCardPlayedBack(boolean playedback,String nickname, int ID) throws NotValidMoveException, ChangedStateException {
        assertGameState(GameState.SELECT_STARTINGCARDFACE);
        Player player = board.getPlayers().stream()
                .filter(x -> x.getNickname().equals(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("player " + nickname + " not found"));
        try {
            if(player.getStation().getCard(40,40).getId() != ID) throw new NotValidMoveException("the card is not the one you have set");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        player.getStation().setCardStartingPlayedBack(nickname,playedback);
        NumberOfPlayerThatHasSettedTheStartingCardFace++;
        if(NumberOfPlayerThatHasSettedTheStartingCardFace == board.getPlayernumber()){
            board.setGameState(GameState.SELECT_OBJECTIVE);
        }
    }






    /**
     * this method is used to set the objective of a player
     * @param nickname the nickname of the player
     * @param id the id of the objective
     * @throws NotValidMoveException if the objective is not selectible
     * @throws ChangedStateException if the game state is not the right one
     */
    public void setObjectiveOfPlayer(String nickname,int id) throws NotValidMoveException, ChangedStateException {

        //check if i am in the right state
        assertGameState(GameState.SELECT_OBJECTIVE);
        Player p = board.getPlayer(nickname);

        //i check if the objective is selectible
        if (p.getSelectibleObjectives().stream()
                .map(card -> card.getId())
                .filter(idcardobjectivetochoose -> idcardobjectivetochoose.equals(id))
                .findFirst()
                .isEmpty()) throw  new NotValidMoveException("the objective is not selectible");
        ArrayList<CardObjective> selectibleObjectives = p.getSelectibleObjectives();
        p.setSecretObjectiveWithObs(selectibleObjectives.stream()
                .filter(card -> card.getId().equals(id))
                .findFirst()
                .orElseThrow());

        //if all player has setted the objective i can start the game
        if( board.getPlayers().stream()
                .filter(player -> player.getSecretObjective() == null)
                .findFirst()
                .isEmpty()){
            board.setGameState(GameState.PLACING_CARD);
        }
    }






    /**
     * this method is used to get the objective of a player
     * @param nickname the nickname of the player
     * @return the objective of the player
     * @throws NotValidMoveException if the player is not found
     */
    public ArrayList<CardResource> getPlayerHand(String nickname) throws NotValidMoveException {
        return board.getPlayer(nickname).getHand();
    }





    /**
     * this method is used to get the objective of a player
     * @param nickname the nickname of the player
     * @param cardId the id of the card
     * @throws NotValidMoveException if the card is not found
     * @throws NotMyTurnException if it is not the turn of the player
     * @throws ChangedStateException if the game state is not the right one
     */
    public synchronized void addCardFromCentralCardsToPlayerHand(String nickname, int cardId) throws NotValidMoveException, NotMyTurnException, ChangedStateException {
        assertGameState(GameState.ADDING_CARD_TO_HAND);
        assertIsMyTurn(nickname);
        CardResource card = board.getCardResource(cardId);

        if (card == null) {
            card = board.getCardGold(cardId);
            if (card == null) {
                throw new NotValidMoveException("card not found");
            }
        }

        Player player = board.getPlayer(nickname);
        if(player.getNumberOfCardInHand() == 2) {
            player.addCardToHandWithObserver(card);
        }
        else throw new NotValidMoveException("you can't have another card");

        board.removeCentralCard(cardId);
        repopulatePlayingBoard();
        board.setGameState(GameState.CHANGING_TURN);
        changeTurn();
    }
    public synchronized void addCardFromDeckToPlayerHand(String nickname, int deck) throws NotValidMoveException, NotMyTurnException, ChangedStateException {
        assertGameState(GameState.ADDING_CARD_TO_HAND);
        assertIsMyTurn(nickname);
        Player p = board.getPlayer(nickname);
        if(p.getNumberOfCardInHand() == 2) {
            if(deck == 6){
                p.addCardToHandWithObserver(board.getCardFromGoldDeck());
            }
            else p.addCardToHandWithObserver(board.getCardFromResourceDeck());
        }
        else throw new NotValidMoveException("you can't have another card");
        board.setGameState(GameState.CHANGING_TURN);
        changeTurn();
    }





    /**
     * this method is used to get the current player
     * @return the current player
     * @throws NotValidMoveException if the game state is not the right one
     */
    public String getCurrentPlayer() throws NotValidMoveException {
        return board.getCurrentPlayer();
    }







    /**
     * this method is used to get the current player
     * @throws NotValidMoveException if the game state is not the right one
     * @throws ChangedStateException if the game state is not the right one
     */
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
     * this method is used to add a card to the playing station of a player
     * @param nickname the nickname of the player
     * @param id the id of the card
     * @param playedback if the card is played back
     * @param X the x coordinate of the card
     * @param Y the y coordinate of the card
     * @throws InvalidPlacingCondition if the card is not in the hand of the player
     */
    public synchronized void addCardToPlayingStation(String nickname, int id, boolean playedback, Integer X, Integer Y) throws InvalidPlacingCondition{
        try {
            assertGameState(GameState.PLACING_CARD);
        } catch (ChangedStateException | NotValidMoveException e) {
            throw new RuntimeException(e);
        }
        try {
            assertIsMyTurn(nickname);
        } catch (NotMyTurnException e) {
            throw new RuntimeException(e);
        }
        int points = 0;
        Player player = board.getPlayer(nickname);

        //check if the card is in your hand
        CardResource card = null;
        try {
            card = player.getHand().stream()
                    .filter(c -> c.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new NotValidMoveException("card not found in your hand"));
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        }


        //adding the card
        points = player.getStation().addCard(card, X, Y, playedback, nickname);
        player.setPoints(player.getPoints() + points);

        //removing the card from the hand
        try {
            player.removeCardFromHandWithObs(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        board.setGameState(GameState.ADDING_CARD_TO_HAND);
    }






    /**
     * This method is used to see if the game is finished
     * It checks if a player has more than 20 points, then it lets other player finish their turn
     * then another last turn for all players is played
     * and finally returns true if you are in the last turn and the last player.
     * @return if the game is finished
     */
    public boolean isGamefinished() {
        //if i am in the last turn
        if (lastTurn) {
            //if i am in the last turn and the last player i return true
            return board.getPlayers().get(board.getPlayernumber() - 1).getNickname().equals(board.getCurrentPlayer());
        }
        else {
            //if i am not in the last turn i check if i am the last player
            if (board.getPlayers().get(board.getPlayernumber() - 1).getNickname().equals(board.getCurrentPlayer())) {
                // and if there is a player with more than 20 points
                for (Player player : board.getPlayers()) {
                    if (player.getPoints() >= 20) {
                        lastTurn = true;
                        return false;
                    }
                }
            }
            return false;
        }
    }






    /**
     * This method is used to get the score board of the game at the end of the game
     * @return  a Hasmap that has as key the nickname of the player and an arraylist that
     * contains as the first element the total points that the players has scored
     * and as the second element the number of objective that he has completed
     */
    public LinkedHashMap<String,ArrayList<Integer>> getScoreBoard() {
        HashMap<String, ArrayList<Integer>> scoreBoard = new HashMap<>();
        ArrayList<Player> players = board.getPlayers();

        for (Player player : players) {

            //the total points of a player
            int totalpoints = player.getPoints();

            //the number of completed obj
            int numOfCompletedObj = 0;

            //calculating the extra points of the objectives
            int secretObjPoints = player.getSecretObjective().getObjective().countObjectivePoints(player.getStation());
            int firsObjPoints = board.getFirstObjective().getObjective().countObjectivePoints(player.getStation());
            int secondObjPoints = board.getSecondObjective().getObjective().countObjectivePoints(player.getStation());


            //calculating the number of completed objectives
            if (secretObjPoints > 0) {
                numOfCompletedObj++;
            }
            if (firsObjPoints > 0) {
                numOfCompletedObj++;
            }
            if (secondObjPoints > 0) {
                numOfCompletedObj++;
            }

            //adding the extra points to the total points
            totalpoints += secretObjPoints + firsObjPoints + secondObjPoints;

            ArrayList<Integer> pointsArraylist = new ArrayList<>();
            pointsArraylist.add(0, totalpoints);
            pointsArraylist.add(1, numOfCompletedObj);

            //populating the board
            scoreBoard.put(player.getNickname(), pointsArraylist);
        }

        LinkedHashMap<String, ArrayList<Integer>> sortedMap = scoreBoard.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().get(0).compareTo(e1.getValue().get(0)))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, _) -> oldValue, LinkedHashMap::new));

        int classificato = 0;
        int prevPlayerPoints = -1;
        int prevPlayerObjectiveCount = -1;
        for (Map.Entry<String, ArrayList<Integer>> entry : sortedMap.entrySet()) {
            if (entry.getValue().get(0) != prevPlayerPoints || entry.getValue().get(1) != prevPlayerObjectiveCount) {
                prevPlayerPoints = entry.getValue().get(0);
                prevPlayerObjectiveCount = entry.getValue().get(1);
                classificato++;
            }
            entry.getValue().add(2, classificato);
        }
        return sortedMap;
    }




    /**
     * This method is used to check the current game state
     * @param gameState the game state that i want to check
     * @throws NotValidMoveException if the game state is not the right one
     * @throws ChangedStateException if the game state is not the right one
     */
    public void assertGameState(GameState gameState) throws NotValidMoveException, ChangedStateException {
        if (board.getGameState() != gameState) {
            throw new ChangedStateException("not valid move, game state is " + board.getGameState() + " but expected " + gameState );
        }
    }


    /**
     * This method is used to check the current game state
     * @param gameState the game state that i want to check
     * @return if the game state is the right one
     */
    public boolean isGameState(GameState gameState) {
        return board.getGameState().equals(gameState);
    }





    /**
     * This method is used to check if it is the turn of a player
     * @param nickname the nickname of the player
     * @throws NotMyTurnException if it is not the turn of the player
     */
    public void assertIsMyTurn(String nickname) throws NotMyTurnException{
        if (!board.getCurrentPlayer().equals(nickname)) throw new NotMyTurnException();
    }






    /**
     * This method is used to repopulate the playing board
     */
    private void repopulatePlayingBoard(){
        if (board.getCentralCardsResource().size() < 2){
            board.addCardResource(board.getCardFromResourceDeck());
        }
        if (board.getCentralCardsGold().size() < 2){
            board.addCardGold(board.getCardFromGoldDeck());
        }
    }






    /**
     * this method is used to add a new private chat
     * @param nickname1 the nickname of the first player
     * @param nickname2 the nickname of the second player
     */
    public void addNewPrivateChat(String nickname1, String nickname2) {
        board.addNewPrivateChat(nickname1, nickname2);
    }






    /**
     * this method is used to add a message to the global chat
     * @param nickname the nickname of the player
     * @param message the message that the player has sent
     */
    public void addMessageToGlobalChat(String nickname, String message) {
        board.addMessageToGlobalChat(nickname, message);
    }





    /**
     * this method is used to add a message to a private chat
     * @param nickname the nickname of the player
     * @param nickname1 the nickname of the other player
     * @param message2 the message that the player has sent
     */
    public void addMessageToPrivateChat(String nickname, String nickname1, String message2) {
        board.addMessageToPrivateChat(nickname, nickname1, message2);
    }





    /**
     * this method is used to get the global chat
     * @param board the board of the game
     */
    public void setBoard(PlayingBoard board) {
        this.board = board;
    }
}

