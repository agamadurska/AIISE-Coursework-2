package com.acmetelecom;

import java.math.BigDecimal;

/**
 * Utility class for formatting money values to human readable form.
 */
class MoneyFormatter {

	/**
	 * Converts a certain amount of pence into GBP.
	 */
	public static String penceToPounds(BigDecimal pence) {
		BigDecimal pounds = pence.divide(new BigDecimal(100));
		return String.format("%.2f", pounds.doubleValue());
  }

}
