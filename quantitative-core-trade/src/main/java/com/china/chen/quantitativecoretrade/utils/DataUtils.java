package com.china.chen.quantitativecoretrade.utils;

import com.china.chen.quantitativecoretrade.constants.KLineInteverEnum;
import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.china.chen.quantitativecoretrade.listener.CandlestickEventListerner;
import com.china.chen.quantitativecoretrade.listener.TradeEventListerner;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.Trade;
import com.huobi.client.model.enums.CandlestickInterval;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class DataUtils {

    /**
    * @Description: 获取对应交易对的实时成交信息
    * @param:   [tradePair]
    * @return:  com.huobi.client.model.Trade
    * @author chenjianjun
    * @date 10/23/19 2:02 PM
    * @version V1.0
    */
    public List<Trade> tradeDatas(String tradePair){
        TradePairEnum tradePairEnum = TradePairEnum.getEnumBykey(tradePair) ;
        List<Trade> tradeData = TradeEventListerner.TRADE_RECORD_CACHE.get(tradePairEnum) ;
        return tradeData ;
    }

    /**
    * @Description: 分交易对和时间间隔获取k线数据
    * @param:   [tradePair, interval]
    * @return:  com.china.chen.quantitativecoretrade.response.common.ResultBean<com.huobi.client.model.Candlestick>
    * @author chenjianjun
    * @date 10/23/19 2:02 PM
    * @version V1.0
    */
    public List<Candlestick> candlestickDatas(String tradePair,String interval){
        TradePairEnum tradePairEnum = TradePairEnum.getEnumBykey(tradePair) ;
        KLineInteverEnum intervalEnum = KLineInteverEnum.getEnumBykey(interval) ;
        Map<CandlestickInterval,LinkedList<Candlestick>> candlesticks =  CandlestickEventListerner.CANDLESTICK_RECORD_CACHE.get(tradePairEnum) ;

        List<Candlestick> stick ;

        switch (intervalEnum){
            case WEEK_1:
                stick = candlesticks.get(CandlestickInterval.WEEK1);
                break ;
            case YEAR_1:
                stick = candlesticks.get(CandlestickInterval.YEAR1);
                break ;
            case MONTH_1:
                stick = candlesticks.get(CandlestickInterval.MON1);
                break ;
            case MINUTE_1:
                stick = candlesticks.get(CandlestickInterval.MIN1);
                break ;
            case MINUTE_5:
                stick = candlesticks.get(CandlestickInterval.MIN5);
                break ;
            case MINUTE_15:
                stick = candlesticks.get(CandlestickInterval.MIN15);
                break ;
            case MINUTE_30:
                stick = candlesticks.get(CandlestickInterval.MIN30);
                break ;
            case MINUTE_60:
                stick = candlesticks.get(CandlestickInterval.MIN60);
                break ;
            default:
                stick = null ;
        }
        return stick ;
    }


}
