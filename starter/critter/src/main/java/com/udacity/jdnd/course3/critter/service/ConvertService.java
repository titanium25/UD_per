package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.DTO.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConvertService {
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    PetService petService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    CustomerService customerService;

    public Schedule dtoToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Long> petIds = scheduleDTO.getPetIds();
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();

        List<Pet> petList = new ArrayList<>();
        if(petIds.size() != 0){
            for(Long id : petIds){
                Pet pet = petService.getPet(id);
                petList.add(pet);
            }
        }

        List<Employee> employeeList = new ArrayList<>();
        if(petIds.size() != 0){
            for(Long id : employeeIds){
                Employee employee = employeeService.getEmployee(id);
                employeeList.add(employee);
            }
        }

        schedule.setEmployees(employeeList);
        schedule.setPets(petList);

        return schedule;
    }

    public ScheduleDTO scheduleToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        List<Employee> listOfEmployees = schedule.getEmployees();
        List<Pet> listOfPets = schedule.getPets();
        List<Long> eIds = listOfEmployees.stream().map(employee -> employee.getId()).collect(Collectors.toList());
        List<Long> pIds = listOfPets.stream().map(pet -> pet.getId()).collect(Collectors.toList());
        scheduleDTO.setEmployeeIds(eIds);
        scheduleDTO.setPetIds(pIds);
        return scheduleDTO;
    }

    public PetDTO petToDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if(pet.getOwner() != null){
            petDTO.setOwnerId(pet.getOwner().getId());
        }
        return petDTO;
    }

    public Pet dtoToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

    public CustomerDTO customerToDTO(Customer customer){
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

    public Customer dtoToCustomer(CustomerDTO customerDTO){
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

    public EmployeeDTO employeeToDTO(Employee employee){
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, dto);
        return dto;
    }

    public Employee dtoToEmployee(EmployeeDTO dto){
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto, employee);
        return employee;
    }
}
