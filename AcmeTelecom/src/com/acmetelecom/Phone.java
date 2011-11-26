package com.acmetelecom;

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
	
	public boolean equals(Phone phoneEntity) {
		return phoneNumber.equals(phoneEntity.getPhoneNumber());
	}
	
	public int hashCode() {
		return phoneNumber.hashCode();
	}

}
