package model.client;

import model.PlayingStation;
import model.enums.SuitEnum;
import model.enums.TokenEnum;
import model.testsTemplate.ClientBoardTemplate;
import model.testsTemplate.PlayingStationTemplate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReductPlayerTest {
ReductPlayer player = ClientBoardTemplate.createReductClient();
    @Test
    void getHand() {
        ArrayList<SuitEnum> handgiorgio = new ArrayList<>();
        player.setHand(handgiorgio);
        assertEquals(handgiorgio, player.getHand());
    }

    @Test
    void getNickname() {
        assertEquals("giorgio", player.getNickname());
    }

    @Test
    void getPoints() {
        player.setPoints(5);
        assertEquals(5,player.getPoints());

    }

    @Test
    void getStation() {
        PlayingStation stationgiorgio= PlayingStationTemplate.test_3Cards_1Positioning();
        player.setStation(stationgiorgio);
        assertEquals(stationgiorgio, player.getStation());
    }

    @Test
    void getToken() {
        ReductPlayer tom = new ReductPlayer("tommy", TokenEnum.BLUE);
        assertEquals(TokenEnum.BLUE, tom.getToken());
    }
}