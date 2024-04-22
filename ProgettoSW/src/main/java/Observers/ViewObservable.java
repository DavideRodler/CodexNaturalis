package Observers;

import View.VirtualUI;

import java.util.ArrayList;
import java.util.List;

public class ViewObservable {

    private List<VirtualUI> viewObserver = new ArrayList<>();

    public void addObserver(VirtualUI viewObserver){
        this.viewObserver.add(viewObserver);
    }

    public void notifyObservers(String message){
        for (VirtualUI viewObserver : viewObserver){
            viewObserver.update(message);
        }
    }
}
