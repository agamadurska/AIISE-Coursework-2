package com.acmetelecom;

import com.acmetelecom.call.CallsLogger;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.entity.Phone;
import com.acmetelecom.output.Printer;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Runner {
    public static final String TEXT_INTRO = "AcmeTelecom billing system interactive test";
    public static final String TEXT_CMD_UNKNOWN = "Unknown command. Type 'exit' to exit or 'help' for help";
    public static final String TEXT_CLIENT_UNKNOWN = "Unknown client - type 1, 2, 3 or 4";


    public static final String PROMPT = "> ";

    public static final String CMD_EXIT = "exit";

    public static final Pattern MATCH_CALL_NEW = Pattern.compile(".*start call from (\\d+) to (\\d+).*");
    public static final Pattern MATCH_CALL_END = Pattern.compile(".*end call from (\\d+) to (\\d+).*");
    public static final Pattern MATCH_BILL_PRINT = Pattern.compile(".*print.*");

    private static Injector injector;
    private static CustomerDatabase customerDatabase;
    private static TariffLibrary tariffLibrary;
    private static CallsLogger callsLogger;
    private static HashMap<String,Phone> phoneHashMap;
    private static BillingSystem billingSystem;
    private static Printer printer;

    public static void main(String[] args) {

        setup();



        Scanner scanner = new Scanner(System.in);
        String cmd = "";

        System.out.println(TEXT_INTRO);

        while (true){
            System.out.print(PROMPT);
            cmd = scanner.nextLine();
            if (cmd.equals(CMD_EXIT)) {
                break;
            }
            else {
                interpret_command(cmd);
            }
        }
    }

    private static void setup() {
        injector = Guice.createInjector(new BillingModule());
        customerDatabase = injector.getInstance(CustomerDatabase.class);
        tariffLibrary = injector.getInstance(TariffLibrary.class);
        callsLogger = injector.getInstance(CallsLogger.class);

        billingSystem = new CompleteBillingSystem(callsLogger,
				customerDatabase, tariffLibrary);
        printer = injector.getInstance(Printer.class);

        phoneHashMap = new HashMap<String, Phone>();

        phoneHashMap.put("1", new Phone("447722113434"));
        phoneHashMap.put("2", new Phone("447766814143"));
        phoneHashMap.put("3", new Phone("447777765432"));
        phoneHashMap.put("4", new Phone("447711111111"));

    }

    private static void interpret_command(String cmd) {
        Matcher m;

        m = MATCH_CALL_NEW.matcher(cmd);
        if(m.matches()) {
            Phone phone1 = phoneHashMap.get(m.group(1));
            Phone phone2 = phoneHashMap.get(m.group(2));

            if (phone1 == null || phone2 == null) {
                System.out.println(TEXT_CLIENT_UNKNOWN);
                return;
            }


            callsLogger.callInitiated(phone1, phone2);
            return;
        }

        m = MATCH_CALL_END.matcher(cmd);
        if(m.matches()) {
            Phone phone1 = phoneHashMap.get(m.group(1));
            Phone phone2 = phoneHashMap.get(m.group(2));

            if (phone1 == null || phone2 == null) {
                System.out.println(TEXT_CLIENT_UNKNOWN);
                return;
            }

            callsLogger.callCompleted(phone1, phone2);
            return;
        }

        m = MATCH_BILL_PRINT.matcher(cmd);
        if(m.matches()) {
            for (CustomerBill customerBill : billingSystem.createCustomersBill()) {
                customerBill.printBill(printer);
            }

            return;
        }

        System.out.println(TEXT_CMD_UNKNOWN);
    }

}
