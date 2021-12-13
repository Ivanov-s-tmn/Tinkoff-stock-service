package com.ivanovsa.stockservice.service;

import com.ivanovsa.stockservice.dto.*;
import com.ivanovsa.stockservice.model.Stock;

public interface StockService {

    Stock getStockByTicker(String ticker);
    StocksDto getStocksByTicker(TickersDto tickers);
    StocksPricesDto getPrices(FigiesDto figiesDto);
}
