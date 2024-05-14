package model.objectives;

import model.PlayingStation;
import model.testsTemplate.PlayingStationTemplate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveDiagonalTest {
    @Test
    public void test_5Cards_1Diagonal() {
        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_5Cards_1Diagonal_c();
        int punti = station.getSecretObjective().getObjective().countObjectivePoints(station);

        // Checking the result
        assertEquals(1, punti, "Test failed. You scored " + punti + " points.");
    }

    @Test
    public void test_2Cards_0Diagonal() {
        // Creating the test object

        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        int punti = station.getSecretObjective().getObjective().countObjectivePoints(station);

        // Checking the result
        assertEquals(0, punti, "Test failed. You scored " + punti + " points.");
    }
}
