package com.cheeray;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

public class CalculatorTest {

	@Test
	public void testCalOK() throws IOException, InterruptedException {
		final Calculator cal = Calculator.instance();
		final InputStream original = System.in;
		System.setIn(CalculatorTest.class.getResourceAsStream("/bets.txt"));
		cal.main(null);
		System.setIn(original);
		cal.getProducts().forEach(p->{
			final List<Dividend> dividends = p.getDividends();
			assertNotNull("No dividends.", dividends);
			switch (p.getType()){
			case W:
				assertEquals("Not single WIN dividend.", 1, dividends.size());
				assertEquals("Wrong WIN dividend.", new BigDecimal("2.61"),dividends.get(0).getDividend());
				break;
			case P:
				assertEquals("Not 3 PLACE dividends.", 3, dividends.size());
				assertEquals("Wrong first place dividend.", new BigDecimal("1.06"),dividends.get(0).getDividend());
				assertEquals("Wrong second place dividend.", new BigDecimal("1.27"),dividends.get(1).getDividend());
				assertEquals("Wrong third place dividend.", new BigDecimal("2.13"),dividends.get(2).getDividend());
				break;
			case E:
				assertEquals("Not single Exacta dividend.", 1, dividends.size());
				assertEquals("Wrong Exacta dividend.", new BigDecimal("2.43"),dividends.get(0).getDividend());
				break;
			}
		});
		
		
	}
}
