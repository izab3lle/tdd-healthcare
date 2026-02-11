package br.edu.ifpe.gcet.healthcare.dto;

import br.edu.ifpe.gcet.healthcare.entities.HealthInsuranceCard;
import br.edu.ifpe.gcet.healthcare.entities.Patient;
import jakarta.persistence.Column;

public class NewPatientDTO {
    private String cpf;
    private String name;
    private String email;
    private long birthDate;

    private String cardCode;
    private long cardExpirationDate;
    private String cardName;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public long getCardExpirationDate() {
        return cardExpirationDate;
    }

    public void setCardExpirationDate(long cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    
    public Patient getPatient() {
        Patient p = new Patient();
        
        p.setName(this.name);
        p.setEmail(this.email);
        p.setBirthDate(this.birthDate);
        p.setCpf(this.cpf);
        
        return p;
    }
    
    public HealthInsuranceCard getHealthInsuranceCard() {
        HealthInsuranceCard c = new HealthInsuranceCard();
        Patient p = new Patient();
        p.setCpf(this.cpf);
        
        c.setPatient(p);
        c.setCode(this.cardCode);
        c.setExpirationDate(this.cardExpirationDate);
        c.setName(this.cardName);
        
        return c;
    }
}
