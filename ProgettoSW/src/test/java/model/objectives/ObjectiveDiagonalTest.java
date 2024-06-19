package model.objectives;

import exception.ChangedStateException;
import exception.NotValidMoveException;
import model.PlayingStation;
import model.cards.CardObjective;
import model.enums.DirectionEnum;
import model.enums.SuitEnum;
import model.testTemplates.PlayingStationTemplate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveDiagonalTest {
    @Test
    public void test_5Cards_1Diagonal() throws ChangedStateException, NotValidMoveException {
        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_5Cards_1Diagonal_c();
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.RIGHT, SuitEnum.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
        int punti1 = cardObjectiveTmp.getObjective().countObjectivePoints(station);


        // Checking the result
        assertEquals(1, punti1, "Test failed. You scored " + punti1 + " points.");
    }

    @Test
    public void test_7Cards_2Diagonal() throws ChangedStateException, NotValidMoveException {
        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_6Cards_2Positioning();
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
        int punti1 = cardObjectiveTmp.getObjective().countObjectivePoints(station);


        // Checking the result
        assertEquals(2, punti1, "Test failed. You scored " + punti1 + " points.");
    }

    @Test
    public void test_2Cards_0Diagonal() throws ChangedStateException, NotValidMoveException {
        // Creating the test object

        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);

        int punti = cardObjectiveTmp.getObjective().countObjectivePoints(station);

        // Checking the result
        assertEquals(0, punti, "Test failed. You scored " + punti + " points.");
    }

    @Test
    void getInformation() {
        Objective obj = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
        DirectionEnum direction = ((ObjectiveDiagonal) obj).getDirection();
        SuitEnum color = ((ObjectiveDiagonal) obj).getColor();
        assertEquals(DirectionEnum.LEFT, direction);
        assertEquals(SuitEnum.ANIMAL, color);

    }

}
