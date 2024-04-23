package controller;

import model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
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
        List<Player> players = new ArrayList<>(game.getBoard().getPlayers().values());
        players.stream().map(x -> x.getNickname()).forEach(System.out::println);
        assertEquals(game.getBoard().getPlayers().size(),4,"test passato");

    }
    @Test
    public void testStations() {
        //input of the players selecting the objectives
        String data = "1 \n0 \n0 \n1 \n ";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

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