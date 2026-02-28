package br.edu.ifpe.gcet.healthcare.services;

import br.edu.ifpe.gcet.healthcare.entities.HealthInsuranceCard;
import br.edu.ifpe.gcet.healthcare.repositories.HealthInsuranceCardRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HealthInsuranceCardServiceTest {
    @Mock
    private HealthInsuranceCardRepository cardRepo;
    @InjectMocks
    private HealthInsuranceCardService service;
    
    private HealthInsuranceCard card;
    private static DateTimeFormatter formatter;

    @BeforeAll
    public static void setupFormatter() {
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }
    
    @BeforeEach
    public void setupCard() throws ParseException {
        card = new HealthInsuranceCard();
        
        card.setName("SUS");
        card.setCode("193746824");
        card.setExpirationDate(LocalDate.parse("02/05/2026", formatter));
    }
    
    @Test
    @DisplayName("Cadastrar carteira do plano com sucesso")
    public void cadastrarCarteiraDoPlanoComSucesso() {
        // Arrange
        when(cardRepo.findById(card.getCode())).thenReturn(Optional.empty());

        // Act
        Optional<HealthInsuranceCard> c = service.saveCard(card);

        // Assert
        verify(cardRepo, times(1)).findById(card.getCode());
        verify(cardRepo, times(1)).save(any());
    }
}