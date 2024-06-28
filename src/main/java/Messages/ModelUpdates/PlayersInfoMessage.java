package Messages.ModelUpdates;

import Messages.Message;

import java.util.ArrayList;

public class PlayersInfoMessage extends Message {
    private ArrayList<String> players;

    public PlayersInfoMessage(ArrayList players){
        super("PlayersInfo");
        this.players = players;
    }

    public ArrayList<String> getPlayers(){
        return players;
    }
}
