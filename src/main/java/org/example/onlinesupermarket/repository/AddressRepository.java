package org.example.onlinesupermarket.repository;

import org.example.onlinesupermarket.entity.Address;
import org.example.onlinesupermarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    
    List<Address> findByUserOrderByDefaultAddressDesc(User user);
    
    List<Address> findByUser(User user);
    
    Optional<Address> findByUserAndDefaultAddressTrue(User user);
    
    @Query("SELECT a FROM Address a WHERE a.user = :user AND a.addressId = :addressId")
    Optional<Address> findByUserAndAddressId(@Param("user") User user, @Param("addressId") Integer addressId);
    
}