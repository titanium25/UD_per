package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.DTO.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ConvertService convertService;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    PetService petService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    CustomerService customerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        scheduleService.save(convertService.DTOtoSchedule(scheduleDTO));
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getSchedules();
        return schedules.stream().map(schedule -> convertService.ScheduleToDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.getPet(petId);
        List<Schedule> schedules = scheduleService.getScheduleByPet(pet);
        return schedules.stream().map(schedule -> convertService.ScheduleToDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        List<Schedule> schedules = scheduleService.getScheduleByEmployee(employee);
        return schedules.stream().map(schedule -> convertService.ScheduleToDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.getCustomer(customerId);
        List<Pet> pets = petService.getPetsByCustomer(customer);
        List<Schedule> schedules = scheduleService.getSchedulesByPet(pets);
        return schedules.stream().map(schedule -> convertService.ScheduleToDTO(schedule)).collect(Collectors.toList());
    }
}