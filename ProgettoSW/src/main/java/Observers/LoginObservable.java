package Observers;

import Network.Client.VirtualView;

import java.util.ArrayList;
import java.util.List;

public class LoginObservable {
    private List<VirtualView> observers = new ArrayList<>();

    public void addObserver(VirtualView observer){
        this.observers.add(observer);
    }

    public void notifyObservers(){
        for (VirtualView observer: observers){
            new Thread(() -> {
                try{
                    observer.Loginupdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
