package org.example.onlinesupermarket.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.onlinesupermarket.dto.address.AddressDTO;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Integer orderId;
    private LocalDateTime orderDate;
    private String status;
    private Double totalAmount;
    private String couponCode;
    private AddressDTO address;
    private List<OrderItemDTO> orderItems;
    private Double calculatedTotal;
}
