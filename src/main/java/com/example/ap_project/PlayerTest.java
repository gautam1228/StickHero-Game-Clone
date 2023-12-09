package com.example.ap_project;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

class PlayerTest {

    @Test
    void testPlayerSingleton() {
        Player player1 = Player.getInstance();
        Player player2 = Player.getInstance();

        assertEquals("Player instance should be a singleton",player1, player2 );
    }

    @Test
    void testAvailableCherries() {
        Player player = Player.getInstance();
        assertEquals(String.valueOf(0), player.getAvailableCherries(), "Initial available cherries should be 0");

        player.setAvailableCherries(10);
        assertEquals(String.valueOf(10), player.getAvailableCherries(), "Available cherries should be 10");

        player.addAvailableCherries(5);
        assertEquals(String.valueOf(15), player.getAvailableCherries(), "Available cherries should be 15");
    }


}
