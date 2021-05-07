package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = dtc(customerDTO);
        customerService.saveCustomer(customer);
        return ctd(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        return customers.stream().map(customerDTO -> ctd(customerDTO)).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = dte(employeeDTO);
        employeeService.saveEmployee(employee);
        return etd(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return etd(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO requestDTO) {
        Set<EmployeeSkill> skills = requestDTO.getSkills();
        DayOfWeek dayOfWeek = requestDTO.getDate().getDayOfWeek();
        List<Employee> employeeList = employeeService.getEmployeesForService(skills, dayOfWeek);

        List<EmployeeDTO> listOfDTO = new ArrayList<>();
        if(employeeList.size() != 0) {
            for(Employee e : employeeList){
                EmployeeDTO dto = etd(e);
                listOfDTO.add(dto);
            }
        }
        return listOfDTO;
    }


    public CustomerDTO ctd(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        List<Pet> pets = customer.getPets();

        List<Long> petIds = new ArrayList<>();
        if(pets.size() != 0){
            for(Pet p : pets){
                Long id = p.getId();
                petIds.add(id);
            }
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    public Customer dtc(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        List<Long> petIds = customerDTO.getPetIds();

        List<Pet> pets = new ArrayList<>();
        if(petIds != null && petIds.size() != 0){
            for(Long id : petIds){
                pets.add(petService.getPet(id));
            }
        }
        customer.setPets(pets);
        return customer;
    }

    public EmployeeDTO etd(Employee employee){
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, dto);
        return dto;
    }

    public Employee dte(EmployeeDTO dto){
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto, employee);
        return employee;
    }
}
