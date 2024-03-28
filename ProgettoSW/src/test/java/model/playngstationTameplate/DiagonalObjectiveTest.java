package model.playngstationTameplate;

import TestsPS_Constructor.test_2Cards_0Diagonal_c;
import model.PlayingStation;
import org.junit.jupiter.api.Test;
import TestsPS_Constructor.test_5Cards_1Diagonal_c;

import static org.junit.jupiter.api.Assertions.*;

public class DiagonalObjectiveTest {
  @Test
  public void test_5Cards_1Diagonal() {
    // Creating the test object
    test_5Cards_1Diagonal_c testObject = new test_5Cards_1Diagonal_c();

    // Creating the PlayingStation
    PlayingStation station = testObject.test_5Cards_1Diagonal_c();
    int punti = station.getFirstSecretObjective().getObjective().checkObjective(station);

    // Checking the result
    assertEquals(0, punti, "Test failed. You scored " + punti + " points.");
  }

  @Test
  public void test_2Cards_0Diagonal() {
    // Creating the test object
    test_2Cards_0Diagonal_c testObject = new test_2Cards_0Diagonal_c();

    // Creating the PlayingStation
    PlayingStation station = testObject.test_2Cards_0Diagonal_c();
    int punti = station.getFirstSecretObjective().getObjective().checkObjective(station);

    // Checking the result
    assertEquals(1, punti, "Test failed. You scored " + punti + " points.");
  }
}
