package com.andre.projetoacer.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.andre.projetoacer.enums.Sex;
import com.andre.projetoacer.enums.Size;
import com.andre.projetoacer.enums.Species;
import com.andre.projetoacer.enums.Type;
import com.andre.projetoacer.enums.Race;

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
	private String description;
	private Race race;
	private MedicalRecords medicalRecords;

	@Field(targetType = FieldType.BINARY)
	private byte[] image;

	public Animal() {

	}

	public Animal(String name, Integer age, Double weight, Sex sex, Species species, Size size, Type type, Race race, String description) {
		super();
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.sex = sex;
		this.species = species;
		this.size = size;
		this.type = type;
		this.race = race;
		this.description = description;
		isAdopted = false;
		medicalRecords = new MedicalRecords();
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

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Boolean getIsAdopted() {
		return isAdopted;
	}

	public void setIsAdopted(Boolean isAdopted) {
		this.isAdopted = isAdopted;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public MedicalRecords getMedicalRecords() {
		return medicalRecords;
	}

	public void setMedicalRecords(MedicalRecords medicalRecords) {
		this.medicalRecords = medicalRecords;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
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
