package com.andre.projetoacer.DTO;

import java.io.Serializable;

import com.andre.projetoacer.domain.Animal;
import com.andre.projetoacer.enums.Sex;
import com.andre.projetoacer.enums.Type;

public class AnimalDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Sex sex;
	private Type type;
	private Boolean isAdopted;
	private String imagemUrl;
	
	public AnimalDTO(Animal animal) {
		name = animal.getName();
		sex = animal.getSex();
		type = animal.getType();
		isAdopted = animal.getIsAdopted();
		imagemUrl = animal.imagemFilePath();
	}
	
	public String getName() {
		return name;
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
