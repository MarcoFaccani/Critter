package com.udacity.jdnd.course3.critter.pet.controller;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.model.PetDTO;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static org.springframework.beans.BeanUtils.copyProperties;


import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
@Slf4j
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        petDTO.setId(petService.savePet(petDTO).getId());
        return petDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertToPetDTO(petService.getPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> output = new ArrayList<>();
        petService.getAllPets().forEach(pet -> output.add(convertToPetDTO(pet)));
        return output;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> output = new ArrayList<>();
        petService.getPetsByOwnerId(ownerId).forEach(pet -> output.add(convertToPetDTO(pet)));
        return output;
    }

    private PetDTO convertToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        copyProperties(pet, petDTO);
        return petDTO;
    }
}
