package com.acmetelecom.call;

import com.acmetelecom.entity.PhoneEntity;

public abstract class CallEvent {

	private final PhoneEntity caller;
	private final PhoneEntity callee;
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

  @Override
  public boolean equals(Object object) {
  	if (object instanceof CallEvent) {
  		CallEvent callEvent = (CallEvent)object;
  		return caller.equals(callEvent.getCaller()) &&
  				callee.equals(callEvent.getCallee()) &&
  				time == callEvent.time();
  	} else {
  		return false;
  	}
  }
  
  @Override
  public int hashCode() {
  	return (int)(((caller.hashCode() * 17) % 98332137 + callee.hashCode()) *
  			17 % 98332137 + time) % 98332137;
  }
  
}
