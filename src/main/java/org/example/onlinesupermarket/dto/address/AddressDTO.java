package org.example.onlinesupermarket.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Integer addressId;
    
    @NotBlank(message = "Recipient name is required")
    @Size(min = 2, max = 100, message = "Recipient name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ\\s]+$", message = "Recipient name can only contain letters and spaces")
    private String recipient;
    
    @NotBlank(message = "Street address is required")
    @Size(min = 5, max = 200, message = "Street address must be between 5 and 200 characters")
    private String street;
    
    @NotBlank(message = "City is required")
    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ\\s]+$", message = "City can only contain letters and spaces")
    private String city;
    
    @NotBlank(message = "Province is required")
    @Size(min = 2, max = 50, message = "Province must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ\\s]+$", message = "Province can only contain letters and spaces")
    private String province;
    
    @NotBlank(message = "Postal code is required")
    @Size(min = 5, max = 10, message = "Postal code must be between 5 and 10 characters")
    @Pattern(regexp = "^[0-9]+$", message = "Postal code can only contain numbers")
    private String postalCode;
    
    private boolean defaultAddress;
}
