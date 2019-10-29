package com.china.chen.quantitativecoretrade.listener;

import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.china.chen.quantitativecoretrade.dto.TradeStatisticsInfo;
import com.china.chen.quantitativecoretrade.utils.DingTalkUtils;
import com.huobi.client.SubscriptionListener;
import com.huobi.client.model.Trade;
import com.huobi.client.model.enums.TradeDirection;
import com.huobi.client.model.event.TradeEvent;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TradeEventListerner implements SubscriptionListener<TradeEvent> {
    /**每个交易对的成交数量容量*/
    private static final Integer TRADE_CAPACITY = 100 ;
    /**大交易金额*/
    private static final Integer BIG_TRADE_AMOUNT = 10000 ;

    public static final Map<TradePairEnum,LinkedList<Trade>> TRADE_RECORD_CACHE = new HashMap() ;

    public static final Map<TradePairEnum,TradeStatisticsInfo> STATISTICS_INFO_MAP = new HashMap<>() ;

    @Override
    public void onReceive(TradeEvent tradeEvent) {
        tradeEvent.getTradeList().forEach(trade -> {
            TradePairEnum key = TradePairEnum.getEnumBykey(tradeEvent.getSymbol()) ;

            LinkedList<Trade> list = TRADE_RECORD_CACHE.get(key) ;
            TradeStatisticsInfo statisticsInfo = STATISTICS_INFO_MAP.get(key) ;
            if(list == null){
                list = new LinkedList<>() ;
                TRADE_RECORD_CACHE.put(key,list) ;
            }
            if(statisticsInfo == null){
                statisticsInfo = TradeStatisticsInfo.builder()
                        .averagePrice(BigDecimal.ZERO)
                        .buySumAmount(BigDecimal.ZERO)
                        .buySumQuantity(BigDecimal.ZERO)
                        .sellSumAmount(BigDecimal.ZERO)
                        .sellSumQuantity(BigDecimal.ZERO).build() ;
                STATISTICS_INFO_MAP.put(key,statisticsInfo) ;
            }

            if(list.size() >= TRADE_CAPACITY){
                list.removeFirst() ;
            }

            if(trade.getDirection() == TradeDirection.BUY){
                statisticsInfo.setBuySumAmount(statisticsInfo.getBuySumAmount().add(trade.getAmount().multiply(trade.getPrice())));
                statisticsInfo.setBuySumQuantity(statisticsInfo.getBuySumQuantity().add(trade.getAmount()));
            }else{
                statisticsInfo.setSellSumAmount(statisticsInfo.getSellSumAmount().add(trade.getAmount().multiply(trade.getPrice())));
                statisticsInfo.setSellSumQuantity(statisticsInfo.getSellSumQuantity().add(trade.getAmount()));
            }

            tradeAlert(key,trade) ;

            list.addLast(trade);

        });
    }

    private void tradeAlert(TradePairEnum key,Trade trade) {
        /**如果出现单笔交易额大于10000usd的订单，则报警*/
        BigDecimal usdtAmount = trade.getAmount().multiply(trade.getPrice()) ;
        if(usdtAmount.compareTo(new BigDecimal(BIG_TRADE_AMOUNT)) > 0){
            DingTalkUtils.sendMarkDown("大额交易"
                    ,"交易对："+key
                    ,"交易方向："+trade.getDirection().toString()
                    ,"金额："+usdtAmount + " USDT");
        }
    }
}
