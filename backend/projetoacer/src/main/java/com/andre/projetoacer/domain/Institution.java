package com.andre.projetoacer.domain;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Institution extends GenericUser{
	private static final long serialVersionUID = 1L;
	private String cnpj;
	private String description;
	private Date createDate;
	
	public Institution(String name, String email, String phoneNumber, String password, Adress adress, String cnpj, String description, Date createDate) {
		super(name, email, phoneNumber, password, adress);
		this.cnpj = cnpj;
		this.description = description;
		this.createDate = createDate;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
