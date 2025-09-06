package com.andre.projetoacer.domain;

import java.io.Serializable;
import java.util.Objects;

import com.andre.projetoacer.enums.Size;
import com.andre.projetoacer.enums.Species;


public class Animal implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private Integer age;
	private Double weight;
	private Species type;
	private Size size;
	private Boolean isAdopted;
	
	public Animal(String name, Integer age, Double weight, Species type, Size size) {
		super();
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.type = type;
		this.size = size;
		isAdopted = false;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Species getType() {
		return type;
	}

	public void setType(Species type) {
		this.type = type;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Boolean getIsAdopted() {
		return isAdopted;
	}

	public void setIsAdopted(Boolean isAdopted) {
		this.isAdopted = isAdopted;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Animal other = (Animal) obj;
		return Objects.equals(id, other.id);
	}
}
