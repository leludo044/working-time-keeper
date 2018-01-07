package net.leludo.wtk;

import java.util.Date;

/**
 * A duration
 */
class Duration {

    private Date start;
    private Date end;

    /** Duration in seconds */
    private long duration ;

    /**
     * Constructor.
     * Without any date, duration equals 0
     */
    public Duration() {
        this.compute();
    }

    /**
     * Constructor.
     * If start date is before end date, duration is computed else duration equals 0
     *
     * @param start Start date
     * @param end End date
     */
    public Duration(Date start, Date end) {
        this.start = start ;
        this.end = end ;
        this.compute();
    }

    /**
     * Fix duration start date.
     * @param start Start date
     * @return This duration
     */
    public Duration start(Date start) {
        this.start = start ;
        this.compute();
        return this ;
    }

    /**
     * Fix duration end date.
     * @param end End date
     * @return This duration
     */
    public Duration end(Date end) {
        this.end = end ;
        this.compute();
        return this;
    }

    /**
     * Compute the duration.
     * Il start date and end date are not null ans start date is before end date
     * then duration is computed else duration equals 0
     */
    protected void compute() {
        if (isOk()) {
            this.duration = (this.end.getTime() - this.start.getTime()) / 1000;
        } else {
            this.duration = 0;
        }
    }
    /**
     * Return the duration in seconds
     * @return Duration in seconds
     */
    public long duration() {
        return this.duration;
    }

    private boolean isOk() {
        return ((this.start != null) && (this.end != null) && this.start.before(this.end));
    }

    /**
     * Format the duration : "hh:mm:ss"
     * @return The format string
     */
    public String format() {
        long hours = this.duration/3600;
        long minutes = (this.duration-(hours*3600))/60;
        long seconds = (this.duration-(hours*3600)-(minutes*60));

        return String.format("%02d:%02d:%02d", hours, minutes, seconds) ;
    }

    @Override
    public String toString() {
        return this.format();
    }
}
