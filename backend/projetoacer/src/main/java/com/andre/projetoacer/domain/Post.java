package com.andre.projetoacer.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.andre.projetoacer.DTO.AuthorDTO;

@Document(collection="post")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private Date date;
	private String title;
	private AuthorDTO author; 
	
	@Field(targetType = FieldType.BINARY) 
    private byte[] imageAnimal;
	
	@Field(targetType = FieldType.BINARY) 
    private byte[] imageUser;
	
	public Post() {
		
	}
	
	public Post(Date date, String title, AuthorDTO author) {
		super();
		this.date = date;
		this.title = title;
		this.author = author;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}

	public byte[] getImageAnimal() {
		return imageAnimal;
	}

	public void setImageAnimal(byte[] imageAnimal) {
		this.imageAnimal = imageAnimal;
	}

	public byte[] getImageUser() {
		return imageUser;
	}

	public void setImageUser(byte[] imageUser) {
		this.imageUser = imageUser;
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
		Post other = (Post) obj;
		return Objects.equals(id, other.id);
	}
}
