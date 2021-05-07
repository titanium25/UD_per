package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("SELECT p FROM Pet p WHERE p.owner.id = :customerId")
    List<Pet> findAllPetsByCustomerId(@Param("customerId") long customerId);

    List<Pet> findPetsByCustomer(Customer customer);

    List<Pet> findAllById(List<Long> petId);
}
