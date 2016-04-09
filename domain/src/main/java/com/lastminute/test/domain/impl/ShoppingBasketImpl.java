/**
 * 
 */
package com.lastminute.test.domain.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.lastminute.test.domain.Good;
import com.lastminute.test.domain.ShoppingBasket;

/**
 * @author nconte
 *
 */
public class ShoppingBasketImpl implements ShoppingBasket {

	private Collection<Good> goods;
	private BigDecimal total;
	private BigDecimal salesTaxes;

	public Collection<Good> getGoods() {
		return goods;
	}

	public void setGoods(Collection<Good> goods) {
		this.goods = goods;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getSalesTaxes() {
		return salesTaxes;
	}

	public void setSalesTaxes(BigDecimal salesTaxes) {
		this.salesTaxes = salesTaxes;
	}

	/**
	 * Add a good into the basket
	 * @param good
	 */
	public void addGood(Good good){
		if(goods == null){
			goods = new ArrayList<Good>();
		}
		goods.add(good);
	}
	
	@Override
	public String toString() {
		final int maxLen = 5;
		StringBuilder builder = new StringBuilder();
		builder.append("ShoppingBasketImpl [goods=");
		builder.append(goods != null ? toString(goods, maxLen) : null);
		builder.append(", total=");
		builder.append(total);
		builder.append(", salesTaxes=");
		builder.append(salesTaxes);
		builder.append("]");
		return builder.toString();
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

}
