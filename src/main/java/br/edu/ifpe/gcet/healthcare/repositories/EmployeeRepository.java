package br.edu.ifpe.gcet.healthcare.repositories;

import br.edu.ifpe.gcet.healthcare.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
