package com.andre.projetoacer.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class GenericUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String name;
	private String email;
	private String phoneNumber;
	private String password;
	
	private Adress adress;
	
	@DBRef(lazy = true)
	private List<Post> posts = new LinkedList<>();
	
	@Field(targetType = FieldType.BINARY)
	private byte[] image;
	
	public GenericUser() {
		
	}

	public GenericUser(String name, String email, String phoneNumber, String password, Adress adress) {
		super();
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.adress = adress;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}
	
	public List<Post> getPosts() {
		return posts;
	}
	
	public byte[] getImage() {
		return image;
	}

	public void setImagem(byte[] image) {
		this.image = image;
	}
	
	public String imagemFilePath() {
		String imagemUrl = ServletUriComponentsBuilder
     	        .fromCurrentContextPath()
     	        .path("/posts/")
     	        .path(id)
     	        .path("/image")
     	        .toUriString();
		return imagemUrl;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericUser other = (GenericUser) obj;
		return Objects.equals(id, other.id);
	}
}
