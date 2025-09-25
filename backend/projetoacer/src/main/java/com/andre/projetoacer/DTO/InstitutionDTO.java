package com.andre.projetoacer.DTO;

import java.io.Serializable;

import com.andre.projetoacer.domain.Address;
import com.andre.projetoacer.domain.Institution;

public class InstitutionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String institutionId;
    private String name;
    private Address address;
    private String imagemUrl;
    private String description;
    private String phoneNumber;

    public InstitutionDTO() {

    }

    public InstitutionDTO(Institution institution) {
        institutionId = institution.getId();
        name = institution.getName();
        address = institution.getAddress();
        description = institution.getDescription();
        phoneNumber = institution.getPhoneNumber();
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }
    
    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}
