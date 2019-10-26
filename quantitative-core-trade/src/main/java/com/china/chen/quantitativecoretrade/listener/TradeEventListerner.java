package com.china.chen.quantitativecoretrade.listener;

import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.huobi.client.SubscriptionListener;
import com.huobi.client.model.Trade;
import com.huobi.client.model.event.TradeEvent;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TradeEventListerner implements SubscriptionListener<TradeEvent> {
    /**每个交易对的成交数量容量*/
    private static final Integer TRADE_CAPACITY = 100 ;

    public static final Map<TradePairEnum,LinkedList<Trade>> TRADE_RECORD_CACHE = new HashMap() ;

    @Override
    public void onReceive(TradeEvent tradeEvent) {
        tradeEvent.getTradeList().forEach(trade -> {
            TradePairEnum key = TradePairEnum.getEnumBykey(tradeEvent.getSymbol()) ;

            LinkedList<Trade> list = TRADE_RECORD_CACHE.get(key) ;
            if(list == null){
                list = new LinkedList<>() ;
            }
            if(list.size() >= TRADE_CAPACITY){
                list.removeFirst() ;
            }
            list.addLast(trade);
            TRADE_RECORD_CACHE.put(key,list) ;
        });
    }
}
