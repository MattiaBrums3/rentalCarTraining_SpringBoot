package com.rentalcar.springboot.rentalcarspringboot.controller;

import com.rentalcar.springboot.rentalcarspringboot.model.Category;
import com.rentalcar.springboot.rentalcarspringboot.model.Vehicle;
import com.rentalcar.springboot.rentalcarspringboot.service.CategoryService;
import com.rentalcar.springboot.rentalcarspringboot.service.VehicleService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin(origins = "http://localhost:4200")
public class VehicleController {
    private VehicleService vehicleService;
    private CategoryService categoryService;

    public VehicleController(VehicleService vehicleService, CategoryService categoryService) {
        this.vehicleService = vehicleService;
        this.categoryService = categoryService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Vehicle>> getVehicles() {
        try {
            List<Vehicle> vehicles = vehicleService.findAll();

            if (vehicles.isEmpty()) {
                return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(vehicles, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable("id") int id) {
        return new ResponseEntity<>(vehicleService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/get-by-category/{id}")
    public ResponseEntity<List<Vehicle>> getVehiclesByCategory(@PathVariable("id") int idCategory) {
        try {
            Category category = categoryService.findById(idCategory);
            List<Vehicle> vehicles = vehicleService.findByCategory(category);

            if (vehicles.isEmpty()) {
                return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
            }

            return new ResponseEntity<>(vehicles, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/post/edit")
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody Vehicle vehicle) {
        try {
            vehicleService.updateVehicle(vehicle);
            return new ResponseEntity<>(vehicle, new HttpHeaders(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Vehicle> deleteVehicle(@PathVariable("id") int id) {
        try {
            vehicleService.deleteVehicle(id);
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
