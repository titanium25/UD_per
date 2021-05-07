package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {
    @Id
    @GeneratedValue
    private Long scheduleId;

    @ManyToMany
    private List<Employee> employees;
    @ManyToMany
    private List<Pet> pets;

    private LocalDate deliveryTime;
    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "ScheduleActivitiesTable",
            joinColumns = @JoinColumn(name = "scheduleId")
    )
    @Column(name = "activity")
    private Set<EmployeeSkill> activities= new HashSet<>();

    public Long getId(){ return scheduleId;}
    public void setId(Long id){ this.scheduleId = id;}

    public List<Employee> getEmployees(){ return employees;}
    public void setEmployees(List<Employee> employees){ this.employees = employees;}

    public List<Pet> getPets(){ return pets;}
    public void setPets(List<Pet> pets){ this.pets = pets;}

    public LocalDate getDeliveryTime(){ return deliveryTime;}
    public void setDeliveryTime(LocalDate time){ this.deliveryTime = time;}

    public Set<EmployeeSkill> getActivities(){ return activities;}
    public void setActivities(Set<EmployeeSkill> activities){ this.activities = activities;}
}