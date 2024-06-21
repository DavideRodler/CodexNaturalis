package View;

import Network.Client.ClientController;
import exception.NonePlayerFoundException;
import model.PlayingStation;
import model.client.ClientBoard;
import model.enums.TokenEnum;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface UI {

    void launchGui(ClientBoard clientBoard, ClientController clientController);

    void showStartingCard();

    void showGameTitle();

    void printCommonObjectives();

    void printSecretObjective();

    void printSelectableObjectives();
    
    //this method only shows the 4 Central cards at the beginning of the game
    void print4CentralCardsAndDecks();

    void printPlayerStation(PlayingStation playingStation);

    //this mehtod only print the hand and is used at the beginning of the game
    void printSetupPlayerHand();

    void printPlayerHand();

    //ToDo

    // this method print the 4 central cards, the hand , the playingstation, the common and private objectives
    // and the other players name and points;
   void printStartOfPlayerTurn();

    void printMenu();

    void privateChatTitlePrinter();

    void askTypeOfChat(int numberOfOtherPlayers, String[] NamesOfOtherPlayers);

    // printa solo la staion aggiornata più risorse e più punti
   void printStationAfterCardHasBeenAdded();

   void printOtherPlayersStation(String nickname) throws NonePlayerFoundException;

    void printFinalPoints(LinkedHashMap<String, ArrayList<Integer>> map);

    void printPlayerToken();

    void waitingForOtherPlayers();

    void showErrorMessage(String message);

    void printAvailableTokens(ArrayList<TokenEnum> availableTokens);


    void askMenuAction();

    void askNotMyTurnMenuAction(String currentPlayer);

    void printSpace();

    void printMenu2and3();

    void printMenuNotMyTurn(String currentPlayer);

    void printMenu2and3NotMyTurn(String currentPlayer);

    void askPrivateMessage(String nickname);

    void printChat();

    void printChatInfo();

    void printPrivateChatInfo();

    void printPrivateChat(String nickname, String nickname1);

    void chatTitlePrinter();

    void printPoints();

    void printGloablChatInfo();

    String askNickname();

    TokenEnum askToken(ArrayList<TokenEnum> availableTokens);

    boolean askStartingCardPlayedBack();

    int askPlayerNumber();

    int askObjectiveCard();

    void cliOrGuiChoice();
}
