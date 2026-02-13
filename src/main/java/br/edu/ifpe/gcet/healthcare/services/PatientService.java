package br.edu.ifpe.gcet.healthcare.services;

import br.edu.ifpe.gcet.healthcare.dto.NewPatientDTO;
import br.edu.ifpe.gcet.healthcare.entities.HealthInsuranceCard;
import br.edu.ifpe.gcet.healthcare.entities.Patient;
import br.edu.ifpe.gcet.healthcare.repositories.HealthInsuranceCardRepository;
import br.edu.ifpe.gcet.healthcare.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Calendar;
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
        
        Calendar minBirthDate = Calendar.getInstance();
        minBirthDate.add(Calendar.YEAR, -18);
        long minBirthDateMillis = minBirthDate.getTimeInMillis();
        
        if(p.getCpf() == null || p.getEmail().isBlank() || p.getName().isBlank()) {
            return ResponseEntity.badRequest().body("Credenciais inválidas!");
        }
        
        if(patientDTO.getBirthDate() < minBirthDateMillis && patientDTO.getBirthDate() > -1) {  // -1 valida as datas após 1970
            return ResponseEntity.badRequest().body("O paciente deve ser maior de idade!");
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
