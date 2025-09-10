package com.andre.projetoacer.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.andre.projetoacer.enums.PetDisease;
import com.andre.projetoacer.enums.PetMedication;

@Document(collation = "records")
public class MedicalRecords implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private List<PetDisease> diseases = new LinkedList<>();
	private List<PetMedication> medications = new LinkedList<>();
	
	public MedicalRecords(List<PetDisease> diseases, List<PetMedication> medications) {
		super();
		this.diseases = diseases;
		this.medications = medications;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public List<PetDisease> getDiseases() {
		return diseases;
	}
	
	public void setDiseases(List<PetDisease> diseases) {
		this.diseases = diseases;
	}
	
	public List<PetMedication> getMedications() {
		return medications;
	}
	
	public void setMedications(List<PetMedication> medications) {
		this.medications = medications;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(diseases, medications);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalRecords other = (MedicalRecords) obj;
		return Objects.equals(diseases, other.diseases) && Objects.equals(medications, other.medications);
	}
}
