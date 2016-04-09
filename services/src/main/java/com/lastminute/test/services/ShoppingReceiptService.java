package com.lastminute.test.services;

import com.lastminute.test.domain.ShoppingBasket;

/**
 * 
 * @author nconte
 *
 */
public interface ShoppingReceiptService {

	/**
	 * Print the receipt based on goods filled into the shopping basket. It
	 * print the receipt details with taxes.
	 * 
	 * @return
	 */
	public ShoppingBasket printReceipt(ShoppingBasket shoppingBasket);

}
