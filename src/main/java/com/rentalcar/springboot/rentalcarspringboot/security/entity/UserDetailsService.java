package com.rentalcar.springboot.rentalcarspringboot.security.entity;

import com.rentalcar.springboot.rentalcarspringboot.model.User;
import com.rentalcar.springboot.rentalcarspringboot.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            return new UserDetails(username, user.getPassword(), user.getSuperUser());
        }else{
            return null;
        }
    }
}
