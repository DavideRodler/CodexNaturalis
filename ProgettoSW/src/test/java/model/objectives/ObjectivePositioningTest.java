package model.objectives;

import model.PlayingStation;
import model.testsTameplate.PlayngStationTameplate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectivePositioningTest {

    //test where the objective is satisfied one time
    @Test
    public void test_Positioning() {
        // Creating the PlayingStation
        PlayingStation station = PlayngStationTameplate.test_3Cards_1Positioning();
        int punti = station.getSecretObjective().getObjective().countObjectivePoints(station);

        // Checking the result
        assertEquals(1, punti, "Test failed. You scored " + punti + " points.");
    }


    //test where the objective is satisfied two times
    @Test
    public void test_Positioning2() {
        // Creating the PlayingStation
        PlayingStation station = PlayngStationTameplate.test_6Cards_2Positioning();
        int punti = station.getSecretObjective().getObjective().countObjectivePoints(station);

        // Checking the result
        assertEquals(2, punti, "Test failed. You scored " + punti + " points.");
    }
}