package org.example.onlinesupermarket.service.register;

import org.example.onlinesupermarket.dto.register.SignUpDto;
import org.springframework.stereotype.Service;

@Service
public interface SignUpService {

     void registerUser(SignUpDto signUpDto) ;
    
}