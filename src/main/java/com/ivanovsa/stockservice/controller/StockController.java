package com.ivanovsa.stockservice.controller;

import com.ivanovsa.stockservice.dto.FigiesDto;
import com.ivanovsa.stockservice.dto.StocksDto;
import com.ivanovsa.stockservice.dto.StocksPricesDto;
import com.ivanovsa.stockservice.dto.TickersDto;
import com.ivanovsa.stockservice.model.Stock;
import com.ivanovsa.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/stocks/{ticker}")
    public Stock getStock(@PathVariable String ticker) {
        return stockService.getStockByTicker(ticker);
    }

    @PostMapping("/stocks/getStocksByTickers")
    public StocksDto getStocksByTickers(@RequestBody TickersDto tickersDto) {
        return stockService.getStocksByTicker(tickersDto);
    }

    @PostMapping("/stocks/prices")
    public StocksPricesDto getPrices(@RequestBody FigiesDto figiesDto) {
        return stockService.getPricesStocksByFigies(figiesDto);
    }
}
