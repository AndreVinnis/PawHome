package com.andre.projetoacer.DTO;

import java.io.Serializable;

import com.andre.projetoacer.domain.Institution;
import com.andre.projetoacer.domain.User;

public class AuthorDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private String phoneNumber;
	private String[] adressResumed = new String[2];
	private String imagemUrl;
	
	public AuthorDTO(User user) {
		name = user.getName();
		email = user.getEmail();
		phoneNumber = user.getPhoneNumber();
		adressResumed[0] = user.getAdress().getCity();
		adressResumed[1] = user.getAdress().getNeighborhood();
		imagemUrl = user.imagemFilePath();
	}
	
	public AuthorDTO(Institution institution) {
		name = institution.getName();
		email = institution.getEmail();
		phoneNumber = institution.getPhoneNumber();
		adressResumed[0] = institution.getAdress().getCity();
		adressResumed[1] = institution.getAdress().getNeighborhood();
		imagemUrl = institution.imagemFilePath();
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String[] getAdressResumed() {
		return adressResumed;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

}
