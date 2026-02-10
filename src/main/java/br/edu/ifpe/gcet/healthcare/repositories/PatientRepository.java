package br.edu.ifpe.gcet.healthcare.repositories;

import br.edu.ifpe.gcet.healthcare.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, String> {
}
