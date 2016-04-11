/**
 * 
 */
package com.lastminute.test.services;

import static java.text.MessageFormat.format;
import static org.springframework.util.Assert.notEmpty;
import static org.springframework.util.Assert.notNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lastminute.test.data.entity.Tax;
import com.lastminute.test.data.repo.TaxRepository;
import com.lastminute.test.domain.Good;
import com.lastminute.test.domain.GoodType;
import com.lastminute.test.domain.ShoppingBasket;

/**
 * Implementation of {@link ShoppingBasket}. After bean initialization, it loads
 * the taxes' table items for cache purpose.
 * 
 * @author nconte
 *
 */
@Service
public class ShoppingReceiptServiceImpl implements ShoppingReceiptService, InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(ShoppingReceiptServiceImpl.class);

	@Autowired
	private TaxRepository taxRepository;

	private Map<GoodType, Tax> taxByType;

	@Override
	public ShoppingBasket printReceipt(ShoppingBasket shoppingBasket) {
		logger.info("start processing shoppingBasket");
		validateInput(shoppingBasket);
		logger.debug(format("input {0}", shoppingBasket));
		fillTaxes(shoppingBasket);
		BigDecimal grandTotal = sumAmount(shoppingBasket, x -> x.getCost());
		BigDecimal grossCosts = sumAmount(shoppingBasket, x -> x.getGrossCost());
		logger.debug(format("grandTotal is {0}", grandTotal));
		logger.debug(format("taxes are {0}", grandTotal.subtract(grossCosts)));
		shoppingBasket.setTotal(grandTotal);
		shoppingBasket.setSalesTaxes(grandTotal.subtract(grossCosts));

		print(shoppingBasket);

		logger.info("end processing shoppingBasket");
		return shoppingBasket;
	}

	/**
	 * Print the receipt into the logs.
	 * 
	 * @param shoppingBasket
	 */
	public void print(ShoppingBasket shoppingBasket) {
		logger.debug("------------------------------");
		for (Good good : shoppingBasket.getGoods()) {
			logger.debug(format("{0} {1}{2} : {3}", good.getQuantity(), good.getImported() ? "imported " : "", good.getName(), good.getCost()));
		}
		logger.debug(format("Sales Taxes: {0}", shoppingBasket.getSalesTaxes()));
		logger.debug(format("Total: {0}", shoppingBasket.getTotal()));
		logger.debug("------------------------------");
	}

	/**
	 * Validate input fields
	 * 
	 * @param shoppingBasket
	 */
	public void validateInput(ShoppingBasket shoppingBasket) {
		notNull(shoppingBasket, "shoppingBasket CANNOT be NULL");
		notEmpty(shoppingBasket.getGoods(), "shoppingBasket CANNOT be EMPTY");
	}

	/**
	 * Fill taxes for any goods in the shopping basket.
	 * 
	 * @param shoppingBasket
	 */
	public void fillTaxes(ShoppingBasket shoppingBasket) {
		logger.debug("start filling taxes");
		shoppingBasket.getGoods().forEach(
				good -> {
					Tax taxToBeApplied = taxByType.get(good.getType());
					logger.debug(format("tax for {0} is {1}", good, taxToBeApplied));
					if (taxToBeApplied != null) {
						good.setTaxRate(good.getImported() ? new BigDecimal(taxToBeApplied.getImportedTax() + taxToBeApplied.getTax()) : new BigDecimal(
								taxToBeApplied.getTax()));
					}
				});
		logger.debug("end filling taxes");
	}

	/**
	 * Sum amount for all goods in the shopping basket with the input function.
	 * 
	 * @param shoppingBasket
	 * @param func
	 */
	public BigDecimal sumAmount(ShoppingBasket shoppingBasket, Function<Good, BigDecimal> func) {
		Optional<BigDecimal> reduce = shoppingBasket.getGoods().stream().map(func).reduce((x, y) -> x.add(y));
		return roundAmountHalfUp(reduce.get());
	}

	/**
	 * Round amount to two decimal (HALF_UF)
	 * 
	 * @param amount
	 * @return
	 */
	public BigDecimal roundAmountHalfUp(BigDecimal amount) {
		return amount.setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * Loads all taxes from TAXES table and put them into a map
	 * {@code Map<GoodType, Tax> taxByType} where key is {@link GoodType} for
	 * caching purpose.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug("start building taxMap");
		taxByType = new ConcurrentHashMap<GoodType, Tax>();
		Iterable<Tax> taxes = taxRepository.findAll();
		taxes.forEach(tax -> {
			logger.debug(format("adding to taxMap {0}", tax));
			taxByType.put(tax.getGoodType(), tax);
		});
		logger.debug("end building taxMap");
	}

	public TaxRepository getTaxRepository() {
		return taxRepository;
	}

	public void setTaxRepository(TaxRepository taxRepository) {
		this.taxRepository = taxRepository;
	}

	public Map<GoodType, Tax> getTaxByType() {
		return taxByType;
	}

	public void setTaxByType(Map<GoodType, Tax> taxByType) {
		this.taxByType = taxByType;
	}

}
