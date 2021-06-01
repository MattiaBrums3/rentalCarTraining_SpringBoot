package com.rentalcar.springboot.rentalcarspringboot.repository;

import com.rentalcar.springboot.rentalcarspringboot.model.Rental;
import com.rentalcar.springboot.rentalcarspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    List<Rental> findByUser(User user);
}
