package com.udacity.jdnd.course3.critter.pet.service;

import static org.springframework.beans.BeanUtils.copyProperties;

import com.udacity.jdnd.course3.critter.pet.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.model.PetDTO;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;


    public Pet savePet(Pet pet) {
        pet = petRepository.save(pet);
        pet.getOwner().addPet(pet);
        customerRepository.save(pet.getOwner());
        return pet;
    }

    public Pet getPetById(final long petId) {
        return petRepository.findById(petId).orElseThrow(PetNotFoundException::new);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwnerId(long ownerId) {
        return petRepository.findAllByOwnerId(ownerId);
    }

    private PetDTO convertToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getOwner().getId()); //to verify
        return petDTO;
    }

}
