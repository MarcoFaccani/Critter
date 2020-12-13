package com.udacity.jdnd.course3.critter.pet.model;

import com.udacity.jdnd.course3.critter.user.model.Customer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String name;
    private PetType type;
    private LocalDate birthDate;
    private String notes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_fk")
    private Customer owner;

}

