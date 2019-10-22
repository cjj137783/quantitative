package com.china.chen.quantitativecoretrade.listener;

import com.alibaba.fastjson.JSON;
import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.huobi.client.SubscriptionListener;
import com.huobi.client.model.event.TradeEvent;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TradeEventListerner implements SubscriptionListener<TradeEvent> {


    public static final Map TRADE_RECORD_CACHE = new HashMap<TradePairEnum,TradeEvent>() ;

    @Override
    public void onReceive(TradeEvent tradeEvent) {
        tradeEvent.getTradeList().forEach(trade -> {
            log.info(JSON.toJSONString(tradeEvent));
            TRADE_RECORD_CACHE.put(tradeEvent.getSymbol(),trade) ;
        });
    }
}
