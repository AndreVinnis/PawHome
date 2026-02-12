package com.andre.projetoacer.DTO.user;

import java.io.Serializable;

import com.andre.projetoacer.domain.GenericUser;

public class AuthorDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String userId;
	private String name;
	private String imagemUrl;
	
	public AuthorDTO() {
	}
	
	public AuthorDTO(GenericUser user) {
		userId = user.getId();
		name = user.getName(); 
	}
	
	public String getUserID() {
		return userId;
	}
	
	public String getName() {
		return name;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

}
