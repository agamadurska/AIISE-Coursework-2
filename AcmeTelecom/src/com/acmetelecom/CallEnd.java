package com.acmetelecom;

public class CallEnd extends CallEvent {
    public CallEnd(String caller, String callee) {
        super(caller, callee, System.currentTimeMillis());
    }
    
    /**
     * Create a CallEnd event.
     * 
     * @param caller	the initiator of the call 
     * @param callee	the received of the call
     * @param endTimeStamp the timestamp when the call ended
     */
    public CallEnd(String caller, String callee, long endTimeStamp) {
    	super(caller, callee, endTimeStamp);
    }

}
