package com.adsquare.tictactoe;

import com.adsquare.tictactoe.adapters.in.rest.controller.GameController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TicTacToeApplicationTests {

    @Autowired
    private GameController subject;

    @Test
    void contextLoads() {
        assertNotNull(subject);
    }

}
