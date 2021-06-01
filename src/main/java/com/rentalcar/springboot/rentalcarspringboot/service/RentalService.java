package com.rentalcar.springboot.rentalcarspringboot.service;

import com.rentalcar.springboot.rentalcarspringboot.model.Rental;

import java.util.List;

public interface RentalService {
    List<Rental> findAll();

    Rental findById(int id);

    List<Rental> findByUser(int id);

    void updateRental(Rental rental);

    void deleteRental(int id);
}
