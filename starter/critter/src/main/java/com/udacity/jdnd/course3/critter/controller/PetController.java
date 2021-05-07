package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.ConvertService;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    ConvertService convertService;

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertService.dtoToPet(petDTO);
        Customer owner = null;

        if(petDTO.getOwnerId() > 0) {
            owner = customerService.getCustomer(petDTO.getOwnerId());
            pet.setOwner(owner);
        }
        pet = petService.savePet(pet);

        if(owner != null){
            owner.addPet(pet);
        }

        return convertService.petToDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertService.petToDTO(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return pets.stream().map(petDTO -> convertService.petToDTO(petDTO)).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> listOfPetsDTO = new ArrayList<>();
        List<Pet> listOfPets = petService.getPetsByOwnerId(ownerId);
        for(Pet p : listOfPets){
            PetDTO dto = convertService.petToDTO(p);
            listOfPetsDTO.add(dto);
        }
        return listOfPetsDTO;
    }


}
