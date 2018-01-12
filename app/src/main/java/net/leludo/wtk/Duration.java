package net.leludo.wtk;

/**
 * Duration in seconds
 */
public class Duration {

    /** The duration in seconds */
    private long duration;

    /**
     * Constructor.
     * @param duration The duration
     */
    public Duration(final long duration) {
        this.duration = duration;
    }

    /**
     * Return the duration in seconds
     * @return The duration in seconds
     */
    public long duration() {
        return this.duration;
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
        return this.format() ;
    }
}
