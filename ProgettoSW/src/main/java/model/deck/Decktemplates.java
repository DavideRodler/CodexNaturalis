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
  private static Integer ID;

  public static LinkedList<CardResource> ResourceCardDeck() {
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
      Face back = new Face(SuitEnum.EMPTY, SuitEnum.EMPTY, SuitEnum.EMPTY, SuitEnum.EMPTY);
      Face front = new Face(AssignCorner(upright), AssignCorner(upleft), AssignCorner(downright),
          AssignCorner(downleft));
      CardResource tmp = new CardResource(ID, front, back, AssignSuit(suite), points, AssignObjective("points"));
      deck.add(tmp);
      ID++;

    }
    //i shuffle the deck before returning it
    shuffle(deck);
    return deck;
  }

  public static LinkedList<CardGold> GoldCardDeck() {
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
      Face back = new Face(SuitEnum.EMPTY, SuitEnum.EMPTY, SuitEnum.EMPTY, SuitEnum.EMPTY);
      Face front = new Face(AssignCorner(upright), AssignCorner(upleft), AssignCorner(downright),
          AssignCorner(downleft));
      CardGold tmp = new CardGold(ID, front, back, AssignSuit(suite), points, costAnimal, costInsect, costFungi,
          costPlant, AssignObjective(center));
      deck.add(tmp);
      ID++;

    }
    //i shuffle the deck before returning it
    shuffle(deck);
    return deck;
  }

  public static LinkedList<CardStarting> StartingCardDeck() {
    ID=0;
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
      CardStarting tmp = new CardStarting(0, front, back, symbols);
      deck.add(tmp);
      ID++;

    }
    //i shuffle the deck before returning it
    shuffle(deck);
    return deck;
  }

  public static LinkedList<CardObjective> ObjectiveCardDeck() {
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
          CardObjective tmp = new CardObjective(ID, points, objectiveDiagonal);
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
          CardObjective tmp = new CardObjective(ID, points, objectivePositioning);
          deck.add(tmp);
        }
        case "resources" -> {
          String resource = (String) card.get("resource");
          int points = ((Long) card.get("points")).intValue();
          Objective objectiveCountingResource = new ObjectiveCountingResource(AssignSuit(resource));
          CardObjective tmp = new CardObjective(ID, points, objectiveCountingResource);
          deck.add(tmp);
        }
        case "gold" -> {
          String goldType = (String) card.get("goldType");
          int points = ((Long) card.get("points")).intValue();
          Objective objectiveCountingGold = AssignObjective(goldType);
          CardObjective tmp = new CardObjective(ID, points, objectiveCountingGold);
          deck.add(tmp);
        }
        default -> throw new IllegalStateException("Unexpected value: " + suite);
      }
      ID++;
    }
    //i shuffle the deck before returning it
    shuffle(deck);
    return deck;
  }

  private static SuitEnum AssignCorner(String s) {
    return switch (s) {
      case "empty" -> SuitEnum.EMPTY;
      case "fungi" -> SuitEnum.FUNGI;
      case "plant" -> SuitEnum.PLANT;
      case "animal" -> SuitEnum.ANIMAL;
      case "insect" -> SuitEnum.INSECT;
      case "manuscript" -> SuitEnum.MANUSCRIPT;
      case "inkwell" -> SuitEnum.INKWELL;
      case "quill" -> SuitEnum.QUILL;
      default -> SuitEnum.NULL;
    };
  }

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

  private static DirectionEnum AssignDirection(String s) {
    return switch (s) {
      case "left" -> DirectionEnum.LEFT;
      case "right" -> DirectionEnum.RIGHT;
      default -> throw new IllegalStateException("Unexpected value: " + s);
    };
  }

  private static PositionEnum AssignPosition(String s) {
    return switch (s) {
      case "top" -> PositionEnum.TOP;
      case "bottom" -> PositionEnum.BOTTOM;
      default -> throw new IllegalStateException("Unexpected value: " + s);
    };
  }

  // create a private method AssignOvbective that takes a string (null,corenrs or
  // gold resource:manuscript,inkwell,quill) and create an object that is
  // statically type Objective and dinamically type Objective,
  // ObjectiveCountingResources or ObjectiveGoldCorners if the string is
  // respectability null, corners or gold resource and return that objective

  private static Objective AssignObjective(String s) {
    Objective obj;
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

}
