package com.acmetelecom.entity;

/**
 * Class modeling an extendible type of phone number.
 * e.g.: one can later add free phone numbers.
 */
public class Phone implements PhoneEntity {

	private String phoneNumber;
	
	public Phone(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof PhoneEntity) {
			return this.phoneNumber.equals(
					((PhoneEntity)object).getPhoneNumber());
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return phoneNumber.hashCode();
	}

}
