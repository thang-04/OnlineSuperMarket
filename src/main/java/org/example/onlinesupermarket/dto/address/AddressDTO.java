package org.example.onlinesupermarket.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Integer addressId;
    private String recipient;
    private String street;
    private String city;
    private String province;
    private String postalCode;
    private boolean defaultAddress;
}
