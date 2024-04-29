package Observers;


import Network.Client.VirtualView;
import model.cards.CardPlaying;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Observable {
    private List<VirtualView> LoginObservers = new ArrayList<>();
    private List<VirtualView> MyBoardObservers = new ArrayList<>();

    private List<Thread> loginThreads = new ArrayList<>();
    private List<Thread> myBoardThreads = new ArrayList<>();


    public void addLoginObserver(VirtualView observer){
        this.LoginObservers.add(observer);
    }

    public void addMyBoardObserver(VirtualView observer){
        this.MyBoardObservers.add(observer);
    }

    public void notifyLoginObservers(){
        for (VirtualView observer: LoginObservers){
            Thread loginThread = new  Thread(() -> {
                try{
                    observer.Loginupdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            loginThreads.add(loginThread); // Aggiungi questa linea
            loginThread.start();
        }
    }

    public void notifyMyBoardObservers(Map<ArrayList<Integer>, CardPlaying> playingStation, String name){
        for (VirtualView observer: MyBoardObservers){
            Thread myBoardThread = new  Thread(() -> {
                try{
                    observer.showMyUpdatedBoard(playingStation, name);
                    System.out.println("FLAG-OBS");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            myBoardThreads.add(myBoardThread); // Aggiungi questa linea
            myBoardThread.start();
        }
    }

    public void stopAllLoginThreads() {
        for (Thread loginThread :  loginThreads) {
            loginThread.interrupt();
        }
        loginThreads.clear(); // Rimuovi i riferimenti ai thread interrotti
    }

    public void stopAllMyBoardThreads() {
        for (Thread myBoardThread :  myBoardThreads) {
            myBoardThread.interrupt();
        }
        myBoardThreads.clear(); // Rimuovi i riferimenti ai thread interrotti
    }

}
