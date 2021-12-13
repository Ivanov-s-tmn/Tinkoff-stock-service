package com.ivanovsa.stockservice.dto;

import com.ivanovsa.stockservice.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StocksDto {

    private List<Stock> stocks;
}
