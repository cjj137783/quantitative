package com.china.chen.quantitativecoretrade.listener;


import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.huobi.client.SubscriptionListener;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.event.CandlestickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CandlestickEventListerner implements SubscriptionListener<CandlestickEvent> {

    /**每个交易对、不同时间间隔的K线数据容量*/
    private static final Integer CANDLESTICK_CAPACITY = 30 ;

    public static final Map<TradePairEnum,Map<CandlestickInterval,LinkedList<Candlestick>>> CANDLESTICK_RECORD_CACHE = new HashMap() ;

    @Override
    public void onReceive(CandlestickEvent candlestickEvent) {
        Map<CandlestickInterval,LinkedList<Candlestick>> innerDataMap = CANDLESTICK_RECORD_CACHE.get(TradePairEnum.getEnumBykey(candlestickEvent.getSymbol())) ;
        if(innerDataMap == null){
            innerDataMap = new HashMap<>() ;
            CANDLESTICK_RECORD_CACHE.put(TradePairEnum.getEnumBykey(candlestickEvent.getSymbol()),innerDataMap) ;
        }

        LinkedList<Candlestick> candlesticks = innerDataMap.get(candlestickEvent.getInterval()) ;
        if(candlesticks == null){
            candlesticks = new LinkedList<>() ;
            innerDataMap.put(candlestickEvent.getInterval(),candlesticks) ;
        }
        if(candlesticks.size() >= CANDLESTICK_CAPACITY){
            candlesticks.removeFirst() ;
        }
        candlesticks.addLast(candlestickEvent.getData());
    }
}
