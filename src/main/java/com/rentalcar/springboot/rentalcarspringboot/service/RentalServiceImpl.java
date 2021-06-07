package com.rentalcar.springboot.rentalcarspringboot.service;

import com.rentalcar.springboot.rentalcarspringboot.model.Rental;
import com.rentalcar.springboot.rentalcarspringboot.model.User;
import com.rentalcar.springboot.rentalcarspringboot.model.Vehicle;
import com.rentalcar.springboot.rentalcarspringboot.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalService {

    private RentalRepository rentalRepository;

    private UserService userService;

    public RentalServiceImpl(RentalRepository rentalRepository, UserService userService) {
        this.rentalRepository = rentalRepository;
        this.userService = userService;
    }

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public Rental findById(int id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        return rental.orElse(null);
    }

    public List<Rental> findByUser(User user) {
        return rentalRepository.findByUser(user);
    }

    public List<Rental> findByVehicle(Vehicle vehicle) {
        return rentalRepository.findByVehicle(vehicle);
    }

    public void updateRental(Rental rental) {
        Rental _rental = new Rental();

        if (rental.getId() != 0) {
            _rental.setId(rental.getId());
        }

        _rental.setUser(rental.getUser());
        _rental.setVehicle(rental.getVehicle());
        _rental.setDateOfStart(rental.getDateOfStart());
        _rental.setDateOfEnd(rental.getDateOfEnd());
        _rental.setApproved(rental.getApproved());
        rentalRepository.save(_rental);
    }

    public void deleteRental(int id) {
        rentalRepository.deleteById(id);
    }
}
