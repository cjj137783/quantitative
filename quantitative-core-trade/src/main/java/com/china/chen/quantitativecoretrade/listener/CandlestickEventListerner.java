package com.china.chen.quantitativecoretrade.listener;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.china.chen.quantitativecoretrade.utils.DingTalkUtils;
import com.huobi.client.SubscriptionListener;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.event.CandlestickEvent;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CandlestickEventListerner implements SubscriptionListener<CandlestickEvent> {


    private static Cache<String,String> cache = CacheBuilder.newBuilder()
                  .expireAfterWrite(3,TimeUnit.MINUTES)
                  .build();

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

        /**暴跌暴涨报警*/
        alert(candlestickEvent) ;
    }

    private void alert(CandlestickEvent candlestickEvent) {
        /**30分钟内快速变盘报警*/
        if(candlestickEvent.getInterval() == CandlestickInterval.MIN30){
            Candlestick candlestick = candlestickEvent.getData() ;
            BigDecimal margin = candlestick.getClose().subtract(candlestick.getOpen()) ;
            BigDecimal ratio = margin.divide(candlestick.getOpen(),3,RoundingMode.HALF_UP) ;
            /**如果低涨幅的绝对值大于2，则证明有行情*/
            if(ratio.abs().compareTo(new BigDecimal("0.02")) > 0){

                if(StringUtils.isEmpty(cache.getIfPresent(candlestickEvent.getSymbol()))){
                    DingTalkUtils.sendMarkDown("暴涨暴跌告警","币种信息： "+candlestickEvent.getSymbol()+" 幅度："+ratio);
                    cache.put(candlestickEvent.getSymbol(),ratio.toPlainString());
                }
            }
        }
    }
}
