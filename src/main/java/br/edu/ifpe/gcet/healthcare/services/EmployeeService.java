package br.edu.ifpe.gcet.healthcare.services;

import br.edu.ifpe.gcet.healthcare.entities.Employee;
import br.edu.ifpe.gcet.healthcare.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Component
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private PasswordEncoder encoder;

    public ResponseEntity<?> saveEmployee(Employee e) {
        try {
            if(isAnyFieldEmptyOrBlank(e) || e.getPassword().length() < 6) {
                return ResponseEntity.badRequest().body("A senha não pode ter menos de 6 caracteres!");
            }
        } catch(IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        
        Optional<Employee> employee = employeeRepo.findEmployeeByEmail(e.getEmail());
        
        if(employee.isPresent()) {
            return ResponseEntity.badRequest().body("Já existe um funcionário cadastrado com esse email");
        }
        
        e.setPassword(this.encoder.encode(e.getPassword()));
        employeeRepo.save(e);
        
        return ResponseEntity.ok().body("Funcionário cadastrado com sucesso!");
    }
    
    private boolean isAnyFieldEmptyOrBlank(Employee e) {
        HashMap<String, String> fields = new HashMap<>();
        
        fields.put("email", e.getEmail());
        fields.put("senha", e.getPassword());
        fields.put("nome", e.getName());
        fields.put("departamento", e.getDepartment());
        
        fields.forEach((name, field) -> {
            if(field == null || field.isBlank()) {
                String msg = (name.equals("senha"))
                        ? "A senha não pode ser vazia"
                        : "O " + name + " não pode ser vazio";

                throw new IllegalArgumentException(msg);
            }
        });
        
        return false;
    }
}
