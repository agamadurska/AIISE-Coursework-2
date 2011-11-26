package com.acmetelecom.call;

import com.acmetelecom.Phone;

public class CallEnd extends CallEvent {
	
	public CallEnd(Phone caller, Phone callee) {
		super(caller, callee, System.currentTimeMillis());
  }
    
	/**
	 * Create a CallEnd event.
	 * 
	 * @param caller	the initiator of the call 
	 * @param callee	the received of the call
	 * @param endTimeStamp the timestamp when the call ended
	 */
	public CallEnd(Phone caller, Phone callee, long endTimeStamp) {
		super(caller, callee, endTimeStamp);
  }

}
