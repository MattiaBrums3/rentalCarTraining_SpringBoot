package com.rentalcar.springboot.rentalcarspringboot.controller;

import com.rentalcar.springboot.rentalcarspringboot.model.User;
import com.rentalcar.springboot.rentalcarspringboot.security.JwtUtils;
import com.rentalcar.springboot.rentalcarspringboot.security.entity.LoginRequest;
import com.rentalcar.springboot.rentalcarspringboot.security.entity.LoginResponse;
import com.rentalcar.springboot.rentalcarspringboot.security.entity.UserDetails;
import com.rentalcar.springboot.rentalcarspringboot.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private UserService userService;

    private PasswordEncoder encoder;

    private JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserService userService,
                          PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("Authentication:" + authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println("token: " + jwt);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userService.findByUsername(userDetails.getUsername());
        System.out.println("username: " + user.getUsername());
        List<String> role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        System.out.println("role: " + role.get(0));
        return ResponseEntity.ok(new LoginResponse(jwt, user.getId(), user.getUsername(), role.get(0)));
    }

}
