package model.objectives;

import controller.GameController;
import exception.ChangedStateException;
import exception.NotValidMoveException;
import model.PlayingStation;
import model.cards.CardObjective;
import model.enums.DirectionEnum;
import model.enums.PositionEnum;
import model.enums.SuitEnum;
import model.enums.TokenEnum;
import model.testsTemplate.PlayingStationTemplate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectivePositioningTest {

    //test where the objective is satisfied one time
    @Test
    public void test_Positioning() throws ChangedStateException, NotValidMoveException {
        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_3Cards_1Positioning();
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("isa", TokenEnum.BLUE);
        game.addPlayer("tommy", TokenEnum.YELLOW);
        game.getBoard().getPlayer("isa").setStation(station);
        ObjectivePositioning objectivetmp = new ObjectivePositioning(SuitEnum.ANIMAL, SuitEnum.PLANT, DirectionEnum.RIGHT, PositionEnum.TOP);
        CardObjective cardObjectiveTmp = new CardObjective(5, 3, objectivetmp);
        game.getBoard().getPlayer("isa").setSecretObjective(cardObjectiveTmp);
        int punti = game.getBoard().getPlayer("isa").getSecretObjective().getObjective().countObjectivePoints(station);

        // Checking the result
        assertEquals(1, punti, "Test failed. You scored " + punti + " points.");
    }


    //test where the objective is satisfied two times
    @Test
    public void test_Positioning2() throws ChangedStateException, NotValidMoveException {
        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_6Cards_2Positioning();
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("isa", TokenEnum.BLUE);
        game.addPlayer("tommy", TokenEnum.YELLOW);
        game.getBoard().getPlayer("isa").setStation(station);
        ObjectivePositioning objectivetmp = new ObjectivePositioning(SuitEnum.ANIMAL, SuitEnum.PLANT, DirectionEnum.RIGHT, PositionEnum.TOP);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
        game.getBoard().getPlayer("isa").setSecretObjective(cardObjectiveTmp);
        int punti = game.getBoard().getPlayer("isa").getSecretObjective().getObjective().countObjectivePoints(station);

        // Checking the result
       assertEquals(2, punti, "Test failed. You scored " + punti + " points.");
    }
}