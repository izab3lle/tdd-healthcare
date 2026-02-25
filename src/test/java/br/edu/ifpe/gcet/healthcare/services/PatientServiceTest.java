package br.edu.ifpe.gcet.healthcare.services;

import br.edu.ifpe.gcet.healthcare.dto.NewPatientDTO;
import br.edu.ifpe.gcet.healthcare.entities.HealthInsuranceCard;
import br.edu.ifpe.gcet.healthcare.entities.Patient;
import br.edu.ifpe.gcet.healthcare.repositories.HealthInsuranceCardRepository;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @Mock
    private PatientRepository patientRepo;

    @Mock
    private HealthInsuranceCardRepository cardRepo;
    
    @InjectMocks
    private PatientService service;
    
    private NewPatientDTO patientDTO;
    
    @BeforeEach
    public void initMockPatient() throws ParseException {
        patientDTO = new NewPatientDTO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        patientDTO.setCpf("355.129.694-47");
        patientDTO.setEmail("paciente@gmail.com");
        patientDTO.setName("Paciente");
        patientDTO.setBirthDate(LocalDate.parse("22/12/1931", formatter));

        patientDTO.setCardCode("193746824");
        patientDTO.setCardName("SUS");
        patientDTO.setCardExpirationDate(LocalDate.parse("02/05/2026", formatter));
    }
    
    @Test
    @DisplayName("Cadastrar paciente com sucesso")
    public void cadastrarPacienteComSucesso() {
        // Arrange
        when(patientRepo.findById(patientDTO.getCpf())).thenReturn(Optional.empty());
        
        // Act
        Optional<Patient> patient = service.savePatient(patientDTO);
        
        // Assert
        verify(patientRepo, times(1)).findById(patientDTO.getCpf());
        verify(patientRepo, times(1)).save(any());
    }

    @Test
    @DisplayName("Cadastrar carteira do plano com sucesso")
    public void cadastrarCarteiraDoPlanoComSucesso() {
        // Arrange
        when(cardRepo.findById(patientDTO.getCardCode())).thenReturn(Optional.empty());

        // Act
        Optional<Patient> patient = service.savePatient(patientDTO);

        // Assert
        verify(cardRepo, times(1)).findById(patientDTO.getCardCode());
        verify(cardRepo, times(1)).save(any());
    }

    @Test
    @DisplayName("Cadastrar paciente com nome vazio")
    public void cadastrarPacienteComNomeVazio() {
        // Arrange
        patientDTO.setName("");

        // Act + Assert
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> service.savePatient(patientDTO));

        verify(patientRepo, never()).findById(patientDTO.getCpf());
        verify(patientRepo, never()).save(any());
        verify(cardRepo, never()).findById(patientDTO.getCardCode());
        verify(cardRepo, never()).save(any());
    }
    
    @Test
    @DisplayName("Cadastrar paciente com data de nascimento vazia")
    public void cadastrarPacienteComDataDeNascimentoVazia() {
        // Arrange
        patientDTO.setBirthDate(LocalDate.now());

        // Act + Assert
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> service.savePatient(patientDTO));
            
        verify(patientRepo, never()).findById(patientDTO.getCpf());
        verify(patientRepo, never()).save(any());
        verify(cardRepo, never()).findById(patientDTO.getCardCode());
        verify(cardRepo, never()).save(any());
    }
}