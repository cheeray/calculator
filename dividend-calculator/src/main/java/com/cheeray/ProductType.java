package com.cheeray;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

/**
 * Product Type.
 * @author Chengwei
 *
 */
public enum ProductType {
	W("Win", new BigDecimal("15.00")),
	P("Place", new BigDecimal("12.00")),
	E("Exacta", new BigDecimal("18.00"));
	
	private final String name;
	private final BigDecimal commission;
	
	private ProductType(final String name, final BigDecimal commission) {
		this.name = name;
		this.commission = commission;
	}
	
	/**
	 * Optional type by name.
	 */
	public static Optional<ProductType> of(final String name) {
		return Arrays.stream(ProductType.values()).filter(t->t.toString().equalsIgnoreCase(name)).findAny();
	}

	public BigDecimal getCommission() {
		return commission;
	}
	
}
