package com.udacity.jdnd.course3.critter.user.model;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String phoneNumber;
    private String notes;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pet> pets = new ArrayList<>();

   public void addPet(Pet pet) {
       this.pets.add(pet);
   }
}
