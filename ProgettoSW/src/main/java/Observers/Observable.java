package Observers;

import View.VirtualView;
import model.PlayingBoard;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private List<VirtualView> viewObserver = new ArrayList<>();

    public void addObserver(VirtualView viewObserver){
        this.viewObserver.add(viewObserver);
    }

    public void notifyObservers(String message){
        for (VirtualView viewObserver : viewObserver){
            viewObserver.update(message);
        }
    }
}
