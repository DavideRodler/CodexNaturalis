package socket.Messages;

import model.enums.TokenEnum;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayersInfoMessage extends Message{
    private HashMap<String,TokenEnum> playersinfo;

    public PlayersInfoMessage(HashMap<String,TokenEnum> playersinfo){
        super("PlayersInfo");
        this.playersinfo = playersinfo;
    }

    public HashMap<String, TokenEnum> getPlayersinfo() {
        return playersinfo;
    }
}
