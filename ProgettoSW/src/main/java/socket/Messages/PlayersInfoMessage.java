package Socket.Messages;

import model.enums.TokenEnum;
import socket.Messages.Message;
import java.util.HashMap;

public class PlayersInfoMessage extends Message{
    private HashMap<String, TokenEnum> players;

    public PlayersInfoMessage(HashMap<String,TokenEnum> players){
        super("PlayersInfo");
        this.players = players;
    }

    public HashMap<String,TokenEnum> getPlayers(){
        return players;
    }
}
