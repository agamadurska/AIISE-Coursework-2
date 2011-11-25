package com.acmetelecom;

public abstract class CallEvent {
    private PhoneEntity caller;
    private PhoneEntity callee;
    private long time;

    // Bosses asked what is the unit for timeStamp????????
    public CallEvent(PhoneEntity caller, PhoneEntity callee, long timeStamp) {
        this.caller = caller;
        this.callee = callee;
        this.time = timeStamp;
    }

    public PhoneEntity getCaller() {
        return caller;
    }

    public PhoneEntity getCallee() {
        return callee;
    }

    public long time() {
        return time;
    }
}
