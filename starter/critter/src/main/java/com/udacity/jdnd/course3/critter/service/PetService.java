package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet getPet(long petId) {
        Pet pet;
        Optional<Pet> byId = petRepository.findById(petId);
        if(byId.isPresent()){
            pet = byId.get();
            return pet;
        } else {
            throw new NoSuchElementException("Pet with id:" + petId + " can not be found");
        }
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwnerId(long ownerId) {
        List<Pet> pets;
        Optional<Customer> customerOptional = customerRepository.findById(ownerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            pets = customer.getPets();
        } else {
            pets = new ArrayList<>();
        }
        return pets;
    }

    public List<Pet> getPetsByCustomer(Customer customer){ return petRepository.findPetsByOwner(customer);}

}
