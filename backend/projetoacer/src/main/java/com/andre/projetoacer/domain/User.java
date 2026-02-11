package com.andre.projetoacer.domain;

import java.util.Date;
import com.andre.projetoacer.DTO.user.UserCreationDTO;
import com.andre.projetoacer.enums.UserRole;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User extends GenericUser{
	private static final long serialVersionUID = 1L;
	private String secondName;
	private String cpf;
	private Date birthDate;

	public User(String name, String email, String phoneNumber, String password, Address address, String secondName, String cpf, Date birthDate, UserRole role) {
		super(name, email, phoneNumber, password, address, role);
		this.secondName = secondName;
		this.cpf = cpf;
		this.birthDate = birthDate;
	}

    public User() {
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
