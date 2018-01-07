package net.leludo.wtk;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Duration class test
 */
public class DurationTest {

    private Duration actor ;

    /**
     * Given a new duration without date
     * When i ask for the duration
     * Then the duration equals 0 second
     * @throws Exception
     */
    @Test
    public void durationWithoutDateEquals0() throws Exception {
        this.actor = new Duration();
        assertEquals(0L, this.actor.duration());
        assertEquals("00:00:00", this.actor.format());
    }

    /**
     * Given a new duration with start and end date
     * And start date is before end date
     * When i ask for the duration
     * Then the duration equals 65 seconds
     * @throws Exception
     */
    @Test
    public void durationWithStartDateBeforeEndDateOk() throws Exception {
        Calendar cal = Calendar.getInstance();
        Date start = cal.getTime() ;
        cal.setTime(start);
        cal.add(Calendar.SECOND, 65);
        Date end = cal.getTime();
        this.actor = new Duration(start, end);
        assertEquals(65L, this.actor.duration());
        assertEquals("00:01:05", this.actor.format());
    }

    /**
     * Given a new duration with start and end date
     * And start date is before end date
     * When i ask for the duration
     * Then the duration equals 65 seconds
     * @throws Exception
     */
    @Test
    public void durationWithStartDateAfterEndDateEquals0() throws Exception {
        Calendar cal = Calendar.getInstance();
        Date end = cal.getTime() ;
        cal.setTime(end);
        cal.add(Calendar.SECOND, 65);
        Date start = cal.getTime();
        this.actor = new Duration(start, end);
        assertEquals(0L, this.actor.duration());
        assertEquals("00:00:00", this.actor.format());
    }

    /**
     * Given a new duration
     * When i set only start date
     * And i ask for the duration
     * Then the duration equals 0 seconds
     * @throws Exception
     */
    @Test
    public void durationWithOnlyStartDateEquals0() throws Exception {
        Calendar cal = Calendar.getInstance();
        Date start = cal.getTime() ;
        this.actor = new Duration().start(start);
        assertEquals(0L, this.actor.duration());
        assertEquals("00:00:00", this.actor.format());
    }

    /**
     * Given a new duration
     * When i set only end date
     * And i ask for the duration
     * Then the duration equals 0 seconds
     * @throws Exception
     */
    @Test
    public void durationWithOnlyEndDateEquals0() throws Exception {
        Calendar cal = Calendar.getInstance();
        Date end = cal.getTime() ;
        this.actor = new Duration().end(end);
        assertEquals(0L, this.actor.duration());
        assertEquals("00:00:00", this.actor.format());
    }

    /**
     * Given a new duration
     * When i set start date and end date
     * And start date is before end date
     * Then the duration equals 65 seconds
     * @throws Exception
     */
    @Test
    public void durationWithStartDateThenWithEndDateOk() throws Exception {
        Calendar cal = Calendar.getInstance();
        Date start = cal.getTime() ;
        cal.setTime(start);
        cal.add(Calendar.SECOND, 65);
        Date end = cal.getTime();
        this.actor = new Duration().start(start).end(end);
        assertEquals(65L, this.actor.duration());
        assertEquals("00:01:05", this.actor.format());
    }
}
