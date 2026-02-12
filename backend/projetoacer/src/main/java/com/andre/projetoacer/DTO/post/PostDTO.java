package com.andre.projetoacer.DTO.post;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.andre.projetoacer.domain.Post;
import com.andre.projetoacer.enums.Sex;
import com.andre.projetoacer.enums.Type;

public class PostDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String userName;
	private String animalName;
	private Sex animalSex;
	private Type animalType;
	private Boolean animalIsAdopted;
	
	@Field(targetType = FieldType.BINARY) 
    private byte[] imageAnimal;
	
	@Field(targetType = FieldType.BINARY) 
    private byte[] imageUser;
	
	public PostDTO(Post post) {
		userName = post.getAuthor().getName();
		animalName = post.getAnimalDTO().getName();
		animalSex = post.getAnimalDTO().getSex();
		animalType = post.getAnimalDTO().getType();
		animalIsAdopted = post.getAnimalDTO().getIsAdopted();
		animalName = post.getAnimalDTO().getName();

        imageAnimal = post.getImageAnimal();
        imageUser = post.getImageUser();
	}

	public String getUserName() {
		return userName;
	}

	public String getAnimalName() {
		return animalName;
	}

	public Sex getAnimalSex() {
		return animalSex;
	}

	public Type getAnimalType() {
		return animalType;
	}

	public Boolean getAnimalIsAdopted() {
		return animalIsAdopted;
	}

	public byte[] getImageAnimal() {
		return imageAnimal;
	}

	public byte[] getImageUser() {
		return imageUser;
	}

	public void setImageAnimal(byte[] imageAnimal) {
		this.imageAnimal = imageAnimal;
	}

	public void setImageUser(byte[] imageUser) {
		this.imageUser = imageUser;
	}
}
