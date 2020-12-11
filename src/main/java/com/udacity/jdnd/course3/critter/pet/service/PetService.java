package com.udacity.jdnd.course3.critter.pet.service;

import static org.springframework.beans.BeanUtils.copyProperties;

import com.udacity.jdnd.course3.critter.pet.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.model.PetDTO;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepistory;
import com.udacity.jdnd.course3.critter.user.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepistory petRepistory;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Pet savePet(PetDTO petDTO) {
        // REVIEW REQUEST: I don't like to do two savings. Isn't there a better way?
        Pet pet = new Pet();
        Customer owner = customerRepository.findById(petDTO.getOwnerId()).orElseThrow(CustomerNotFoundException::new);
        pet.setOwner(owner);
        copyProperties(petDTO, pet);
        pet = petRepistory.save(pet);
        owner.getPetIds().add(pet.getId());
        customerRepository.save(owner); //I do this after saving Pet because I need Pet to have an id
        return pet;
    }

    public Pet getPetById(final long petId) {
        return petRepistory.findById(petId).orElseThrow(PetNotFoundException::new);
    }

    public Iterable<Pet> getAllPets() {
        return petRepistory.findAll();
    }

    public List<Pet> getPetsByOwnerId(long ownerId) {
        return petRepistory.findAllByOwnerId(ownerId);
    }
}
