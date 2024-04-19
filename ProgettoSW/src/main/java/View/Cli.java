package View;

import Observers.ViewObservable;
import Rmi.VirtualServer;
import model.PlayingBoard;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Cli extends ViewObservable{
    private final PrintStream out;
    private final VirtualServer server;

    public Cli(VirtualServer server) {

        this.server = server;
        out = System.out;
    }

    public void init() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        out.print("GAME IS STARTING\n");
        this.notifyObservers("start");
    }

}