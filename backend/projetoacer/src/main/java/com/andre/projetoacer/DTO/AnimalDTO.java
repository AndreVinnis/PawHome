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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public Boolean getAdopted() {
        return isAdopted;
    }

    public void setAdopted(Boolean adopted) {
        isAdopted = adopted;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
