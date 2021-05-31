package com.rentalcar.springboot.rentalcarspringboot.service;

import com.rentalcar.springboot.rentalcarspringboot.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int id);

    void updateUser(User user);

    void deleteUser(int id);
}
