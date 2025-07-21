package org.example.onlinesupermarket.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyIncome {
    private Integer month;
    private Double total;
}