package com.china.chen.quantitativecoretrade.listener;


import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.huobi.client.SubscriptionListener;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.event.CandlestickEvent;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CandlestickEventListerner implements SubscriptionListener<CandlestickEvent> {

    public static final Map<TradePairEnum,Map<CandlestickInterval,Candlestick>> CANDLESTICK_RECORD_CACHE = new HashMap() ;

    @Override
    public void onReceive(CandlestickEvent candlestickEvent) {
        Map<CandlestickInterval,Candlestick> innerDataMap = CANDLESTICK_RECORD_CACHE.get(TradePairEnum.getEnumBykey(candlestickEvent.getSymbol())) ;
        if(innerDataMap == null){
            innerDataMap = new HashMap<>() ;
            CANDLESTICK_RECORD_CACHE.put(TradePairEnum.getEnumBykey(candlestickEvent.getSymbol()),innerDataMap) ;
        }

        innerDataMap.put(candlestickEvent.getInterval(),candlestickEvent.getData()) ;
    }


}
