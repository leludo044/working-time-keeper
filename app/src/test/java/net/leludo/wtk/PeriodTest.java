package net.leludo.wtk;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Period class test
 */
public class PeriodTest {

    private Period actor ;

    /**
     * Given a new period without date
     * When i ask for the duration
     * Then the duration equals 0 second
     * @throws Exception
     */
    @Test
    public void periodWithoutDateEquals0() throws Exception {
        this.actor = new Period();
        assertEquals(0L, this.actor.duration());
        assertEquals("00:00:00", this.actor.format());
    }

    /**
     * Given a new period with start and end date
     * And start date is before end date
     * When i ask for the duration
     * Then the duration equals 65 seconds
     * @throws Exception
     */
    @Test
    public void periodWithStartDateBeforeEndDateOk() throws Exception {
        Calendar cal = Calendar.getInstance();
        Date start = cal.getTime() ;
        cal.setTime(start);
        cal.add(Calendar.SECOND, 65);
        Date end = cal.getTime();
        this.actor = new Period(start, end);
        assertEquals(65L, this.actor.duration());
        assertEquals("00:01:05", this.actor.format());
    }

    /**
     * Given a new period with start and end date
     * And start date is before end date
     * When i ask for the duration
     * Then the duration equals 65 seconds
     * @throws Exception
     */
    @Test
    public void periodWithStartDateAfterEndDateEquals0() throws Exception {
        Calendar cal = Calendar.getInstance();
        Date end = cal.getTime() ;
        cal.setTime(end);
        cal.add(Calendar.SECOND, 65);
        Date start = cal.getTime();
        this.actor = new Period(start, end);
        assertEquals(0L, this.actor.duration());
        assertEquals("00:00:00", this.actor.format());
    }

    /**
     * Given a new period
     * When i set only start date
     * And i ask for the duration
     * Then the duration equals 0 seconds
     * @throws Exception
     */
    @Test
    public void periodWithOnlyStartDateEquals0() throws Exception {
        Calendar cal = Calendar.getInstance();
        Date start = cal.getTime() ;
        this.actor = new Period().start(start);
        assertEquals(0L, this.actor.duration());
        assertEquals("00:00:00", this.actor.format());
    }

    /**
     * Given a new period
     * When i set only end date
     * And i ask for the duration
     * Then the duration equals 0 seconds
     * @throws Exception
     */
    @Test
    public void periodWithOnlyEndDateEquals0() throws Exception {
        Calendar cal = Calendar.getInstance();
        Date end = cal.getTime() ;
        this.actor = new Period().end(end);
        assertEquals(0L, this.actor.duration());
        assertEquals("00:00:00", this.actor.format());
    }

    /**
     * Given a new period
     * When i set start date and end date
     * And start date is before end date
     * ANd i ask for the duration
     * Then the duration equals 65 seconds
     * @throws Exception
     */
    @Test
    public void periodWithStartDateThenWithEndDateOk() throws Exception {
        Calendar cal = Calendar.getInstance();
        Date start = cal.getTime() ;
        cal.setTime(start);
        cal.add(Calendar.SECOND, 65);
        Date end = cal.getTime();
        this.actor = new Period().start(start).end(end);
        assertEquals(65L, this.actor.duration());
        assertEquals("00:01:05", this.actor.format());
    }
}
