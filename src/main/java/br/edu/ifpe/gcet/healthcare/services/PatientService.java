package br.edu.ifpe.gcet.healthcare.services;

import br.edu.ifpe.gcet.healthcare.dto.NewPatientDTO;
import br.edu.ifpe.gcet.healthcare.entities.HealthInsuranceCard;
import br.edu.ifpe.gcet.healthcare.entities.Patient;
import br.edu.ifpe.gcet.healthcare.repositories.HealthInsuranceCardRepository;
import br.edu.ifpe.gcet.healthcare.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class PatientService {
    @Autowired
    private PatientRepository patientRepo;
    
    @Autowired
    private HealthInsuranceCardRepository cardRepo;
    
    public PatientService(PatientRepository patientRepository, HealthInsuranceCardRepository cardRepository) {
        this.patientRepo = patientRepository;
        this.cardRepo = cardRepository;
    }
    
    public Optional<Patient> savePatient(NewPatientDTO patientDTO) {
        Patient p = patientDTO.getPatient();
        HealthInsuranceCard c = patientDTO.getHealthInsuranceCard();
        
        LocalDate minBirthDate = LocalDate.now().minusYears(18);
        
        if(p.getCpf() == null || p.getEmail().isBlank() || p.getName().isBlank()) {
            throw new IllegalArgumentException("Credenciais inválidas!");
        }
        
        if(patientDTO.getBirthDate().isAfter(minBirthDate)) {
            throw new IllegalArgumentException("O paciente deve ser maior de idade!");
        }

        Optional<Patient> patient = patientRepo.findById(p.getCpf());
        Optional<HealthInsuranceCard> card = cardRepo.findById(c.getCode());
        
        if(patient.isPresent() || card.isPresent()) {
            return Optional.empty();
        }
        
        cardRepo.save(c);

        return Optional.ofNullable(patientRepo.save(p));
    }
}
