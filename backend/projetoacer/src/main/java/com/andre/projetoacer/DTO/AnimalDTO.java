package com.andre.projetoacer.DTO;

import java.util.Objects;

import com.andre.projetoacer.domain.Animal;
import com.andre.projetoacer.enums.Sex;
import com.andre.projetoacer.enums.Species;
import com.andre.projetoacer.enums.Type;

public class AnimalDTO {
	private Integer id;
	private String name;
	private Sex sex;
	private Species species;
	private Type type;
	private Boolean isAdopted;
	
	public AnimalDTO(Animal animal) {
		id = animal.getId();
		name = animal.getName();
		sex = animal.getSex();
		species = animal.getSpecies();
		type = animal.getType();
		isAdopted = animal.getIsAdopted();
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

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Species getSpecies() {
		return species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
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
		AnimalDTO other = (AnimalDTO) obj;
		return Objects.equals(id, other.id);
	}
}
