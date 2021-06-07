package com.rentalcar.springboot.rentalcarspringboot.service;

import com.rentalcar.springboot.rentalcarspringboot.model.Category;
import com.rentalcar.springboot.rentalcarspringboot.model.Vehicle;
import com.rentalcar.springboot.rentalcarspringboot.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    private VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle findById(int id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        return vehicle.orElse(null);
    }

    public List<Vehicle> findByCategory(Category category) {
        return vehicleRepository.findByCategory(category);
    }

    public void updateVehicle(Vehicle vehicle) {
        Vehicle _vehicle = new Vehicle();

        if (vehicle.getId() != 0) {
            _vehicle.setId(vehicle.getId());
        }

        _vehicle.setModel(vehicle.getModel());
        _vehicle.setManufacturer(vehicle.getManufacturer());
        _vehicle.setLicensePlate(vehicle.getLicensePlate());
        _vehicle.setYearOfRegistration(vehicle.getYearOfRegistration());
        _vehicle.setCategory((vehicle.getCategory()));
        vehicleRepository.save(_vehicle);
    }

    public void deleteVehicle(int id) {
        vehicleRepository.deleteById(id);
    }
}
