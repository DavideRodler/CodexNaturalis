package model.testsTemplate;

import model.PlayingStation;
import model.cards.*;
import model.cards.face.Face;
import model.cards.face.Corner;
import model.enums.DirectionEnum;
import model.enums.PositionEnum;
import model.enums.SuitEnum;
import model.objectives.*;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayingStationTemplate {
    public static PlayingStation test_2Cards_0Diagonal_c() {

        // i create two card resource with an EMPTY back and some Suits in the front,
        // with all corner that can be covered
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY),
                new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.EMPTY),
                new Corner(SuitEnum.FUNGI));
        Points obj = new ObjectiveAssign();
        // the card resource is of type ANIMAL
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, SuitEnum.ANIMAL, 0, obj);

        // now i have to make the starting card, i use the same front and back as the
        // resources
        // first i make the ArrayList for the centralsuit
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);

        CardStarting cardStarting = new CardStarting(3, frontTmp, backTmp, suitList);

        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
        // central card, for this test i don't need them
        //
        // the objective is of type Diagonal and type ANIMAL
        //    ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
        //   CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);

        // for the second objective i set it to null
        PlayingStation station = new PlayingStation(new HashMap<ArrayList<Integer>, CardPlaying>());

        // now i have to populate the table
        station.getMap().put(creatingCordinatesArray(40, 40), cardStarting);
        station.getMap().put(creatingCordinatesArray(39, 39), cardAnimal1);
        station.getMap().put(creatingCordinatesArray(38, 38), cardAnimal2);

        return station;
    }

    public static PlayingStation test_5Cards_1Diagonal_c() {

        // i create three card resource with an EMPTY back and some Suits in the front,
        // with all corner that can be covered
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY),
                new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.EMPTY),
                new Corner(SuitEnum.FUNGI));
        Points obj = new ObjectiveAssign();
        // the card resource is of type ANIMAL
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardAnimal3 = new CardResource(2, frontTmp, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardAnimal4 = new CardResource(3, frontTmp, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardAnimal5 = new CardResource(4, frontTmp, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardAnimal6 = new CardResource(5, frontTmp, backTmp, SuitEnum.ANIMAL, 0, obj);

        // now i have to make the starting card, i use the same front and back as the
        // resoources
        // first i make the ArrayList for the centralsuit
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);


        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
        // central card, for this test i don't need them
        //
        // the objective is of type Diagonal and type ANIMAL
        // ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.RIGHT, SuitEnum.ANIMAL);
        // CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);

        // for the second objective i set it to null
        PlayingStation station = new PlayingStation(new HashMap<ArrayList<Integer>, CardPlaying>());

        // now i have to populate the table
        station.getMap().put(creatingCordinatesArray(39, 39), cardAnimal1);
        station.getMap().put(creatingCordinatesArray(38, 38), cardAnimal2);
        station.getMap().put(creatingCordinatesArray(37, 37), cardAnimal3);
        /*station.addCard(cardAnimal4, 36, 36);
        station.addCard(cardAnimal5, 35, 35);
        station.addCard(cardAnimal6, 34, 34);*/

        return station;
    }


    // creates a tameplate for a Playngstation where the objectivepositioning is statisfied one time
    public static PlayingStation test_3Cards_1Positioning() {

        // i create two card resource with an EMPTY back and some Suits in the front,
        // with all corner that can be covered
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY),
                new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.EMPTY),
                new Corner(SuitEnum.FUNGI));
        Points obj = new ObjectiveAssign();
        // the card resource is of type ANIMAL
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardPlant1 = new CardResource(1, frontTmp, backTmp, SuitEnum.PLANT, 0, obj);
        CardResource cardPlant2 = new CardResource(2, frontTmp, backTmp, SuitEnum.PLANT, 0, obj);
        CardResource cardAnimal2 = new CardResource(4, frontTmp, backTmp, SuitEnum.ANIMAL, 0, obj);

        // now i have to make the starting card, i use the same front and back as the
        // resources
        // first i make the ArrayList for the centralsuit
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);


        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
        // central card, for this test i don't need them
        //
        // the objective is of type Diagonal and type ANIMAL
        ObjectivePositioning objectivetmp = new ObjectivePositioning(SuitEnum.ANIMAL, SuitEnum.PLANT, DirectionEnum.RIGHT, PositionEnum.TOP);
        CardObjective cardObjectiveTmp = new CardObjective(5, 3, objectivetmp);

        // for the second objective i set it to null
        PlayingStation station = new PlayingStation(new HashMap<ArrayList<Integer>, CardPlaying>());

        // now i have to populate the table
        station.getMap().put(creatingCordinatesArray(41, 41), cardAnimal1);
        station.getMap().put(creatingCordinatesArray(42, 42), cardPlant1);
        station.getMap().put(creatingCordinatesArray(41, 43), cardAnimal2);
        station.getMap().put(creatingCordinatesArray(42, 44), cardPlant2);


        return station;

    }

    // creates a tameplate for a Playngstation where the objectivepositioning is statisfied two times
    public static PlayingStation test_6Cards_2Positioning() {

        // i create two card resource with an EMPTY back and some Suits in the front,
        // with all corner that can be covered
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY),
                new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.EMPTY),
                new Corner(SuitEnum.FUNGI));
        Points obj = new ObjectiveAssign();
        // the card resource is of type ANIMAL
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardPlant1 = new CardResource(1, frontTmp, backTmp, SuitEnum.PLANT, 0, obj);
        CardResource cardPlant2 = new CardResource(2, frontTmp, backTmp, SuitEnum.PLANT, 0, obj);
        CardResource cardAnimal2 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardPlant3 = new CardResource(1, frontTmp, backTmp, SuitEnum.PLANT, 0, obj);
        CardResource cardPlant4 = new CardResource(2, frontTmp, backTmp, SuitEnum.PLANT, 0, obj);

        // now i have to make the starting card, i use the same front and back as the
        // resources
        // first i make the ArrayList for the centralsuit
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);

        CardStarting cardStarting = new CardStarting(6, frontTmp, backTmp, suitList);

        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
        // central card, for this test i don't need them
        //
        // the objective is of type Diagonal and type ANIMAL
        ObjectivePositioning objectivetmp = new ObjectivePositioning(SuitEnum.ANIMAL, SuitEnum.PLANT, DirectionEnum.RIGHT
                , PositionEnum.TOP);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);

        // for the second objective i set it to null
        PlayingStation station = new PlayingStation(new HashMap<ArrayList<Integer>, CardPlaying>());

        // now i have to populate the table
        station.getMap().put(creatingCordinatesArray(40, 40), cardStarting);
        station.getMap().put(creatingCordinatesArray(41, 41), cardAnimal1);
        station.getMap().put(creatingCordinatesArray(42, 42), cardPlant1);
        station.getMap().put(creatingCordinatesArray(41, 43), cardAnimal2);
        station.getMap().put(creatingCordinatesArray(42, 44), cardPlant2);

        station.getMap().put(creatingCordinatesArray(43, 45), cardAnimal1);
        station.getMap().put(creatingCordinatesArray(44, 46), cardPlant1);
        station.getMap().put(creatingCordinatesArray(43, 47), cardAnimal2);
        station.getMap().put(creatingCordinatesArray(44, 48), cardPlant2);

        //second time objective is satisfied
        station.getMap().put(creatingCordinatesArray(43, 45), cardAnimal1);
        station.getMap().put(creatingCordinatesArray(42, 46), cardPlant1);
        station.getMap().put(creatingCordinatesArray(43, 47), cardAnimal2);
        station.getMap().put(creatingCordinatesArray(42, 48), cardPlant2);

        return station;

    }

    public static PlayingStation test_null_corner_c() { //2 animal, 1 plant, 2 fungi, 1 insect, 1 points
        // i create two card resource with an EMPTY back and some Suits in the front,
        // with all corner that can be covered
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));

        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));

        Face frontTmp2 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
        Points obj = new ObjectiveAssign();
        // the card resource is of type ANIMAL
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 1, obj);
        CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, SuitEnum.ANIMAL, 1, obj);

        // now i have to make the starting card, i use the same front and back as the
        // resources
        // first i make the ArrayList for the centralsuit
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);

        CardStarting cardStarting = new CardStarting(3, frontTmp2, backTmp, suitList);
//
//        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
//        // central card, for this test i don't need them
//        //
//        // the objective is of type Diagonal and type ANIMAL
        //ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
        //CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
//
//        // for the second objective i set it to null
        PlayingStation station = new PlayingStation(new HashMap<>());
//
//        // now i have to populate the table
        station.getMap().put(creatingCordinatesArray(40, 40), cardStarting);
        station.getMap().put(creatingCordinatesArray(39, 39), cardAnimal1);
        station.getMap().put(creatingCordinatesArray(38, 38), cardAnimal2);
//
        return station;
    }

    //
    public static PlayingStation test_alone_card_c() { //2 animal, 1 plant, 1 fungi, 1 insect, 0 points
//        // i create two card resource with an EMPTY back and some Suits in the front,
//        // with all corner that can be covered
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
//
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
//
        Face frontTmp2 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
        Points obj = new ObjectiveAssign();
//        // the card resource is of type ANIMAL
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 1, obj);
        CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, SuitEnum.ANIMAL, 1, obj);
//
//        // now i have to make the starting card, i use the same front and back as the
//        // resources
//        // first i make the ArrayList for the centralsuit
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);
//
        CardStarting cardStarting = new CardStarting(3, frontTmp2, backTmp, suitList);
//
//        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
//        // central card, for this test i don't need them
//        //
//        // the objective is of type Diagonal and type ANIMAL
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
//
//        // for the second objective i set it to null
        PlayingStation station = new PlayingStation(new HashMap<>());
//
//        // now i have to populate the table
        station.getMap().put(creatingCordinatesArray(40, 40), cardStarting);
        station.getMap().put(creatingCordinatesArray(50, 50), cardAnimal1);
        station.getMap().put(creatingCordinatesArray(60, 60), cardAnimal2);
//
        return station;
    }
//
        public static PlayingStation test_goldCard_cost_c () { //2 animal, 1 plant, 1 fungi, 1 insect, 0 points
//        // i create two card resource with an EMPTY back and some Suits in the front,
//        // with all corner that can be covered
            Face back = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
//
            Face front = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
//
            Face frontGold = new Face(new Corner(SuitEnum.INKWELL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
//
            Points objectiveGold = new ObjectiveCountingGold(1, 0, 0);
//
//        // the card resource is of type ANIMAL
            CardGold cardAnimal1 = new CardGold(0, frontGold, back, SuitEnum.ANIMAL, 1, 2, 1, 0, 0, objectiveGold);
            CardGold cardAnimal2 = new CardGold(0, frontGold, back, SuitEnum.ANIMAL, 1, 4, 2, 0, 0, objectiveGold);
            cardAnimal2.setPlayingBack(true);
//        // now i have to make the starting card, i use the same front and back as the
//        // resources
//        // first i make the ArrayList for the centralsuit
            ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
            suitList.add(SuitEnum.ANIMAL);
            suitList.add(SuitEnum.PLANT);
            suitList.add(SuitEnum.INSECT);
//
            CardStarting cardStarting = new CardStarting(3, front, back, suitList);
//
//        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
//        // central card, for this test i don't need them
//        //
//        // the objective is of type Diagonal and type ANIMAL
            ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
            CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
//
//        // for the second objective i set it to null
            PlayingStation station = new PlayingStation(new HashMap<>());
//
//        // now i have to populate the table
            station.getMap().put(creatingCordinatesArray(40, 40), cardAnimal1);
            station.getMap().put(creatingCordinatesArray(41, 41), cardAnimal1);
            station.getMap().put(creatingCordinatesArray(39, 39), cardAnimal2);
//
//
            return station;
        }
//
            public static PlayingStation test_out_of_bound_c () { //2 animal, 1 plant, 1 fungi, 1 insect, 0 points
//        // i create two card resource with an EMPTY back and some Suits in the front,
//        // with all corner that can be covered
                Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
//
                Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
//
                Face frontTmp2 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
                Points obj = new ObjectiveAssign();
//        // the card resource is of type ANIMAL
                CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 1, obj);
                CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, SuitEnum.ANIMAL, 1, obj);
//
//        // now i have to make the starting card, i use the same front and back as the
//        // resources
//        // first i make the ArrayList for the centralsuit
                ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
                suitList.add(SuitEnum.ANIMAL);
                suitList.add(SuitEnum.PLANT);
                suitList.add(SuitEnum.INSECT);
//
                CardStarting cardStarting = new CardStarting(3, frontTmp2, backTmp, suitList);
//
//        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
//        // central card, for this test i don't need them
//        //
//        // the objective is of type Diagonal and type ANIMAL
                ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
                CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
//
//        // for the second objective i set it to null
                PlayingStation station = new PlayingStation(new HashMap<>());
//
//        // now i have to populate the table
                station.getMap().put(creatingCordinatesArray(40, 40), cardStarting);
                station.getMap().put(creatingCordinatesArray(90, 90), cardAnimal1);
                station.getMap().put(creatingCordinatesArray(41, 41), cardAnimal2);
//
                return station;
            }
//
                public static PlayingStation test_same_axes_c () { //2 animal, 1 plant, 1 fungi, 1 insect, 0 points
//        // i create two card resource with an EMPTY back and some Suits in the front,
//        // with all corner that can be covered
                    Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
//
                    Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
//
                    Face frontTmp2 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
                    Points obj = new ObjectiveAssign();
                    // the card resource is of type ANIMAL
                    CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 1, obj);
                    CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, SuitEnum.ANIMAL, 1, obj);
//
//        // now i have to make the starting card, i use the same front and back as the
//        // resources
//        // first i make the ArrayList for the centralsuit
                    ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
                    suitList.add(SuitEnum.ANIMAL);
                    suitList.add(SuitEnum.PLANT);
                    suitList.add(SuitEnum.INSECT);
//
                    CardStarting cardStarting = new CardStarting(3, frontTmp2, backTmp, suitList);
//
//        // i make a PlayingStation with a cardObjective of type diagonalLeft and no
//        // central card, for this test i don't need them
//        //
//        // the objective is of type Diagonal and type ANIMAL
                    ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
                    CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
//
//        // for the second objective i set it to null
                    PlayingStation station = new PlayingStation(new HashMap<>());
//
//        // now i have to populate the table
                    station.getMap().put(creatingCordinatesArray(40, 40), cardStarting);
                    station.getMap().put(creatingCordinatesArray(40, 39), cardAnimal1);
                    station.getMap().put(creatingCordinatesArray(41, 40), cardAnimal2);
//
                    return station;
                }

                public static ArrayList<Integer> creatingCordinatesArray (int x, int y){
                    ArrayList<Integer> key = new ArrayList<>();
                    key.add(0, x);
                    key.add(1, y);
                    return key;
                }



}

