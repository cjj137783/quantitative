package com.china.chen.quantitativecoretrade.listener;

import com.alibaba.fastjson.JSON;
import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.huobi.client.SubscriptionListener;
import com.huobi.client.model.Trade;
import com.huobi.client.model.event.TradeEvent;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TradeEventListerner implements SubscriptionListener<TradeEvent> {


    public static final Map<TradePairEnum,Trade> TRADE_RECORD_CACHE = new HashMap() ;

    @Override
    public void onReceive(TradeEvent tradeEvent) {
        tradeEvent.getTradeList().forEach(trade -> {
            log.info(JSON.toJSONString(tradeEvent));
            TradePairEnum key = TradePairEnum.getEnumBykey(tradeEvent.getSymbol()) ;
            TRADE_RECORD_CACHE.put(key,trade) ;
        });
    }
}
