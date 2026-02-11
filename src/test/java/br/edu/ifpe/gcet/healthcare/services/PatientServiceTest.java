package br.edu.ifpe.gcet.healthcare.services;

import br.edu.ifpe.gcet.healthcare.entities.HealthInsuranceCard;
import br.edu.ifpe.gcet.healthcare.entities.Patient;
import br.edu.ifpe.gcet.healthcare.repositories.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @Mock
    private PatientRepository repository;
    
    @Autowired
    @InjectMocks
    private PatientService service;
    
    private Patient p;
    private HealthInsuranceCard pCard;
    
    @BeforeEach
    public void initMockPatient() throws ParseException {
        p = new Patient();
        p.setCpf("355.129.694-47");
        p.setEmail("paciente@gmail.com");
        p.setName("Paciente");
        p.setBirthDate(new SimpleDateFormat("MM/dd/yyyy")
                .parse("22/12/1931").getTime());
        
        pCard = new HealthInsuranceCard();
        pCard.setPatient(p);
        pCard.setCode("193746824");
        pCard.setName("SUS");
        pCard.setExpirationDate(new SimpleDateFormat("MM/dd/yyyy")
                .parse("02/05/2026").getTime());
    }
    
    @Test
    @DisplayName("Cadastrar paciente com sucesso")
    public void cadastrarPacienteComSucesso() {
        // Arrange
        when(repository.findById(p.getCpf())).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<?> response = service.savePatient(p);
        
        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(repository, times(1)).findById(p.getCpf());
        verify(repository, times(1)).save(p);
    }
}