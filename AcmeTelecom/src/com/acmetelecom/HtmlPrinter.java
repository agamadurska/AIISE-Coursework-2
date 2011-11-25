package com.acmetelecom;

import java.io.PrintStream;

/**
 * Bosses don't use singletons.
 * Bosses don't use hardcoded values.
 * Bosses use dependency injection.
 * Bosses put first the public classes then the private ones.
 */
class HtmlPrinter implements Printer {

	private final PrintStream printStream;
	
	/**
	 * Create an HtmlPrinter.
	 * 
	 * @param printStream the stream to print to
	 */
    public HtmlPrinter(PrintStream printStream) {
    	this.printStream = printStream;
    }

    public void printHeading(String name, String phoneNumber, String pricePlan) {
        beginHtml();
        printStream.println(h2(name + "/" + phoneNumber + " - " + "Price Plan: " + pricePlan));
        beginTable();
    }

    public void printItem(String time, PhoneEntity callee, String duration, String cost) {
        printStream.println(tr(td(time) + td(callee.getPhoneNumber()) + td(duration) + td(cost)));
    }

    public void printTotal(String total) {
        endTable();
        printStream.println(h2("Total: " + total));
        endHtml();
    }

    private void beginTable() {
        printStream.println("<table border=\"1\">");
        printStream.println(tr(th("Time") + th("Number") + th("Duration") + th("Cost")));
    }

    private void endTable() {
        printStream.println("</table>");
    }

    private String h2(String text) {
        return "<h2>" + text + "</h2>";
    }
    
    private String tr(String text) {
        return "<tr>" + text + "</tr>";
    }

    private String th(String text) {
        return "<th width=\"160\">" + text + "</th>";
    }

    private String td(String text) {
        return "<td>" + text + "</td>";
    }

    private void beginHtml() {
        printStream.println("<html>");
        printStream.println("<head></head>");
        printStream.println("<body>");
        printStream.println("<h1>");
        printStream.println("Acme Telecom");
        printStream.println("</h1>");
    }

    private void endHtml() {
        printStream.println("</body>");
        printStream.println("</html>");
    }
}
