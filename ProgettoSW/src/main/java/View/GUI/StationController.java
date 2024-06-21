package View.GUI;

import Network.Client.ClientController;
import Socket.Messages.SelectionOfSecretObjMessage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.cards.*;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.SuitEnum;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class StationController implements Initializable {


    private ClientController clientController;

    @FXML
    private HBox cardPlacementBox;

    @FXML
    private FlowPane centralCardsAndDecksPane;

    @FXML
    private ImageView centralGoldImage2;

    @FXML
    private ImageView centralResourceImage1;

    @FXML
    private ImageView centralResourceImage2;

    @FXML
    private ImageView centralGoldImage1;

    @FXML
    private ImageView commonObjectiveImage1;

    @FXML
    private ImageView commonObjectiveImage2;

    @FXML
    private ImageView deckGoldImage;

    @FXML
    private ImageView deckObjectivesImage;

    @FXML
    private ImageView deckResourceImage;

    @FXML
    private Button chatButton;

    @FXML
    private ImageView chooseCard1;

    @FXML
    private ImageView chooseCard2;

    @FXML
    private HBox chooseCardBox;

    @FXML
    private Button endTurnButton;

    @FXML
    private ImageView firstCardInHand;

    @FXML
    private VBox handPane;

    @FXML
    private VBox menuPane;

    @FXML
    private Button placeCardDownLeftButton;

    @FXML
    private Button placeCardUpLeftButton;

    @FXML
    private Button placeCardUpRightButton;

    @FXML
    private Button placeCardDownRightButton;

    @FXML
    private Button player1StationButton;

    @FXML
    private Button player2StationButton;

    @FXML
    private Button player3StationButton;

    @FXML
    private Button scoreboardButton;

    @FXML
    private ImageView secondCardInHand;

    @FXML
    private ImageView secretObjectiveInHand;

    @FXML
    private Button selectCardButton;

    @FXML
    private StackPane stationPane;

    @FXML
    private ImageView thirdCardInHand;

    @FXML
    private Button turnCardButton;

    private Card activeCard;

    private boolean playedBack;

    //una mappa per salvarmi le carte che ho nella station
    private Map<ImageView, Card> imageToCardPlayingHashMap = new HashMap<>();

    //una mappa per salvare le carte che posso selezionare (ovvero quelle che ho in mano)
    private Map<ImageView, Card> playableCardsHashMap = new HashMap<>();

    //una mappa per le carte che ho nella station

    private Map<ImageView, CardPlaying> cardsInStationMap = new HashMap<>();

    private CardLoader cardLoader;

    @FXML
    private ImageView startingCard;

    public StationController(){

    }

    public StationController(ClientController clientController) {
        this.clientController = clientController;
        cardLoader = new CardLoader();
    }


    /**
     * this method handles when a cards gets clicked. It sets activeCard to the clicked card.
     * @param event the mouse click event
     * */
    private void handleCardClick(MouseEvent event){
        //ottengo l'immagine che è stata cliccata
        ImageView selectedCard = (ImageView) event.getSource();
        //controllo se la carta fa parte delle SELECTABLECARDS
        //se lo è --> allora la gioco
        //--> mando messaggio che l'ho piazzata e la aggiungo allo stack pane
        if(imageToCardPlayingHashMap.containsKey(selectedCard)){
            activeCard = imageToCardPlayingHashMap.get(selectedCard);
            turnCardButton.setVisible(true);
            selectCardButton.setVisible(true);
            chooseCard1.setOnMouseClicked(this::chooseStartingCardToPlayClick);
            chooseCard2.setOnMouseClicked(this::chooseStartingCardToPlayClick);
            //imageToCardPlayingHashMap.remove(selectedCard);
            //tolgo l'immagine dalla mappa solo una volta che ho giocato una carta --> infatti potrei volere selezionare
            //più carte nello stesso turno
        }

    }

    /**
     * this method sets the side of the starting card
     * @param event mouse click
     */
    private void chooseStartingCardToPlayClick(MouseEvent event) {
            //ho scelto la carta che voglio giocare
            //Devo fare due cose: 1) dire al controller che ho scelto la carta da piazzare 2) la devo aggiungere al pane
            //1)
            ImageView selectedCard = (ImageView) event.getSource();

            if(imageToCardPlayingHashMap.containsKey(selectedCard) && selectedCard.equals(chooseCard1)){
                //invio al controller che ho giocato la carta 1
                //devo piazzare
            } else if(imageToCardPlayingHashMap.containsKey(selectedCard) && selectedCard.equals(chooseCard2)) {
                //invio al controller che ho giocato la carta in bakc
            }

            chooseCard1.setImage(null);
            chooseCard2.setImage(null);
        }

    /**
     * this method sets the secret objective
     * @param event
     */
    @FXML
        void objectiveChosen(MouseEvent event){
            ImageView selectedCard = (ImageView) event.getSource();

            if(selectedCard.equals(chooseCard1)){
                //mando messaggio che il giocatore ha scelto il primo obiettivo
                //clientController.messageToServerhandler(new SelectionOfSecretObjMessage(0)); //cosa vuole la position??
            } else{
                //clientController.messageToServerhandler(new SelectionOfSecretObjMessage(1));
            }
            //tolgo la carta dalla mappa
            secretObjectiveInHand.setImage(selectedCard.getImage());
            chooseCard1.setImage(null);
            chooseCard2.setImage(null);
        }

    /**
     * this method handles the choice of the card to be played
     * @param event
     */
    @FXML
        void chooseCardToPlayCLicked(MouseEvent event){
            ImageView selectedCard = (ImageView) event.getSource();

            if(selectedCard.equals(firstCardInHand)) {
                //messaggio che ho deciso di giocare quella carta
            } else if (selectedCard.equals(secondCardInHand)) {
                //gioco seconda carta
            } else if(selectedCard.equals(thirdCardInHand)) {
                //gioco la terza
            }
            //nella label scrivo: seleziona la carta su cui vuoi piazzare
            //adesso tutte e sole le carte selezionabili devono essere quelle "in gioco"
            //mappa delle carte in gioco? così che puoi selezionare solo quelle per poter piazzarci
            //solo una volta premuta quella carta allora mostro i bottoni di piazzamento.
            cardPlacementBox.setVisible(true);
            placeCardDownRightButton.setOnMouseClicked(this::chooseCardPlacement);
            placeCardDownLeftButton.setOnMouseClicked(this::chooseCardPlacement);
            placeCardUpLeftButton.setOnMouseClicked(this::chooseCardPlacement);
            placeCardUpRightButton.setOnMouseClicked(this::chooseCardPlacement);

        }

    /**
     * this method handles the placement of the card in the station
     * @param event mouse click on the corresponding button
     */
    private void chooseCardPlacement(MouseEvent event){
            Button buttonPressed = (Button) event.getSource();
            if(buttonPressed.equals(placeCardDownLeftButton)){
                //mando messaggio che voglio giocare in basso a sinistra
            } else if(buttonPressed.equals(placeCardDownRightButton)){

            } else if(buttonPressed.equals(placeCardUpLeftButton)) {

            } else if((buttonPressed.equals(placeCardUpRightButton))) {

            }
        }

        /**
         * this method shows the starting card to the player.
         *
         */
        public void showStartingCard() {
            CardStarting cardStarting =  clientController.getClientModel().getMyplayer().getStation().getCardStarting();

            //creo le nuove immagini
            ImageView startingCardFront = new ImageView();
            ImageView startingCardBack = new ImageView();
            //load del front e back
            startingCardFront.setImage(cardLoader.getFront(cardStarting.getId()));
            startingCardBack.setImage(cardLoader.getBack(cardStarting.getId()));
            //setto il front e il back della carta iniziale nelle posizioni da cui scegliere
            chooseCard1.setImage(startingCardFront.getImage());
            chooseCard2.setImage(startingCardBack.getImage());
            imageToCardPlayingHashMap.put(startingCardFront, cardStarting);
            imageToCardPlayingHashMap.put(startingCardBack, cardStarting);
            //aggiungo alle immagini gli handler -> se immagine premuta viene scelta la carta corrispondente
            // mi tengo mappa (o comunque un riferimento dall'immagine alla carta)
            //mando messaggio al controller (con un thread a parte) che ho scelto la mia carta
            startingCardBack.setOnMouseClicked(this::handleCardClick);
            startingCardFront.setOnMouseClicked(this::handleCardClick);
            imageToCardPlayingHashMap.put(startingCardFront, cardStarting);
            //ho ottenuto l'immagine della carta --> devo metterla nel centro della station
            //questo lo faccio una volta che la carta viene premuta
        }

    /**
     * this method shows the common objectives
     */
    public void showCommonObjectives(){
            CardObjective commonObjective1 = clientController.getClientModel().getFirstObjective();
            CardObjective commonObjective2 = clientController.getClientModel().getSecondObjective();
            commonObjectiveImage1.setImage(cardLoader.getFront(commonObjective1.getId()));
            commonObjectiveImage2.setImage(cardLoader.getFront(commonObjective2.getId()));
        }


    /**
     * this method shows the two objective the player can choose from
     */
    public void showSelectableObjectives(){
            CardObjective selectableObj1 = clientController.getClientModel().getMyplayer().getSelectibleObjectives().getFirst();
            CardObjective selectableObj2 = clientController.getClientModel().getMyplayer().getSelectibleObjectives().getLast();

            chooseCard1.setImage(cardLoader.getFront(selectableObj1.getId()));
            chooseCard2.setImage(cardLoader.getFront(selectableObj2.getId()));
            //dovrei avere un qualcosa del tipo:
            chooseCard1.setOnMouseClicked(this::objectiveChosen);
        }


    /**
     * this method shows the central cards and the decks
     */
    public void showCentralCardsAndDecks(){
            CardResource centralResourceCard1 = clientController.getClientModel().getCentralCardsResource().getFirst();
            CardResource centralResourceCard2 = clientController.getClientModel().getCentralCardsResource().getLast();
            CardGold centralGoldCard1 = clientController.getClientModel().getCentralCardsGold().getFirst();
            CardGold centralGoldCard2 = clientController.getClientModel().getCentralCardsGold().getLast();

            centralResourceImage1.setImage(cardLoader.getFront(centralResourceCard1.getId(), centralResourceCard1.getSymbol()));
            centralResourceImage2.setImage(cardLoader.getFront(centralResourceCard2.getId(), centralResourceCard2.getSymbol()));
            centralGoldImage1.setImage(cardLoader.getFront(centralGoldCard1.getId(), centralGoldCard1.getSymbol()));
            centralGoldImage2.setImage(cardLoader.getFront(centralGoldCard2.getId(), centralGoldCard2.getSymbol()));
            deckGoldImage.setImage(cardLoader.getTopDeckGold(clientController.getClientModel().getBackOfGoldDeck()));
            deckResourceImage.setImage(cardLoader.getTopDeckResource(clientController.getClientModel().getBackOfResourceDeck()));

            //qua si deve usare il chooseCardToDrawClicked
        }

    /**
     * this method shows the player hand
     */
    public void showPlayerHand(){
            CardResource cardInHand1 = clientController.getClientModel().getMyplayer().getHand().getFirst();
            CardResource cardInHand2 = clientController.getClientModel().getMyplayer().getHand().get(1);
            CardResource cardInHand3 = clientController.getClientModel().getMyplayer().getHand().get(2);
            CardObjective secretObjective = clientController.getClientModel().getMyplayer().getSecretObjective();

            firstCardInHand.setImage(cardLoader.getFront(cardInHand1.getId(), cardInHand1.getSymbol()));
            secondCardInHand.setImage(cardLoader.getFront(cardInHand2.getId(), cardInHand2.getSymbol()));
            thirdCardInHand.setImage(cardLoader.getFront(cardInHand3.getId(), cardInHand3.getSymbol()));
            if(secretObjective!= null) {
                secretObjectiveInHand.setImage(cardLoader.getFront(secretObjective.getId()));

            }

            firstCardInHand.setOnMouseClicked(this::chooseCardToPlayCLicked);
            secondCardInHand.setOnMouseClicked(this::chooseCardToPlayCLicked);
            thirdCardInHand.setOnMouseClicked(this::chooseCardToPlayCLicked);

        }


    public void testCardStart(){
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp2 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);
        CardStarting cardStarting = new CardStarting(3, frontTmp2, backTmp, suitList);
        //creo le nuove immagini
        CardLoader cardLoader1 = new CardLoader();
        ImageView startingCardFront = new ImageView();
        ImageView startingCardBack = new ImageView();
        //load del front e back
        startingCardFront.setImage(cardLoader1.getFront(cardStarting.getId()));
        startingCardBack.setImage(cardLoader1.getBack(cardStarting.getId()));
        //setto il front e il back della carta iniziale nelle posizioni da cui scegliere
        chooseCard1.setImage(startingCardFront.getImage());
        chooseCard2.setImage(startingCardBack.getImage());
        chooseCard1.setVisible(true);
        chooseCard2.setVisible(true);
        imageToCardPlayingHashMap.put(startingCardFront, cardStarting);
        imageToCardPlayingHashMap.put(startingCardBack, cardStarting);
        //aggiungo alle immagini gli handler -> se immagine premuta viene scelta la carta corrispondente
        // mi tengo mappa (o comunque un riferimento dall'immagine alla carta)
        //mando messaggio al controller (con un thread a parte) che ho scelto la mia carta
        startingCardBack.setOnMouseClicked(this::handleCardClick);
        startingCardFront.setOnMouseClicked(this::handleCardClick);
        imageToCardPlayingHashMap.put(startingCardFront, cardStarting);
    }

//TODO: una volta che una carta viene giocata, toglierla dalla mappa! non deve più essere selezionabile e girata.
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            centralCardsAndDecksPane.setVisible(false);
            endTurnButton.setVisible(false);
            handPane.setVisible(false);
            cardPlacementBox.setVisible(false);
            stationPane.setVisible(false);
            testCardStart();
        }
}
