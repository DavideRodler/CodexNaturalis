package model;

import exception.InvalidPlacingCondition;
import model.cards.CardGold;
import model.cards.CardPlaying;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.SuitEnum;
import model.objectives.Objective;
import model.objectives.ObjectiveAssign;
import model.objectives.Points;
import model.testTemplates.PlayingStationTemplate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static model.testTemplates.PlayingStationTemplate.creatingCordinatesArray;
import static org.junit.jupiter.api.Assertions.*;

class PlayingStationTest {

    PlayingStation station = PlayingStationTemplate.playingStation_test();

    @Test
    public void checkCounters() throws InvalidPlacingCondition {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp1 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT));
        Face frontTmp2 = new Face(new Corner(SuitEnum.FUNGI), new Corner(SuitEnum.INSECT), new Corner(SuitEnum.FUNGI), new Corner(SuitEnum.INSECT));
        Face frontTmp3 = new Face(new Corner(SuitEnum.QUILL), new Corner(SuitEnum.MANUSCRIPT), new Corner(SuitEnum.INKWELL), new Corner(SuitEnum.MANUSCRIPT));
        Points obj = new ObjectiveAssign();
        CardResource cardAnimal1 = new CardResource(0, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardAnimal2 = new CardResource(1, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardInsect1 = new CardResource(2, frontTmp2, backTmp, SuitEnum.INSECT, 0, obj);
        CardResource cardInsect2 = new CardResource(3, frontTmp2, backTmp, SuitEnum.INSECT, 0, obj);
        CardResource cardMix = new CardResource(4, frontTmp3, backTmp, SuitEnum.INSECT, 0, obj);
        CardResource cardMix2 = new CardResource(5, frontTmp3, backTmp, SuitEnum.INSECT, 0, obj);
        cardAnimal1.setPlayingBack(false);
        cardAnimal2.setPlayingBack(false);
        cardInsect1.setPlayingBack(false);
        cardInsect2.setPlayingBack(false);
        cardMix.setPlayingBack(false);
        cardMix2.setPlayingBack(true);
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);
        CardStarting cardStarting = new CardStarting(6, backTmp, backTmp, suitList);
        station.getMap().put(creatingCordinatesArray(40, 40), cardStarting);
        station.updateCounters(cardStarting);
        station.getMap().put(creatingCordinatesArray(41, 41), cardAnimal1);
        station.updateCounters(cardAnimal1);
        station.getMap().get(creatingCordinatesArray(40, 40)).getFront().getUpRight().setCovered(true);
        station.updateCounters(station.getMap().get(creatingCordinatesArray(40, 40)).getFront().getUpRight());
        station.getMap().put(creatingCordinatesArray(42, 42), cardAnimal2);
        station.updateCounters(cardAnimal2);
        station.getMap().get(creatingCordinatesArray(41, 41)).getFront().getUpRight().setCovered(true);
        station.updateCounters(station.getMap().get(creatingCordinatesArray(41, 41)).getFront().getUpRight());
        station.getMap().put(creatingCordinatesArray(43, 43), cardInsect1);
        station.updateCounters(cardInsect1);
        station.getMap().get(creatingCordinatesArray(42, 42)).getFront().getUpRight().setCovered(true);
        station.updateCounters(station.getMap().get(creatingCordinatesArray(42, 42)).getFront().getUpRight());
        station.getMap().put(creatingCordinatesArray(44, 44), cardInsect2);
        station.updateCounters(cardInsect2);
        station.getMap().get(creatingCordinatesArray(43, 43)).getFront().getUpRight().setCovered(true);
        station.updateCounters(station.getMap().get(creatingCordinatesArray(43, 43)).getFront().getUpRight());
        station.getMap().put(creatingCordinatesArray(45, 45), cardMix);
        station.updateCounters(cardMix);
        station.getMap().get(creatingCordinatesArray(44, 44)).getFront().getUpRight().setCovered(true);
        station.updateCounters(station.getMap().get(creatingCordinatesArray(44, 44)).getFront().getUpRight());
        station.getMap().put(creatingCordinatesArray(46, 46), cardMix2);
        station.updateCounters(cardMix2);
        station.getMap().get(creatingCordinatesArray(45, 45)).getFront().getUpRight().setCovered(true);
        station.updateCounters(station.getMap().get(creatingCordinatesArray(45, 45)).getFront().getUpRight());
        assertEquals(3, station.getCountAnimal());
        assertEquals(5, station.getCountPlant());
        assertEquals(6, station.getCountInsect());
        assertEquals(2, station.getCountFungi());
        assertEquals(0, station.getCountQuill());
        assertEquals(1, station.getCountInkwell());
        assertEquals(2, station.getCountManuscript());


    }

    @Test
    void getMap() {
        HashMap<ArrayList<Integer>, CardPlaying> map = new HashMap<>();
        station.setMap(map);
        assertEquals(map, station.getMap());
    }

    @Test
    void getCardStarting() {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);
        CardStarting cardStarting = new CardStarting(6, backTmp, backTmp, suitList);
        station.setCardStarting(cardStarting, "isa");
        assertEquals(cardStarting, station.getCardStarting());
    }

    @Test
    void getCoordinates() {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp1 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT));
        Points obj = new ObjectiveAssign();
        CardResource cardAnimal1 = new CardResource(0, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, obj);
        station.getMap().put(creatingCordinatesArray(41, 41), cardAnimal1);
        assertEquals(creatingCordinatesArray(41, 41), station.getCoordinates(cardAnimal1));
    }


    @Test
    void getCard() {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp1 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT));
        Points obj = new ObjectiveAssign();
        CardResource cardAnimal1 = new CardResource(0, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, obj);
        station.getMap().put(creatingCordinatesArray(41, 41), cardAnimal1);
        assertEquals(cardAnimal1, station.getCard(41, 41));

    }


    @Test
    void enoughResources() throws InvalidPlacingCondition {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp1 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT));
        Face frontTmp2 = new Face(new Corner(SuitEnum.FUNGI), new Corner(SuitEnum.INSECT), new Corner(SuitEnum.FUNGI), new Corner(SuitEnum.INSECT));
        Face frontTmp3 = new Face(new Corner(SuitEnum.QUILL), new Corner(SuitEnum.MANUSCRIPT), new Corner(SuitEnum.INKWELL), new Corner(SuitEnum.MANUSCRIPT));
        Points obj = new ObjectiveAssign();
        CardResource cardAnimal1 = new CardResource(0, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardAnimal2 = new CardResource(1, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardInsect1 = new CardResource(2, frontTmp2, backTmp, SuitEnum.INSECT, 0, obj);
        CardResource cardInsect2 = new CardResource(3, frontTmp2, backTmp, SuitEnum.INSECT, 0, obj);
        CardResource cardMix = new CardResource(4, frontTmp3, backTmp, SuitEnum.INSECT, 0, obj);
        CardResource cardMix2 = new CardResource(5, frontTmp3, backTmp, SuitEnum.INSECT, 0, obj);
        cardAnimal1.setPlayingBack(false);
        cardAnimal2.setPlayingBack(false);
        cardInsect1.setPlayingBack(false);
        cardInsect2.setPlayingBack(false);
        cardMix.setPlayingBack(false);
        cardMix2.setPlayingBack(true);
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);
        CardStarting cardStarting = new CardStarting(6, backTmp, backTmp, suitList);
        station.getMap().put(creatingCordinatesArray(40, 40), cardStarting);
        station.updateCounters(cardStarting);
        station.getMap().put(creatingCordinatesArray(41, 41), cardAnimal1);
        station.updateCounters(cardAnimal1);
        station.getMap().get(creatingCordinatesArray(40, 40)).getFront().getUpRight().setCovered(true);
        station.updateCounters(station.getMap().get(creatingCordinatesArray(40, 40)).getFront().getUpRight());
        station.getMap().put(creatingCordinatesArray(42, 42), cardAnimal2);
        station.updateCounters(cardAnimal2);
        station.getMap().get(creatingCordinatesArray(41, 41)).getFront().getUpRight().setCovered(true);
        station.updateCounters(station.getMap().get(creatingCordinatesArray(41, 41)).getFront().getUpRight());
        station.getMap().put(creatingCordinatesArray(43, 43), cardInsect1);
        station.updateCounters(cardInsect1);
        station.getMap().get(creatingCordinatesArray(42, 42)).getFront().getUpRight().setCovered(true);
        station.updateCounters(station.getMap().get(creatingCordinatesArray(42, 42)).getFront().getUpRight());
        station.getMap().put(creatingCordinatesArray(44, 44), cardInsect2);
        station.updateCounters(cardInsect2);
        station.getMap().get(creatingCordinatesArray(43, 43)).getFront().getUpRight().setCovered(true);
        station.updateCounters(station.getMap().get(creatingCordinatesArray(43, 43)).getFront().getUpRight());
        station.getMap().put(creatingCordinatesArray(45, 45), cardMix);
        station.updateCounters(cardMix);
        station.getMap().get(creatingCordinatesArray(44, 44)).getFront().getUpRight().setCovered(true);
        station.updateCounters(station.getMap().get(creatingCordinatesArray(44, 44)).getFront().getUpRight());
        station.getMap().put(creatingCordinatesArray(46, 46), cardMix2);
        station.updateCounters(cardMix2);
        station.getMap().get(creatingCordinatesArray(45, 45)).getFront().getUpRight().setCovered(true);
        station.updateCounters(station.getMap().get(creatingCordinatesArray(45, 45)).getFront().getUpRight());

        CardGold cardGold = new CardGold(12, frontTmp3, backTmp, SuitEnum.ANIMAL, 2,3, 1, 2, 1,obj);
        CardGold cardGold2 = new CardGold(12, frontTmp3, backTmp, SuitEnum.ANIMAL, 2,30, 10, 20, 10,obj);
        assertEquals(true, station.enoughResources(cardGold));
        assertEquals(false, station.enoughResources(cardGold2));

        assertEquals(2, station.addCard(cardGold, 47, 47, false, "isa"));
    }


    @Test
    void isPlayable() throws InvalidPlacingCondition {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp1 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT));
        Points obj = new ObjectiveAssign();
        CardResource cardAnimal1 = new CardResource(0, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardAnimal2 = new CardResource(0, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, obj);
        station.getMap().put(creatingCordinatesArray(40,40), cardAnimal2);
        station.getMap().put(creatingCordinatesArray(40,42), cardAnimal2);
        station.getMap().put(creatingCordinatesArray(42,42), cardAnimal2);
        LinkedHashMap<ArrayList<Integer>, Boolean> numCornerCovered = new LinkedHashMap<>();
        ArrayList<Integer> coordinates1 = new ArrayList<>();
        coordinates1.add(0, 40);
        coordinates1.add(1,40);
        numCornerCovered.put(coordinates1, true);
        ArrayList<Integer> coordinates2 = new ArrayList<>();
        coordinates2.add(0, 42);
        coordinates2.add(1,40);
        numCornerCovered.put(coordinates2,false);
        ArrayList<Integer> coordinates3 = new ArrayList<>();
        coordinates3.add(0, 42);
        coordinates3.add(1,42);
        numCornerCovered.put(coordinates3, true);
        ArrayList<Integer> coordinates4 = new ArrayList<>();
        coordinates4.add(0, 40);
        coordinates4.add(1,42);
        numCornerCovered.put(coordinates4, true);

        assertEquals(numCornerCovered, station.isPlayable(false, cardAnimal1, 41, 41));

    }

    @Test
    void setCardStarting() {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);
        CardStarting cardStarting = new CardStarting(6, backTmp, backTmp, suitList);
        station.setCardStarting(cardStarting, "isa");
        assertEquals(cardStarting, station.getCardStarting());
    }

    @Test
    void isPlayable_nocards() throws InvalidPlacingCondition {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp1 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT));
        Points obj = new ObjectiveAssign();
        CardResource cardAnimal1 = new CardResource(0, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, obj);
        station.getMap().put(creatingCordinatesArray(40,40), cardAnimal1);
        Boolean thrown = false;
        try {
            station.isPlayable(false, cardAnimal1, 42,41);
        } catch (InvalidPlacingCondition e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    void isPlayable_downrightnull(){
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp1 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.NULL), new Corner(SuitEnum.PLANT));
        Points obj = new ObjectiveAssign();
        CardResource cardAnimal1 = new CardResource(0, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, obj);
        station.getMap().put(creatingCordinatesArray(41,41), cardAnimal1);
        Boolean thrown = false;
        try {
            station.isPlayable(false, cardAnimal1, 42,42);
        } catch (InvalidPlacingCondition e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    void isPlayable_uprightnull(){
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp1 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT));
        Points obj = new ObjectiveAssign();
        CardResource cardAnimal1 = new CardResource(0, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardAnimal2 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 0, obj);
        station.getMap().put(creatingCordinatesArray(43,43), cardAnimal1);
        Boolean thrown = false;
        try {
            station.isPlayable(false, cardAnimal2, 42,42);
        } catch (InvalidPlacingCondition e) {
            thrown = true;
        }
        assertTrue(thrown);

    }

    @Test
    void isPlayable_downleftnull(){
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp1 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT));
        Points obj = new ObjectiveAssign();
        CardResource cardAnimal1 = new CardResource(0, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardAnimal2 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 0, obj);
        station.getMap().put(creatingCordinatesArray(41,43), cardAnimal1);
        Boolean thrown = false;
        try {
            station.isPlayable(false, cardAnimal2, 42,42);
        } catch (InvalidPlacingCondition e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    void isPlayable_upleftnull(){
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp1 = new Face(new Corner(SuitEnum.NULL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT));
        Points obj = new ObjectiveAssign();
        CardResource cardAnimal1 = new CardResource(0, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource cardAnimal2 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 0, obj);
        station.getMap().put(creatingCordinatesArray(43,41), cardAnimal1);
        Boolean thrown = false;
        try {
            station.isPlayable(false, cardAnimal2, 42,42);
        } catch (InvalidPlacingCondition e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
    @Test
    void isPlayable_2cornersamecard() throws InvalidPlacingCondition {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp1 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT));
        Points obj = new ObjectiveAssign();
        CardResource cardAnimal1 = new CardResource(0, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, obj);
        station.getMap().put(creatingCordinatesArray(40,40), cardAnimal1);
        station.getMap().put(creatingCordinatesArray(40,41), cardAnimal1);
        Boolean thrown = false;
        try {
            station.isPlayable(false, cardAnimal1, 41,40);
        } catch (InvalidPlacingCondition e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
    @Test
    void addCard() throws InvalidPlacingCondition {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp1 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT));
        Points obj = new ObjectiveAssign();
        CardResource cardAnimal1 = new CardResource(0, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, obj);
        station.getMap().put(creatingCordinatesArray(40,40), cardAnimal1);
        station.updateCounters(cardAnimal1);
        station.getMap().put(creatingCordinatesArray(40,42), cardAnimal1);
        station.updateCounters(cardAnimal1);
        station.getMap().put(creatingCordinatesArray(42,42), cardAnimal1);
        station.updateCounters(cardAnimal1);
        station.getMap().put(creatingCordinatesArray(42,40), cardAnimal1);
        station.updateCounters(cardAnimal1);
        CardGold cardAdd = new CardGold(12, frontTmp1, backTmp, SuitEnum.ANIMAL, 2, 3,0,0,0,obj);
        assertEquals(2, station.addCard(cardAdd, 41,41,false, "isa"));

    }
    @Test
    void setCardStartingPlayedBack(){
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);
        CardStarting cardStarting = new CardStarting(6, backTmp, backTmp, suitList);
        station.setCardStarting(cardStarting, "isa");
        station.setCardStartingPlayedBack("isa", true);
        assertEquals(true, station.getMap().get(creatingCordinatesArray(40,40)).getPlayingBack());
    }

}