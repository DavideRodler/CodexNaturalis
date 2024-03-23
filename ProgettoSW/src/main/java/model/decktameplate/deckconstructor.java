package model.decktameplate;

import com.fasterxml.jackson.core.type.TypeReference;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.cards.face.Corner;
import model.cards.face.CornerGold;
import model.cards.face.CornerResource;
import model.cards.face.Face;
import model.enums.Direction;
import model.enums.Position;
import model.enums.Suit;
import model.objectives.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// create a static class that has for each deck a static method that returns the List of all model.cards that need to be in that deck
public class deckconstructor {
    // creating the DeckResource Deck
    // each line in the text represent the card:
    // we have in order: suit of the card, the four corner(starting from upright) and the points
    private static int CardId;
    public static List<CardResource> DeckResource() {
        List<CardResource> deck = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<CardData> cards = objectMapper.readValue(new File("src/main/java/model/decktameplate/DeckResource.json"), new TypeReference<>() {});

            for (CardData cardData : cards) {
                Suit suit = AssignSuit(cardData.getType());
                Corner upright = AssignCorner(cardData.getUpright());
                Corner upleft = AssignCorner(cardData.getUpleft());
                Corner downright = AssignCorner(cardData.getDownright());
                Corner downleft = AssignCorner(cardData.getDownleft());
                Face front = new Face(upright, upleft, downright, downleft);
                Face back = new Face(new Corner(), new Corner(), new Corner(), new Corner());
                int point = cardData.getPoints();
                CardResource tmp = new CardResource(CardId, front, back, suit, point);
                deck.add(tmp);
                CardId++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deck;
    }

    public static List<CardStarting> StartingCardDeck(){
        List<CardStarting> deck = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<CardData> cards = objectMapper.readValue(new File("src/main/java/model/decktameplate/StartingDeck.json"), new TypeReference<>() {
            });

            for (CardData cardData : cards) {
                List<Suit> suits = cardData.getSuite().stream().map(deckconstructor::AssignSuit).collect(Collectors.toList());
                Corner upright = AssignCorner(cardData.getUpright());
                Corner upleft = AssignCorner(cardData.getUpleft());
                Corner downright = AssignCorner(cardData.getDownright());
                Corner downleft = AssignCorner(cardData.getDownleft());
                Face front = new Face(upright,upleft,downright,downleft);
                Face back = new Face(AssignCorner(cardData.getUpright()),AssignCorner(cardData.getUpleft()),AssignCorner(cardData.getDownright()), AssignCorner(cardData.getDownleft()));
                // Assuming the first suit in the list is the main suit for the card
                CardStarting tmp = new CardStarting(CardId,front,back,suits);
                deck.add(tmp);
                CardId++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deck;
    }

    public static List<CardGold> DeckGold() {
        List<CardGold> deck = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<CardData> cards = objectMapper.readValue(new File("src/main/java/model/decktameplate/GoldDeck.json"), new TypeReference<>() {
            });

            for (CardData cardData : cards) {
                Suit suit = AssignSuit(cardData.getType());
                Corner upright = AssignCorner(cardData.getUpright());
                Corner upleft = AssignCorner(cardData.getUpleft());
                Corner downright = AssignCorner(cardData.getDownright());
                Corner downleft = AssignCorner(cardData.getDownleft());
                Objective objective = AssignObjective(cardData.getObjective());
                int points = cardData.getPoints();
                int costAnimal = cardData.getCostAnimal();
                int costInsect = cardData.getCostInsect();
                int costFungi = cardData.getCostFungi();
                int costPlant = cardData.getCostPlant();
                Face front = new Face(upright, upleft, downright, downleft);
                Face back = new Face(new Corner(), new Corner(), new Corner(), new Corner());
                CardGold tmp = new CardGold(CardId, front, back, suit, points, costAnimal, costInsect, costFungi, costPlant, objective);
                deck.add(tmp);
                CardId++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deck;
    }

    public static List<Objective> deckObjective() {
        List<Objective> deck = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<CardData> objectives = objectMapper.readValue(new File("src/main/java/model/decktameplate/ObjectiveCard.json"), new TypeReference<>() {});

            for (CardData cardData : objectives) {
                Objective tmp;
                switch (cardData.getType()) {
                    case "diagonal":
                        Direction direction = Direction.valueOf(cardData.getDiagonal().toUpperCase());
                        Suit color = Suit.valueOf(cardData.getResource().toUpperCase());
                        tmp = new ObjectiveDiagonal(direction, color);
                        tmp.setPoints(2);
                        break;
                    case "positioning":
                        Suit colorOneCard = Suit.valueOf(cardData.getOneCard().toUpperCase());
                        Suit colorTwoCards = Suit.valueOf(cardData.getTwoCards().toUpperCase());
                        Direction horizontalDirection = Direction.valueOf(cardData.getHorizontal().toUpperCase());
                        Position verticalDirection = Position.valueOf(cardData.getVertical().toUpperCase());
                        tmp = new ObjectivePositioning(colorOneCard, colorTwoCards, horizontalDirection, verticalDirection);
                        tmp.setPoints(3);
                        break;
                    case "resources":
                        Suit symbol = Suit.valueOf(cardData.getResource().toUpperCase());
                        tmp = new ObjectiveCountingResource(symbol);
                        tmp.setPoints(2);
                        break;
                    case "gold":
                        String goldType = cardData.getGoldType();
                        switch (goldType) {
                            case "inkwell":
                                tmp = new ObjectiveCountingGold(2, 0, 0);
                                tmp.setPoints(2);
                                break;
                            case "manuscript":
                                tmp = new ObjectiveCountingGold(0, 2, 0);
                                tmp.setPoints(2);
                                break;
                            case "quill":
                                tmp = new ObjectiveCountingGold(0, 0, 2);
                                tmp.setPoints(2);
                                break;
                            case "all":
                                tmp = new ObjectiveCountingGold(1, 1, 1);
                                tmp.setPoints(3);
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid gold type: " + goldType);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid objective type: " + cardData.getType());
                }
                deck.add(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deck;
    }

    private static Corner AssignCorner(String s){
        return switch (s) {
            case "empty" -> new Corner();
            case "fungi" -> new CornerResource(Suit.FUNGI);
            case "plant" -> new CornerResource(Suit.PLANT);
            case "animal" -> new CornerResource(Suit.ANIMAL);
            case "insect" -> new CornerResource(Suit.INSECT);
            case "manuscript" -> new CornerGold(model.enums.GoldSuit.MANUSCRIPT);
            case "inkwell" -> new CornerGold(model.enums.GoldSuit.INKWELL);
            case "quill" -> new CornerGold(model.enums.GoldSuit.QUILL);
            default -> null;
        };
    }

    private static Suit AssignSuit(String s){
        return switch (s) {
            case "fungi" -> Suit.FUNGI;
            case "plant" -> Suit.PLANT;
            case "animal" -> Suit.ANIMAL;
            case "insect" -> Suit.INSECT;
            default -> null;
        };
    }

    //create a private method AssignOvbective that takes a string (null,corenrs or gold resource:manuscript,inkwell,quill) and create an object that is statically type Objective and dinamically type Objective, ObjectiveCountingResources or ObjectiveGoldCorners if the string is respectability null, corners or gold resource and return that objective


    private static Objective AssignObjective(String s){
        Objective obj;
        return switch (s) {
            case "null" -> {
                obj = new Objective();
                yield obj;
            }
            case "corners" -> {
                obj = new ObjectiveGoldCorners();
                yield obj;
            }
            case "manuscript" -> {
                obj = new ObjectiveCountingGold(0,1,0);
                yield obj;
            }
            case "inkwell" -> {
                obj = new ObjectiveCountingGold(1,0,0);
                yield obj;
            }
            case "quill" -> {
                obj = new ObjectiveCountingGold(0,0,1);
                yield obj;
            }
            default -> null;
        };
    }

    private static Objective AssignSymbol(String s){
        Objective obj;
        return switch (s) {
            case "diagonal" -> {
                obj = new ObjectiveDiagonal();
                yield obj;
            }
            case "corners" -> {
                obj = new ObjectiveGoldCorners();
                yield obj;
            }
            case "manuscript" -> {
                obj = new ObjectiveCountingGold(0,1,0);
                yield obj;
            }
            case "inkwell" -> {
                obj = new ObjectiveCountingGold(1,0,0);
                yield obj;
            }
            case "quill" -> {
                obj = new ObjectiveCountingGold(0,0,1);
                yield obj;
            }
            default -> null;
        };
    }

    private static Objective AssignSymbol(String s, Suit color, Direction direction){
        Objective obj;
        return switch (s) {
            case "diagonal" -> {
                obj = new ObjectiveDiagonal();
                yield obj;
            }
            case "corners" -> {
                obj = new ObjectiveGoldCorners();
                yield obj;
            }
            case "manuscript" -> {
                obj = new ObjectiveCountingGold(0,1,0);
                yield obj;
            }
            case "inkwell" -> {
                obj = new ObjectiveCountingGold(1,0,0);
                yield obj;
            }
            case "quill" -> {
                obj = new ObjectiveCountingGold(0,0,1);
                yield obj;
            }
            default -> null;
        };
    }






















}
