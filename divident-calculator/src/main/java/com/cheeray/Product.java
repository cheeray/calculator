package com.cheeray;

import java.io.PrintStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Product.
 * 
 * @author Chengwei
 *
 */
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private final ProductType type;
	private final List<Bet> bets = new ArrayList<>();
	// private BigDecimal total = new BigDecimal("0.00");
	private long total = 0l;
	private List<Integer> results;
	private final List<Dividend> dividends = new ArrayList<>();

	public Product(ProductType type) {
		this.type = type;
	}

	public ProductType getType() {
		return type;
	}

	public List<Integer> getResults() {
		return results;
	}

	public void result(List<Integer> results) {
		if (results == null || results.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.results = results;
		calDividends();
	}

	public void add(Bet bet) {
		bets.add(bet);
		total += bet.getStake();
	}

	/**
	 * Cal dividends.
	 */
	private void calDividends() {
		dividends.clear();
		// total wins ...

		switch (type) {
		case W:
		case E:
			final Optional<Integer> ot = bets.stream().filter(b -> b.isWinner(results)).map(Bet::getStake)
					.reduce(Integer::sum);
			if (ot.isPresent()) {
				final BigDecimal div = new BigDecimal(total)
						.multiply(new BigDecimal("100.00").subtract(type.getCommission()))
						.divide(new BigDecimal(ot.get()).multiply(new BigDecimal("100.00")), MathContext.DECIMAL64)
						.setScale(2, RoundingMode.DOWN);

				if (type == ProductType.E) {
					dividends.add(new Dividend(type, "" + results.get(0) + "," + results.get(1), div));
				} else {
					dividends.add(new Dividend(type, "" + results.get(0), div));
				}
			} else {
				// no winner
			}
			break;
		case P:
			results.stream().forEach(r -> {
				final Optional<Integer> t = bets.stream().filter(b -> b.getSelections().contains(r)).map(Bet::getStake)
						.reduce(Integer::sum);
				if (t.isPresent()) {
					final BigDecimal div = new BigDecimal(total)
							.multiply(new BigDecimal("100.00").subtract(type.getCommission()))
							.divide(new BigDecimal(t.get()).multiply(new BigDecimal("300.00")), MathContext.DECIMAL64)
							.setScale(2, RoundingMode.HALF_EVEN);
					dividends.add(new Dividend(type, "" + r, div));
				}
			});

			break;
		default:
			throw new UnsupportedOperationException();
		}

	}

	public List<Dividend> getDividends() {
		return dividends;
	}

	/**
	 * Output dividends.
	 * 
	 * @param out
	 */
	public void outputDividends(PrintStream out) {
		dividends.forEach(out::println);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((results == null) ? 0 : results.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (results == null) {
			if (other.results != null)
				return false;
		} else if (!results.equals(other.results))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
