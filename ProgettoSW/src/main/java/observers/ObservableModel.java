package observers;

import Messages.Message;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used to manage the observers
 */
public class ObservableModel {
    private List<Observer> observers = new ArrayList<>();
    private HashMap<String, Observer> observerHashMap = new HashMap<>();

    /**
     * This method is used to add an observer
     * @param observer the observer to add
     */
    public void addObserver(Observer observer){
        this.observers.add(observer);

    }

    /**
     * This method is used to add a specific observer
     * @param key the key of the observer
     * @param observer the observer to add
     */
    public void addSpecificObserver(String key, Observer observer){
        this.observerHashMap.put(key, observer);
    }


    /**
     * This method is used to notify all observers
     */
    public void notifyObservers(Message message) throws RemoteException {
        synchronized (observers) {
            for (Observer observer : observers) {
                observer.update(message);
            }
        }
    }

    /**
     * This method is used to notify a specific observer
     * @param key the key of the observer
     */
    public void notifySpecificObserver(String key, Message message) throws RemoteException {
        synchronized (observerHashMap.get(key)) {
            observerHashMap.get(key).update(message);
        }
    }

    /**
     * This method is used to remove all observer
     */
    public void removeAllObservers(){
        this.observers.clear();
        this.observerHashMap.clear();
    }
}
