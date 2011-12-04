package com.acmetelecom;

import com.acmetelecom.call.CallsLogger;
import com.acmetelecom.call.SimpleCallsLogger;
import com.acmetelecom.customer.CentralCustomerDatabase;
import com.acmetelecom.customer.CentralTariffDatabase;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.output.HtmlPrinter;
import com.acmetelecom.output.Printer;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * GUICE module, dealing with binding external dependencies with the billing
 * system. Currently this includes the printer, customer database, as well as
 * tariff library,.
 */
public class BillingModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CallsLogger.class).to(SimpleCallsLogger.class);
	}
	
	@Provides
	Printer getPrinter() {
		return new HtmlPrinter(System.out);
	}
	
	@Provides
	CustomerDatabase getCustomerDatabase() {
		return CentralCustomerDatabase.getInstance();
	}
	
	@Provides
	TariffLibrary getTariffLibrary() {
		return CentralTariffDatabase.getInstance();
	}
	
}
