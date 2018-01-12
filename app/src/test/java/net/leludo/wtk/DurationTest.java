package net.leludo.wtk;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Period class test
 */
public class DurationTest {

    private Duration actor ;

    /**
     * Given a new duration of 0 second
     * When i ask for the duration
     * Then the duration equals 0 second
     * And the format is "00:00:00"
     * @throws Exception
     */
    @Test
    public void duration0() throws Exception {
        this.actor = new Duration(0);
        assertEquals(0L, this.actor.duration());
        assertEquals("00:00:00", this.actor.format());
    }

    /**
     * Given a new duration of 60 seconds
     * When i ask for the duration
     * Then the duration equals 60 seconds
     * And the format is "00:01:00"
     * @throws Exception
     */
    @Test
    public void duration60() throws Exception {
        this.actor = new Duration(60);
        assertEquals(60L, this.actor.duration());
        assertEquals("00:01:00", this.actor.format());
    }

    /**
     * Given a new duration of 3600 seconds
     * When i ask for the duration
     * Then the duration equals 3600 seconds
     * And the format is "01:00:00"
     * @throws Exception
     */
    @Test
    public void duration3600() throws Exception {
        this.actor = new Duration(3600);
        assertEquals(3600L, this.actor.duration());
        assertEquals("01:00:00", this.actor.format());
    }

}
