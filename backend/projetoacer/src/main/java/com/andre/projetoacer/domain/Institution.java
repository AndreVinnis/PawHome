package com.andre.projetoacer.domain;

public class Institution extends GenericUser{
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
