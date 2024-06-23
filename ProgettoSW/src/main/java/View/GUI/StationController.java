package View.GUI;

import Network.Client.ClientController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Player;
import model.PlayingStation;
import model.cards.*;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.client.ClientBoard;
import model.client.ReductPlayer;
import model.enums.SuitEnum;
import model.enums.TokenEnum;

import java.io.IOException;
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
    private Label instructionsLabel;

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
    private Pane stationPane;

    @FXML
    private ImageView thirdCardInHand;

    @FXML
    private Button turnCardButton;

    private ImageView cardToPlayOn;

    private boolean playedBack;

    //una mappa per salvarmi le carte che ho nella station
    private Map<ImageView, Card> imageToCardPlayingHashMap = new HashMap<>();

    //una mappa per salvare le carte che posso selezionare (ovvero quelle che ho in mano)
    private Map<ImageView, Card> playableCardsHashMap = new HashMap<>();

    //una mappa per le carte che ho nella station

    private Map<ImageView, CardPlaying> cardsInStationMap = new HashMap<>();

    private CardLoader cardLoader;

    private ImageView cardToPlay;

    private ImageView cardToBeReplaces;

    @FXML
    private ImageView startingCard;

    public StationController(){

    }

    public StationController(ClientController clientController) {
        this.clientController = clientController;
        cardLoader = new CardLoader();
    }


    /**
     * this method handles when a cards gets clicked. It sets cardToPlayOn to the clicked card.
     * @param event the mouse click event
     * */
    private void handleCardClick(MouseEvent event){
        //ottengo l'immagine che è stata cliccata
        ImageView selectedCard = (ImageView) event.getSource();
        System.out.println(("Hai premuto la carta 1!"));
        //controllo se la carta fa parte delle SELECTABLECARDS
        //se lo è --> allora la gioco
        //--> mando messaggio che l'ho piazzata e la aggiungo allo stack pane
        if(imageToCardPlayingHashMap.containsKey(selectedCard)){
            //cardToPlayOn = imageToCardPlayingHashMap.get(selectedCard);
            turnCardButton.setVisible(true);
            selectCardButton.setVisible(true);
            chooseCard1.setOnMouseClicked(this::chooseStartingCard);
            chooseCard2.setOnMouseClicked(this::chooseStartingCard);
            //imageToCardPlayingHashMap.remove(selectedCard);
            //tolgo l'immagine dalla mappa solo una volta che ho giocato una carta --> infatti potrei volere selezionare
            //più carte nello stesso turno
        }

    }

    /**
     * this method sets the side of the starting card
     * @param event mouse click
     */
    private void chooseStartingCard(MouseEvent event) {
        //ho scelto la carta che voglio giocare
        //Devo fare due cose: 1) dire al controller che ho scelto la carta da piazzare 2) la devo aggiungere al pane
        //1)
        ImageView selectedCard = (ImageView) event.getSource();;
        setCardDimensions(selectedCard);
        ImageView startingCard = new ImageView();
//      stationPane.setVisible(true);
//      selectedCard.setLayoutX(500);
//      selectedCard.setLayoutY(375);
//      stationPane.getChildren().add(selectedCard);
        //chooseCard2.setImage(chooseCard1.getImage());

        //tolto da if la condizione che era dentro la mappa

        if(selectedCard.equals(chooseCard1)){
            //invio al controller che ho giocato la carta 1
            //devo piazzare
            System.out.println("Hai selezionato la carta 1");

            startingCard.setImage(chooseCard1.getImage());
        } else if(selectedCard.equals(chooseCard2)) {
            startingCard.setImage(chooseCard2.getImage());
            //invio al controller che ho giocato la carta in bakc
        }
            setCardDimensions(startingCard);
            stationPane.setVisible(true);
            startingCard.setLayoutX(500);
            startingCard.setLayoutY(375);
            startingCard.setOnMouseClicked(this::chooseCardToPlayOn);
            stationPane.getChildren().add(startingCard);
            chooseCard1.setVisible(false);
            chooseCard2.setVisible(false);
            chooseCard1.setOnMouseClicked(null);
            chooseCard2.setOnMouseClicked(null);
            testChooseObjective();

            //potrei togliere dalla mappa la carta non giocata, ma non saprei come.
        //oppure aggiungo handler solo in starting card.
        }

    /**
     * this method sets the secret objective
     * @param event
     */
    @FXML
    void objectiveChosen(MouseEvent event){
        ImageView selectedCard = (ImageView) event.getSource();
        ImageView secretObjective = new ImageView();

        if(selectedCard.equals(chooseCard1)){
            //mando messaggio che il giocatore ha scelto il primo obiettivo
            //clientController.messageToServerhandler(new SelectionOfSecretObjMessage(0)); //cosa vuole la position??
            secretObjective.setImage(chooseCard1.getImage());
            //setCardDimensions(secretObjective);
            secretObjectiveInHand.setImage(secretObjective.getImage());
        } else{
            secretObjective.setImage(chooseCard2.getImage());
            //setCardDimensions(secretObjective);
            secretObjectiveInHand.setImage(secretObjective.getImage());
            //clientController.messageToServerhandler(new SelectionOfSecretObjMessage(1));
        }
        //tolgo la carta dalla mappa
        //secretObjectiveInHand.setImage(selectedCard.getImage());
        chooseCard1.setImage(null);
        chooseCard2.setImage(null);
        chooseCard1.setOnMouseClicked(null);
        chooseCard2.setOnMouseClicked(null);
        testChooseAndPlayFromHand();
    }

    void cardInHandChosen(MouseEvent event){
        ImageView selectedCard = (ImageView) event.getSource();
        ImageView cardChosen = new ImageView();
        CardLoader cL1 = new CardLoader();
        //TODO: possibile controllo che posso effettivamente scegliere la carta con la mappa:
        // a inizio turno popolo la mappa con le carte che ho in mano, a fine turno la svuoto
        // in questo modo non posso "usare" una carta se non è il mio turno.
        // oppure più semplicemente quando non è il mio turno non faccio vedere le carte che ho in mano

        //mappa mi serve lo stesso per ottenere il back (oppure fare in qualche altro modo)
        //qua per provare me le passo direttamente


        if(selectedCard.equals(firstCardInHand)){
            chooseCard1.setImage(firstCardInHand.getImage());
            chooseCard2.setImage(cL1.getBack(1, SuitEnum.FUNGI));
            cardChosen.setImage(firstCardInHand.getImage());
            cardToBeReplaces = selectedCard;
            System.out.println("Scelta la prima");
        } else if(selectedCard.equals(secondCardInHand)) {
            chooseCard1.setImage(secondCardInHand.getImage());
            chooseCard2.setImage(cL1.getBack(7, SuitEnum.FUNGI));
            cardChosen.setImage(secondCardInHand.getImage());
            cardToBeReplaces = selectedCard;
            System.out.println("Scelta la seconda");
        } else if(selectedCard.equals(thirdCardInHand)) {
            chooseCard1.setImage(thirdCardInHand.getImage());
            chooseCard2.setImage(cL1.getBack(62, SuitEnum.ANIMAL));
            cardToBeReplaces = selectedCard;
            cardChosen.setImage(thirdCardInHand.getImage());
            System.out.println("Scelta la terza");
        }
        instructionsLabel.setText("Choose front or back!");
        chooseCard1.setOnMouseClicked(this::chooseCardSide);
        chooseCard2.setOnMouseClicked(this::chooseCardSide);
    }

    void chooseCardSide(MouseEvent event){
        cardToPlay = (ImageView) event.getSource();
        //a questo punto le carte da cui scegliere non devono ancora poter fare niente.
        //potranno fare qualcosa solo dopo che è stata scelta la carta su cui giocare.
//        chooseCard1.setOnMouseClicked(this::chooseCardToPlayOn);
//        chooseCard1.setOnMouseClicked(this::chooseCardToPlayOn);
        instructionsLabel.setText("Choose a card on the station to play on!");
        //potrei fare che per tutte le immagini che sono dentro la mappa di carte giocabili aggiungo handler
        for(ImageView image: cardsInStationMap.keySet()){
            image.setOnMouseClicked(this::chooseCardToPlayOn);
        }
    }

    void chooseCardToPlayOn(MouseEvent event){
        ImageView selectedCard = (ImageView) event.getSource();
        cardToPlayOn = selectedCard;
        System.out.println("Hai premuto una carta su cui piazzare");
        instructionsLabel.setText("Choose where you want to play your cards using the buttons");
        cardPlacementBox.setVisible(true);
        placeCardDownRightButton.setOnMouseClicked(this::chooseCardPlacement);
        placeCardDownLeftButton.setOnMouseClicked(this::chooseCardPlacement);
        placeCardUpLeftButton.setOnMouseClicked(this::chooseCardPlacement);
        placeCardUpRightButton.setOnMouseClicked(this::chooseCardPlacement);
        //TODO aggiungere handler anche gli altri button
        selectedCard.getLayoutX();
        selectedCard.getLayoutY();

    }


    //TODO non mi serve
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
            //adesso mi metto nell'ipotesi che la carta si posso sempre giocare --> non considero caso in cui non possa giocarla
            Button buttonPressed = (Button) event.getSource();
            double x = cardToPlayOn.getLayoutX();
            double y = cardToPlayOn.getLayoutY();
            System.out.println("Bottone premuto");
            if(buttonPressed.equals(placeCardDownLeftButton)){
                System.out.println("Scelto bottone in basso a sinistra");
                cardToPlay.setLayoutX(x-85);
                cardToPlay.setLayoutY(y+40);
                setCardDimensions(cardToPlay);
                stationPane.getChildren().add(cardToPlay);

                //mando messaggio che voglio giocare in basso a sinistra
            } else if(buttonPressed.equals(placeCardDownRightButton)){
                System.out.println("Scelto bottone in basso a destra");
                cardToPlay.setLayoutX(x+85);
                cardToPlay.setLayoutY(y+40);
                setCardDimensions(cardToPlay);
                stationPane.getChildren().add(cardToPlay);

            } else if(buttonPressed.equals(placeCardUpLeftButton)) {
                System.out.println("Scelto bottone in alto a sinistra");
                cardToPlay.setLayoutX(x-85);
                cardToPlay.setLayoutY(y-40);
                setCardDimensions(cardToPlay);
                stationPane.getChildren().add(cardToPlay);

            } else if((buttonPressed.equals(placeCardUpRightButton))) {
                System.out.println("Scelto bottone in alto a destra");
                cardToPlay.setLayoutX(x+85);
                cardToPlay.setLayoutY(y-40);
                setCardDimensions(cardToPlay);
                stationPane.getChildren().add(cardToPlay);
            }
            //chooseCard1.setImage(null);
            chooseCard2.setImage(null);
            instructionsLabel.setText("Choose a card to draw");
            //aggiungo gli handler alle carte centrali
            centralGoldImage1.setOnMouseClicked(this::chooseCardToDraw);
            centralGoldImage2.setOnMouseClicked(this::chooseCardToDraw);
            centralResourceImage1.setOnMouseClicked(this::chooseCardToDraw);
            centralResourceImage2.setOnMouseClicked(this::chooseCardToDraw);
            deckResourceImage.setOnMouseClicked(this::chooseCardToDraw);
            deckGoldImage.setOnMouseClicked(this::chooseCardToDraw);
    }

    private void chooseCardToDraw(MouseEvent event){
        ImageView im = (ImageView) event.getSource();
        System.out.println("hai scelto una carta da pescare");
        if(cardToBeReplaces.equals(firstCardInHand)){
            firstCardInHand.setImage(im.getImage());
        } else if(cardToBeReplaces.equals(secondCardInHand)){
            secondCardInHand.setImage(im.getImage());
        }
        instructionsLabel.setText("End your turn");
        im.setImage(null);
    }

    //TODO:

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


    public void testCardStarting(){
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp2 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);
        CardStarting cardStarting = new CardStarting(86, frontTmp2, backTmp, suitList);
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
        // mi tengo mappa (o comunque un riferimento dall'immagine alla carta)
        //mando messaggio al controller (con un thread a parte) che ho scelto la mia carta
        cardsInStationMap.put(startingCardFront, cardStarting);
        cardsInStationMap.put(startingCardBack, cardStarting);
        //aggiungo alle immagini gli handler -> se immagine premuta viene scelta la carta corrispondente
        chooseCard1.setOnMouseClicked(this::chooseStartingCard);
        chooseCard2.setOnMouseClicked(this::chooseStartingCard);
//        startingCardBack.setOnMouseClicked(this::handleCardClick);
//        startingCardFront.setOnMouseClicked(this::handleCardClick);
        imageToCardPlayingHashMap.put(startingCardFront, cardStarting);
    }

    public void testChooseObjective(){
        ImageView obj1 = new ImageView();
        ImageView obj2 = new ImageView();
        CardLoader cardLoader1 = new CardLoader();
        obj1.setImage(cardLoader1.getFront(87));
        obj2.setImage(cardLoader1.getFront(88));
        chooseCard1.setOnMouseClicked(this::objectiveChosen);
        chooseCard2.setOnMouseClicked(this::objectiveChosen);
        chooseCard1.setVisible(true);
        chooseCard2.setVisible(true);
        chooseCard1.setImage(obj1.getImage());
        chooseCard2.setImage(obj2.getImage());
        instructionsLabel.setText("Choose your secret objective");

    }

    public void testChooseAndPlayFromHand(){
        CardLoader cl = new CardLoader();
        firstCardInHand.setImage(cl.getFront(1, SuitEnum.FUNGI));
        secondCardInHand.setImage(cl.getFront(7, SuitEnum.FUNGI));
        thirdCardInHand.setImage(cl.getFront(62, SuitEnum.ANIMAL));
        //Aggiungo immagini alla mappa--> così riesco a ottenere la carta dall'immagine --> se viene scelta
        // posso estrarre il back della carta.
        //playableCardsHashMap.put()
        firstCardInHand.setOnMouseClicked(this::cardInHandChosen);
        secondCardInHand.setOnMouseClicked(this::cardInHandChosen);
        thirdCardInHand.setOnMouseClicked(this::cardInHandChosen);
        instructionsLabel.setText("Choose a card to play from your hand");
    }

    public void testCardDrawing(){
        CardLoader cl = new CardLoader();
        deckGoldImage.setImage(cl.getTopDeckGold(SuitEnum.ANIMAL));
        deckResourceImage.setImage(cl.getTopDeckResource(SuitEnum.FUNGI));
        centralResourceImage1.setImage(cl.getFront(28, SuitEnum.ANIMAL));
        centralResourceImage2.setImage(cl.getFront(27, SuitEnum.ANIMAL));
        centralGoldImage1.setImage(cl.getFront(44, SuitEnum.FUNGI));
        centralGoldImage2.setImage(cl.getFront(69, SuitEnum.ANIMAL));
    }

    //metodo da chiamare ogni volta che si crea una carta

    /**
     * this method sets the dimension of the imageView representing the card.
     * @param card
     */
    private void setCardDimensions(ImageView card){
        card.setFitHeight(65);
        card.setFitWidth(110);
    }


//TODO: una volta che una carta viene giocata, toglierla dalla mappa! non deve più essere selezionabile e girata.
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
//        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
//        Face frontTmp2 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
//        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
//        suitList.add(SuitEnum.ANIMAL);
//        suitList.add(SuitEnum.PLANT);
//        suitList.add(SuitEnum.INSECT);
//        Points obj = new ObjectiveAssign();
//        CardStarting cardStarting = new CardStarting(84, frontTmp2, backTmp, suitList);
//        CardResource cardResource = new CardResource(21, frontTmp2, backTmp, SuitEnum.ANIMAL, 1, obj);
//        CardLoader cardLoader1 = new CardLoader();
//        ImageView startingCardFront = new ImageView();
//        ImageView testDownRight = new ImageView();
//        ImageView testUpRight = new ImageView();
//        ImageView testUpLeft = new ImageView();
//        ImageView testDownLeft = new ImageView();
//        startingCardFront.setImage(cardLoader1.getFront(cardStarting.getId()));
//        testDownRight.setImage(cardLoader1.getFront(cardResource.getId(), SuitEnum.ANIMAL));
//        testUpRight.setImage(cardLoader1.getFront(cardResource.getId(), SuitEnum.ANIMAL));
//        testUpLeft.setImage(cardLoader1.getFront(cardResource.getId(), SuitEnum.ANIMAL));
//        testDownLeft.setImage(cardLoader1.getFront(cardResource.getId(), SuitEnum.ANIMAL));
//        setCardDimensions(startingCardFront);
//        setCardDimensions(testDownRight);
//        setCardDimensions(testUpRight);
//        setCardDimensions(testUpLeft);
//        setCardDimensions(testDownLeft);

//        centralCardsAndDecksPane.setVisible(false);
//        endTurnButton.setVisible(false);
//        handPane.setVisible(false);
//        cardPlacementBox.setVisible(false);
//        stationPane.setVisible(true);
            testCardDrawing();
        testCardStarting();
        //centerImage(stationPane, startingCardFront);
//        startingCardFront.setLayoutX(500);
//        startingCardFront.setLayoutY(375);
//        stationPane.getChildren().add(startingCardFront);
//        double x = startingCardFront.getLayoutX();
//        double y = startingCardFront.getLayoutY();
//        testDownRight.setLayoutX(x+85);
//        testDownRight.setLayoutY(y+40);
//        stationPane.getChildren().add(testDownRight);
//        testUpRight.setLayoutX(x+85);
//        testUpRight.setLayoutY(y-40);
//        stationPane.getChildren().add(testUpRight);
//        testUpLeft.setLayoutX(x-85);
//        testUpLeft.setLayoutY(y-40);
//        stationPane.getChildren().add(testUpLeft);
//        testDownLeft.setLayoutX(x-85);
//        testDownLeft.setLayoutY(y+40);
//        stationPane.getChildren().add(testDownLeft);
        instructionsLabel.setText("Choose the side of your starting card starting card");
        scoreboardButton.setOnMouseClicked(this::switchToScoreBoard);
        }

    public void switchToScoreBoard(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ScoreBoard.fxml"));
            Parent root = fxmlLoader.load();
            ScoreBoardController controller = fxmlLoader.getController();
            controller.setPreScene(scoreboardButton.getScene());
            Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            /**ReductPlayer tommy = new ReductPlayer(2, new ArrayList<>(), "tommy", new PlayingStation(new HashMap<>()), TokenEnum.BLUE );
            ReductPlayer eric = new ReductPlayer(4, new ArrayList<>(), "eric", new PlayingStation(new HashMap<>()), TokenEnum.YELLOW );
            ReductPlayer dave = new ReductPlayer(6, new ArrayList<>(), "dave", new PlayingStation(new HashMap<>()), TokenEnum.RED);
            ArrayList<ReductPlayer> redplayers= new ArrayList<>();
            redplayers.add(0, tommy);
            redplayers.add(1, eric);
            redplayers.add(2, dave);
            Player isa = new Player("isa", TokenEnum.GREEN, new PlayingStation(new HashMap<>()), 15, new ArrayList<>());
            controller.setClientBoard(new ClientBoard(null, null, redplayers, isa, null, null, null));
            controller.updateTokens2(); **/
            controller.updateTokens();
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
