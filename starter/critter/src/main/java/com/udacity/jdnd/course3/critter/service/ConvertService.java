package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.DTO.ScheduleDTO;
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

    public Schedule DTOtoSchedule(ScheduleDTO scheduleDTO){
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

    public ScheduleDTO ScheduleToDTO(Schedule schedule) {
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
}
