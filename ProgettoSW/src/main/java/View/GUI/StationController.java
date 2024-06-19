package View.GUI;

import Network.Client.ClientController;
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
import model.cards.CardStarting;

import java.net.URL;
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

        private ImageView selectedCard;

    public StationController(ClientController clientController) {
        this.clientController = clientController;
    }
    //TODO resettare la selected card dopo che è stato premuto il bottone.

        public void addCardsToHand(){

        }

        //in questo metodo voglio che quando l'utente clicca su una carta, questa diventa la selectedCard.
        private void selectCard(Event event){
        }

        /**
         * this method handles when a cards gets clicked. It sets selectedCard to the clicked card.
         * @param event the mouse click event
         */
        private void handleCardClick(MouseEvent event){
                selectedCard = (ImageView) event.getSource();
        }



        /**
         * this method adds the starting card to the station.
         * @param cardStarting is the starting card to be added
         */
        public void showStartingCard(CardStarting cardStarting) {
                //TODO: bisogna mettere cardStarting nel centro dello stackPane
                int id = cardStarting.getId();
                CardLoader cardLoader = new CardLoader();
                ImageView startingCardFront = new ImageView();
                ImageView startingCardBack = new ImageView();
                startingCardFront.setImage(cardLoader.getFront(id));
                startingCardBack.setImage(cardLoader.getBack(id));
                //TODO: aggiungere handler alla carta ogni volta che una carta viene aggiunta
                startingCardBack.setOnMouseClicked(this::handleCardClick);
                startingCardFront.setOnMouseClicked(this::handleCardClick);
                //ho ottenuto l'immagine della carta --> devo metterla nel centro della station
                stationPane.getChildren().add(startingCardFront);
        }




        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                centralCardsAndDecksPane.setVisible(false);
                endTurnButton.setVisible(false);
                handPane.setVisible(false);
                cardPlacementBox.setVisible(false);
                stationPane.setVisible(false);
        }
}
