package controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class GameTest {

    @Test
    public void testPlayers(){
        Game game = new Game();
        game.startGame();
        game.addPlayer("tommy");
        game.addPlayer("davide");
        game.addPlayer("isa");
        game.addPlayer("eric");
        game.setPlayerOrder();
        game.getBoard().getPlayers().stream().map(x -> x.getNickname()).forEach(System.out::println);
        assertEquals(game.getBoard().getPlayers().size(),4,"test passato");

    }

}