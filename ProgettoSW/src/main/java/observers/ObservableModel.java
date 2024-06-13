package observers;

import Socket.Messages.Message;
import exception.NonePlayerFoundException;

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


    public void notifyObservers(Message message) throws RemoteException, NonePlayerFoundException {
        for (Observer observer : observers){
            observer.update(message);
        }
    }

    public void notifySpecificObserver(String key, Message message) throws RemoteException, NonePlayerFoundException {
        observerHashMap.get(key).update(message);
    }

    public void notifyAllObserversExceptOne(String key, Message message) throws RemoteException, NonePlayerFoundException {
        for (Observer observer : observers){
            if (!observer.equals(observerHashMap.get(key))){
                observer.update(message);
            }
        }
    }
}
