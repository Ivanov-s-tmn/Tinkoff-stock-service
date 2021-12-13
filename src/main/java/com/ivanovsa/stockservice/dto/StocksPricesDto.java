package com.ivanovsa.stockservice.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class StocksPricesDto {

    private List<StockPrice> prices;
}
