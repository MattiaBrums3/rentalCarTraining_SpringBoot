package com.rentalcar.springboot.rentalcarspringboot.repository;

import com.rentalcar.springboot.rentalcarspringboot.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByTypology(String typology);
}
