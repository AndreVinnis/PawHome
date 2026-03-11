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

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
