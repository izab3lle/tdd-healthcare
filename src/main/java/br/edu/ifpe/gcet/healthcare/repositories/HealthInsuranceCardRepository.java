package br.edu.ifpe.gcet.healthcare.repositories;

import br.edu.ifpe.gcet.healthcare.entities.HealthInsuranceCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthInsuranceCardRepository extends JpaRepository<HealthInsuranceCard, String> {
}
