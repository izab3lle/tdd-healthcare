package br.edu.ifpe.gcet.healthcare.services;

import br.edu.ifpe.gcet.healthcare.entities.HealthInsuranceCard;
import br.edu.ifpe.gcet.healthcare.repositories.HealthInsuranceCardRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HealthInsuranceCardService {
    @Autowired
    private HealthInsuranceCardRepository cardRepo;

    public HealthInsuranceCardService(HealthInsuranceCardRepository cardRepository) {
        this.cardRepo = cardRepository;
    }

    public Optional<HealthInsuranceCard> saveCard(HealthInsuranceCard c) {
        if(c.getCode() == null || c.getCode().isBlank()) {
            throw new IllegalArgumentException("Credenciais da carteira do plano inválidas!");
        }

        Optional<HealthInsuranceCard> card = cardRepo.findById(c.getCode());

        if(card.isPresent()) {
            return Optional.empty();
        }

        return Optional.ofNullable(cardRepo.save(c));
    }
}
