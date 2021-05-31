package com.rentalcar.springboot.rentalcarspringboot.repository;

import com.rentalcar.springboot.rentalcarspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
