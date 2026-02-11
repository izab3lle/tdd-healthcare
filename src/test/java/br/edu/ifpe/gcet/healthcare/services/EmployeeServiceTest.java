package br.edu.ifpe.gcet.healthcare.services;

import br.edu.ifpe.gcet.healthcare.entities.Employee;
import br.edu.ifpe.gcet.healthcare.repositories.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository repository;
    
    @Autowired
    @InjectMocks
    private EmployeeService service;
    
    private Employee e;
    
    @BeforeEach
    public void initMockEmployee() {
        e = new Employee();
        e.setName("Funcionário");
        e.setEmail("funcionario@gmail.com");
        e.setPassword("funcionario1");
        e.setDepartment("Administração");
    }

    @Test
    @DisplayName("Cadastrar funcionário com sucesso")
    public void cadastrarFuncionarioComSucesso() {
        // Arrange
        when(repository.findEmployeeByEmail(e.getEmail())).thenReturn(Optional.empty());
        when(repository.save(e)).thenReturn(e);

        // Act
        ResponseEntity<?> response = service.saveEmployee(e);
        
        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(repository, times(1)).findEmployeeByEmail(e.getEmail());
        verify(repository, times(1)).save(e);
    }

    @Test
    @DisplayName("Cadastrar funcionário com nome vazio")
    public void castrarFuncionarioComNomeVazio() {
        // Arrange
        e.setName("");

        // Act
        ResponseEntity<?> response = service.saveEmployee(e);

        // Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(repository, never()).save(e);
    }
}
