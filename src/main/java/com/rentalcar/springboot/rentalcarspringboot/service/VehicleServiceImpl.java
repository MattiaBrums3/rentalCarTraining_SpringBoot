package com.rentalcar.springboot.rentalcarspringboot.service;

import com.rentalcar.springboot.rentalcarspringboot.model.Vehicle;
import com.rentalcar.springboot.rentalcarspringboot.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle findById(int id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        return vehicle.orElse(null);
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        Vehicle _vehicle = new Vehicle();

        if (vehicle.getId() != 0) {
            _vehicle.setId(vehicle.getId());
        }

        _vehicle.setModel(vehicle.getModel());
        _vehicle.setManufacturer(vehicle.getManufacturer());
        _vehicle.setLicensePlate(vehicle.getLicensePlate());
        _vehicle.setYearOfRegistration(vehicle.getYearOfRegistration());
        vehicleRepository.save(_vehicle);
    }

    @Override
    public void deleteVehicle(int id) {
        vehicleRepository.deleteById(id);
    }
}
