package com.acmetelecom.call;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.acmetelecom.Phone;

public class Call {

	private CallEvent start;
	private CallEvent end;

	public Call(CallEvent start, CallEvent end) {
		this.start = start;
		this.end = end;
	}

	public Phone callee() {
		return start.getCallee();
	}

  public int durationSeconds() {
  	return (int) (((end.time() - start.time()) / 1000));
  }

  public String date() {
  	return SimpleDateFormat.getInstance().format(new Date(start.time()));
  }

  public Date startTime() {
  	return new Date(start.time());
  }

  public Date endTime() {
      return new Date(end.time());
  }

}
