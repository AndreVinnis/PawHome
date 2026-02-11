package com.andre.projetoacer.domain;

import java.util.Date;

import com.andre.projetoacer.DTO.user.UserCreationDTO;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User extends GenericUser{
	private static final long serialVersionUID = 1L;
	private String secondName;
	private String cpf;
	private Date birthDate;

	public User(String name, String email, String phoneNumber, String password, Address address, String secondName, String cpf, Date birthDate) {
		super(name, email, phoneNumber, password, address);
		this.secondName = secondName;
		this.cpf = cpf;
		this.birthDate = birthDate;
	}

    public User() {
    }

    public User(UserCreationDTO newUser) {
        super(newUser.name(), newUser.email(), newUser.phoneNumber(), newUser.password(), new Address(newUser.cep(), newUser.city(), newUser.neighborhood(), newUser.number(), newUser.referencePoint()));
        this.secondName = newUser.secondName();
        this.cpf = newUser.cpf();
        this.birthDate = newUser.birthDate();
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
