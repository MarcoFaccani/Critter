package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Schedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDate date;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Pet> pets = new ArrayList<>();

    @ElementCollection
    private Set<EmployeeSkill> activities;
}
