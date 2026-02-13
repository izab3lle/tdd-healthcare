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
import java.util.Arrays;
import java.util.Optional;

@Component
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private PasswordEncoder encoder;

    public ResponseEntity<?> saveEmployee(Employee e) {
        if(e.getPassword().length() < 6 || isAnyFieldEmptyOrBlank(e)) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<Employee> employee = employeeRepo.findEmployeeByEmail(e.getEmail());
        
        if(employee.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        e.setPassword(this.encoder.encode(e.getPassword()));
        employeeRepo.save(e);
        
        return ResponseEntity.ok().body("Funcionário cadastrado com sucesso!");
    }
    
    private boolean isAnyFieldEmptyOrBlank(Employee e) {
        Method[] fields = Arrays.stream(e.getClass().getMethods())
                .filter(m -> m.getName().contains("get") && !m.getName().contains("Id"))
                .toArray(Method[]::new);
        
        for(Method m : fields) {
            try {
                Object field = m.invoke(e);
                
                if(field == null) return true;
                
                if(m.getReturnType() == String.class) {
                    if(field.toString().isBlank()) return true;
                }
                
            } catch(InvocationTargetException | IllegalAccessException ex) {
                throw new RuntimeException("Erro ao acessar um dos campos do objeto");
            }
        }
        
        return false;
    }
}
