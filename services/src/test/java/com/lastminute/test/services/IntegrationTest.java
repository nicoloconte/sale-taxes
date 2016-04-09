package com.lastminute.test.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lastminute.test.data.DataConfiguration;
import com.lastminute.test.domain.GoodType;
import com.lastminute.test.domain.ShoppingBasket;
import com.lastminute.test.domain.impl.GoodImpl;
import com.lastminute.test.domain.impl.ShoppingBasketImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfiguration.class, ServiceConfiguration.class})
public class IntegrationTest {

	@Autowired
	private ShoppingReceiptService service;
	
	@Before
	public void before(){
		assertNotNull("service is NULL", service);
	}
	
	@Test
	public void testReceipt1(){
		ShoppingBasket shoppingBasket = new ShoppingBasketImpl();

		GoodImpl book = new GoodImpl("book", GoodType.BOOK, new BigDecimal("12.49"));
		shoppingBasket.addGood(book);
		GoodImpl music = new GoodImpl("music", GoodType.OTHER, new BigDecimal("14.99"));
		shoppingBasket.addGood(music);
		GoodImpl chocolate = new GoodImpl("chocolate", GoodType.FOOD, new BigDecimal("0.85"));
		shoppingBasket.addGood(chocolate);
		
		ShoppingBasket shoppingBasketOut = service.printReceipt(shoppingBasket);
		assertNotNull(shoppingBasketOut);
		assertEquals(shoppingBasket.getGoods().size(), shoppingBasketOut.getGoods().size());
		assertEquals(new BigDecimal("12.49"), book.getCost());
		assertEquals(new BigDecimal("16.49"), music.getCost());
		assertEquals(new BigDecimal("0.85"), chocolate.getCost());
		assertEquals(new BigDecimal("1.50"), shoppingBasketOut.getSalesTaxes());
		assertEquals(new BigDecimal("29.83"), shoppingBasketOut.getTotal());
	}
	
	@Test
	public void testReceipt3(){
		ShoppingBasket shoppingBasket = new ShoppingBasketImpl();

		GoodImpl perf = new GoodImpl("bottle of perfume imp", GoodType.OTHER, new BigDecimal("27.99"), true);
		shoppingBasket.addGood(perf);
		GoodImpl perf1 = new GoodImpl("bottle of perfume", GoodType.OTHER, new BigDecimal("18.99"));
		shoppingBasket.addGood(perf1);
		GoodImpl headache = new GoodImpl("packet of headache pills", GoodType.MEDICAL, new BigDecimal("9.75"));
		shoppingBasket.addGood(headache);
		GoodImpl choco = new GoodImpl("chocolates", GoodType.FOOD, new BigDecimal("11.25"), true);
		shoppingBasket.addGood(choco); 
		
		ShoppingBasket shoppingBasketOut = service.printReceipt(shoppingBasket);
		assertNotNull(shoppingBasketOut);
		assertEquals(shoppingBasket.getGoods().size(), shoppingBasketOut.getGoods().size());
		assertEquals(new BigDecimal("32.19"), perf.getCost());
		assertEquals(new BigDecimal("20.89"), perf1.getCost());
		assertEquals(new BigDecimal("9.75"), headache.getCost());
		assertEquals(new BigDecimal("11.85"), choco.getCost());
		assertEquals(new BigDecimal("6.70"), shoppingBasketOut.getSalesTaxes());
		assertEquals(new BigDecimal("74.68"), shoppingBasketOut.getTotal());
	}

	@Test
	public void testReceipt2(){
		ShoppingBasket shoppingBasket = new ShoppingBasketImpl();

		GoodImpl choco = new GoodImpl("box of chocolates", GoodType.FOOD, new BigDecimal("10"), true);
		shoppingBasket.addGood(choco);
		GoodImpl perf = new GoodImpl("bottle of perfume", GoodType.OTHER, new BigDecimal("47.50"), true);
		shoppingBasket.addGood(perf);
		
		ShoppingBasket shoppingBasketOut = service.printReceipt(shoppingBasket);
		assertNotNull(shoppingBasketOut);
		assertEquals(shoppingBasket.getGoods().size(), shoppingBasketOut.getGoods().size());
		assertEquals(new BigDecimal("10.50"), choco.getCost());
		assertEquals(new BigDecimal("54.65"), perf.getCost());
		assertEquals(new BigDecimal("7.65"), shoppingBasketOut.getSalesTaxes());
		assertEquals(new BigDecimal("65.15"), shoppingBasketOut.getTotal());
	}
}
