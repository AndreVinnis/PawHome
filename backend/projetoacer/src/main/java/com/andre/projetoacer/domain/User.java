package com.andre.projetoacer.domain;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User extends GenericUser{
	private static final long serialVersionUID = 1L;
	private String secondName;
	private String cpf;
	private Date birthDate;

	public User(String name, String email, String phoneNumber, String password, Adress adress, String secondName, String cpf, Date birthDate) {
		super(name, email, phoneNumber, password, adress);
		this.secondName = secondName;
		this.cpf = cpf;
		this.birthDate = birthDate;
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
}
