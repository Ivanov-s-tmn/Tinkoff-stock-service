package com.ivanovsa.stockservice.service;

import com.ivanovsa.stockservice.dto.*;
import com.ivanovsa.stockservice.exception.StockNotFoundException;
import com.ivanovsa.stockservice.model.Currency;
import com.ivanovsa.stockservice.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrumentList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TinkoffStockService implements StockService {

    private final OpenApi openApi;

    @Async
    public CompletableFuture<MarketInstrumentList> getMarketInstrumentList(String ticker) {
        var context = openApi.getMarketContext();
        return context.searchMarketInstrumentsByTicker(ticker);
    }

    @Override
    public Stock getStockByTicker(String ticker) {
        var cf = getMarketInstrumentList(ticker);

        var list = cf.join().getInstruments();
        if (list.isEmpty()) {
            throw new StockNotFoundException(String.format("Stock %s not found", ticker));
        }
        var item = list.get(0);
        return new Stock(
                item.getTicker(),
                item.getFigi(),
                item.getName(),
                item.getType().getValue(),
                Currency.valueOf(item.getCurrency().getValue()),
                "TINKOFF"
        );
    }

    @Override
    public StocksDto getStocksByTicker(TickersDto tickers) {
        List<CompletableFuture<MarketInstrumentList>> marketInstrument =
                new ArrayList<>();
        tickers.getTickers()
                .forEach(ticker -> marketInstrument.add(getMarketInstrumentList(ticker)));
        List<Stock> stocks = marketInstrument.stream()
                .map(CompletableFuture::join)
                .map(mi -> {
                    if (!mi.getInstruments().isEmpty()) {
                        return mi.getInstruments().get(0);
                    }
                    return null;
                })
                .filter(el -> Objects.nonNull(el))
                .map(mi ->
                    new Stock(
                            mi.getTicker(),
                            mi.getFigi(),
                            mi.getName(),
                            mi.getType().getValue(),
                            Currency.valueOf(mi.getCurrency().getValue()),
                            "TINKOFF"
                    ))
                .collect(Collectors.toList());

        return new StocksDto(stocks);
    }

    public StockPrice getPrice(String figi) {
        var orderBook =
                openApi.getMarketContext().getMarketOrderbook(figi, 0).join().get();
        return new StockPrice(figi, orderBook.getLastPrice().doubleValue());
    }

    @Override
    public StocksPricesDto getPrices(FigiesDto figiesDto) {
        var stockPrices = figiesDto.getFigies().stream()
                .map(this::getPrice)
                .collect(Collectors.toList());
        return new StocksPricesDto(stockPrices);
    }
}
