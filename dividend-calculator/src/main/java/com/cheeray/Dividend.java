package com.cheeray;

import java.math.BigDecimal;

public class Dividend {
	private final ProductType type;

	private final String selections;

	private final BigDecimal dividend;

	public Dividend(final ProductType type, final String selections, final BigDecimal dividend) {
		this.type = type;
		this.selections = selections;
		this.dividend = dividend;
	}

	public ProductType getType() {
		return type;
	}

	public String getSelections() {
		return selections;
	}

	public BigDecimal getDividend() {
		return dividend;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dividend == null) ? 0 : dividend.hashCode());
		result = prime * result + ((selections == null) ? 0 : selections.hashCode());
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
		Dividend other = (Dividend) obj;
		if (dividend == null) {
			if (other.dividend != null)
				return false;
		} else if (!dividend.equals(other.dividend))
			return false;
		if (selections == null) {
			if (other.selections != null)
				return false;
		} else if (!selections.equals(other.selections))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s:%s:$%s", type, selections, dividend);
	}

}
