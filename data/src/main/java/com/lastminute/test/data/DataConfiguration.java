package com.lastminute.test.data;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

/**
 * Spring configuration for data module.
 * 
 * @author nconte
 *
 */
@Configuration
@EnableJpaRepositories("com.lastminute.test.data.repo")
public class DataConfiguration {

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScripts("db/script/create.sql", "db/script/insert.sql").build();
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		EclipseLinkJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.H2);
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(true);
		return jpaVendorAdapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerfactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerfactory.setDataSource(dataSource());
		entityManagerfactory.setJpaVendorAdapter(jpaVendorAdapter());
		entityManagerfactory.setPackagesToScan("com.lastminute.test.data.entity");

		Map<String, String> jpaProperties = new HashMap<String, String>();
		jpaProperties.put("eclipselink.weaving", "false");
		jpaProperties.put("eclipselink.logging.parameters", "true");
		entityManagerfactory.setJpaPropertyMap(jpaProperties);
		return entityManagerfactory;
	}

}
