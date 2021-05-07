package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    public Long save(Schedule schedule){ return scheduleRepository.save(schedule).getId();}

    public Schedule getScheduleById(Long id){ return scheduleRepository.findScheduleById(id);}

    public List<Schedule> getSchedules() { return scheduleRepository.findAll();}

    public List<Schedule> getScheduleByPet(Pet pet) { return scheduleRepository.findScheduleByPets(pet);
    }

    public List<Schedule> getScheduleByEmployee(Employee employee) { return scheduleRepository.findScheduleByEmployees(employee);}

    public List<Schedule> getSchedulesByPet(List<Pet> pets) { return scheduleRepository.findByPetsIn(pets);}


}