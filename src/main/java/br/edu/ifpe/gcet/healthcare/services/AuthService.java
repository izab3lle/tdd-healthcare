package br.edu.ifpe.gcet.healthcare.services;

import br.edu.ifpe.gcet.healthcare.dto.EmployeeLoginDTO;
import br.edu.ifpe.gcet.healthcare.entities.Employee;
import br.edu.ifpe.gcet.healthcare.repositories.EmployeeRepository;
import br.edu.ifpe.gcet.healthcare.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthService {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private EmployeeRepository employeeRepo;
    @Autowired
    private PasswordEncoder encoder;

    public AuthService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, JwtUtils utils) {
        this.employeeRepo = employeeRepository;
        this.encoder = passwordEncoder;
        this.jwtUtils = utils;
    }
    
    public Optional<String> login(EmployeeLoginDTO loginDTO) {

        Optional<Employee> employee = employeeRepo.findEmployeeByEmail(loginDTO.getLogin());
        
        if(employee.isPresent()) {
            Employee loggedUser = employee.get();
            
            if(this.encoder.matches(loginDTO.getPassword(), loggedUser.getPassword())) {
                return Optional.of((this.jwtUtils
                        .generateToken(loggedUser.getEmail(), "USER")));
            }
        }
        
        throw new IllegalArgumentException("Credenciais inválidas!");
    }
}
