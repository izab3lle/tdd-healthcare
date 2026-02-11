package br.edu.ifpe.gcet.healthcare.services;

import br.edu.ifpe.gcet.healthcare.entities.Patient;
import br.edu.ifpe.gcet.healthcare.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PatientService {
    @Autowired
    private PatientRepository patientRepo;
    
    public ResponseEntity<?> savePatient(Patient p) {
        if(p.getEmail() == null || p.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("Credenciais inválidas!");
        }

        Optional<Patient> patient = patientRepo.findById(p.getCpf());
        
        if(patient.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        patientRepo.save(p);
        
        return ResponseEntity.ok().body("Paciente cadastrado com sucesso!");
    } 
}
