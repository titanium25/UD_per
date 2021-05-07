package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT E FROM Employee E WHERE :day MEMBER OF E.daysAvailable")
    List<Employee> findAllByDaysAvailable(@Param("day") DayOfWeek day);
}