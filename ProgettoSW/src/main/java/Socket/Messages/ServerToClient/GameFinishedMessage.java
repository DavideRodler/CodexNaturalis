package Socket.Messages.ServerToClient;

import Socket.Messages.Message;
import model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class GameFinishedMessage extends Message {
    private LinkedHashMap<String, ArrayList<Integer>> scoreBoard;

    public GameFinishedMessage(LinkedHashMap<String, ArrayList<Integer>> scoreBoard) {
        super("GameFinished");
        this.scoreBoard = scoreBoard;
    }

    public LinkedHashMap<String, ArrayList<Integer>> getScoreBoard() {
        return scoreBoard;
    }
}
