package com.txbb.nfctimeapp.database;

public class Session {
    private long startTime;
    private long endTime;

    public Session(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
