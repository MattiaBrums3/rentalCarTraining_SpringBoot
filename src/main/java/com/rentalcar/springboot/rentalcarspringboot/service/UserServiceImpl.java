package com.rentalcar.springboot.rentalcarspringboot.service;

import com.rentalcar.springboot.rentalcarspringboot.model.User;
import com.rentalcar.springboot.rentalcarspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public void updateUser(User user) {
        User _user = new User();

        if (user.getId() != 0) {
            _user.setId(user.getId());
        }

        _user.setName(user.getName());
        _user.setSurname(user.getSurname());
        _user.setDateOfBirth(user.getDateOfBirth());
        _user.setFiscalCode(user.getFiscalCode());
        _user.setSuperUser(user.getSuperUser());
        _user.setUsername(user.getUsername());
        _user.setPassword(user.getPassword());
        userRepository.save(_user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
