package org.example.onlinesupermarket.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private String recipient;
    private String street;
    private String city;
    private String province;
    private String postalCode;
    private boolean isDefault = false;
}
