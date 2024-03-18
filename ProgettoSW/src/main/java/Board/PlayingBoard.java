package Board;

import Players.Player;
import cards.CardGold;
import cards.CardObjective;
import cards.CardResource;
import cards.CardStarting;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlayingBoard {

    private Map<String, CardGold> deckCardGold;
    private Map<String, CardResource> deckCardResource;
    private Map<String, CardObjective> deckCardObjective;
    private Map<String, CardStarting> deckCardStarting;
    private Map<Integer, Player> playerMap;
    private CardResource CentralFirstCard;
    private CardResource CentralSecondCard;
    private CardResource CentralThirdCard;
    private CardResource CentralFourthCard;
    private CardObjective FirstObjective;
    private CardObjective SecondObjective;


    //Constructor
    public PlayingBoard(CardResource centralFirstCard, CardResource centralSecondCard, CardResource centralThirdCard, CardResource centralFourthCard, CardObjective firstObjective, CardObjective secondObjective) {
        deckCardGold = new HashMap<>();
        deckCardResource = new HashMap<>();
        deckCardObjective = new HashMap<>();
        deckCardStarting = new HashMap<>();
        playerMap = new HashMap<>();
        CentralFirstCard = centralFirstCard;
        CentralSecondCard = centralSecondCard;
        CentralThirdCard = centralThirdCard;
        CentralFourthCard = centralFourthCard;
        FirstObjective = firstObjective;
        SecondObjective = secondObjective;
    }


    //-------------------GETTER-----------------------------
    public Map<String, CardGold> getDeckCardGold() {
        return deckCardGold;
    }

    public Map<String, CardObjective> getDeckCardObjective() {
        return deckCardObjective;
    }

    public Map<String, CardResource> getDeckCardResource() {
        return deckCardResource;
    }

    public Map<String, CardStarting> getDeckCardStarting() {
        return deckCardStarting;
    }

    public CardResource getCentralSecondCard() {
        return CentralSecondCard;
    }

    public CardResource getCentralFourthCard() {
        return CentralFourthCard;
    }

    public CardResource getCentralFirstCard() {
        return CentralFirstCard;
    }

    public CardResource getCentralThirdCard() {
        return CentralThirdCard;
    }

    public CardObjective getFirstObjective() {
        return FirstObjective;
    }

    public CardObjective getSecondObjective() {
        return SecondObjective;
    }
    //--------------------GETTING FASE ENDED----------------------------



    //--------------------SETTER----------------------------
    public void setCentralFirstCard(CardResource centralFirstCard) {
        CentralFirstCard = centralFirstCard;
    }

    public void setCentralThirdCard(CardResource centralThirdCard) {
        CentralThirdCard = centralThirdCard;
    }

    public void setCentralSecondCard(CardResource centralSecondCard) {
        CentralSecondCard = centralSecondCard;
    }

    public void setCentralFourthCard(CardResource centralFourthCard) {
        CentralFourthCard = centralFourthCard;
    }

    //Setter that initializes the player map (randomly selects the first player and assigns turns to all other players clockwise)
    public void setPlayer(Player[] players, int size) {

        // Generating a random number between 0 and the length of the vector
        Random random = new Random();
        int N = random.nextInt(size);

        //Adding players starting from position N-1
        for (int i = N-1; i < size; i++) {
            playerMap.put(i - N + 2, players[i]);
        }

        //Adding players from position 0 to position N-2
        for (int i = 0; i < N-1; i++) {
            playerMap.put(i + size - N + 1, players[i]);
        }
    }

    //--------------------SETTING FASE ENDED----------------------------




    CardStarting[] drawCardStarting(){

    }

    CardObjective[] pickTwoObjectives(){

    }

}
