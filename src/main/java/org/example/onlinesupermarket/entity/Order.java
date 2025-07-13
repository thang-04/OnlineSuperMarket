package org.example.onlinesupermarket.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "addressId", nullable = false)
    private Address address;

    private String couponCode;

    @Column(columnDefinition = "DATETIME2 DEFAULT GETDATE()", updatable = false)
    private LocalDateTime orderDate;

    @Column(length = 100, columnDefinition = "NVARCHAR(100) DEFAULT 'Pending'")
    private String status;

    private Double totalAmount;

    // Constructor để khởi tạo giá trị mặc định khi tạo đối tượng mới
    @PrePersist
    protected void onCreate() {
        if (orderDate == null) {
            orderDate = LocalDateTime.now();
        }
        if (status == null) {
            status = "Pending";
        }
    }
}