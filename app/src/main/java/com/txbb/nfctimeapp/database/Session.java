package com.txbb.nfctimeapp.database;

public class Session {
    private int startTime;
    private int duration;

    public Session(int startTime, int duration){
        this.startTime = startTime;
        this.duration = duration;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }
}
