package Socket.Messages;

import model.enums.TokenEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayersInfoMessage extends Message{
    private ArrayList<String> players;

    public PlayersInfoMessage(ArrayList players){
        super("PlayersInfo");
        this.players = players;
    }

    public ArrayList<String> getPlayers(){
        return players;
    }
}
