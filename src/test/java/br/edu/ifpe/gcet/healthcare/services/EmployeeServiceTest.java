package br.edu.ifpe.gcet.healthcare.services;

import br.edu.ifpe.gcet.healthcare.entities.Employee;
import br.edu.ifpe.gcet.healthcare.repositories.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository repository;
    
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
        when(repository.findById(e.getId())).thenReturn(Optional.empty());
        when(repository.save(e)).thenReturn(e);

        // Act
        String responseBody = (String) service.saveEmployee(e).getBody();

        // Assert
        Assertions.assertEquals("Funcionário cadastrado com sucesso!", responseBody);
        verify(repository, times(1)).findById(e.getId());
        verify(repository, times(1)).save(e);
    }
}
