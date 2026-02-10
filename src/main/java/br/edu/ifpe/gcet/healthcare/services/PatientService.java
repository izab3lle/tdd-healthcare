package br.edu.ifpe.gcet.healthcare.services;

import br.edu.ifpe.gcet.healthcare.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientService {
    @Autowired
    private PatientRepository patientRepo;
}
