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
    
    @InjectMocks
    private PatientService service;
    
    private Patient patient;
    
    @BeforeEach
    public void initMockPatient() throws ParseException {
        patient = new Patient();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        patient.setCpf("355.129.694-47");
        patient.setEmail("paciente@gmail.com");
        patient.setName("Paciente");
        patient.setBirthDate(LocalDate.parse("22/12/1931", formatter));
    }
    
    @Test
    @DisplayName("Cadastrar paciente com sucesso")
    public void cadastrarPacienteComSucesso() {
        // Arrange
        when(patientRepo.findById(patient.getCpf())).thenReturn(Optional.empty());
        
        // Act
        Optional<Patient> p = service.savePatient(patient);
        
        // Assert
        verify(patientRepo, times(1)).findById(patient.getCpf());
        verify(patientRepo, times(1)).save(any());
    }

    @Test
    @DisplayName("Cadastrar paciente com nome vazio")
    public void cadastrarPacienteComNomeVazio() {
        // Arrange
        patient.setName("");

        // Act + Assert
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> service.savePatient(patient));

        verify(patientRepo, never()).findById(patient.getCpf());
        verify(patientRepo, never()).save(any());
    }
    
    @Test
    @DisplayName("Cadastrar paciente com data de nascimento vazia")
    public void cadastrarPacienteComDataDeNascimentoVazia() {
        // Arrange
        patient.setBirthDate(LocalDate.now());

        // Act + Assert
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> service.savePatient(patient));
            
        verify(patientRepo, never()).findById(patient.getCpf());
        verify(patientRepo, never()).save(any());
    }
}