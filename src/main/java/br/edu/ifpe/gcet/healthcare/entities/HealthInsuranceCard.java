package br.edu.ifpe.gcet.healthcare.entities;

import jakarta.persistence.*;

@Entity
public class HealthInsuranceCard {
    @Id
    @Column(unique = true)
    private String code;
    private long expirationDate;
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "patient_cpf", nullable = false)
    private Patient patient;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
