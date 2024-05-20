package Socket.Messages;

import model.enums.GameState;

import java.io.Serializable;

public class ChangeStateMessage extends Message implements Serializable {
    private GameState gameState;

    public ChangeStateMessage(String type, GameState gameState) {
        super("ChangeState");
        this.gameState = gameState;
    }
    public GameState getGameState() {
        return gameState;
    }
}
