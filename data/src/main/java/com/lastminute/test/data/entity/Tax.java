/**
 * 
 */
package com.lastminute.test.data.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lastminute.test.domain.GoodType;

/**
 * @author nconte
 *
 */
@Entity
@Table(name = "TAXES", uniqueConstraints = @UniqueConstraint(columnNames = { "GOODTYPE" }))
public class Tax {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Enumerated(EnumType.STRING)
	private GoodType goodType;
	private Double tax;
	private Double importedTax;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GoodType getGoodType() {
		return goodType;
	}

	public void setGoodType(GoodType goodType) {
		this.goodType = goodType;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getImportedTax() {
		return importedTax;
	}

	public void setImportedTax(Double importedTax) {
		this.importedTax = importedTax;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tax [id=");
		builder.append(id);
		builder.append(", goodType=");
		builder.append(goodType);
		builder.append(", tax=");
		builder.append(tax);
		builder.append(", importedTax=");
		builder.append(importedTax);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tax other = (Tax) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
