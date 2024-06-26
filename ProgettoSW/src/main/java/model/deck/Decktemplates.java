package model.deck;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.cards.face.Face;
import model.cards.face.Corner;
import model.enums.DirectionEnum;
import model.enums.PositionEnum;
import model.enums.SuitEnum;
import model.objectives.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static java.util.Collections.shuffle;

// create a static class that has for each deck a static method that returns the List of all model.cards that need to be in that deck
public class Decktemplates implements Serializable {
  // creating the DeckResource Deck
  // each line in the text represent the card:
  // we have in order: suit of the card, the four corner(starting from upright)
  // and the points

//  there are 40 cards in the deck
//  the id starts from 1 and goes to 40




  /**
   * This method creates a deck of resource cards (id from 1 to 40)
   * @return a LinkedList of CardResource
   */
  public static LinkedList<CardResource> ResourceCardDeck() {
    int id =1;
    LinkedList<CardResource> deck = new LinkedList<>();
    JSONParser parser = new JSONParser();
      JSONArray resourceCard = null;
      try {
          resourceCard = (JSONArray) parser
              .parse(new FileReader("src/main/java/model/deck/resourceDeck.json"));
      } catch (IOException | ParseException e) {
          throw new RuntimeException(e);
      }
      for (Object obj : resourceCard) {
      JSONObject card = (JSONObject) obj;
      String suite = (String) card.get("type");
      String upright = (String) card.get("upright");
      String upleft = (String) card.get("upleft");
      String downright = (String) card.get("downright");
      String downleft = (String) card.get("downleft");
      int points = ((Long) card.get("points")).intValue();
      Face back = new Face(AssignCorner("empty"), AssignCorner("empty"), AssignCorner("empty"), AssignCorner("empty"));
      Face front = new Face(AssignCorner(upright), AssignCorner(upleft), AssignCorner(downright),
          AssignCorner(downleft));
      CardResource tmp = new CardResource(id, front, back, AssignSuit(suite), points, AssignObjective("points"));
      deck.add(tmp);
      id++;

    }
    //i shuffle the deck before returning it
    shuffle(deck);
    return deck;
  }



  /**
   * This method creates a deck of gold cards ( id from 41 to 80)
   * @return a LinkedList of CardGold
   */
  public static LinkedList<CardGold> GoldCardDeck() {
    int id = 41;
    LinkedList<CardGold> deck = new LinkedList<>();
    JSONParser parser = new JSONParser();
      JSONArray goldCard = null;
      try {
          goldCard = (JSONArray) parser.parse(new FileReader("src/main/java/model/deck/GoldDeck.json"));
      } catch (IOException | ParseException e) {
          throw new RuntimeException(e);
      }
      for (Object obj : goldCard) {
      JSONObject card = (JSONObject) obj;
      String suite = (String) card.get("type");
      String upright = (String) card.get("upright");
      String upleft = (String) card.get("upleft");
      String downright = (String) card.get("downright");
      String downleft = (String) card.get("downleft");
      String center = (String) card.get("center");
      int points = ((Long) card.get("points")).intValue();
      int costAnimal = ((Long) card.get("costAnimal")).intValue();
      int costInsect = ((Long) card.get("costInsect")).intValue();
      int costFungi = ((Long) card.get("costFungi")).intValue();
      int costPlant = ((Long) card.get("costPlant")).intValue();
      Face back = new Face(AssignCorner("empty"), AssignCorner("empty"), AssignCorner("empty"), AssignCorner("empty"));
      Face front = new Face(AssignCorner(upright), AssignCorner(upleft), AssignCorner(downright),
          AssignCorner(downleft));
      CardGold tmp = new CardGold(id, front, back, AssignSuit(suite), points, costAnimal, costInsect, costFungi,
          costPlant, AssignObjective(center));
      deck.add(tmp);
      id++;

    }
    //i shuffle the deck before returning it
    shuffle(deck);
    return deck;
  }





  /**
   * This method creates a deck of starting cards (id from 81 to 86)
   * @return a LinkedList of CardStarting
   */
  public static LinkedList<CardStarting> StartingCardDeck() {
    int id =81;
    LinkedList<CardStarting> deck = new LinkedList<>();
    JSONParser parser = new JSONParser();
      JSONArray startingCard = null;
      try {
          startingCard = (JSONArray) parser
              .parse(new FileReader("src/main/java/model/deck/StartingDeck.json"));
      } catch (IOException | ParseException e) {
          throw new RuntimeException(e);
      }
      for (Object obj : startingCard) {
      ArrayList<SuitEnum> symbols = new ArrayList<>();
      JSONObject card = (JSONObject) obj;
      JSONArray suite = (JSONArray) card.get("suite");
      for (Object vecObject : suite) {
        symbols.add(AssignSuit((String) vecObject));
      }
      String uprightFront = (String) card.get("uprightFront");
      String upleftFront = (String) card.get("upleftFront");
      String downrightFront = (String) card.get("downrightFront");
      String downleftFront = (String) card.get("downleftFront");
      String uprightBack = (String) card.get("uprightBack");
      String upleftBack = (String) card.get("upleftBack");
      String downrightBack = (String) card.get("downrightBack");
      String downleftBack = (String) card.get("downleftBack");
      Face back = new Face(AssignCorner(uprightBack), AssignCorner(upleftBack), AssignCorner(downrightBack),
          AssignCorner(downleftBack));
      Face front = new Face(AssignCorner(uprightFront), AssignCorner(upleftFront), AssignCorner(downrightFront),
          AssignCorner(downleftFront));
      CardStarting tmp = new CardStarting(id, front, back, symbols);
      deck.add(tmp);
      id++;

    }
    //i shuffle the deck before returning it
    shuffle(deck);
    return deck;
  }

  /**
   * This method creates a deck of objective cards (id from 87 to 102)
   * @return a LinkedList of CardObjective
   */
  public static LinkedList<CardObjective> ObjectiveCardDeck() {
    int id =87;
    LinkedList<CardObjective> deck = new LinkedList<>();
    JSONParser parser = new JSONParser();
      JSONArray objectiveCard = null;
      try {
          objectiveCard = (JSONArray) parser
              .parse(new FileReader("src/main/java/model/deck/ObjectiveCard.json"));
      } catch (IOException | ParseException e) {
          throw new RuntimeException(e);
      }
      for (Object obj : objectiveCard) {
        JSONObject card = (JSONObject) obj;
        String suite = (String) card.get("type");
        switch (suite) {
          case "diagonal" -> {
            String resource = (String) card.get("resource");
            String diagonal = (String) card.get("diagonal");
            int points = ((Long) card.get("points")).intValue();
            Objective objectiveDiagonal = new ObjectiveDiagonal(AssignDirection(diagonal), AssignSuit(resource));
            CardObjective tmp = new CardObjective(id, points, objectiveDiagonal);
            deck.add(tmp);
          }
          case "positioning" -> {
            String twoCards = (String) card.get("twoCards");
            String oneCard = (String) card.get("oneCard");
            String Horizontal = (String) card.get("horizontal");
            String Vertical = (String) card.get("vertical");
            int points = ((Long) card.get("points")).intValue();
            Objective objectivePositioning = new ObjectivePositioning(AssignSuit(oneCard), AssignSuit(twoCards),
                    AssignDirection(Horizontal), AssignPosition(Vertical));
            CardObjective tmp = new CardObjective(id, points, objectivePositioning);
            deck.add(tmp);
          }
          case "resources" -> {
            String resource = (String) card.get("resource");
            int points = ((Long) card.get("points")).intValue();
            Objective objectiveCountingResource = new ObjectiveCountingResource(AssignSuit(resource));
            CardObjective tmp = new CardObjective(id, points, objectiveCountingResource);
            deck.add(tmp);
          }
          case "gold" -> {
            String goldType = (String) card.get("goldType");
            int points = ((Long) card.get("points")).intValue();
            Objective objectiveCountingGold = AssignObjectives(goldType);
            CardObjective tmp = new CardObjective(id, points, objectiveCountingGold);
            deck.add(tmp);
          }
          default -> throw new IllegalStateException("Unexpected value: " + suite);
        }
        id++;
      }
    //i shuffle the deck before returning it
    shuffle(deck);
    return deck;
  }


  /**
   * This method assign specific suite to the corners of a specific card
   * @param s the suite of the corner
   * @return the corner with the specific suite
   */
  private static Corner AssignCorner(String s) {
    return switch (s) {
      case "empty" -> new Corner(SuitEnum.EMPTY);
      case "fungi" -> new Corner(SuitEnum.FUNGI);
      case "plant" -> new Corner(SuitEnum.PLANT);
      case "animal" -> new Corner(SuitEnum.ANIMAL);
      case "insect" -> new Corner(SuitEnum.INSECT);
      case "manuscript" -> new Corner(SuitEnum.MANUSCRIPT);
      case "inkwell" -> new Corner(SuitEnum.INKWELL);
      case "quill" -> new Corner(SuitEnum.QUILL);
      default -> new Corner(SuitEnum.NULL);
    };
  }




  /**
   * This method assign specific suite to the card
   * @param s the suite of the card
   * @return the suite of the card
   */
  private static SuitEnum AssignSuit(String s) {
    return switch (s) {
      case "fungi" -> SuitEnum.FUNGI;
      case "plant" -> SuitEnum.PLANT;
      case "animal" -> SuitEnum.ANIMAL;
      case "insect" -> SuitEnum.INSECT;
      case "empty" -> SuitEnum.EMPTY;
      case "null" -> SuitEnum.NULL;
      default -> throw new IllegalStateException("Unexpected value: " + s);
    };
  }


  /**
   * This method assign specific direction to the objective
   * @param s the direction of the objective
   * @return the direction of the objective
   */
  private static DirectionEnum AssignDirection(String s) {
    return switch (s) {
      case "left" -> DirectionEnum.LEFT;
      case "right" -> DirectionEnum.RIGHT;
      default -> throw new IllegalStateException("Unexpected value: " + s);
    };
  }


  /**
   * This method assign specific position to the objective
   * @param s the position of the objective
   * @return the position of the objective
   */
  private static PositionEnum AssignPosition(String s) {
    return switch (s) {
      case "top" -> PositionEnum.TOP.TOP;
      case "bottom" -> PositionEnum.BOTTOM;
      default -> throw new IllegalStateException("Unexpected value: " + s);
    };
  }


  /**
   * create a private method AssignOvbective that takes a string (null,corenrs or
   *  gold resource:manuscript,inkwell,quill) and create an object that is
   *   statically type Objective and dinamically type Objective,
   *    ObjectiveCountingResources or ObjectiveGoldCorners if the string is
   *    respectability null, corners or gold resource and return that objective
   * @param s
   * @return
   */
  private static Points AssignObjective(String s) {
    Points obj;
    return switch (s) {

      case "points" -> {
        obj = new ObjectiveAssign();
        yield obj;
      }
      case "corners" -> {
        obj = new ObjectiveGoldCorners();
        yield obj;
      }
      case "manuscript" -> {
        obj = new ObjectiveCountingGold(0, 1, 0);
        yield obj;
      }
      case "inkwell" -> {
        obj = new ObjectiveCountingGold(1, 0, 0);
        yield obj;
      }
      case "quill" -> {
        obj = new ObjectiveCountingGold(0, 0, 1);
        yield obj;
      }
      case "all" -> {
        obj = new ObjectiveCountingGold(1, 1, 1);
        yield obj;
      }
      default -> throw new IllegalStateException("Unexpected value: " + s);
    };
  }


  /**
   * This method assign specific objective to the gold card
   * @param goldType the type of the gold card
   * @return the objective of the gold card
   */
  private static Objective AssignObjectives(String goldType) {
    Objective obj;
    return switch (goldType) {
      case "manuscript" -> {
        obj = new ObjectiveCountingGold(0, 1, 0);
        yield obj;
      }
      case "inkwell" -> {
        obj = new ObjectiveCountingGold(1, 0, 0);
        yield obj;
      }
      case "quill" -> {
        obj = new ObjectiveCountingGold(0, 0, 1);
        yield obj;
      }
      case "all" -> {
        obj = new ObjectiveCountingGold(1, 1, 1);
        yield obj;
      }
      default -> throw new IllegalStateException("Unexpected value: " + goldType);
    };
  }

}
