package net.leludo.wtk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A period
 */
class Period {

    private Date start;
    private Date end;

    /** Period in seconds */
    private Duration duration ;

    /**
     * Constructor.
     * Without any date, period equals 0
     */
    public Period() {
        this.compute();
    }

    /**
     * Constructor.
     * If start date is before end date, period is computed else period equals 0
     *
     * @param start Start date
     * @param end End date
     */
    public Period(Date start, Date end) {
        this.start = start ;
        this.end = end ;
        this.compute();
    }

    /**
     * Fix period start date.
     * @param start Start date
     * @return This period
     */
    public Period start(Date start) {
        this.start = start ;
        this.compute();
        return this ;
    }

    /**
     * Fix period end date.
     * @param end End date
     * @return This period
     */
    public Period end(Date end) {
        this.end = end ;
        this.compute();
        return this;
    }

    /**
     * Compute the period.
     * Il start date and end date are not null ans start date is before end date
     * then period is computed else period equals 0
     */
    protected void compute() {
        if (isOk()) {
            this.duration = new Duration((this.end.getTime() - this.start.getTime()) / 1000);
        } else {
            this.duration = new Duration(0);
        }
    }
    /**
     * Return the period in seconds
     * @return Period in seconds
     */
    public long duration() {
        return this.duration.duration();
    }

    private boolean isOk() {
        return ((this.start != null) && (this.end != null) && this.start.before(this.end));
    }

    /**
     * Format the period : "hh:mm:ss"
     * @return The format string
     */
    public String format() {
        return this.duration.format();
    }

    public String formatedStartTime() {
        return String.format("%tT", this.start);
    }

    public String formatedEndTime() {
        return String.format("%tT", this.end);
    }

    @Override
    public String toString() {
        return String.format("%tT / %tT = %s", this.start, this.end, this.format());
    }
}
