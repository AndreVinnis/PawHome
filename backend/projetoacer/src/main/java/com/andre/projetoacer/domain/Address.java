package com.andre.projetoacer.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String cep;
	private String city;
	private String neighborhood;
	private Integer number;
	private String referencePoint;
	
	public Address() {
		
	}

	public Address(String cep, String city, String neighborhood, Integer number, String referencePoint) {
		super();
		this.cep = cep;
		this.city = city;
		this.neighborhood = neighborhood;
		this.number = number;
		this.referencePoint = referencePoint;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getReferencePoint() {
		return referencePoint;
	}

	public void setReferencePoint(String referencePoint) {
		this.referencePoint = referencePoint;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cep, number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(cep, other.cep) && Objects.equals(number, other.number);
	}
}
