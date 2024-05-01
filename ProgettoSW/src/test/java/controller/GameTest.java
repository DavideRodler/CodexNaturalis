package controller;

import model.Player;
import model.PlayingBoard;
import model.enums.TokenEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class GameTest {

    @Test
    public void testChecknickname(){
        GameController game = new GameController(null, null);
        game.initGameController();
        game.addPlayer("tommy", TokenEnum.BLACK);
        game.addPlayer("davide", TokenEnum.BLUE);
        assertEquals(true ,game.checkNicknameAvailability("isa"));
        game.addPlayer("isa", TokenEnum.YELLOW);
        game.addPlayer("eric", TokenEnum.BLACK);
        assertEquals(true ,game.checkNicknameAvailability("giorgio"));
        game.setPlayerOrder();

    }
    @Test
    public void testTokenAvailability(){
        GameController game = new GameController(null, null);
        game.initGameController();
        System.out.println(game.getAvailableToken());
        game.addPlayer("eric", TokenEnum.BLACK);
        System.out.println(game.getAvailableToken());
    }

}