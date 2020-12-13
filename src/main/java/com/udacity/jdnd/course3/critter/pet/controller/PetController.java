package com.udacity.jdnd.course3.critter.pet.controller;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.model.PetDTO;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static org.springframework.beans.BeanUtils.copyProperties;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
@Slf4j
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertToPet(petDTO);
        if (petDTO.getOwnerId() != null) pet.setOwner(userService.getCustomerById(petDTO.getOwnerId()));
        pet = petService.savePet(pet);
        return convertToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertToPetDTO(petService.getPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.getAllPets().stream()
                .map(this::convertToPetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.getPetsByOwnerId(ownerId).stream()
                .map(this::convertToPetDTO)
                .collect(Collectors.toList());
    }

    private Pet convertToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        copyProperties(petDTO, pet);
        return pet;
    }

    private PetDTO convertToPetDTO(Pet pet) {
        if (pet == null) return null;
        else {
            PetDTO petDTO = new PetDTO();
            copyProperties(pet, petDTO);
            petDTO.setOwnerId(pet.getOwner().getId()); //to verify
            return petDTO;
        }
    }

}
