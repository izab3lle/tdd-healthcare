package br.edu.ifpe.gcet.healthcare.repositories;

import br.edu.ifpe.gcet.healthcare.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    public Optional<Employee> findEmployeeByEmail(String email);

}
