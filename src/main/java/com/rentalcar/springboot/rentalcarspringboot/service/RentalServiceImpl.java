package com.rentalcar.springboot.rentalcarspringboot.service;

import com.rentalcar.springboot.rentalcarspringboot.model.Rental;
import com.rentalcar.springboot.rentalcarspringboot.model.User;
import com.rentalcar.springboot.rentalcarspringboot.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RentalServiceImpl implements RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired UserService userService;

    @Override
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @Override
    public Rental findById(int id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        return rental.orElse(null);
    }

    @Override
    public List<Rental> findByUser(int id) {
        User user = userService.findById(id);
        return rentalRepository.findByUser(user);
    }

    @Override
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

    @Override
    public void deleteRental(int id) {
        rentalRepository.deleteById(id);
    }
}
