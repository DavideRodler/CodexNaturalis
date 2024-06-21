package View.GUI;

import Network.Client.ClientController;
import javafx.event.ActionEvent;
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
import model.cards.Card;
import model.cards.CardPlaying;
import model.cards.CardStarting;

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
        private Button chatButton;

        @FXML
        private Button endTurnButton;

        @FXML
        private ImageView firstCardInHand;

        @FXML
        private VBox handPane;

        @FXML
        private VBox menuPane;

        @FXML
        private Button placeButtonDownButton;

        @FXML
        private Button placeCardUpLeftButton;

        @FXML
        private Button placeCardUpRightButton;

        @FXML
        private Button placeDownRightButton;

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

        private Map<ImageView, CardPlaying> imageToCardPlayingHashMap = new HashMap<>();

        private Map <CardPlaying, ArrayList<ImageView>> cardPlayingToImageHashMap= new HashMap<>();

        private CardLoader cardLoader;

        @FXML
        private ImageView startingCard;

    public StationController(ClientController clientController) {
        this.clientController = clientController;
        cardLoader = new CardLoader();
    }
    //TODO resettare la active card dopo che è stato premuto il bottone.

        public void addCardsToHand(){

        }

        //in questo metodo voglio che quando l'utente clicca su una carta, questa diventa la activeCard.
        private void selectCard(Event event){
        }

        /**
         * this method handles when a cards gets clicked. It sets activeCard to the clicked card.
         * @param event the mouse click event
         */
        private void handleCardClick(MouseEvent event){
                ImageView selectedCard = (ImageView) event.getSource();
                if(imageToCardPlayingHashMap.containsKey(selectedCard)){
                    activeCard = imageToCardPlayingHashMap.get(selectedCard);
                    turnCardButton.setVisible(true);
                    selectCardButton.setVisible(true);
                }
        }

        //TODO: potrei farmi diversi handler per le diverse situazioni di gioco.
        // per l'inizio mi servono solo situazion



        /**
         * this method adds the starting card to the station.
         *
         */
        public void showStartingCard() {
                CardStarting cardStarting =  clientController.getClientModel().getMyplayer().getStation().getCardStarting();

                ArrayList<ImageView> images = new ArrayList<>();
                ImageView startingCardFront = new ImageView();
                ImageView startingCardBack = new ImageView();
                startingCardFront.setImage(cardLoader.getFront(cardStarting.getId()));
                startingCardBack.setImage(cardLoader.getBack(cardStarting.getId()));
                startingCardBack.setOnMouseClicked(this::handleCardClick);
                startingCardFront.setOnMouseClicked(this::handleCardClick);
                imageToCardPlayingHashMap.put(startingCardFront, cardStarting);
                imageToCardPlayingHashMap.put(startingCardBack, cardStarting);
                images.add(startingCardFront);
                images.add(startingCardBack);
                cardPlayingToImageHashMap.put(cardStarting, images);
                //ho ottenuto l'immagine della carta --> devo metterla nel centro della station
                stationPane.getChildren().add(startingCard);
                startingCard.setImage(images.getFirst().getImage()); //inizializzo con la carta centrale
        }

    //TODO: metodo per fare in modo che quando il bottone select viene premuto --> si ottengano le coordinate della activeCard e si resetti la activeCard
    // inoltre deve controllare se la carta è giocata in front o in back.

        @FXML
        void turnCard(ActionEvent event){
            boolean front = true;

            if(activeCard != null){ // controllo se è in front
                cardPlayingToImageHashMap.get(activeCard); //come controllo se la carta è giocata è la 0 o la 1??
            }

            turnCardButton.setVisible(false);
            activeCard = null;
        }


        private void getImageBack(){

        }



//TODO: una volta che una carta viene giocata, toglierla dalla mappa! non deve più essere selezionabile e girata.
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                centralCardsAndDecksPane.setVisible(false);
                endTurnButton.setVisible(false);
                handPane.setVisible(false);
                cardPlacementBox.setVisible(false);
                stationPane.setVisible(false);
        }
}
