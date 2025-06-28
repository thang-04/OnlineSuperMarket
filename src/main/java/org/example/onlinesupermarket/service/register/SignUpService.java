package org.example.onlinesupermarket.service.register;

import org.example.onlinesupermarket.dto.register.SignUpDto;
import org.example.onlinesupermarket.entity.Role;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.mapper.register.UserMapper;
import org.example.onlinesupermarket.repository.RoleRepository;
import org.example.onlinesupermarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public interface SignUpService {

     void registerUser(SignUpDto signUpDto) ;
    
}