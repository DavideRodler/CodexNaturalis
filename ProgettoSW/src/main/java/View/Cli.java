/*package View;

import Observers.ViewObservable;
import Rmi.RmiServer;
import Rmi.VirtualServer;
import controller.GameController;
import model.PlayingBoard;
import model.cards.CardStarting;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


public class Cli extends ViewObservable{

    private final VirtualServer server;
    private Thread thread_input;


    public Cli(VirtualServer server) {

        this.server = server;
        //out = System.out;
    }


    public void init() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        System.out.println("GAME IS STARTING");
        this.notifyObservers("start");
    }

    //method to read a line
    public String readLine() throws ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new InputReaderTask());
        thread_input = new Thread(futureTask);
        thread_input.start();

        String input = null;

        try {
            input = futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return input;
    }

    public void askNickname() {
        System.out.print("Enter your nickname: ");
        try {
            String nickname = readLine();
            notifyObservers("nickname: " + nickname); // TODO: scrivere bene messaggi
            System.out.println("Nickname chosen: " + nickname);
        } catch (ExecutionException e) {
            System.out.println("A problem occurred");
        }

    }

    public void askNumberOfPlayers (){
        System.out.println("Enter your number of players: "); //has to be between 2 and 4
        try {
            String playerNumber = readLine();
            int numPlayers = Integer.parseInt(playerNumber);
            notifyObservers("number of players: " + playerNumber); //TODO: scrivere bene i messaggi
            System.out.println("Number of players: " + numPlayers);
        } catch (ExecutionException e){
            System.out.println("A problem occurred");
        }
    }

    //metodo che printa le prime carte nel centro del tavolo
    public void showInitialCards() {
        System.out.println("These are the resource cards: "); //bisogna printare le carte
        System.out.println("This is the gold card"); //same di sopra
    }


    public void chooseStartingCardSide (CardStarting card) {
        System.out.println("This is your starting card: "); //non so come printare carta
        System.out.print("Choose the side of the card you want to play(front or back): ");
        try {
            String side = readLine();
            if(side.equals("front")){
                notifyObservers("card played front");
            } else if(side.equals("back")){
                notifyObservers("card played back");
            }
        } catch (ExecutionException e){
            System.out.println("A problem occurred");
        }
    }

    public void chooseTokenColor() {
        System.out.println("Choose the color of your token: ");
        try {
            String color = readLine();
            notifyObservers("color: " + color);
        } catch (ExecutionException e){
            System.out.println("A problem occurred");
        }
    }

    public void drawInitialCards () {
        System.out.println("These are your initial resource cards: "); //bisogna printare le carte
        System.out.println("This is your initial gold card"); //same di sopra
    }

    public void showCommonObjectives() {
        System.out.println("These are the common objectives: "); //printare gli obiettivi
    }

    public void chooseSecretObjective() {
        System.out.println("Choose your secret objective(first or second): ");//printare i due obiettivi
        try {
            String chosenObjective = readLine();
            notifyObservers("objective chosen: " + chosenObjective); //l'altro va messo in fondo al mazzo
        } catch (ExecutionException e){
            System.out.println("A problem occurred");
        }

    }

    public void chooseFirstPlayer(){
        notifyObservers("choose the players order");
        System.out.println("The first player is: ");
    }

    public void playCard() {
        System.out.println("It's your turn!");
        System.out.println("Which card do you want to play?");
        try {
            String cardPlayed = readLine();
            notifyObservers("card chosen: " + cardPlayed);
            System.out.println("You have x points");
        } catch (ExecutionException e){
            System.out.println("A problem occurred");
        }
    }

    public void drawCard() {
        System.out.println("Where do you want to draw your card from? (center of the table, resource deck or gold deck");
        try {
            String cardSource = readLine();
            notifyObservers("source chosen: " + cardSource);
            System.out.println("Your turn is over");
        } catch (ExecutionException e){
            System.out.println("A problem occurred");
        }
    }

}*/