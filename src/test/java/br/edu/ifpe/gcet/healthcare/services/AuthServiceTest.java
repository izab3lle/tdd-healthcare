package br.edu.ifpe.gcet.healthcare.services;

import br.edu.ifpe.gcet.healthcare.dto.EmployeeLoginDTO;
import br.edu.ifpe.gcet.healthcare.entities.Employee;
import br.edu.ifpe.gcet.healthcare.repositories.EmployeeRepository;
import br.edu.ifpe.gcet.healthcare.security.JwtUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private static EmployeeRepository employeeRepo;
    @Mock
    private PasswordEncoder encoder;
    
    @Autowired
    @InjectMocks
    private AuthService service;
    
    private static Employee e;
    private EmployeeLoginDTO loginDTO;
    
    @BeforeAll
    public static void setupUserLogin() {
        e = new Employee();
        
        e.setName("Funcionário");
        e.setEmail("funcionario@gmail.com");
        e.setPassword("funcionario1");
        e.setDepartment("Administração");
    }
    
    @BeforeEach
    public void setupLoginDTO() {
        loginDTO = new EmployeeLoginDTO();
        
        loginDTO.setLogin(e.getEmail());
        loginDTO.setPassword(e.getPassword());
    }
    
    @Test
    @DisplayName("Realizar login de funcionário com sucesso")
    public void realizarLoginDeFuncionarioComSucesso() {
        // Arrange
        when(employeeRepo.findEmployeeByEmail(loginDTO.getLogin())).thenReturn(Optional.of(e));
        when(encoder.matches(e.getPassword(), loginDTO.getPassword())).thenReturn(true);
        when(jwtUtils.generateToken(e.getEmail(), "USER")).thenReturn("TOKEN");
        
        // Act
        ResponseEntity<?> response = service.login(loginDTO);
        
        // Assert
        verify(employeeRepo, times(1)).findEmployeeByEmail(e.getEmail());
        
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertFalse(response.getBody().toString().isBlank());
    }

    @Test
    @DisplayName("Realizar login de funcionário com email vazio")
    public void realizarLoginDeFuncionarioComEmailVazio() {
        // Arrange
        loginDTO.setLogin("");
        
        // Act
        ResponseEntity<?> response = service.login(loginDTO);
        
        // Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(employeeRepo, never()).save(e);
    }

    @Test
    @DisplayName("Realizar login de funcionário com email inválido")
    public void realizarLoginDeFuncionarioComEmailInválido() {
        // Arrange
        loginDTO.setLogin("medico@gmail.com");
        
        // Act
        ResponseEntity<?> response = service.login(loginDTO);

        // Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(employeeRepo, never()).save(e);
    }

    @Test
    @DisplayName("Realizar login de funcionário com senha vazia")
    public void realizarLoginDeFuncionarioComSenhaVazia() {
        // Arrange
        loginDTO.setPassword("");

        // Act
        ResponseEntity<?> response = service.login(loginDTO);

        // Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(employeeRepo, never()).save(e);
    }
}