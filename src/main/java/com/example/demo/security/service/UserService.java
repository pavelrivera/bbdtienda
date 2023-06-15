package com.example.demo.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.security.entity.Role;
import com.example.demo.security.entity.User;
import com.example.demo.security.enums.ERole;
import com.example.demo.security.repository.RoleRepository;
import com.example.demo.security.repository.UserRepository;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;


    public void initialCharge() {
        User newuser1= new User();
        newuser1.setUsername("admin");
        newuser1.setEmail("admin1234@gmail.com");
        newuser1.setPassword(encoder.encode("admin"));
                            
        Set<Role> roles = new HashSet<>();
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);
        newuser1.setRoles(roles);
        userRepository.save(newuser1);

        User newuser2= new User();
        newuser2.setUsername("user");
        newuser2.setEmail("user1234@gmail.com");
        newuser2.setPassword(encoder.encode("user"));
                            
        Set<Role> roles2 = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles2.add(userRole);
        newuser2.setRoles(roles2);
        userRepository.save(newuser2);
        
    }

}
