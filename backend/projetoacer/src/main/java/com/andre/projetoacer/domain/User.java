package com.andre.projetoacer.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document
public class User extends GenericUser{
	private static final long serialVersionUID = 1L;
	private String secondName;
	private String cpf;
	private Integer age;
	
	@Field(targetType = FieldType.BINARY)
	private byte[] image;

	public User(String name, String email, String phoneNumber, String password, Adress adress, String secondName, String cpf, Integer age, byte[] image) {
		super(name, email, phoneNumber, password, adress);
		this.secondName = secondName;
		this.cpf = cpf;
		this.age = age;
		this.image = image;
		
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public byte[] getImage() {
    	return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
