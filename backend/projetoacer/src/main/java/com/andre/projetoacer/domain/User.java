package com.andre.projetoacer.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "user")
public class User extends GenericUser{
	private static final long serialVersionUID = 1L;
	private String secondName;
	private String cpf;
	private Integer age;
	
	public User(String secondName, String cpf, Integer age) {
		super();
		this.secondName = secondName;
		this.cpf = cpf;
		this.age = age;
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
}
