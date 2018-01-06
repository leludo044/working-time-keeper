package net.leludo.wtk;

import java.util.Date;

class Duration {

    private Date start;
    private Date end;
    private long duration ;

    public Duration() {
        this.duration = 0 ;
    }

    public Duration(Date start, Date end) {
        this.start = start ;
        this.end = end ;
        this.duration = 0;
    }

    public void start(Date start) {
        this.start = start ;
    }

    public void end(Date end) {
        this.end = end ;
    }

    public boolean isOk() {
        return ((this.start != null) && (this.end != null) && this.start.before(this.end));
    }

    public String format() {
        this.duration = (this.end.getTime()-this.start.getTime())/1000;
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
