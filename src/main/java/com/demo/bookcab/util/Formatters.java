package com.demo.bookcab.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Formatters {

	public static double formatDecimalRoundToTwo(double input) {
		BigDecimal bd = BigDecimal.valueOf(input);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
