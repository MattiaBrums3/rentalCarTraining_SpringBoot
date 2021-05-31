package com.rentalcar.springboot.rentalcarspringboot.service;

import com.rentalcar.springboot.rentalcarspringboot.model.Vehicle;

import java.util.List;

public interface VehicleService {
    List<Vehicle> findAll();

    Vehicle findById(int id);

    void updateVehicle(Vehicle vehicle);

    void deleteVehicle(int id);
}
