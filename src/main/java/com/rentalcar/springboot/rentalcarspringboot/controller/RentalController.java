package com.rentalcar.springboot.rentalcarspringboot.controller;

import com.rentalcar.springboot.rentalcarspringboot.model.Rental;
import com.rentalcar.springboot.rentalcarspringboot.model.User;
import com.rentalcar.springboot.rentalcarspringboot.model.Vehicle;
import com.rentalcar.springboot.rentalcarspringboot.service.RentalService;
import com.rentalcar.springboot.rentalcarspringboot.service.UserService;
import com.rentalcar.springboot.rentalcarspringboot.service.VehicleService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
@CrossOrigin(origins = "http://localhost:4200")
public class RentalController {
    private RentalService rentalService;
    private UserService userService;
    private VehicleService vehicleService;

    public RentalController(RentalService rentalService, UserService userService, VehicleService vehicleService) {
        this.rentalService = rentalService;
        this.userService = userService;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Rental>> getRentals() {
        try {
            List<Rental> rentals = rentalService.findAll();

            if (rentals.isEmpty()) {
                return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(rentals, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable("id") int id) {
        return new ResponseEntity<>(rentalService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/get-by-user/{id}")
    public ResponseEntity<List<Rental>> getRentalsByUser(@PathVariable("id") int id) {
        try {
            User user = userService.findById(id);
            List<Rental> rentals = rentalService.findByUser(user);

            if (rentals.isEmpty()) {
                return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(rentals, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-by-vehicle/{id}")
    public ResponseEntity<List<Rental>> getRentalsByVehicle(@PathVariable("id") int id) {
        try {
            Vehicle vehicle = vehicleService.findById(id);
            List<Rental> rentals = rentalService.findByVehicle(vehicle);

            if (rentals.isEmpty()) {
                return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(rentals, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/post/edit")
    public ResponseEntity<Rental> updateRental(@RequestBody Rental rental) {
        try {
            rentalService.updateRental(rental);
            return new ResponseEntity<>(rental, new HttpHeaders(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Rental> deleteRental(@PathVariable("id") int id) {
        try {
            rentalService.deleteRental(id);
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
