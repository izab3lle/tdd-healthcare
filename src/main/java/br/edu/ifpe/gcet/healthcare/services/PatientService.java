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
    
    public PatientService(PatientRepository patientRepository) {
        this.patientRepo = patientRepository;
    }
    
    public Optional<Patient> savePatient(Patient patient) {
        LocalDate minBirthDate = LocalDate.now().minusYears(18);
        
        if(patient.getCpf() == null || patient.getEmail().isBlank() || patient.getName().isBlank()) {
            throw new IllegalArgumentException("Credenciais inválidas!");
        }
        
        if(patient.getBirthDate().isAfter(minBirthDate)) {
            throw new IllegalArgumentException("O paciente deve ser maior de idade!");
        }

        Optional<Patient> p = patientRepo.findById(patient.getCpf());
        
        if(p.isPresent()) {
            return Optional.empty();
        }

        return Optional.ofNullable(patientRepo.save(patient));
    }
}
