package com.cheeray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Calculate pool dividends.
 * 
 * @author Chengwei
 *
 */
public class Calculator {

	private static class CalHolder {
		private static final Calculator CAL = new Calculator();
	}

	public static Calculator instance(){
		return CalHolder.CAL;
	}
	
	public static void main(String[] args) {
		// Init cal
		final Calculator cal = Calculator.instance();
		// create a scanner so we can read the command-line input
		try (final Scanner scanner = new Scanner(System.in)) {

			// prompt for the user's name
			System.out.println("Place a bet: Bet:[W|P|E]:[1,...]:[1,...]");
			System.out.println("Set results: Result:[1,...]:[1,...]:[1,...].");
			while (scanner.hasNextLine()) {
				// Init a bet ...
				Bet bet = null;

				try {
					// Scan input ...
					final String[] ins = scanner.nextLine().replaceAll("\\b", "").split(":");
					if (ins.length != 4) {
						throw new IllegalFormatException();
					}

					final String tag = ins[0];
					if (tag.equalsIgnoreCase("result")) {
						final List<Integer> results = new ArrayList<>(3);
						results.add(Integer.valueOf(ins[1]));
						results.add(Integer.valueOf(ins[2]));
						results.add(Integer.valueOf(ins[3]));
						// Setting results ...
						cal.result(results);
					} else if (!tag.equalsIgnoreCase("bet")) {
						throw new IllegalFormatException();
					}

					// pool type ...

					final Optional<ProductType> ot = ProductType.of(ins[1]);
					if (ot.isPresent()) {
						bet = new Bet(ot.get());
					} else {
						throw new IllegalFormatException();
					}

					// selections

					final List<Integer> selections = Arrays.stream(ins[2].split(",")).map(s -> Integer.parseInt(s))
							.collect(Collectors.toList());
					bet.setSelections(selections);

					// stakes
					final int stake = Integer.parseInt(ins[3]);
					bet.setStake(stake);
					cal.place(bet);
				} catch (IllegalFormatException e) {
					System.out.println("Wrong format, please input prefix \"Bet\".");
				}
			}
		}
	}

	/** Map product to its type. */
	private final Map<ProductType, Product> products = new HashMap<>();

	private Calculator() {
		Arrays.stream(ProductType.values()).forEach(t -> {
			products.put(t, new Product(t));
		});
	}

	/**
	 * Place a bet.
	 * 
	 * @param bet
	 */
	private void place(Bet bet) {
		products.get(bet.getType()).add(bet);
	}

	/**
	 * Setting results and output dividends.
	 * 
	 * @param results
	 */
	private void result(List<Integer> results) {
		products.values().forEach(p -> {
			p.result(results);
		});
		Arrays.stream(ProductType.values()).forEach(t -> {
			final Product p = products.get(t);
			if (p != null) {
				p.outputDividends(System.out);
			}
		});
	}

	public List<Product> getProducts() {
		return new ArrayList<>(products.values());
	}

}
