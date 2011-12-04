package com.acmetelecom.call;

import com.acmetelecom.entity.PhoneEntity;

/**
 * Class representing a call-end event.
 */
public class CallEnd extends CallEvent {
	
	public CallEnd(PhoneEntity caller, PhoneEntity callee) {
		super(caller, callee, System.currentTimeMillis());
	}

	/**
	 * Create a CallEnd event.
	 * 
	 * @param caller the initiator of the call 
	 * @param callee the received of the call
	 * @param endTimeStamp the timestamp when the call ended
	 */
	public CallEnd(PhoneEntity caller, PhoneEntity callee, long endTimeStamp) {
		super(caller, callee, endTimeStamp);
  }

}
