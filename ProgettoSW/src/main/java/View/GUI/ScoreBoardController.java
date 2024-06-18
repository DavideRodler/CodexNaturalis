package View.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.client.ClientBoard;
import model.client.ReductPlayer;
import model.enums.SuitEnum;
import model.enums.TokenEnum;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;


public class ScoreBoardController {

    @FXML
    private ImageView imgScoreBoard;

    @FXML
    private Label lblFirstPlace;

    @FXML
    private Label lblFourthPlace;

    @FXML
    private Label lblSecondPlace;

    @FXML
    private Label lblThirdPlace;

    @FXML
    private ImageView pos0;

    @FXML
    private ImageView pos1;

    @FXML
    private ImageView pos10;

    @FXML
    private ImageView pos11;

    @FXML
    private ImageView pos12;

    @FXML
    private ImageView pos13;

    @FXML
    private ImageView pos14;

    @FXML
    private ImageView pos15;

    @FXML
    private ImageView pos16;

    @FXML
    private ImageView pos17;

    @FXML
    private ImageView pos18;

    @FXML
    private ImageView pos19;

    @FXML
    private ImageView pos2;

    @FXML
    private ImageView pos20;

    @FXML
    private ImageView pos21;

    @FXML
    private ImageView pos22;

    @FXML
    private ImageView pos23;

    @FXML
    private ImageView pos24;

    @FXML
    private ImageView pos25;

    @FXML
    private ImageView pos26;

    @FXML
    private ImageView pos27;

    @FXML
    private ImageView pos28;

    @FXML
    private ImageView pos3;

    @FXML
    private ImageView pos4;

    @FXML
    private ImageView pos5;

    @FXML
    private ImageView pos6;

    @FXML
    private ImageView pos7;

    @FXML
    private ImageView pos8;

    @FXML
    private ImageView pos9;

    private Integer oldpoints;

    public void updateScoreBoard(){
        //prendo coordinate delle caselle e sposto sopra la pedina
    }

    public void updatePointsLabel(){

    }

    public Image getImageToken(TokenEnum token){
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(token +"_token.png");
        assert stream != null;
        return new Image(stream);
    }

    public ImageView findPos(Integer points){
        switch(points){
            case 1: return pos1;
            case 2: return pos2;
            case 3: return pos3;
            case 4: return pos4;
            case 5: return pos5;
            case 6: return pos6;
            case 7: return pos7;
            case 8: return pos8;
            case 9: return pos9;
            case 10: return pos10;
            case 11: return pos11;
            case 12: return pos12;
            case 13: return pos13;
            case 14: return pos14;
            case 15: return pos15;
            case 16: return pos16;
            case 17: return pos17;
            case 18: return pos18;
            case 19: return pos19;
            case 20: return pos20;
            case 21: return pos21;
            case 22: return pos22;
            case 23: return pos23;
            case 24: return pos24;
            case 25: return pos25;
            case 26: return pos26;
            case 27: return pos27;
            case 28: return pos28;
            default: return pos0;
        }
    }

    /**
     *
     * @param player
     */
    public void moveToken(ClientBoard player){ //da collegare al flusso di gioco, appena prima di passare al next player
        if(!Objects.equals(player.getMyplayer().getPoints(), oldpoints)) {
            checkOtherPlayerPoints(player.getOtherplayers(), oldpoints);
            findPos(player.getMyplayer().getPoints()).setImage(this.getImageToken(player.getMyplayer().getToken()));
            oldpoints = player.getMyplayer().getPoints();
        }
    }

    /**
     *
     * @param otherPlayers
     * @param points
     */
    public void checkOtherPlayerPoints(ArrayList<ReductPlayer> otherPlayers, int points){
        for(ReductPlayer player: otherPlayers ){
            if(player.getPoints() == points){
                findPos(points).setImage(this.getImageToken(player.getToken()));
            }
        }
    }

}

