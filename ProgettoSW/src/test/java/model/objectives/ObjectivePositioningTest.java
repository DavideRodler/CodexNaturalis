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
import model.testTemplates.PlayingStationTemplate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectivePositioningTest {

    //test where the objective is satisfied one time
    @Test
    public void test_Positioning() throws ChangedStateException, NotValidMoveException {
        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_3Cards_1Positioning();
        ObjectivePositioning objectivetmp = new ObjectivePositioning(SuitEnum.PLANT, SuitEnum.ANIMAL, DirectionEnum.LEFT, PositionEnum.TOP);
        CardObjective cardObjectiveTmp = new CardObjective(5, 3, objectivetmp);
        int punti = cardObjectiveTmp.getObjective().countObjectivePoints(station);

        // Checking the result
        assertEquals(1, punti, "Test failed. You scored " + punti + " points.");
    }


    //test where the objective is satisfied two times
    @Test
    public void test_Positioning2() throws ChangedStateException, NotValidMoveException {
        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_6Cards_2Positioning();
        ObjectivePositioning objectivetmp = new ObjectivePositioning(SuitEnum.FUNGI, SuitEnum.ANIMAL, DirectionEnum.RIGHT, PositionEnum.TOP);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
        int punti = cardObjectiveTmp.getObjective().countObjectivePoints(station);

        // Checking the result
        assertEquals(0, punti, "Test failed. You scored " + punti + " points.");
    }

    @Test
    public void test_Positioning1_down() throws ChangedStateException, NotValidMoveException {
        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_3Cards_1Positioning_down();
        ObjectivePositioning objectivetmp = new ObjectivePositioning(SuitEnum.ANIMAL, SuitEnum.INSECT, DirectionEnum.RIGHT, PositionEnum.BOTTOM);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
        int punti = cardObjectiveTmp.getObjective().countObjectivePoints(station);

        // Checking the result
        assertEquals(1, punti, "Test failed. You scored " + punti + " points.");
    }
    @Test
    public void test_Positioning2_down() throws ChangedStateException, NotValidMoveException {
        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_6Cards_2Positioning_down();
        ObjectivePositioning objectivetmp = new ObjectivePositioning(SuitEnum.FUNGI, SuitEnum.ANIMAL, DirectionEnum.LEFT, PositionEnum.BOTTOM);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
        int punti = cardObjectiveTmp.getObjective().countObjectivePoints(station);

        // Checking the result
        assertEquals(2, punti, "Test failed. You scored " + punti + " points.");
    }



    @Test
    void getInformation() {
        Objective obj = new ObjectivePositioning(SuitEnum.ANIMAL, SuitEnum.PLANT, DirectionEnum.RIGHT, PositionEnum.TOP);
        DirectionEnum direction = ((ObjectivePositioning) obj).getHorizontalDirection();
        PositionEnum position = ((ObjectivePositioning) obj).getVerticalDirection();
        SuitEnum color1 = ((ObjectivePositioning) obj).getColorOneCard();
        SuitEnum color2 = ((ObjectivePositioning) obj).getColorTwoCards();
        assertEquals(DirectionEnum.RIGHT, direction);
        assertEquals(PositionEnum.TOP, position);
        assertEquals(SuitEnum.ANIMAL, color1);
        assertEquals(SuitEnum.PLANT, color2);
    }
}