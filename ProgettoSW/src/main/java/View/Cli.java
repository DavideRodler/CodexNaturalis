package View;

import Observers.ViewObservable;
import Rmi.RmiServer;
import Rmi.VirtualServer;
import controller.GameController;
import model.PlayingBoard;

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
        System.out.print("GAME IS STARTING\n");
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
            printNickname(nickname);
        } catch (ExecutionException e) {
            System.out.println("A problem occurred");
        }

    }
    //method to make sure askNickname works
    public void printNickname(String nick) {
        System.out.println("Nickname chosen: " + nick);
    }

    public void askNumberOfPlayers (){

    }



}