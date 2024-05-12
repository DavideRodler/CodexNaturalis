package observers;

import View.VirtualUI;
import socket.Messages.Message;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ObservableModel {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        this.observers.add(observer);
    }

    public void notifyObservers(Message message) throws RemoteException {
        for (Observer observer : observers){
            observer.update(message);
        }
    }
}
