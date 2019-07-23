package com.cheeray;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Bet model.
 * 
 * @author Chengwei
 *
 */
public class Bet implements Serializable {

	private static final long serialVersionUID = 1L;

	private final ProductType type;

	private List<Integer> selections;

	private int stake;

	private BigDecimal dividend;

	public Bet(ProductType type) {
		this.type = type;
	}

	/**
	 * Is winning ticket?
	 * @param results
	 * @return
	 */
	public boolean isWinner(final List<Integer> results) {
		switch (type) {
		case W:
			return selections.contains(results.get(0));
		case P:
			return selections.stream().anyMatch(s->results.contains(s));
		case E:
			return selections.get(0).equals(results.get(0)) && selections.get(1).equals(results.get(1));
		default:
			throw new UnsupportedOperationException();
		}
	}

	public ProductType getType() {
		return type;
	}

	public List<Integer> getSelections() {
		return selections;
	}

	public void setSelections(List<Integer> selections) {
		this.selections = selections;
	}

	public int getStake() {
		return stake;
	}

	public void setStake(int stake) {
		this.stake = stake;
	}

	public BigDecimal getDividend() {
		return dividend;
	}

	public void setDividend(BigDecimal dividend) {
		this.dividend = dividend;
	}

}
