package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDate date;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Employee> employees;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Pet> pets;

    @ElementCollection
    private Set<EmployeeSkill> activities;

    @ElementCollection
    private List<Long> employeeIds;

    @ElementCollection
    private List<Long> petIds;
}
