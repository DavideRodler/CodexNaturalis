package View;

import model.PlayingStation;
import model.client.ClientBoard;
import model.enums.TokenEnum;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface UI {


    void askNickname();

    void askPlayerNumber();

    void askToken(ArrayList<TokenEnum> availableTokens);

    void askStartingCardPlayedBack();

    void askObjectiveCard();


    void showStartingCard();

    void printIsNotMyTurnMenu();

    void printIsMyTurnMenu();

    void askWhichCardToDraw();






    void printCommonObjectives();

    void printSecretObjective();

    void printSelectableObjectives();



    //this method only shows the 4 Central cards at the beginning of the game
    void print4CentralCards();
    
    void printPlayerStation(PlayingStation playingStation);

    //this mehtod only print the hand and is used at the beginning of the game
    void printSetupPlayerHand();

    void printPlayerHand();

    Integer[] askCoordinatesOfCards();



    //ToDo

    // this method print the 4 central cards, the hand , the playingstation, the common and private objectives
    // and the other players name and points;

   // printa solo la staion aggiornata più risorse e più punti
   void printStationAfterCardHasBeenAdded();

   void printOtherPlayersStation(String nickname);

    void printFinalPoints(LinkedHashMap<String, ArrayList<Integer>> map);


    void printCardAddedSuccessfully();

    void printCardNotAdded(String message);


    void printMenu();
}
