package com.andre.projetoacer.DTO;

import java.io.Serializable;

import com.andre.projetoacer.domain.Animal;
import com.andre.projetoacer.enums.Sex;
import com.andre.projetoacer.enums.Type;

public class AnimalDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String animalId;
	private String name;
	private Integer age;
	private Sex sex;
	private Type type;
	private Boolean isAdopted;
	private String imagemUrl;
	
	public AnimalDTO() {
		
	}
	public AnimalDTO(Animal animal) {
		animalId = animal.getId();
		name = animal.getName();
		age = animal.getAge();
		sex = animal.getSex();
		type = animal.getType();
		isAdopted = animal.getIsAdopted();
	}
	
	public String getAnimalId() {
		return animalId;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public Sex getSex() {
		return sex;
	}

	public Type getType() {
		return type;
	}

	public Boolean getIsAdopted() {
		return isAdopted;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

}
