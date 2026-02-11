package br.edu.ifpe.gcet.healthcare.services;

import br.edu.ifpe.gcet.healthcare.dto.NewPatientDTO;
import br.edu.ifpe.gcet.healthcare.entities.HealthInsuranceCard;
import br.edu.ifpe.gcet.healthcare.entities.Patient;
import br.edu.ifpe.gcet.healthcare.repositories.HealthInsuranceCardRepository;
import br.edu.ifpe.gcet.healthcare.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PatientService {
    @Autowired
    private PatientRepository patientRepo;
    
    @Autowired
    private HealthInsuranceCardRepository cardRepo;
    
    public ResponseEntity<?> savePatient(NewPatientDTO patientDTO) {
        Patient p = patientDTO.getPatient();
        HealthInsuranceCard c = patientDTO.getHealthInsuranceCard();
        
        if(p.getEmail() == null || p.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("Credenciais inválidas!");
        }

        if(c.getCode() == null || c.getCode().isBlank()) {
            return ResponseEntity.badRequest().body("Credenciais da carteira do plano inválidas!");
        }

        Optional<Patient> patient = patientRepo.findById(p.getCpf());
        Optional<HealthInsuranceCard> card = cardRepo.findById(c.getCode());
        
        if(patient.isPresent() || card.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        patientRepo.save(p);
        cardRepo.save(c);
        
        return ResponseEntity.ok().body("Paciente cadastrado com sucesso!");
    } 
}
