/**
 * 
 */
package com.lastminute.test.domain;

import java.math.BigDecimal;

/**
 * @author nconte
 *
 */
public interface Good {

	public String getName();

	public BigDecimal getCost();

	public GoodType getType();
	
	public Boolean getImported();
	
	public BigDecimal getTaxRate();

	public void setTaxRate(BigDecimal taxRate);

	public BigDecimal getGrossCost();
	
	public Integer getQuantity();
}
