/**
 * 
 */
package com.lastminute.test.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.lastminute.test.data.entity.Tax;

/**
 * CRUD Repository for TAXES table. See entity {@link Tax}.
 * 
 * @author nconte
 *
 */
public interface TaxRepository extends CrudRepository<Tax, Long> {

}
