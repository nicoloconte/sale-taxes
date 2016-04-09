package com.lastminute.test.data;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lastminute.test.data.entity.Tax;
import com.lastminute.test.data.repo.TaxRepository;
import com.lastminute.test.domain.GoodType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataConfiguration.class)
public class DataTest {

	@Autowired
	private TaxRepository taxRepository;

	@Before
	public void before() {
		taxRepository.deleteAll();
	}

	@Test
	public void testRepository() {
		assertNotNull(taxRepository);
	}

	@Test(expected = Exception.class)
	public void testNotUniqueEmpty() {
		Tax tax = new Tax();
		tax.setGoodType(GoodType.BOOK);
		tax.setImportedTax(0.05);

		taxRepository.save(tax);

		Tax tax2 = new Tax();
		tax2.setGoodType(GoodType.BOOK);
		tax2.setImportedTax(0.05);

		taxRepository.save(tax2);
	}

	@Test
	public void testRepoPopulation() {
		Tax tax = new Tax();
		tax.setGoodType(GoodType.BOOK);
		tax.setImportedTax(0.05);

		taxRepository.save(tax);

		assertNotNull("TAX ID is NULL", taxRepository.findOne(tax.getId()));
		assertNotNull("TAX is not saved", taxRepository.findOne(tax.getId()));
	}
}
