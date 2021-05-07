package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(long employeeId) {
        Employee employee;
        Optional<Employee> byId = employeeRepository.findById(employeeId);
        if(byId.isPresent()){
            employee = byId.get();
            return employee;
        } else {
            throw new NoSuchElementException("Employee with id:" + employeeId + " can not be found");
        }
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee;
        Optional<Employee> byId = employeeRepository.findById(employeeId);
        if(byId.isPresent()){
            employee = byId.get();
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
        } else {
            throw new NoSuchElementException("Employee with id:" + employeeId + " can not be found");
        }
    }

    public List<Employee> getEmployeesForService(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek) {
        List<Employee> employees = new ArrayList<>();
        List<Employee> employeesAvailable = employeeRepository.findAllByDaysAvailable(dayOfWeek);
        for (Employee e : employeesAvailable){
            if(e.getSkills().containsAll(skills)){
                employees.add(e);
            }
        }
        return employees;
    }
}
