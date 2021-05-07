package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
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
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = dtp(petDTO);
        Customer owner = null;

        if(petDTO.getOwnerId() > 0) {
            owner = customerService.getCustomer(petDTO.getOwnerId());
            pet.setOwner(owner);
        }
        pet = petService.savePet(pet);

        if(owner != null){
            owner.addPet(pet);
        }

        return ptd(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return ptd(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return pets.stream().map(petDTO -> ptd(petDTO)).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> listOfPetsDTO = new ArrayList<>();
        List<Pet> listOfPets = petService.getPetsByOwnerId(ownerId);
        for(Pet p : listOfPets){
            PetDTO dto = ptd(p);
            listOfPetsDTO.add(dto);
        }
        return listOfPetsDTO;
    }

    private PetDTO ptd(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if(pet.getOwner() != null){
            petDTO.setOwnerId(pet.getOwner().getId());
        }
        return petDTO;
    }

    private Pet dtp(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

}
