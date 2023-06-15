package com.example.demo.security.service;

import org.springframework.stereotype.Service;
import com.example.demo.security.entity.Role;
import com.example.demo.security.enums.ERole;
import com.example.demo.security.repository.RoleRepository;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;


    public void initialCharge() {
        Role newrole1= new Role();
        newrole1.setName(ERole.ROLE_ADMIN);
        roleRepository.save(newrole1);
        
        Role newrole2= new Role();
        newrole2.setName(ERole.ROLE_EDIT);
        roleRepository.save(newrole2);

        Role newrole3= new Role();
        newrole3.setName(ERole.ROLE_USER);
        roleRepository.save(newrole3);
    }

}
