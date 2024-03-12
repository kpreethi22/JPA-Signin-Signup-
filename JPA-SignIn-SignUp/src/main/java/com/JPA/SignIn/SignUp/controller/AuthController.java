package com.JPA.SignIn.SignUp.controller;

import com.JPA.SignIn.SignUp.entity.Role;
import com.JPA.SignIn.SignUp.entity.User;
import com.JPA.SignIn.SignUp.payload.LoginDto;
import com.JPA.SignIn.SignUp.payload.SignUpDto;
import com.JPA.SignIn.SignUp.repository.RoleRepository;
import com.JPA.SignIn.SignUp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
    @RequestMapping("/api/auth")
    public class AuthController {
        private AuthenticationManager authenticationManager;
        public AuthController(AuthenticationManager authenticationManager) {
            this.authenticationManager = authenticationManager;
        }
        @Autowired
        private RoleRepository roleRepository;
        @Autowired
         private UserRepository userRepository;
        @Autowired
        private PasswordEncoder passwordEncoder;


        // http://localhost:8080/api/auth/signin
        @PostMapping("/signin")
        public ResponseEntity<String> authenticateUser(@RequestBody LoginDto
                                                                        loginDto) {
            Authentication authentication = authenticationManager.authenticate(
                    new
                            UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                            loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User SignedIn successfully!",HttpStatus.OK);

        }
        @PostMapping("/signup")
        public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
            // add check for username exists in a DB
            if(userRepository.existsByUsername(signUpDto.getUsername())){
                return new ResponseEntity<>("Username is already taken!",
                        HttpStatus.BAD_REQUEST);
            }
            // add check for email exists in DB
            if(userRepository.existsByEmail(signUpDto.getEmail())){
                return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
            }
            // create user object
            User user = new User();
            user.setName(signUpDto.getName());
            user.setUsername(signUpDto.getUsername());
            user.setEmail(signUpDto.getEmail());
            user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
//            comment this two lines while signingup
            Role roles = roleRepository.findByName("ROLE_ADMIN").get();
            user.setRoles(Collections.singleton(roles));
            userRepository.save(user);
            return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
        }
//        // http://localhost:8080/api/auth/signup
//
//    }





}
