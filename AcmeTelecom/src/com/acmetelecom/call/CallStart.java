package com.acmetelecom.call;

import com.acmetelecom.entity.PhoneEntity;

/**
 * Class representing a call-start event.
 */
public class CallStart extends CallEvent {
	
	public CallStart(PhoneEntity caller, PhoneEntity callee) {
		super(caller, callee, System.currentTimeMillis());
	}
    
	/**
	 * Create a CallStart event.
	 * 
	 * @param caller the initiator of the call 
	 * @param callee the received of the call
	 * @param endTimeStamp the timestamp when the call started
	 */
	public CallStart(PhoneEntity caller, PhoneEntity callee,
			long startTimeStamp) {
		super(caller, callee, startTimeStamp);
  }
    
}
