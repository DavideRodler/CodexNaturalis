package observers;

import View.VirtualUI;
import model.enums.TokenEnum;
import socket.Messages.Message;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ObservableModel {
    private List<Observer> observers = new ArrayList<>();
    private List<String> nicknames = new ArrayList<>();
    private HashMap<String, Observer> nicknameToObserver  = new HashMap<>();

    public void addObserver(Observer observer, String nickname){
        this.observers.add(observer);
        this.nicknames.add(nickname);

    }

    public void addObserver(Observer observer){
        this.observers.add(observer);
    }
    public void removeObserver(Observer observer){
        this.observers.remove(observer);
    }

    public void notifyObservers(Message message) throws RemoteException {
        int i = 0;
        for (Observer observer : observers){
            observer.update(message);
        }
    }

    public void notifySpecificObserver(String nickname, Message message) throws RemoteException {
        nicknameToObserver.get(nickname).update(message);
    }

    public void newOrderObserver(List<String> newOrder){
        for (int j = 0; j < newOrder.size(); j++) {
            int i = this.nicknames.indexOf(newOrder.get(j));
            Collections.swap(this.observers, i, j);
        }
        for (int i = 0; i < observers.size(); i++) {
            nicknameToObserver.put(newOrder.get(i), observers.get(i));
        }
    }
}
