package com.lastminute.test.services;

import static java.text.MessageFormat.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lastminute.test.data.DataConfiguration;
import com.lastminute.test.data.entity.Tax;
import com.lastminute.test.data.repo.TaxRepository;
import com.lastminute.test.domain.GoodType;
import com.lastminute.test.domain.impl.GoodImpl;
import com.lastminute.test.domain.impl.ShoppingBasketImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataConfiguration.class)
public class ShoppingReceiptServiceImplTest {

	private static final Logger logger = LoggerFactory.getLogger(ShoppingReceiptServiceImplTest.class);

	@Autowired
	private TaxRepository taxRepository;
	private ShoppingReceiptServiceImpl service;

	@Before
	public void buildService() {
		assertNotNull("REPO is NULL", taxRepository);
		service = new ShoppingReceiptServiceImpl();
		service.setTaxRepository(taxRepository);
	}

	@Test
	public void testRoundTo05(){
		BigDecimal bd1 = new BigDecimal("1.76");
		bd1 = bd1.divide(new BigDecimal("0.05"), 0, RoundingMode.UP).multiply(new BigDecimal("0.05"));
		assertEquals(new BigDecimal("1.80"), bd1);
		
		BigDecimal bd2 = new BigDecimal("1.74");
		bd2 = bd2.divide(new BigDecimal("0.05"), 0, RoundingMode.UP).multiply(new BigDecimal("0.05"));
		assertEquals(new BigDecimal("1.75"), bd2);
		
		BigDecimal bd3 = new BigDecimal("1.72");
		bd3 = bd3.divide(new BigDecimal("0.05"), 0, RoundingMode.UP).multiply(new BigDecimal("0.05"));
		assertEquals(new BigDecimal("1.75"), bd3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testValidateInputNullException() {
		assertNotNull("service is NULL", service);
		service.validateInput(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testValidateInputEmptyException() {
		assertNotNull("service is NULL", service);
		ShoppingBasketImpl shoppingBasket = new ShoppingBasketImpl();
		service.validateInput(shoppingBasket);
	}

	@Test
	public void testAfterProperty() {
		try {
			service.afterPropertiesSet();
			Map<GoodType, Tax> taxByType = service.getTaxByType();
			assertNotNull("taxMap is NULL", taxByType);
			logger.debug(format("taxMap size {0}", taxByType.size()));
			Assert.assertEquals(4, taxByType.size());
			logger.debug("printing taxMap content");
			taxByType.forEach((type, tax) -> logger.debug(format("{0}|{1}", type, tax)));
		} catch (Exception e) {
			logger.error("testAfterProperty FAIL", e);
			fail(e.getMessage());
		}
	}

	@Test
	public void testFillTaxes() {
		ShoppingBasketImpl shoppingBasket = new ShoppingBasketImpl();

		GoodImpl book = new GoodImpl("book", GoodType.BOOK, new BigDecimal("12.49"));
		shoppingBasket.addGood(book);
		GoodImpl food = new GoodImpl("food", GoodType.FOOD, new BigDecimal("14.99"));
		shoppingBasket.addGood(food);
		GoodImpl medical = new GoodImpl("medical", GoodType.MEDICAL, new BigDecimal("4.49"));
		shoppingBasket.addGood(medical);
		GoodImpl other = new GoodImpl("other", GoodType.OTHER, new BigDecimal("9.54"));
		shoppingBasket.addGood(other);
		GoodImpl otherImp = new GoodImpl("other imp", GoodType.OTHER, new BigDecimal("9.54"), true);
		shoppingBasket.addGood(otherImp);
		GoodImpl foodImp = new GoodImpl("food imp", GoodType.FOOD, new BigDecimal("14.99"), true);
		shoppingBasket.addGood(foodImp);

		try {
			service.afterPropertiesSet();
		} catch (Exception e) {
			logger.error("testAfterProperty FAIL", e);
			fail(e.getMessage());
		}
		service.fillTaxes(shoppingBasket);

		shoppingBasket.getGoods().forEach(good -> {logger.debug(good.toString());assertNotNull("TAX is NULL", good.getTaxRate());});
	}
	
	@Test
	public void testPrint(){
		ShoppingBasketImpl shoppingBasket = new ShoppingBasketImpl();

		GoodImpl book = new GoodImpl("book", GoodType.BOOK, new BigDecimal("12.49"));
		shoppingBasket.addGood(book);
		GoodImpl food = new GoodImpl("food", GoodType.FOOD, new BigDecimal("14.99"));
		shoppingBasket.addGood(food);
		GoodImpl medical = new GoodImpl("medical", GoodType.MEDICAL, new BigDecimal("4.49"));
		shoppingBasket.addGood(medical);
		GoodImpl other = new GoodImpl("other", GoodType.OTHER, new BigDecimal("9.54"));
		shoppingBasket.addGood(other);
		GoodImpl otherImp = new GoodImpl("other imp", GoodType.OTHER, new BigDecimal("9.54"), true);
		shoppingBasket.addGood(otherImp);
		GoodImpl foodImp = new GoodImpl("food imp", GoodType.FOOD, new BigDecimal("14.99"), true);
		shoppingBasket.addGood(foodImp);
		
		shoppingBasket.setTotal(new BigDecimal("99.99"));
		shoppingBasket.setSalesTaxes(new BigDecimal("9.99"));
		
		service.print(shoppingBasket);
	}
	
	@Test
	public void testSumAmount(){
		ShoppingBasketImpl shoppingBasket = new ShoppingBasketImpl();
		GoodImpl book = new GoodImpl("book", GoodType.BOOK, new BigDecimal("1.20"));
		shoppingBasket.addGood(book);
		GoodImpl food = new GoodImpl("food", GoodType.FOOD, new BigDecimal("1.30"));
		shoppingBasket.addGood(food);
		
		BigDecimal sumAmount = service.sumAmount(shoppingBasket, x -> x.getGrossCost());
		assertEquals(new BigDecimal("2.50").setScale(2, RoundingMode.HALF_UP), sumAmount);
	}
}
