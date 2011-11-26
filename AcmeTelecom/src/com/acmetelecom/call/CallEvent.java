package com.acmetelecom.call;

import com.acmetelecom.Phone;

public abstract class CallEvent {

	private Phone caller;
	private Phone callee;
	private long time;

	// Bosses asked what is the unit for timeStamp????????
	public CallEvent(Phone caller, Phone callee, long timeStamp) {
		this.caller = caller;
		this.callee = callee;
		this.time = timeStamp;
	}

	public Phone getCaller() {
		return caller;
	}

  public Phone getCallee() {
  	return callee;
  }

  public long time() {
  	return time;
  }

}
