package com.china.chen.quantitativecoretrade.listener;


import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.huobi.client.SubscriptionListener;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.PriceDepth;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.event.CandlestickEvent;
import com.huobi.client.model.event.PriceDepthEvent;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
* @class_name: PriceDepthListener
* @Description: 获取价格深度数据
* @author chenjianjun
* @date 10/26/19 8:12 PM
* @version V1.0
*/
@Slf4j
public class Ticker implements SubscriptionListener<PriceDepthEvent> {

    public static Map<TradePairEnum,PriceDepth> PRICE_DEPTH_CACHE = new HashMap<>() ;

    private static BigDecimal buy ;
    private static BigDecimal sell ;

    @Override
    public void onReceive(PriceDepthEvent priceDepthEvent) {
        PRICE_DEPTH_CACHE.put(TradePairEnum.getEnumBykey(priceDepthEvent.getSymbol()),priceDepthEvent.getData()) ;
        buy = priceDepthEvent.getData().getBids().get(0).getPrice() ;
        sell = priceDepthEvent.getData().getAsks().get(0).getPrice() ;
    }


    public static BigDecimal buy(){
        return buy ;
    }

    public static BigDecimal sell(){
        return sell ;
    }
}
