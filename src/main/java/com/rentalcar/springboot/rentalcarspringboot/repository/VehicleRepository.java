package com.rentalcar.springboot.rentalcarspringboot.repository;

import com.rentalcar.springboot.rentalcarspringboot.model.Category;
import com.rentalcar.springboot.rentalcarspringboot.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findByCategory(Category category);
}
