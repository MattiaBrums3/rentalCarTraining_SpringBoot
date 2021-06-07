package com.rentalcar.springboot.rentalcarspringboot.service;

import com.rentalcar.springboot.rentalcarspringboot.model.Category;
import com.rentalcar.springboot.rentalcarspringboot.model.Vehicle;

import java.util.List;

public interface VehicleService {
    List<Vehicle> findAll();

    Vehicle findById(int id);

    List<Vehicle> findByCategory(Category category);

    void updateVehicle(Vehicle vehicle);

    void deleteVehicle(int id);
}
