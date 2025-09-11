package com.andre.projetoacer.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.andre.projetoacer.enums.Sex;
import com.andre.projetoacer.enums.Size;
import com.andre.projetoacer.enums.Species;
import com.andre.projetoacer.enums.Type;

@Document
public class Animal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String name;
	private Integer age;
	private Double weight;
	private Sex sex;
	private Species species;
	private Size size;
	private Type type;
	private Boolean isAdopted;
	private String descricao;

	@Field(targetType = FieldType.BINARY)
	private byte[] image;

	public Animal() {

	}

	public Animal(String name, Integer age, Double weight, Sex sex, Species species, Size size, Type type, String descricao) {
		super();
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.sex = sex;
		this.species = species;
		this.size = size;
		this.type = type;
		this.descricao = descricao;
		isAdopted = false;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Species getSpecies() {
		return species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Boolean getIsAdopted() {
		return isAdopted;
	}

	public void setIsAdopted(Boolean isAdopted) {
		this.isAdopted = isAdopted;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public String imagemFilePath() {
		String imagemUrl = ServletUriComponentsBuilder
     	        .fromCurrentContextPath()
     	        .path("/posts/") //tlz dê erro (com certeza vai)
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
		Animal other = (Animal) obj;
		return Objects.equals(id, other.id);
	}
}
