package model.objectives;

import controller.GameController;
import exception.ChangedStateException;
import exception.NotValidMoveException;
import model.Player;
import model.PlayingStation;
import model.cards.CardObjective;
import model.cards.CardPlaying;
import model.cards.CardResource;
import model.enums.DirectionEnum;
import model.enums.SuitEnum;
import model.enums.TokenEnum;
import model.testsTemplate.PlayingStationTemplate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveDiagonalTest {
    @Test
    public void test_5Cards_1Diagonal() throws ChangedStateException, NotValidMoveException {
        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_5Cards_1Diagonal_c();
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("isa", TokenEnum.BLUE);
        game.addPlayer("tommy", TokenEnum.YELLOW);
        game.getBoard().getPlayer("isa").setStation(station);
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.RIGHT, SuitEnum.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
        game.getBoard().getPlayer("isa").setSecretObjective(cardObjectiveTmp);
        int punti = game.getBoard().getPlayer("isa").getSecretObjective().getObjective().countObjectivePoints(station);

        // Checking the result
        assertEquals(1, punti, "Test failed. You scored " + punti + " points.");
    }

    @Test
    public void test_2Cards_0Diagonal() throws ChangedStateException, NotValidMoveException {
        // Creating the test object

        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("isa", TokenEnum.BLUE);
        game.addPlayer("tommy", TokenEnum.YELLOW);
        game.getBoard().getPlayer("isa").setStation(station);
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
        game.getBoard().getPlayer("isa").setSecretObjective(cardObjectiveTmp);
        int punti = game.getBoard().getPlayer("isa").getSecretObjective().getObjective().countObjectivePoints(station);

        // Checking the result
        assertEquals(0, punti, "Test failed. You scored " + punti + " points.");
    }
}
