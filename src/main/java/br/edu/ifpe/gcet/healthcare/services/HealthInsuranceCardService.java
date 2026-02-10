package br.edu.ifpe.gcet.healthcare.services;

import br.edu.ifpe.gcet.healthcare.repositories.HealthInsuranceCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HealthInsuranceCardService {
    @Autowired
    private HealthInsuranceCardRepository cardRepo;
}
