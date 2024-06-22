package observers;

import Socket.Messages.Message;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObservableModel {
    private List<Observer> observers = new ArrayList<>();
    private HashMap<String, Observer> observerHashMap = new HashMap<>();

    public void addObserver(Observer observer){
        this.observers.add(observer);

    }

    public void addSpecificObserver(String key, Observer observer){
        this.observerHashMap.put(key, observer);
    }


    public void notifyObservers(Message message) throws RemoteException {
        for (Observer observer : observers){
            new Thread(() -> {
                try {
                    observer.update(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void notifySpecificObserver(String key, Message message) throws RemoteException {
        new Thread(() -> {
            try {
                observerHashMap.get(key).update(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void notifyAllObserversExceptOne(String key, Message message) throws RemoteException {
        for (Observer observer : observers){
            if (!observer.equals(observerHashMap.get(key))){
                observer.update(message);
            }
        }
    }
}
