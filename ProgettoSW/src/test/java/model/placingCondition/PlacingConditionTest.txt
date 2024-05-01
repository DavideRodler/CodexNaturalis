package model.placingCondition;

import model.PlayingStation;
import model.testsTameplate.PlayngStationTameplate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlacingConditionTest {


    //In this test I am testing that a card cannot be placed, even if its
    // coordinates are valid, above a zero corner of a neighboring card
    //  Finally I am also testing that the costs and scores are updated correctly
    //  with the placement of new cards
    @Test
    public void test_null_corner() {
        // Creating the PlayingStation
        PlayingStation station = PlayngStationTameplate.test_null_corner_c();

        int punti = station.getPlayer().getPoints();
        int animals = station.getCountAnimal();
        int plants = station.getCountPlant();
        int fungi = station.getCountFungi();
        int insects = station.getCountInsect();
        int inkwell = station.getCountInkwell();
        int quill = station.getCountQuill();
        int manuscript = station.getCountManuscript();


        // Checking the result
        assertEquals(1, punti, "Test failed. You scored " + punti + " points.");
        assertEquals(3, animals, "Test failed. You scored " + animals + " animals.");
        assertEquals(1, plants, "Test failed. You scored " + plants + " plants.");
        assertEquals(2, fungi, "Test failed. You scored " + fungi + " fungi.");
        assertEquals(1, insects, "Test failed. You scored " + insects + " insects.");
        assertEquals(0, inkwell, "Test failed. You scored " + inkwell + " inkwell.");
        assertEquals(0, quill, "Test failed. You scored " + quill + " quill.");
        assertEquals(0, manuscript, "Test failed. You scored " + manuscript + " manuscript.");
    }



    //In this test I am testing that a card cannot be placed, even if its coordinates
    // are valid, where there is no other card nearby and therefore I cannot connect
    // it to any corner of cards already placed
    //  Finally I am also testing that the costs and scores are updated correctly
    //  with the placement of new cards

    @Test
    public void test_alone_card() {
        // Creating the PlayingStation
        PlayingStation station = PlayngStationTameplate.test_alone_card_c();
        int punti = station.getPlayer().getPoints();
        int animals = station.getCountAnimal();
        int plants = station.getCountPlant();
        int fungi = station.getCountFungi();
        int insects = station.getCountInsect();
        int inkwell = station.getCountInkwell();
        int quill = station.getCountQuill();
        int manuscript = station.getCountManuscript();


        // Checking the result
        assertEquals(0, punti, "Test failed. You scored " + punti + " points.");
        assertEquals(3, animals, "Test failed. You scored " + animals + " animals.");
        assertEquals(1, plants, "Test failed. You scored " + plants + " plants.");
        assertEquals(1, fungi, "Test failed. You scored " + fungi + " fungi.");
        assertEquals(1, insects, "Test failed. You scored " + insects + " insects.");
        assertEquals(0, inkwell, "Test failed. You scored " + inkwell + " inkwell.");
        assertEquals(0, quill, "Test failed. You scored " + quill + " quill.");
        assertEquals(0, manuscript, "Test failed. You scored " + manuscript + " manuscript.");
    }




    //In this test I'm simply testing the functionality of rejecting a request
    // to place a card in a coordinate that goes outside the size of the playing area and
    // a card in the same position of another card
    //  Finally I am also testing that the costs and scores are updated correctly
    //  with the placement of new cards
    @Test
    public void test_out_of_bound() {
        // Creating the PlayingStation
        PlayingStation station = PlayngStationTameplate.test_out_of_bound_c();
        int punti = station.getPlayer().getPoints();
        int animals = station.getCountAnimal();
        int plants = station.getCountPlant();
        int fungi = station.getCountFungi();
        int insects = station.getCountInsect();
        int inkwell = station.getCountInkwell();
        int quill = station.getCountQuill();
        int manuscript = station.getCountManuscript();


        // Checking the result
        assertEquals(0, punti, "Test failed. You scored " + punti + " points.");
        assertEquals(3, animals, "Test failed. You scored " + animals + " animals.");
        assertEquals(1, plants, "Test failed. You scored " + plants + " plants.");
        assertEquals(1, fungi, "Test failed. You scored " + fungi + " fungi.");
        assertEquals(1, insects, "Test failed. You scored " + insects + " insects.");
        assertEquals(0, inkwell, "Test failed. You scored " + inkwell + " inkwell.");
        assertEquals(0, quill, "Test failed. You scored " + quill + " quill.");
        assertEquals(0, manuscript, "Test failed. You scored " + manuscript + " manuscript.");
    }



    //  In this test I'm testing two things:
    //  1) I'm testing that by placing a gold card on the back you don't need
    //      to meet the cost requirement(I can always place a card on the back)
    //  2) I'm testing that by placing a gold card in front, it can only be
    //      placed if there are sufficient resources in your station
    //  Finally I am also testing that the costs and scores are updated correctly
    //  with the placement of new cards, for example in this case I have also tested
    //  that the gold card scoring method which assigns points for each gold resource
    //  of the type specified on the card itself , would add the correct score to the player.
    @Test
    public void test_goldCard_cost() {
        // Creating the PlayingStation
        PlayingStation station = PlayngStationTameplate.test_goldCard_cost_c();
        int punti = station.getPlayer().getPoints();
        int animals = station.getCountAnimal();
        int plants = station.getCountPlant();
        int fungi = station.getCountFungi();
        int insects = station.getCountInsect();
        int inkwell = station.getCountInkwell();
        int quill = station.getCountQuill();
        int manuscript = station.getCountManuscript();


        // Checking the result
        assertEquals(1, punti, "Test failed. You scored " + punti + " points.");
        assertEquals(3, animals, "Test failed. You scored " + animals + " animals.");
        assertEquals(1, plants, "Test failed. You scored " + plants + " plants.");
        assertEquals(1, fungi, "Test failed. You scored " + fungi + " fungi.");
        assertEquals(1, insects, "Test failed. You scored " + insects + " insects.");
        assertEquals(1, inkwell, "Test failed. You scored " + inkwell + " inkwell.");
        assertEquals(0, quill, "Test failed. You scored " + quill + " quill.");
        assertEquals(0, manuscript, "Test failed. You scored " + manuscript + " manuscript.");
    }



    //In this test I'm testing that I can't place a card covering two corners
    // of another card, or that I can't place a card along the same axes of adjacent cards
    //  Finally I am also testing that the costs and scores are updated correctly
    //  with the placement of new cards
    @Test
    public void test_same_axes() {
        // Creating the PlayingStation
        PlayingStation station = PlayngStationTameplate.test_same_axes_c();
        int punti = station.getPlayer().getPoints();
        int animals = station.getCountAnimal();
        int plants = station.getCountPlant();
        int fungi = station.getCountFungi();
        int insects = station.getCountInsect();
        int inkwell = station.getCountInkwell();
        int quill = station.getCountQuill();
        int manuscript = station.getCountManuscript();


        // Checking the result
        assertEquals(0, punti, "Test failed. You scored " + punti + " points.");
        assertEquals(3, animals, "Test failed. You scored " + animals + " animals.");
        assertEquals(1, plants, "Test failed. You scored " + plants + " plants.");
        assertEquals(1, fungi, "Test failed. You scored " + fungi + " fungi.");
        assertEquals(1, insects, "Test failed. You scored " + insects + " insects.");
        assertEquals(0, inkwell, "Test failed. You scored " + inkwell + " inkwell.");
        assertEquals(0, quill, "Test failed. You scored " + quill + " quill.");
        assertEquals(0, manuscript, "Test failed. You scored " + manuscript + " manuscript.");
    }


}
