package com.acmetelecom;

public class CallStart extends CallEvent {
	
    public CallStart(String caller, String callee) {
        super(caller, callee, System.currentTimeMillis());
    }
    
    /**
     * Create a CallStart event.
     * 
     * @param caller	the initiator of the call 
     * @param callee	the received of the call
     * @param endTimeStamp the timestamp when the call started
     */
    public CallStart(String caller, String callee, long startTimeStamp) {
    	super(caller, callee, startTimeStamp);
    }
    
}
