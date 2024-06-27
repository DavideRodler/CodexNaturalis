package View.GUI;

import Network.Client.ClientController;
import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.chats.PrivateChat;
import model.cards.*;
import model.client.ReductPlayer;

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
    private VBox chatBox;

    @FXML
    private Button chatButton;

    @FXML
    private HBox chatChoiceBox;

    @FXML
    private ScrollPane chatPane;

    @FXML
    private Button chatSendTextButton;

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField chatTextField;

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
    private ImageView chooseCard1;

    @FXML
    private ImageView chooseCard2;

    @FXML
    private HBox chooseCardBox;

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
    private Button privateChatButton;

    @FXML
    private ChoiceBox<String> privateChatChoice;

    @FXML
    private Button publicChatButton;

    @FXML
    private Label instructionsLabel;


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
    private ImageView startingCard;

    private ImageView cardToPlayOn;

    private Map<ImageView, CardPlaying> imageToCardMap= new HashMap<>(); //qua ci sono le carte che ho in mano --> così dall'immagine riesco ad ottenere la carta

    //una mappa per le carte che ho nella station

    //private Map<ImageView, CardPlaying> cardsInStationMap = new HashMap<>();

    private final CardLoader cardLoader;

    private ImageView cardToPlay;

    private int indexOfCardToReplaced;

    private double firstCoordinate;

    private double secondCoordinate;

    private boolean playedBack;

    private int postion;

    private String receiverNick;


    public StationController() {
        cardLoader = new CardLoader();
    }

    public void updateHand() {
        showPlayerHand();
    }

    //TODO--> TOGLIERE HANDLER QUANDO NON CE NE E' PIU' BIOSGNO

    /**
     * this method sets the side of the starting card
     * @param event mouse click
     */
    private void chooseStartingCard(MouseEvent event) {
        //ho scelto la carta che voglio giocare
        //Devo fare due cose: 1) dire al controller che ho scelto la carta da piazzare 2) la devo aggiungere al pane
        //1)
        ImageView selectedCard = (ImageView) event.getSource();;
        ImageView startingCard = new ImageView();
//      stationPane.setVisible(true);
//      selectedCard.setLayoutX(500);
//      selectedCard.setLayoutY(375);
//      stationPane.getChildren().add(selectedCard);
        //chooseCard2.setImage(chooseCard1.getImage());

        //tolto da if la condizione che era dentro la mappa
        setCardDimensions(selectedCard);

        if(selectedCard.equals(chooseCard1)){
            //invio al controller che ho giocato la carta 1
            //devo piazzare
            System.out.println("Hai selezionato la carta 1");

            startingCard.setImage(chooseCard1.getImage());
            //qua front
            clientController.setupOfStartingCard_UI(false);
        } else if(selectedCard.equals(chooseCard2)) {
            startingCard.setImage(chooseCard2.getImage());
            clientController.setupOfStartingCard_UI(true);
            //qua back
            //invio al controller che ho giocato la carta in back
        }

            imageToCardMap.put(startingCard, clientController.getClientModel().getMyplayer().getStation().getCardStarting());
            stationPane.setVisible(true);
            // 500 e 375 è il centro del pane
            startingCard.setLayoutX(500);
            startingCard.setLayoutY(375);
            setCardDimensions(startingCard);
            //aggiungo handler per essere carta su cui piazzare
            startingCard.setOnMouseClicked(this::chooseCardToPlayOn);
            stationPane.getChildren().add(startingCard);
            chooseCard1.setVisible(false);
            chooseCard2.setVisible(false);
            //tolgo handler
            chooseCard1.setOnMouseClicked(null);
            chooseCard2.setOnMouseClicked(null);

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
            clientController.setupOfSecretObjective_UI(0);
            secretObjective.setImage(chooseCard1.getImage());
            secretObjectiveInHand.setImage(secretObjective.getImage());
        } else{
            secretObjective.setImage(chooseCard2.getImage());
            clientController.setupOfSecretObjective_UI(1);
            //setCardDimensions(secretObjective);
            secretObjectiveInHand.setImage(secretObjective.getImage());
        }
        //tolgo la carta dalla mappa
        //secretObjectiveInHand.setImage(selectedCard.getImage());
        handPane.setVisible(true);
        chooseCard1.setImage(null);
        chooseCard2.setImage(null);
        chooseCard1.setOnMouseClicked(null);
        chooseCard2.setOnMouseClicked(null);
        //testChooseAndPlayFromHand();
    }

    void cardInHandChosen(MouseEvent event){
        ImageView selectedCard = (ImageView) event.getSource();


        //mappa mi serve lo stesso per ottenere il back (oppure fare in qualche altro modo)
        //qua per provare me le passo direttamente


        if(selectedCard.equals(firstCardInHand)){
            //
            chooseCard1.setImage(cardLoader.getFront(clientController.getClientModel().getMyplayer().getHand().getFirst().getId()));
            chooseCard2.setImage(cardLoader.getBack(clientController.getClientModel().getMyplayer().getHand().getFirst().getId()));
            indexOfCardToReplaced = 0;
            System.out.println("Scelta la prima");
        } else if(selectedCard.equals(secondCardInHand)) {
            chooseCard1.setImage(cardLoader.getFront(clientController.getClientModel().getMyplayer().getHand().get(1).getId()));
            chooseCard2.setImage(cardLoader.getBack(clientController.getClientModel().getMyplayer().getHand().get(1).getId()));
            indexOfCardToReplaced = 1;
            System.out.println("Scelta la seconda");
        } else if(selectedCard.equals(thirdCardInHand)) {
            chooseCard1.setImage(cardLoader.getFront(clientController.getClientModel().getMyplayer().getHand().get(2).getId()));
            chooseCard2.setImage(cardLoader.getBack(clientController.getClientModel().getMyplayer().getHand().get(2).getId()));
            indexOfCardToReplaced = 2;
            System.out.println("Scelta la terza");
        }
        instructionsLabel.setText("Choose front or back!");
        chooseCard1.setVisible(true);
        chooseCard2.setVisible(true);
        chooseCard1.setOnMouseClicked(this::chooseCardSide);
        chooseCard2.setOnMouseClicked(this::chooseCardSide);
    }

    void chooseCardSide(MouseEvent event){
        ImageView selectedCard = (ImageView) event.getSource();
        cardToPlay = new ImageView();
        cardToPlay.setImage(selectedCard.getImage());

        if(selectedCard.equals(chooseCard1)){
            playedBack = false;
        } else if(selectedCard.equals(chooseCard2)){
            playedBack = true;
        }
        //TODO: DA FARE DOPO?
        //imageToCardMap.put(cardToPlay, clientController.getClientModel().getMyplayer().getHand().get(indexOfCardToReplaced));
        instructionsLabel.setText("Choose a card on the station to play on!");
        //potrei fare che per tutte le immagini che sono dentro la mappa di carte giocabili aggiungo handler
        for(ImageView image: imageToCardMap.keySet()){
            image.setOnMouseClicked(this::chooseCardToPlayOn);
        }
    }

    //TODO: aggiungere le carte alle mappe!

    void chooseCardToPlayOn(MouseEvent event){
        cardToPlayOn = (ImageView) event.getSource();
        System.out.println("Hai premuto una carta su cui piazzare");
        instructionsLabel.setText("Choose where you want to play your cards using the buttons");
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
            firstCoordinate = cardToPlayOn.getLayoutX();
            secondCoordinate = cardToPlayOn.getLayoutY();
            System.out.println("Bottone premuto");
            int firstCoordinate = 0, secondCoordinate = 0;
            for(Map.Entry<ArrayList<Integer>, CardPlaying> entry : clientController.getClientModel().getMyplayer().getStation().getMap().entrySet()){
                if(entry.getValue().getId().equals((imageToCardMap.get(cardToPlayOn)).getId())) {
                    firstCoordinate = entry.getKey().getFirst();
                    secondCoordinate = entry.getKey().getLast();
                }
            }
            imageToCardMap.put(cardToPlay, clientController.getClientModel().getMyplayer().getHand().get(indexOfCardToReplaced));
            if(buttonPressed.equals(placeCardDownLeftButton)){
                System.out.println("Scelto bottone in basso a sinistra");
                postion = 0;
                // boolean playedbakck,
                //    int x,
                //    int y,
                //    int cardId
                //adesso vogliamo ottenere dalla carta su cui voglio piazzare
                clientController.playCardOnPlayngStation_UI(playedBack, firstCoordinate+1, secondCoordinate-1, clientController.getClientModel().getMyplayer().getHand().get(indexOfCardToReplaced).getId());

                //mando messaggio che voglio giocare in basso a sinistra
            } else if(buttonPressed.equals(placeCardDownRightButton)){
                System.out.println("Scelto bottone in basso a destra");
                clientController.playCardOnPlayngStation_UI(playedBack, firstCoordinate+1, secondCoordinate+1, clientController.getClientModel().getMyplayer().getHand().get(indexOfCardToReplaced).getId());
                postion = 1;

            } else if(buttonPressed.equals(placeCardUpLeftButton)) {
                System.out.println("Scelto bottone in alto a sinistra");
                clientController.playCardOnPlayngStation_UI(playedBack, firstCoordinate-1, secondCoordinate-1, clientController.getClientModel().getMyplayer().getHand().get(indexOfCardToReplaced).getId());
                postion = 2;

            } else if((buttonPressed.equals(placeCardUpRightButton))) {
                System.out.println("Scelto bottone in alto a destra");
                clientController.playCardOnPlayngStation_UI(playedBack, firstCoordinate-1, secondCoordinate+1, clientController.getClientModel().getMyplayer().getHand().get(indexOfCardToReplaced).getId());
                postion = 3;
            }

            chooseCard1.setOnMouseClicked(null);
            chooseCard2.setOnMouseClicked(null);
            chooseCard1.setVisible(false);
            chooseCard2.setVisible(false);
            cardPlacementBox.setVisible(false);

            //chooseCard1.setImage(null);
            //chooseCard2.setImage(null);
            //aggiungo gli handler alle carte centrali
    }

    public void updateCentralCardsAndDecks(){
        showCentralCardsAndDecks();
    }

    public void cardPlacedCorrectly(){
        //aggiungo carta alla mappa delle carte piazzate

        switch(postion){
            case 0 -> {
                cardToPlay.setLayoutX(this.firstCoordinate -80);
                cardToPlay.setLayoutY(this.secondCoordinate +40);
                setCardDimensions(cardToPlay);
                stationPane.getChildren().add(cardToPlay);
            }
            case 1 -> {
                cardToPlay.setLayoutX(this.firstCoordinate +80);
                cardToPlay.setLayoutY(this.secondCoordinate +40);
                setCardDimensions(cardToPlay);
                stationPane.getChildren().add(cardToPlay);
            }
            case 2 -> {
                cardToPlay.setLayoutX(this.firstCoordinate -80);
                cardToPlay.setLayoutY(this.secondCoordinate -40);
                setCardDimensions(cardToPlay);
                stationPane.getChildren().add(cardToPlay);
            }
            case 3 -> {
                cardToPlay.setLayoutX(this.firstCoordinate +80);
                cardToPlay.setLayoutY(this.secondCoordinate -40);
                setCardDimensions(cardToPlay);
                stationPane.getChildren().add(cardToPlay);
            }
        }
        instructionsLabel.setText("Choose a card to draw");
        System.out.println("hai piazzato la carta");
        //aggiungo handler alle carte da pescare
        centralGoldImage1.setOnMouseClicked(this::chooseCardToDraw);
        centralGoldImage2.setOnMouseClicked(this::chooseCardToDraw);
        centralResourceImage1.setOnMouseClicked(this::chooseCardToDraw);
        centralResourceImage2.setOnMouseClicked(this::chooseCardToDraw);
        deckResourceImage.setOnMouseClicked(this::chooseCardToDraw);
        deckGoldImage.setOnMouseClicked(this::chooseCardToDraw);
        //tolgo handler ai bottoni di piazzamento
        placeCardDownRightButton.setOnMouseClicked(null);
        placeCardUpRightButton.setOnMouseClicked(null);
        placeCardUpLeftButton.setOnMouseClicked(null);
        placeCardDownLeftButton.setOnMouseClicked(null);
    }

    public void cardPlaceIncorrectly(String message){
        imageToCardMap.remove(cardToPlay, clientController.getClientModel().getMyplayer().getHand().get(indexOfCardToReplaced));
        instructionsLabel.setText(message);
        cardToPlay.setImage(null);
        cardPlacementBox.setVisible(false);
        //tolgo gli handler ai bottoni piazzamento perchè inizio nuovo ciclo di piazzamento
        placeCardDownRightButton.setOnMouseClicked(null);
        placeCardUpRightButton.setOnMouseClicked(null);
        placeCardUpLeftButton.setOnMouseClicked(null);
        placeCardDownLeftButton.setOnMouseClicked(null);
        //aggiungo di nuovo handler alle carte che ho in mano
        firstCardInHand.setOnMouseClicked(this::cardInHandChosen);
        secondCardInHand.setOnMouseClicked(this::cardInHandChosen);
        thirdCardInHand.setOnMouseClicked(this::cardInHandChosen);
    }

    private void chooseCardToDraw(MouseEvent event){
        ImageView choiceOfDraw = (ImageView) event.getSource();
        System.out.println("hai scelto una carta da pescare");
        if(choiceOfDraw.equals(centralGoldImage1)){
            clientController.startAfterCardHasBeenAddedToStation_UI(1);
        } else if(choiceOfDraw.equals(centralGoldImage2)){
            clientController.startAfterCardHasBeenAddedToStation_UI(2);
        } else if(choiceOfDraw.equals(centralResourceImage1)){
            clientController.startAfterCardHasBeenAddedToStation_UI(3);
        } else if(choiceOfDraw.equals(centralResourceImage2)){
            clientController.startAfterCardHasBeenAddedToStation_UI(4);
        }
        else if(choiceOfDraw.equals(deckGoldImage))
        {
            clientController.startAfterCardHasBeenAddedToStation_UI(6);
        }
        else if(choiceOfDraw.equals(deckResourceImage))
        {
            clientController.startAfterCardHasBeenAddedToStation_UI(5);
        }
        instructionsLabel.setText("Your turn is finished.");
        choiceOfDraw.setImage(null);

    }


    private void sendMessage(MouseEvent event) {
        String message = chatTextField.getText();
        clientController.sendGlobalMessage(new GlobalChatMessage("GLOBAL", message, clientController.getClientModel().getMyplayer().getNickname()));
        chatTextField.clear();
    }


    private void sendPrivateMessage(MouseEvent event){
        String message = chatTextField.getText();
        clientController.sendPrivateMessage(new PrivateChatMessage(message, clientController.getClientModel().getMyplayer().getNickname(), receiverNick));
        chatTextField.clear();
    }


    private void openChat(MouseEvent event) {
        chatButton.setOnMouseClicked(null);
        chatBox.setVisible(true);
        chatButton.setOnMouseClicked(this::closeChat);
        chatSendTextButton.setVisible(true);
        chatTextField.setVisible(true);
        publicChatButton.setOnMouseClicked(this::showPublicChat);
        privateChatButton.setOnMouseClicked(this::showPrivateChat);
        for(ReductPlayer player: clientController.getClientModel().getOtherplayers()){
            privateChatChoice.getItems().remove(player.getNickname());
        }
        for(ReductPlayer player: clientController.getClientModel().getOtherplayers()){
            privateChatChoice.getItems().add(player.getNickname());
        }
    }

    private void closeChat(MouseEvent event) {
        chatButton.setOnMouseClicked(null);
        chatBox.setVisible(false);
        chatButton.setOnMouseClicked(this::openChat);
        chatSendTextButton.setVisible(false);
        chatTextField.setVisible(false);
    }

    private void showPublicChat(MouseEvent event){
        printGlobalChat();
        chatSendTextButton.setOnMouseClicked(this::sendMessage);
    }



    private void showPrivateChat(MouseEvent event){
        this.receiverNick = privateChatChoice.getValue();
        boolean resume = false;
        for(ReductPlayer player : clientController.getClientModel().getOtherplayers()){
            if (player.getNickname().equals(receiverNick)) {
                resume = true;
                break;
            }
        }
        if(resume) {
            printPrivateChat();
            chatSendTextButton.setOnMouseClicked(this::sendPrivateMessage);
        }
    }


    public void printPrivateChat(){
        chatTextArea.clear();
        for(PrivateChat privateChat : clientController.getClientModel().getPrivateChats()){
            if(privateChatChoice.getValue().equals(privateChat.getNickname1()) || privateChatChoice.getValue().equals(privateChat.getNickname2()))
            {
                for(PrivateChatMessage privateChatMessage : privateChat.getMessage()){
                    chatTextArea.appendText(privateChatMessage.getNicknameReceiver() + ": " + privateChatMessage.getMessage() + "\n");
                }
            }
        }
    }

    public void printGlobalChat(){
        chatTextArea.clear();
        for(GlobalChatMessage globalMessage : clientController.getClientModel().getGlobalChat().getMessage()) {
            chatTextArea.appendText(globalMessage.getNickname() + ": " + globalMessage.getMessage() + "\n");
        }
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
            //aggiungo alle immagini gli handler -> se immagine premuta viene scelta la carta corrispondente
            // mi tengo mappa (o comunque un riferimento dall'immagine alla carta)
            chooseCard1.setOnMouseClicked(this::chooseStartingCard);
            chooseCard2.setOnMouseClicked(this::chooseStartingCard);
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
            CardObjective commonObj1 = clientController.getClientModel().getFirstObjective();
            CardObjective commonObj2 = clientController.getClientModel().getSecondObjective();

            instructionsLabel.setText("Choose your secret objective");
            centralCardsAndDecksPane.setVisible(true);

            commonObjectiveImage1.setImage(cardLoader.getFront(commonObj1.getId()));
            commonObjectiveImage2.setImage(cardLoader.getFront(commonObj2.getId()));
            deckObjectivesImage.setImage(cardLoader.getTopDeckObjectives());
            chooseCard1.setImage(cardLoader.getFront(selectableObj1.getId()));
            chooseCard2.setImage(cardLoader.getFront(selectableObj2.getId()));

            chooseCard1.setOnMouseClicked(this::objectiveChosen);
            chooseCard2.setOnMouseClicked(this::objectiveChosen);
            chooseCard1.setVisible(true);
            chooseCard2.setVisible(true);

            showPlayerHand();
    }


    /**
     * this method shows the central cards and the decks
     */
    public void showCentralCardsAndDecks(){
            CardResource centralResourceCard1 = clientController.getClientModel().getCentralCardsResource().getFirst();
            CardResource centralResourceCard2 = clientController.getClientModel().getCentralCardsResource().getLast();
            CardGold centralGoldCard1 = clientController.getClientModel().getCentralCardsGold().getFirst();
            CardGold centralGoldCard2 = clientController.getClientModel().getCentralCardsGold().getLast();
            centralCardsAndDecksPane.setVisible(true);

            centralResourceImage1.setImage(cardLoader.getFront(centralResourceCard1.getId()));
            centralResourceImage2.setImage(cardLoader.getFront(centralResourceCard2.getId()));
            centralGoldImage1.setImage(cardLoader.getFront(centralGoldCard1.getId()));
            centralGoldImage2.setImage(cardLoader.getFront(centralGoldCard2.getId()));
            deckGoldImage.setImage(cardLoader.getTopDeckGold(clientController.getClientModel().getBackOfGoldDeck()));
            deckResourceImage.setImage(cardLoader.getTopDeckResource(clientController.getClientModel().getBackOfResourceDeck()));

//            imageToCardMap.put(centralResourceImage1, centralResourceCard1);
//            imageToCardMap.put(centralResourceImage2, centralResourceCard2);
//            imageToCardMap.put(centralGoldImage1, centralGoldCard1);
//            imageToCardMap.put(centralGoldImage2, centralGoldCard2);


            showPlayerHand();

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

            firstCardInHand.setImage(cardLoader.getFront(cardInHand1.getId()));
            secondCardInHand.setImage(cardLoader.getFront(cardInHand2.getId()));
            thirdCardInHand.setImage(cardLoader.getFront(cardInHand3.getId()));
            if(secretObjective!= null) {
                secretObjectiveInHand.setImage(cardLoader.getFront(secretObjective.getId()));
            }

            //non lo si fa adesso, lo si farà quando una di queste carte deve essere giocatore
//            imageToCardMap.put(firstCardInHand, cardInHand1);
//            imageToCardMap.put(secondCardInHand, cardInHand2);
//            imageToCardMap.put(thirdCardInHand, cardInHand3);


        //TODO --> questi in realtà devo aspettare a metterli --> solo quando è turno del giocatore

//            firstCardInHand.setOnMouseClicked(this::chooseCardToPlayCLicked);
//            secondCardInHand.setOnMouseClicked(this::chooseCardToPlayCLicked);
//            thirdCardInHand.setOnMouseClicked(this::chooseCardToPlayCLicked);

    }

    public void updateCurrentPlayerView(){
        if(clientController.getClientModel().getCurrentPlayer().equals(clientController.getClientModel().getMyplayer().getNickname())){
            startTurn();
        } else {
            notMyTurn(clientController.getClientModel().getCurrentPlayer());
        }
    }

    private void startTurn(){
        instructionsLabel.setText("It's your turn! Choose a card to play from your hand!");
        updateCentralCardsAndDecks();
        cardPlacementBox.setVisible(false);
        chatButton.setOnMouseClicked(this::openChat);
        menuPane.setVisible(true);
        if(firstCardInHand != null){
            firstCardInHand.setOnMouseClicked(this::cardInHandChosen);
        }
        if(secondCardInHand != null){
            secondCardInHand.setOnMouseClicked(this::cardInHandChosen);
        }
        if(thirdCardInHand != null){
            thirdCardInHand.setOnMouseClicked(this::cardInHandChosen);
        }
        scoreboardButton.setOnMouseClicked(this::switchToScoreBoard);

        //TODO: ricordare di togliere gli handler appena non più necessari
    }

    private void notMyTurn(String currentPlayer){
        updateCentralCardsAndDecks();
        instructionsLabel.setText("It's " + currentPlayer + "'s turn. Wait for your turn to play");
        chatButton.setOnMouseClicked(this::openChat);
        menuPane.setVisible(true);
        scoreboardButton.setOnMouseClicked(this::switchToScoreBoard);
    }

    /**
     * this method sets the dimension of the imageView representing a card.
     * @param card the image of the card
     */
    private void setCardDimensions(ImageView card){
        card.setFitHeight(65);
        card.setFitWidth(110);
    }


//TODO: una volta che una carta viene giocata, toglierla dalla mappa! non deve più essere selezionabile e girata.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
          centralCardsAndDecksPane.setVisible(false);
          handPane.setVisible(false);
          cardPlacementBox.setVisible(false);
          stationPane.setVisible(false);
          menuPane.setVisible(false);
          //queste tre vanno sempre insieme
          chatBox.setVisible(false);
          chatSendTextButton.setVisible(false);
          chatTextField.setVisible(false);
          instructionsLabel.setText("Choose the side of your starting card starting card");
    }

    public void switchToScoreBoard(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ScoreBoard.fxml"));
            Parent root = fxmlLoader.load();
            ScoreBoardController controller = fxmlLoader.getController();
            controller.setClientController(clientController);
            controller.setPreScene(scoreboardButton.getScene());
            Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            controller.updateTokens();
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }
}
