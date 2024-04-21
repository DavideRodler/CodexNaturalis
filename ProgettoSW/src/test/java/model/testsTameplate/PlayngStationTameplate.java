package model.testsTameplate;

import model.Player;
import model.PlayingStation;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.Direction;
import model.enums.Position;
import model.enums.Suit;
import model.objectives.*;

import java.util.ArrayList;

public class PlayngStationTameplate {
    public static PlayingStation test_2Cards_0Diagonal_c() {

        // i create two card resource with an EMPTY back and some Suits in the front,
        // with all corner that can be covered
        Face backTmp = new Face(new Corner(Suit.EMPTY), new Corner(Suit.EMPTY), new Corner(Suit.EMPTY),
                new Corner(Suit.EMPTY));
        Face frontTmp = new Face(new Corner(Suit.ANIMAL), new Corner(Suit.PLANT), new Corner(Suit.EMPTY),
                new Corner(Suit.FUNGI));
        Objective obj = new ObjectiveAssign();
        // the card resource is of type ANIMAL
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, Suit.ANIMAL, 0, obj);
        CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, Suit.ANIMAL, 0, obj);

        // now i have to make the starting card, i use the same front and back as the
        // resources
        // first i make the ArrayList for the centralsuit
        ArrayList<Suit> suitList = new ArrayList<Suit>();
        suitList.add(Suit.ANIMAL);
        suitList.add(Suit.PLANT);
        suitList.add(Suit.INSECT);

        CardStarting cardStarting = new CardStarting(3, frontTmp, backTmp, suitList);

        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
        // central card, for this test i don't need them
        //
        // the objective is of type Diagonal and type ANIMAL
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(Direction.LEFT, Suit.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);

        // for the second objective i set it to null
        Player player = new Player("test");
        PlayingStation station = new PlayingStation(player,cardStarting, cardObjectiveTmp);

        // now i have to populate the table
        station.addCard(cardAnimal1, 39, 39);
        station.addCard(cardAnimal2, 38, 38);

        return station;
    }
    public static PlayingStation test_5Cards_1Diagonal_c() {

        // i create three card resource with an EMPTY back and some Suits in the front,
        // with all corner that can be covered
        Face backTmp = new Face(new Corner(Suit.EMPTY), new Corner(Suit.EMPTY), new Corner(Suit.EMPTY),
                new Corner(Suit.EMPTY));
        Face frontTmp = new Face(new Corner(Suit.ANIMAL), new Corner(Suit.PLANT), new Corner(Suit.EMPTY),
                new Corner(Suit.FUNGI));
        Objective obj = new ObjectiveAssign();
        // the card resource is of type ANIMAL
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, Suit.ANIMAL, 0, obj);
        CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, Suit.ANIMAL, 0, obj);
        CardResource cardAnimal3 = new CardResource(2, frontTmp, backTmp, Suit.ANIMAL, 0, obj);
        CardResource cardAnimal4 = new CardResource(3, frontTmp, backTmp, Suit.ANIMAL, 0, obj);
        CardResource cardAnimal5 = new CardResource(4, frontTmp, backTmp, Suit.ANIMAL, 0, obj);
        CardResource cardAnimal6 = new CardResource(5, frontTmp, backTmp, Suit.ANIMAL, 0, obj);

        // now i have to make the starting card, i use the same front and back as the
        // resoources
        // first i make the ArrayList for the centralsuit
        ArrayList<Suit> suitList = new ArrayList<Suit>();
        suitList.add(Suit.ANIMAL);
        suitList.add(Suit.PLANT);
        suitList.add(Suit.INSECT);

        CardStarting cardStarting = new CardStarting(3, frontTmp, backTmp, suitList);

        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
        // central card, for this test i don't need them
        //
        // the objective is of type Diagonal and type ANIMAL
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(Direction.RIGHT, Suit.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);

        // for the second objective i set it to null
        Player player = new Player("test");
        PlayingStation station = new PlayingStation(player, cardStarting, cardObjectiveTmp);

        // now i have to populate the table
        station.addCard(cardAnimal1, 39, 39);
        station.addCard(cardAnimal2, 38, 38);
        station.addCard(cardAnimal3, 37, 37);
        /*station.addCard(cardAnimal4, 36, 36);
        station.addCard(cardAnimal5, 35, 35);
        station.addCard(cardAnimal6, 34, 34);*/

        return station;
    }



    // creates a tameplate for a Playngstation where the objectivepositioning is statisfied one time
    public static PlayingStation test_3Cards_1Positioning() {

        // i create two card resource with an EMPTY back and some Suits in the front,
        // with all corner that can be covered
        Face backTmp = new Face(new Corner(Suit.EMPTY), new Corner(Suit.EMPTY), new Corner(Suit.EMPTY),
                new Corner(Suit.EMPTY));
        Face frontTmp = new Face(new Corner(Suit.ANIMAL), new Corner(Suit.PLANT), new Corner(Suit.EMPTY),
                new Corner(Suit.FUNGI));
        Objective obj = new ObjectiveAssign();
        // the card resource is of type ANIMAL
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, Suit.ANIMAL, 0, obj);
        CardResource cardPlant1 = new CardResource(1, frontTmp, backTmp, Suit.PLANT, 0, obj);
        CardResource cardPlant2 = new CardResource(2, frontTmp, backTmp, Suit.PLANT, 0, obj);
        CardResource cardAnimal2 = new CardResource(4, frontTmp, backTmp, Suit.ANIMAL, 0, obj);

        // now i have to make the starting card, i use the same front and back as the
        // resources
        // first i make the ArrayList for the centralsuit
        ArrayList<Suit> suitList = new ArrayList<Suit>();
        suitList.add(Suit.ANIMAL);
        suitList.add(Suit.PLANT);
        suitList.add(Suit.INSECT);

        CardStarting cardStarting = new CardStarting(6, frontTmp, backTmp, suitList);

        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
        // central card, for this test i don't need them
        //
        // the objective is of type Diagonal and type ANIMAL
        ObjectivePositioning objectivetmp = new ObjectivePositioning(Suit.ANIMAL, Suit.PLANT, Direction.RIGHT, Position.TOP);
        CardObjective cardObjectiveTmp = new CardObjective(5, 3, objectivetmp);

        // for the second objective i set it to null
        Player player = new Player("test");
        PlayingStation station = new PlayingStation(player,cardStarting, cardObjectiveTmp);

        // now i have to populate the table
        station.addCard(cardAnimal1, 41, 41);
        station.addCard(cardPlant1, 42, 42);
        station.addCard(cardAnimal2, 41, 43);
        station.addCard(cardPlant2, 42, 44);


        return station;

    }

    // creates a tameplate for a Playngstation where the objectivepositioning is statisfied two times
    public static PlayingStation test_6Cards_2Positioning() {

        // i create two card resource with an EMPTY back and some Suits in the front,
        // with all corner that can be covered
        Face backTmp = new Face(new Corner(Suit.EMPTY), new Corner(Suit.EMPTY), new Corner(Suit.EMPTY),
                new Corner(Suit.EMPTY));
        Face frontTmp = new Face(new Corner(Suit.ANIMAL), new Corner(Suit.PLANT), new Corner(Suit.EMPTY),
                new Corner(Suit.FUNGI));
        Objective obj = new ObjectiveAssign();
        // the card resource is of type ANIMAL
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, Suit.ANIMAL, 0, obj);
        CardResource cardPlant1 = new CardResource(1, frontTmp, backTmp, Suit.PLANT, 0, obj);
        CardResource cardPlant2 = new CardResource(2, frontTmp, backTmp, Suit.PLANT, 0, obj);
        CardResource cardAnimal2 = new CardResource(0, frontTmp, backTmp, Suit.ANIMAL, 0, obj);
        CardResource cardPlant3 = new CardResource(1, frontTmp, backTmp, Suit.PLANT, 0, obj);
        CardResource cardPlant4 = new CardResource(2, frontTmp, backTmp, Suit.PLANT, 0, obj);

        // now i have to make the starting card, i use the same front and back as the
        // resources
        // first i make the ArrayList for the centralsuit
        ArrayList<Suit> suitList = new ArrayList<Suit>();
        suitList.add(Suit.ANIMAL);
        suitList.add(Suit.PLANT);
        suitList.add(Suit.INSECT);

        CardStarting cardStarting = new CardStarting(6, frontTmp, backTmp, suitList);

        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
        // central card, for this test i don't need them
        //
        // the objective is of type Diagonal and type ANIMAL
        ObjectivePositioning objectivetmp = new ObjectivePositioning(Suit.ANIMAL, Suit.PLANT, Direction.RIGHT
                , Position.TOP);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);

        // for the second objective i set it to null
        Player player = new Player("test");
        PlayingStation station = new PlayingStation(player, cardStarting, cardObjectiveTmp);

        // now i have to populate the table
        //first time objective is satisfied
        station.addCard(cardAnimal1, 41, 41);
        station.addCard(cardPlant1, 42, 42);
        station.addCard(cardAnimal2, 41, 43);
        station.addCard(cardPlant2, 42, 44);


        //second time objective is satisfied
        station.addCard(cardAnimal1, 43, 45);
        station.addCard(cardPlant1, 42, 46);
        station.addCard(cardAnimal2, 43, 47);
        station.addCard(cardPlant2, 42, 48);

        return station;

    }

    public static PlayingStation test_null_corner_c() { //2 animal, 1 plant, 2 fungi, 1 insect, 1 points
        // i create two card resource with an EMPTY back and some Suits in the front,
        // with all corner that can be covered
        Face backTmp = new Face(new Corner(Suit.EMPTY), new Corner(Suit.EMPTY), new Corner(Suit.EMPTY),
                new Corner(Suit.EMPTY));

        Face frontTmp = new Face(new Corner(Suit.ANIMAL), new Corner(Suit.NULL), new Corner(Suit.EMPTY),
                new Corner(Suit.FUNGI));

        Face frontTmp2 = new Face(new Corner(Suit.ANIMAL), new Corner(Suit.ANIMAL), new Corner(Suit.EMPTY),
                new Corner(Suit.FUNGI));
        Objective obj = new ObjectiveAssign();
        // the card resource is of type ANIMAL
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, Suit.ANIMAL, 1, obj);
        CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, Suit.ANIMAL, 1, obj);

        // now i have to make the starting card, i use the same front and back as the
        // resources
        // first i make the ArrayList for the centralsuit
        ArrayList<Suit> suitList = new ArrayList<Suit>();
        suitList.add(Suit.ANIMAL);
        suitList.add(Suit.PLANT);
        suitList.add(Suit.INSECT);

        CardStarting cardStarting = new CardStarting(3, frontTmp2, backTmp, suitList);

        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
        // central card, for this test i don't need them
        //
        // the objective is of type Diagonal and type ANIMAL
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(Direction.LEFT, Suit.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);

        // for the second objective i set it to null
        Player player = new Player("test");
        PlayingStation station = new PlayingStation(player, cardStarting, cardObjectiveTmp);

        // now i have to populate the table
        station.addCard(cardAnimal1, 39, 39);
        station.addCard(cardAnimal2, 38, 38);

        return station;
    }

    public static PlayingStation test_alone_card_c() { //2 animal, 1 plant, 1 fungi, 1 insect, 0 points
        // i create two card resource with an EMPTY back and some Suits in the front,
        // with all corner that can be covered
        Face backTmp = new Face(new Corner(Suit.EMPTY), new Corner(Suit.EMPTY), new Corner(Suit.EMPTY),
                new Corner(Suit.EMPTY));

        Face frontTmp = new Face(new Corner(Suit.ANIMAL), new Corner(Suit.NULL), new Corner(Suit.EMPTY),
                new Corner(Suit.FUNGI));

        Face frontTmp2 = new Face(new Corner(Suit.ANIMAL), new Corner(Suit.ANIMAL), new Corner(Suit.EMPTY),
                new Corner(Suit.FUNGI));
        Objective obj = new ObjectiveAssign();
        // the card resource is of type ANIMAL
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, Suit.ANIMAL, 1, obj);
        CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, Suit.ANIMAL, 1, obj);

        // now i have to make the starting card, i use the same front and back as the
        // resources
        // first i make the ArrayList for the centralsuit
        ArrayList<Suit> suitList = new ArrayList<Suit>();
        suitList.add(Suit.ANIMAL);
        suitList.add(Suit.PLANT);
        suitList.add(Suit.INSECT);

        CardStarting cardStarting = new CardStarting(3, frontTmp2, backTmp, suitList);

        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
        // central card, for this test i don't need them
        //
        // the objective is of type Diagonal and type ANIMAL
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(Direction.LEFT, Suit.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);

        // for the second objective i set it to null
        Player player = new Player("test");
        PlayingStation station = new PlayingStation(player, cardStarting, cardObjectiveTmp);

        // now i have to populate the table
        station.addCard(cardAnimal1, 50, 50);
        station.addCard(cardAnimal2, 60, 60);

        return station;
    }

    public static PlayingStation test_goldCard_cost_c() { //2 animal, 1 plant, 1 fungi, 1 insect, 0 points
        // i create two card resource with an EMPTY back and some Suits in the front,
        // with all corner that can be covered
        Face back = new Face(new Corner(Suit.EMPTY), new Corner(Suit.EMPTY), new Corner(Suit.EMPTY),
                new Corner(Suit.EMPTY));

        Face front = new Face(new Corner(Suit.ANIMAL), new Corner(Suit.EMPTY), new Corner(Suit.EMPTY),
                new Corner(Suit.FUNGI));

        Face frontGold = new Face(new Corner(Suit.INKWELL), new Corner(Suit.EMPTY), new Corner(Suit.EMPTY),
                new Corner(Suit.EMPTY));

        Objective objectiveGold = new ObjectiveCountingGold(1,0,0);

        // the card resource is of type ANIMAL
        CardGold cardAnimal1 = new CardGold(0, frontGold, back, Suit.ANIMAL, 1, 2,1,0,0, objectiveGold);
        CardGold cardAnimal2 = new CardGold(0, frontGold, back, Suit.ANIMAL, 1, 4,2,0,0, objectiveGold);
        cardAnimal2.setPlayingBack(true);
        // now i have to make the starting card, i use the same front and back as the
        // resources
        // first i make the ArrayList for the centralsuit
        ArrayList<Suit> suitList = new ArrayList<Suit>();
        suitList.add(Suit.ANIMAL);
        suitList.add(Suit.PLANT);
        suitList.add(Suit.INSECT);

        CardStarting cardStarting = new CardStarting(3, front, back, suitList);

        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
        // central card, for this test i don't need them
        //
        // the objective is of type Diagonal and type ANIMAL
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(Direction.LEFT, Suit.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);

        // for the second objective i set it to null
        Player player = new Player("test");
        PlayingStation station = new PlayingStation(player, cardStarting, cardObjectiveTmp);

        // now i have to populate the table
        station.addCard(cardAnimal1, 41, 41);
        station.addCard(cardAnimal2, 39, 39);


        return station;
    }

    public static PlayingStation test_out_of_bound_c() { //2 animal, 1 plant, 1 fungi, 1 insect, 0 points
        // i create two card resource with an EMPTY back and some Suits in the front,
        // with all corner that can be covered
        Face backTmp = new Face(new Corner(Suit.EMPTY), new Corner(Suit.EMPTY), new Corner(Suit.EMPTY),
                new Corner(Suit.EMPTY));

        Face frontTmp = new Face(new Corner(Suit.ANIMAL), new Corner(Suit.NULL), new Corner(Suit.EMPTY),
                new Corner(Suit.FUNGI));

        Face frontTmp2 = new Face(new Corner(Suit.ANIMAL), new Corner(Suit.ANIMAL), new Corner(Suit.EMPTY),
                new Corner(Suit.FUNGI));
        Objective obj = new ObjectiveAssign();
        // the card resource is of type ANIMAL
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, Suit.ANIMAL, 1, obj);
        CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, Suit.ANIMAL, 1, obj);

        // now i have to make the starting card, i use the same front and back as the
        // resources
        // first i make the ArrayList for the centralsuit
        ArrayList<Suit> suitList = new ArrayList<Suit>();
        suitList.add(Suit.ANIMAL);
        suitList.add(Suit.PLANT);
        suitList.add(Suit.INSECT);

        CardStarting cardStarting = new CardStarting(3, frontTmp2, backTmp, suitList);

        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
        // central card, for this test i don't need them
        //
        // the objective is of type Diagonal and type ANIMAL
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(Direction.LEFT, Suit.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);

        // for the second objective i set it to null
        Player player = new Player("test");
        PlayingStation station = new PlayingStation(player, cardStarting, cardObjectiveTmp);

        // now i have to populate the table
        station.addCard(cardAnimal1, 90, 90);
        station.addCard(cardAnimal2, 40, 40);

        return station;
    }

    public static PlayingStation test_same_axes_c() { //2 animal, 1 plant, 1 fungi, 1 insect, 0 points
        // i create two card resource with an EMPTY back and some Suits in the front,
        // with all corner that can be covered
        Face backTmp = new Face(new Corner(Suit.EMPTY), new Corner(Suit.EMPTY), new Corner(Suit.EMPTY),
                new Corner(Suit.EMPTY));

        Face frontTmp = new Face(new Corner(Suit.ANIMAL), new Corner(Suit.NULL), new Corner(Suit.EMPTY),
                new Corner(Suit.FUNGI));

        Face frontTmp2 = new Face(new Corner(Suit.ANIMAL), new Corner(Suit.ANIMAL), new Corner(Suit.EMPTY),
                new Corner(Suit.FUNGI));
        Objective obj = new ObjectiveAssign();
        // the card resource is of type ANIMAL
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, Suit.ANIMAL, 1, obj);
        CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, Suit.ANIMAL, 1, obj);

        // now i have to make the starting card, i use the same front and back as the
        // resources
        // first i make the ArrayList for the centralsuit
        ArrayList<Suit> suitList = new ArrayList<Suit>();
        suitList.add(Suit.ANIMAL);
        suitList.add(Suit.PLANT);
        suitList.add(Suit.INSECT);

        CardStarting cardStarting = new CardStarting(3, frontTmp2, backTmp, suitList);

        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
        // central card, for this test i don't need them
        //
        // the objective is of type Diagonal and type ANIMAL
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(Direction.LEFT, Suit.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);

        // for the second objective i set it to null
        Player player = new Player("test");
        PlayingStation station = new PlayingStation(player, cardStarting, cardObjectiveTmp);

        // now i have to populate the table
        station.addCard(cardAnimal1, 40, 39);
        station.addCard(cardAnimal2, 41, 40);

        return station;
    }

}
