package com.rentalcar.springboot.rentalcarspringboot.service;

import com.rentalcar.springboot.rentalcarspringboot.model.Rental;
import com.rentalcar.springboot.rentalcarspringboot.model.User;
import com.rentalcar.springboot.rentalcarspringboot.model.Vehicle;

import java.util.List;

public interface RentalService {
    List<Rental> findAll();

    Rental findById(int id);

    List<Rental> findByUser(User user);

    List<Rental> findByVehicle(Vehicle vehicle);

    void updateRental(Rental rental);

    void deleteRental(int id);
}
