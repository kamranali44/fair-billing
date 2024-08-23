package com.bt.fairbilling;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserSessionTest {
    private UserSession userSession;

    @BeforeEach
    void setup(){
        userSession = new UserSession(0, 0, new ArrayList<>());
    }

    @Test
    void testIncrementSessionCount() {
        assertEquals(0, userSession.getCount());
        userSession.incrementCount();
        userSession.incrementCount();
        userSession.incrementCount();
        assertEquals(3, userSession.getCount());
    }

    @Test
    void testAddToTotalDuration() {
        assertEquals(0, userSession.getDuration());
        userSession.addToDuration(10);
        userSession.addToDuration(17);
        assertEquals(27, userSession.getDuration());
    }

}
