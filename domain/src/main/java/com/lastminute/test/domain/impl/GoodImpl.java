/**
 * 
 */
package com.lastminute.test.domain.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.lastminute.test.domain.Good;
import com.lastminute.test.domain.GoodType;

/**
 * Implementation of a Good to buy. ({@code implements Good}).
 * 
 * Default quantity is 1 and for default a Food isn't imported.
 * 
 * @author nconte
 *
 */
public class GoodImpl implements Good {

	private String name;
	private GoodType type;
	private Boolean imported = Boolean.FALSE;
	private BigDecimal taxRate;
	private BigDecimal grossCost;
	private Integer quantity = 1;

	public GoodImpl(String name, GoodType type, BigDecimal grossCost) {
		this.name = name;
		this.type = type;
		this.grossCost = grossCost;
	}

	public GoodImpl(String name, GoodType type, BigDecimal grossCost, Boolean imported) {
		this.name = name;
		this.type = type;
		this.grossCost = grossCost;
		this.imported = imported;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getCost() {
		BigDecimal cost = grossCost.multiply(new BigDecimal(quantity));
		if (taxRate == null) {
			return cost;
		} else {
			BigDecimal tax = cost.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
			tax = tax.divide(new BigDecimal("0.05"), 0, RoundingMode.UP).multiply(new BigDecimal("0.05"));
			return cost.add(tax);
		}
	}

	public GoodType getType() {
		return type;
	}

	public void setType(GoodType type) {
		this.type = type;
	}

	public Boolean getImported() {
		return imported;
	}

	public void setImported(Boolean imported) {
		this.imported = imported;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public BigDecimal getGrossCost() {
		return grossCost;
	}

	public void setGrossCost(BigDecimal grossCost) {
		this.grossCost = grossCost;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GoodImpl [name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", imported=");
		builder.append(imported);
		builder.append(", taxRate=");
		builder.append(taxRate);
		builder.append(", grossCost=");
		builder.append(grossCost);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append("]");
		return builder.toString();
	}

}
