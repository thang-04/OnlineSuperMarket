package org.example.onlinesupermarket.service.address;

import org.example.onlinesupermarket.dto.address.AddressDTO;
import org.example.onlinesupermarket.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {
    List<AddressDTO> getAllAddressesByUser(User user);
    
    AddressDTO getAddressById(Integer addressId, User user);
    
    void createAddress(Integer userId, AddressDTO addressDTO);

    
    void deleteAddress(Integer addressId, User user);
    
    AddressDTO setDefaultAddress(Integer addressId, User user);

    AddressDTO getDefaultAddress(User user);

    void updateAddress(Integer userId, AddressDTO addressDTO);
}
