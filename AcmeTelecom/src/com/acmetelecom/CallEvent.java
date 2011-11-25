package com.acmetelecom;

public abstract class CallEvent {
    private String caller;
    private String callee;
    private long time;

    // Bosses asked what is the unit for timeStamp????????
    public CallEvent(String caller, String callee, long timeStamp) {
        this.caller = caller;
        this.callee = callee;
        this.time = timeStamp;
    }

    public String getCaller() {
        return caller;
    }

    public String getCallee() {
        return callee;
    }

    public long time() {
        return time;
    }
}
