package org.example.onlinesupermarket.mapper.address;

import org.example.onlinesupermarket.dto.address.AddressDTO;
import org.example.onlinesupermarket.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressDTO toDTO(Address address) {
        if (address == null) return null;
        AddressDTO dto = new AddressDTO();
        dto.setAddressId(address.getAddressId());
        dto.setRecipient(address.getRecipient());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setProvince(address.getProvince());
        dto.setPostalCode(address.getPostalCode());
        dto.setDefaultAddress(address.isDefaultAddress());
        return dto;
    }

    public Address toEntity(AddressDTO dto) {
        if (dto == null) return null;
        Address address = new Address();
        address.setAddressId(dto.getAddressId());
        address.setRecipient(dto.getRecipient());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setProvince(dto.getProvince());
        address.setPostalCode(dto.getPostalCode());
        address.setDefaultAddress(dto.isDefaultAddress());
        return address;
    }

}
