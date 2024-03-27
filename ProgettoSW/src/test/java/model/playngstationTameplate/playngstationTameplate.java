package model.playngstationTameplate;

import java.util.ArrayList;

import model.PlayingStation;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.Direction;
import model.enums.Suit;
import model.objectives.ObjectiveDiagonal;

public class playngstationTameplate {
  // this method create a test table with three card of Sign ANIMAL in a left
  // diagonal from the starting card
  public static PlayingStation playngstationDiagonalConstructor() {

    // i create three card resource with an EMPTY back and some Suits in the front,
    // with all corner that can be covered
    Face frontTmp = new Face(new Corner(Suit.EMPTY), new Corner(Suit.EMPTY), new Corner(Suit.EMPTY),
        new Corner(Suit.EMPTY));
    Face backTmp = new Face(new Corner(Suit.ANIMAL), new Corner(Suit.PLANT), new Corner(Suit.EMPTY),
        new Corner(Suit.FUNGI));

    // the card resource is of type ANIMAL
    CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, Suit.ANIMAL, 0);
    CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, Suit.ANIMAL, 0);
    CardResource cardAnimal3 = new CardResource(2, frontTmp, backTmp, Suit.ANIMAL, 0);

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
    ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(Direction.LEFT, Suit.ANIMAL);
    CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);

    // for the second objective i set it to null
    PlayingStation station = new PlayingStation(cardStarting, cardObjectiveTmp, null);

    // now i have to populate the table
    station.addCard(cardAnimal1, 39, 39);
    station.addCard(cardAnimal2, 38, 38);
    station.addCard(cardAnimal3, 37, 37);

    return station;
  }

  public static void main(String[] args) {
    PlayingStation station = playngstationDiagonalConstructor();
    int punti = station.getFirstSecretObjective().getObjective().checkObjective(station);
    System.out.println("hai fatto " + punti + " diagonali");

  }
}
