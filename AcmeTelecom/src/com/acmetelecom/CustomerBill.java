package com.acmetelecom;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.acmetelecom.call.Call;
import com.acmetelecom.call.CallEnd;
import com.acmetelecom.call.CallEvent;
import com.acmetelecom.call.CallStart;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.output.Printer;

public class CustomerBill {

	private final Customer customer;
	private final TariffLibrary tariffLibrary;
	private final List<CallEvent> customerEvents;
	private Tariff tariff;
	
	public CustomerBill(Customer customer, TariffLibrary tariffLibrary,
			List<CallEvent> customerEvents) {
		this.customer = customer;
		this.tariffLibrary = tariffLibrary;
		this.customerEvents = customerEvents;
		refreshTariff();
	}

	/**
	 * Returns customer calls.
	 * 
	 * @return a list containing the calls the customer has made.
	 */
	public List<Call> getCustomerCalls() {
		List<Call> calls = new ArrayList<Call>();
		// This assumes that only an end event can follow a start event.
		CallEvent start = null;
		for (CallEvent event : customerEvents) {
			if (event instanceof CallStart) {
				start = event;
			}
			if (event instanceof CallEnd && start != null) {
				calls.add(new Call(start, event));
				start = null;
			}
		}
		return calls;
	}
	
	/**
	 * Computes the cost of a call according to customer's tariff.
	 * 
	 * @param  call the call for which to compute the cost
	 * @return the cost
	 */
	public BigDecimal computeCost(Call call) {
		
		BigDecimal cost;
		DaytimePeakPeriod peakPeriod = new DaytimePeakPeriod();
		
		// The call starts and ends during offpeak hours.
		if (peakPeriod.offPeak(call.startTime()) &&
				peakPeriod.offPeak(call.endTime()) &&
				call.durationSeconds() < 12 * 60 * 60) {
			cost = new BigDecimal(call.durationSeconds())
					.multiply(tariff.offPeakRate());
		}
		// The call starts and ends during peak hours.
		else if (!peakPeriod.offPeak(call.startTime()) &&
				!peakPeriod.offPeak(call.endTime()) &&
				call.durationSeconds() < 12 * 60 * 60){
			cost = new BigDecimal(call.durationSeconds())
					.multiply(tariff.peakRate());
		}
		// The call starts during offpeak and ends during peak hours.
		else if (peakPeriod.offPeak(call.startTime()) &&
				!peakPeriod.offPeak(call.endTime()) &&
				call.durationSeconds() < 12 * 60 * 60){
			// The part of the conversation during offpeak.
			cost = new BigDecimal(milisecondsToseconds(
					peakPeriod.getPeakStart()
					- convertToMiliseconds(call.startTime())))
					.multiply(tariff.offPeakRate());
			// The part of the conversation during peak.
			BigDecimal peakCost = new BigDecimal(milisecondsToseconds(
					convertToMiliseconds(call.endTime())
					- peakPeriod.getPeakStart()))
					.multiply(tariff.peakRate());
			cost = cost.add(peakCost);
		}
		// The call starts during peak and ends during offpeak hours.
		else {
			// The part of the conversation during peak.
			cost = new BigDecimal(milisecondsToseconds(
					peakPeriod.getPeakEnd() 
					- convertToMiliseconds(call.startTime())))
					.multiply(tariff.peakRate());
			// The part of the conversation during offpeak.
			BigDecimal offPeakCost = new BigDecimal(milisecondsToseconds(
					convertToMiliseconds(call.endTime())
					- peakPeriod.getPeakEnd()))
					.multiply(tariff.offPeakRate());
			cost = cost.add(offPeakCost);
		}
		return cost;
	}
	
	@SuppressWarnings("deprecation")
	private long convertToMiliseconds(java.util.Date date){
		return date.getHours() * 60 * 60 * 1000 +
				date.getMinutes() * 60 * 1000 +
				date.getSeconds() * 1000;
	}
	
	private long milisecondsToseconds(long miliseconds){
		return miliseconds / 1000;
	}
	
	/**
	 * Charges the customer for all the calls he/she made.
	 * 
	 * @return the cost of all the calls
	 */
	public BigDecimal charge() {
		BigDecimal totalBill = new BigDecimal(0);
		for (Call call : getCustomerCalls()) {
			BigDecimal cost = computeCost(call);
			cost = cost.setScale(0, RoundingMode.HALF_UP);
			BigDecimal callCost = cost;
			totalBill = totalBill.add(callCost);
			call.setCallCost(callCost);
		}
		return totalBill;
	}
	
	public void printBill(Printer printer) {
		BigDecimal totalBill = charge();
		printer.printHeading(customer.getFullName(), customer.getPhoneNumber(),
			customer.getPricePlan());
		for (Call call : getCustomerCalls()) {
			printer.printItem(call.date(), call.callee(),
				call.durationMinutes(),
					MoneyFormatter.penceToPounds(call.cost()));
		}
		printer.printTotal(MoneyFormatter.penceToPounds(totalBill));
	}
	
	private void refreshTariff() {
		tariff = tariffLibrary.tarriffFor(customer);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof CustomerBill) {
			CustomerBill customerBill = (CustomerBill)object;
			return customer.equals(customerBill.customer) &&
					customerEvents.equals(customerBill.customerEvents);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return customer.hashCode() % 98393893 + customerEvents.hashCode();
	}

}
