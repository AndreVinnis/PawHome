package com.andre.projetoacer.DTO;

import java.io.Serializable;

import com.andre.projetoacer.domain.Institution;
import com.andre.projetoacer.domain.User;

public class AuthorDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String userId;
	private String name;
	private String imagemUrl;
	
	public AuthorDTO() {
		
	}
	public AuthorDTO(User user) {
		userId = user.getId();
		name = user.getName();
		//imagemUrl = user.imagemFilePath();
	}
	
	public AuthorDTO(Institution institution) {
		name = institution.getName();
		imagemUrl = institution.imagemFilePath();
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
