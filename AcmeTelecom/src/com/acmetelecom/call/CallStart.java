package com.acmetelecom.call;

import com.acmetelecom.Phone;

public class CallStart extends CallEvent {
	
	public CallStart(Phone caller, Phone callee) {
		super(caller, callee, System.currentTimeMillis());
  }
    
	/**
	 * Create a CallStart event.
	 * 
	 * @param caller	the initiator of the call 
	 * @param callee	the received of the call
	 * @param endTimeStamp the timestamp when the call started
	 */
	public CallStart(Phone caller, Phone callee,
			long startTimeStamp) {
		super(caller, callee, startTimeStamp);
  }
    
}
