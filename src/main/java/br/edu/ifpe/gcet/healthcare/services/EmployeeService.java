package br.edu.ifpe.gcet.healthcare.services;

import br.edu.ifpe.gcet.healthcare.entities.Employee;
import br.edu.ifpe.gcet.healthcare.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepo;

    public ResponseEntity<?> saveEmployee(Employee e) {
        if(e.getEmail() == null || e.getEmail().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<Employee> employee = employeeRepo.findById(e.getId());
        
        if(employee.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        employeeRepo.save(e);
        
        return ResponseEntity.ok().body("Funcionário cadastrado com sucesso!");
    }
}
