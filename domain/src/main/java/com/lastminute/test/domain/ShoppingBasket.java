/**
 * 
 */
package com.lastminute.test.domain;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author nconte
 *
 */
public interface ShoppingBasket {

	public Collection<Good> getGoods();
	
	public void addGood(Good good);

	public BigDecimal getTotal();

	public BigDecimal getSalesTaxes();
	
	public void setTotal(BigDecimal total);

	public void setSalesTaxes(BigDecimal salesTaxes);
}
