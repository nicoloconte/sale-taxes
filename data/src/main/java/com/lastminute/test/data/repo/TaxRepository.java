/**
 * 
 */
package com.lastminute.test.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.lastminute.test.data.entity.Tax;

/**
 * @author nconte
 *
 */
public interface TaxRepository extends CrudRepository<Tax, Long> {

}
