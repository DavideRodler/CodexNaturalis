package controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;


class GameTest {

    @Test
    public void testPlayers(){
        GameController game = new GameController();
        game.initGameController();
        game.addPlayer("tommy");
        game.addPlayer("davide");
        game.addPlayer("isa");
        game.addPlayer("eric");
        game.setPlayerOrder();
        game.getBoard().getPlayers().stream().map(x -> x.getNickname()).forEach(System.out::println);
        assertEquals(game.getBoard().getPlayers().size(),4,"test passato");

    }
    @Test
    public void testStations() {
        GameController game = new GameController();
        game.initGameController();
        game.addPlayer("tommy");
        game.addPlayer("davide");
        game.addPlayer("isa");
        game.addPlayer("eric");
        game.setPlayerOrder();
        game.createSations();
    }

}