package org.example.onlinesupermarket.service.address.impl;

import org.example.onlinesupermarket.dto.address.AddressDTO;
import org.example.onlinesupermarket.entity.Address;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.repository.AddressRepository;
import org.example.onlinesupermarket.repository.UserRepository;
import org.example.onlinesupermarket.service.address.AddressService;
import org.example.onlinesupermarket.mapper.address.AddressMapper;
import org.example.onlinesupermarket.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<AddressDTO> getAllAddressesByUser(User user) {
        List<Address> addresses = addressRepository.findByUserOrderByDefaultAddressAsc(user);
        return addresses.stream()
                .map(addressMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public AddressDTO getAddressById(Integer addressId, User user) {
        Address address = addressRepository.findByUserAndAddressId(user, addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        return addressMapper.toDTO(address);
    }
    
    @Override
    public void createAddress(Integer userId, AddressDTO addressDTO) {
        Optional<User> user = userRepository.findByUserId(userId);
        Address address = addressMapper.toEntity(addressDTO);
        if (user.isPresent()){
            address.setUser(user.get());
            addressRepository.save(address);
        }
    }
    
    @Override
    public void deleteAddress(Integer addressId, User user) {
        Address address = addressRepository.findByUserAndAddressId(user, addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        
        // If deleting default address, set another address as default
        if (address.isDefaultAddress()) {
            List<Address> otherAddresses = addressRepository.findByUser(user);
            otherAddresses.remove(address);
            if (!otherAddresses.isEmpty()) {
                Address newDefault = otherAddresses.get(0);
                newDefault.setDefaultAddress(true);
                addressRepository.save(newDefault);
            }
        }
        
        addressRepository.delete(address);
    }
    
    @Override
    public AddressDTO setDefaultAddress(Integer addressId, User user) {
        // Remove default from current default address
        addressRepository.findByUserAndDefaultAddressTrue(user).ifPresent(existingDefault -> {
            existingDefault.setDefaultAddress(false);
            addressRepository.save(existingDefault);
        });
        
        // Set new default address
        Address address = addressRepository.findByUserAndAddressId(user, addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        address.setDefaultAddress(true);
        Address savedAddress = addressRepository.save(address);
        return addressMapper.toDTO(savedAddress);
    }
    
    @Override
    public AddressDTO getDefaultAddress(User user) {
        Address defaultAddress = addressRepository.findByUserAndDefaultAddressTrue(user)
                .orElse(null);
        return defaultAddress != null ? addressMapper.toDTO(defaultAddress) : null;
    }
    
    @Override
    public void updateAddress(Integer userId, AddressDTO addressDTO) {
        Optional<User> userOpt = userRepository.findByUserId(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Address address = addressRepository.findByUserAndAddressId(user, addressDTO.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));
            address.setRecipient(addressDTO.getRecipient());
            address.setStreet(addressDTO.getStreet());
            address.setCity(addressDTO.getCity());
            address.setProvince(addressDTO.getProvince());
            address.setPostalCode(addressDTO.getPostalCode());
            address.setDefaultAddress(addressDTO.isDefaultAddress());
            addressRepository.save(address);
        }
    }
    
    private String determineAddressType(String recipient) {
        if (recipient == null) return "Other";
        String lowerRecipient = recipient.toLowerCase();
        if (lowerRecipient.contains("home") || lowerRecipient.contains("nhà")) {
            return "Home";
        } else if (lowerRecipient.contains("office") || lowerRecipient.contains("công ty")) {
            return "Office";
        } else {
            return "Other";
        }
    }
}
