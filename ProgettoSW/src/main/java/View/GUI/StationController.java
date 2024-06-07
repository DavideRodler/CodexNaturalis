package View.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;

//TODO: qua ci sarà il board condiviso dai giocatori: punti, carte centrali, ob comuni.
// a lato terrei sempre la mano del giocatore.
// metterei un button per switchare tra questa scena e la propria station.

public class StationController {

        @FXML
        private Button btnchoosestartingcard;

        @FXML
        private Button btnobj1;

        @FXML
        private Button btnobj2;

        @FXML
        private Button btnplayerplus1;

        @FXML
        private Button btnplayerplus2;

        @FXML
        private Button btnplayerplus3;

        @FXML
        private Button btnscoreboard;

        @FXML
        private Button btnturnstartingcard;

        @FXML
        private ImageView cardhand1;

        @FXML
        private ImageView cardhand2;

        @FXML
        private ImageView cardhand3;

        @FXML
        private ImageView centralcard1;

        @FXML
        private ImageView centralcard2;

        @FXML
        private ImageView centralcard3;

        @FXML
        private ImageView centralcard4;

        @FXML
        private ImageView chooseobj1;

        @FXML
        private ImageView chooseobj2;

        @FXML
        private ImageView commonobj1;

        @FXML
        private ImageView commonobj2;

        @FXML
        private ImageView deckgold;

        @FXML
        private ImageView deckresource;

        @FXML
        private ImageView secretobj;

        @FXML
        private ImageView startingcard;

        public void show4CentralCards(CardGold cardGold1, CardGold cardGold2, CardResource cardResource1, CardResource cardResource2){
                // centralcard1 = immagine cardGold1
                // centralcard2 = immagine cardGold2
                // centralcard3 = immagine cardRes1
                // centralcard4 = immagine cardRes2
        }

        public void show2CommonObjectives(CardObjective obj1, CardObjective obj2){
                // commonobj1 = immagine obj1
                // commonobj2 = immagine obj2
        }

        public void showStartingCard(CardStarting cardStarting){
                // cardstarting = immagine cardStarting
        }

        public Boolean chooseSideStartingCard(){
                //restituisco lato su cui ho posizionato la carta iniziale
        return true;
        } //TODO: segnalino colorato su carta iniziale

        public void showSelectableObjectives(CardObjective obj1, CardObjective obj2){
                // commonobj1 = immagine obj1
                // commonobj2 = immagine obj2
        }

        public int chooseSecretObjective(){
                //controllo button schiacciato e prendo indice corrispondente (1 passa 0, 2 passa 1)
                return 0;
        }


        public void showPlayerHand(CardResource card1, CardResource card2, CardResource card3, CardObjective obj){
                // cardhand1 = immagine card1
                // cardhand2 = immagine card2
                // cardhand3 = immagine card3
                // secretobj = immagine obj
        }


}
