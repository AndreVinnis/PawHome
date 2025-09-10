package com.andre.projetoacer.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "institution")
public class Institution extends GenericUser{
	private static final long serialVersionUID = 1L;
	private String cnpj;
	private String description;
	
	public Institution(String cnpj, String description) {
		super();
		this.cnpj = cnpj;
		this.description = description;
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
}
