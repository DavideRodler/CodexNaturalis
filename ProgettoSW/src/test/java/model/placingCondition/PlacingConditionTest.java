package model.placingCondition;
import controller.GameController;
import exception.ChangedStateException;
import exception.NotValidMoveException;
import model.PlayingStation;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.enums.*;
import model.objectives.ObjectiveDiagonal;
import model.testsTemplate.PlayingStationTemplate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class PlacingConditionTest {

//
//    //In this test I am testing that a card cannot be placed, even if its
//    // coordinates are valid, above a zero corner of a neighboring card
//    //  Finally I am also testing that the costs and scores are updated correctly
//    //  with the placement of new cards
    @Test
    public void test_null_corner() throws Exception {
        // Creating the PlayingStation

        GameController game = new GameController();
        game.getBoard().setGameState(GameState.SET_PLAYER_NUMBER);
        game.setPlayerNumber(2);
        game.getBoard().setGameState(GameState.SET_NAME_AND_TOKEN);
        //numero di player settato, si mescolano in automatico e posso aggiungere nickname e token
        game.addPlayer("tommy", TokenEnum.BLACK);
        game.addPlayer("isa", TokenEnum.YELLOW);
        game.InitializeGame();
        //tutti i giocatori sono salvati, gli obiettivi e le carte Starting sono distribuite in automatico

        //ho settato gli obiettivi di tutti i player il gioco inizia in automatico
        String Player1 = game.getCurrentPlayer();
        ArrayList<CardResource> Player1Hand = game.getPlayerHand(Player1);
        game.getBoard().setGameState(GameState.PLACING_CARD);
        int puntiIn = Player1Hand.get(0).getPoints();
        int animalsIn = Player1Hand.get(0).countResource(SuitEnum.ANIMAL) + game.getBoard().getPlayer(game.getCurrentPlayer()).getStation().getCardStarting().countResource(SuitEnum.ANIMAL);
        int plantsIn = Player1Hand.get(0).countResource(SuitEnum.PLANT) + game.getBoard().getPlayer(game.getCurrentPlayer()).getStation().getCardStarting().countResource(SuitEnum.PLANT);
        int fungiIn = Player1Hand.get(0).countResource(SuitEnum.FUNGI) + game.getBoard().getPlayer(game.getCurrentPlayer()).getStation().getCardStarting().countResource(SuitEnum.FUNGI);
        int insectsIn = Player1Hand.get(0).countResource(SuitEnum.INSECT) + game.getBoard().getPlayer(game.getCurrentPlayer()).getStation().getCardStarting().countResource(SuitEnum.INSECT);
        int inkwellIn = Player1Hand.get(0).countResource(SuitEnum.INKWELL);
        int quillIn = Player1Hand.get(0).countResource(SuitEnum.QUILL);
        int manuscriptIn = Player1Hand.get(0).countResource(SuitEnum.MANUSCRIPT);
        switch(game.getBoard().getPlayer(Player1).getStation().getCardStarting().getFront().getUpLeft().getDrawing()){
            case SuitEnum.ANIMAL:
                animalsIn--;
                break;
            case SuitEnum.PLANT:
                plantsIn--;
                break;
            case SuitEnum.FUNGI:
                fungiIn--;
                break;
            case SuitEnum.INSECT:
                insectsIn--;
                break;
            case SuitEnum.INKWELL:
                inkwellIn--;
                break;
            case SuitEnum.QUILL:
                quillIn--;
                break;
            case SuitEnum.MANUSCRIPT:
                manuscriptIn--;
                break;
        }

        game.addCardToPlayingStation(Player1, Player1Hand.get(0).getId(),true,39,39);
        int punti = game.getBoard().getPlayer(game.getCurrentPlayer()).getPoints();
        int animals = game.getBoard().getPlayer(game.getCurrentPlayer()).getStation().getCountAnimal();
        int plants = game.getBoard().getPlayer(game.getCurrentPlayer()).getStation().getCountPlant();
        int fungi = game.getBoard().getPlayer(game.getCurrentPlayer()).getStation().getCountFungi();
        int insects = game.getBoard().getPlayer(game.getCurrentPlayer()).getStation().getCountInsect();
        int inkwell = game.getBoard().getPlayer(game.getCurrentPlayer()).getStation().getCountInkwell();
        int quill = game.getBoard().getPlayer(game.getCurrentPlayer()).getStation().getCountQuill();
        int manuscript = game.getBoard().getPlayer(game.getCurrentPlayer()).getStation().getCountManuscript();


//        // Checking the result
        assertEquals(puntiIn, punti, "Test failed. You scored " + punti + " points.");
        assertEquals(animalsIn, animals, "Test failed. You scored " + animals + " animals.");
        assertEquals(plantsIn, plants, "Test failed. You scored " + plants + " plants.");
        assertEquals(fungiIn, fungi, "Test failed. You scored " + fungi + " fungi.");
        assertEquals(insectsIn, insects, "Test failed. You scored " + insects + " insects.");
        assertEquals(inkwellIn, inkwell, "Test failed. You scored " + inkwell + " inkwell.");
        assertEquals(quillIn, quill, "Test failed. You scored " + quill + " quill.");
        assertEquals(manuscriptIn, manuscript, "Test failed. You scored " + manuscript + " manuscript.");
    }
//
//
//
//    //In this test I am testing that a card cannot be placed, even if its coordinates
//    // are valid, where there is no other card nearby and therefore I cannot connect
//    // it to any corner of cards already placed
//    //  Finally I am also testing that the costs and scores are updated correctly
//    //  with the placement of new cards
//
    @Test
    public void test_alone_card() throws ChangedStateException, NotValidMoveException {
        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_alone_card_c();
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("isa", TokenEnum.BLUE);
        game.addPlayer("tommy", TokenEnum.YELLOW);
        game.getBoard().getPlayer("isa").setStation(station);
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
        game.getBoard().getPlayer("isa").setSecretObjective(cardObjectiveTmp);
        int punti = game.getBoard().getPlayer("isa").getPoints();
        int animals = station.getCountAnimal();
        int plants = station.getCountPlant();
        int fungi = station.getCountFungi();
        int insects = station.getCountInsect();
        int inkwell = station.getCountInkwell();
        int quill = station.getCountQuill();
        int manuscript = station.getCountManuscript();
//
//
//        // Checking the result
        assertEquals(0, punti, "Test failed. You scored " + punti + " points.");
        assertEquals(3, animals, "Test failed. You scored " + animals + " animals.");
        assertEquals(1, plants, "Test failed. You scored " + plants + " plants.");
        assertEquals(1, fungi, "Test failed. You scored " + fungi + " fungi.");
        assertEquals(1, insects, "Test failed. You scored " + insects + " insects.");
        assertEquals(0, inkwell, "Test failed. You scored " + inkwell + " inkwell.");
        assertEquals(0, quill, "Test failed. You scored " + quill + " quill.");
        assertEquals(0, manuscript, "Test failed. You scored " + manuscript + " manuscript.");
    }
//
//
//
//
//    //In this test I'm simply testing the functionality of rejecting a request
//    // to place a card in a coordinate that goes outside the size of the playing area and
//    // a card in the same position of another card
//    //  Finally I am also testing that the costs and scores are updated correctly
//    //  with the placement of new cards
    @Test
    public void test_out_of_bound() throws ChangedStateException, NotValidMoveException {
        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_out_of_bound_c();
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("isa", TokenEnum.BLUE);
        game.addPlayer("tommy", TokenEnum.YELLOW);
        game.getBoard().getPlayer("isa").setStation(station);
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
        game.getBoard().getPlayer("isa").setSecretObjective(cardObjectiveTmp);
        int punti = game.getBoard().getPlayer("isa").getPoints();
        int animals = station.getCountAnimal();
        int plants = station.getCountPlant();
        int fungi = station.getCountFungi();
        int insects = station.getCountInsect();
        int inkwell = station.getCountInkwell();
        int quill = station.getCountQuill();
        int manuscript = station.getCountManuscript();
//
//
//        // Checking the result
        assertEquals(0, punti, "Test failed. You scored " + punti + " points.");
        assertEquals(3, animals, "Test failed. You scored " + animals + " animals.");
        assertEquals(1, plants, "Test failed. You scored " + plants + " plants.");
        assertEquals(1, fungi, "Test failed. You scored " + fungi + " fungi.");
        assertEquals(1, insects, "Test failed. You scored " + insects + " insects.");
        assertEquals(0, inkwell, "Test failed. You scored " + inkwell + " inkwell.");
        assertEquals(0, quill, "Test failed. You scored " + quill + " quill.");
        assertEquals(0, manuscript, "Test failed. You scored " + manuscript + " manuscript.");
    }
//
//
//
//    //  In this test I'm testing two things:
//    //  1) I'm testing that by placing a gold card on the back you don't need
//    //      to meet the cost requirement(I can always place a card on the back)
//    //  2) I'm testing that by placing a gold card in front, it can only be
//    //      placed if there are sufficient resources in your station
//    //  Finally I am also testing that the costs and scores are updated correctly
//    //  with the placement of new cards, for example in this case I have also tested
//    //  that the gold card scoring method which assigns points for each gold resource
//    //  of the type specified on the card itself , would add the correct score to the player.
    @Test
    public void test_goldCard_cost() throws ChangedStateException, NotValidMoveException {
        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_goldCard_cost_c();
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("isa", TokenEnum.BLUE);
        game.addPlayer("tommy", TokenEnum.YELLOW);
        game.getBoard().getPlayer("isa").setStation(station);
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
        game.getBoard().getPlayer("isa").setSecretObjective(cardObjectiveTmp);
        int punti = game.getBoard().getPlayer("isa").getPoints();
        int animals = station.getCountAnimal();
        int plants = station.getCountPlant();
        int fungi = station.getCountFungi();
        int insects = station.getCountInsect();
        int inkwell = station.getCountInkwell();
        int quill = station.getCountQuill();
        int manuscript = station.getCountManuscript();
//
//
//        // Checking the result
        assertEquals(1, punti, "Test failed. You scored " + punti + " points.");
        assertEquals(3, animals, "Test failed. You scored " + animals + " animals.");
        assertEquals(1, plants, "Test failed. You scored " + plants + " plants.");
        assertEquals(1, fungi, "Test failed. You scored " + fungi + " fungi.");
        assertEquals(1, insects, "Test failed. You scored " + insects + " insects.");
        assertEquals(1, inkwell, "Test failed. You scored " + inkwell + " inkwell.");
        assertEquals(0, quill, "Test failed. You scored " + quill + " quill.");
        assertEquals(0, manuscript, "Test failed. You scored " + manuscript + " manuscript.");
    }
//
//
//
//    //In this test I'm testing that I can't place a card covering two corners
//    // of another card, or that I can't place a card along the same axes of adjacent cards
//    //  Finally I am also testing that the costs and scores are updated correctly
//    //  with the placement of new cards
    @Test
    public void test_same_axes() throws ChangedStateException, NotValidMoveException {
        // Creating the PlayingStation
        PlayingStation station = PlayingStationTemplate.test_same_axes_c();
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("isa", TokenEnum.BLUE);
        game.addPlayer("tommy", TokenEnum.YELLOW);
        game.getBoard().getPlayer("isa").setStation(station);
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
        game.getBoard().getPlayer("isa").setSecretObjective(cardObjectiveTmp);
        int punti = game.getBoard().getPlayer("isa").getPoints();
        int animals = station.getCountAnimal();
        int plants = station.getCountPlant();
        int fungi = station.getCountFungi();
        int insects = station.getCountInsect();
        int inkwell = station.getCountInkwell();
        int quill = station.getCountQuill();
        int manuscript = station.getCountManuscript();
//
//
//        // Checking the result
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
