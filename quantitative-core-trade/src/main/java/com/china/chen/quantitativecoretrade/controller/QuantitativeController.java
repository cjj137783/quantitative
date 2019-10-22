package com.china.chen.quantitativecoretrade.controller;


import com.china.chen.quantitativecoretrade.constants.KLineInteverEnum;
import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.china.chen.quantitativecoretrade.listener.CandlestickEventListerner;
import com.china.chen.quantitativecoretrade.listener.TradeEventListerner;
import com.china.chen.quantitativecoretrade.response.common.ResultBean;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.Trade;
import com.huobi.client.model.enums.CandlestickInterval;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class QuantitativeController {

    /**
    * @Description: 获取对应交易对的实时成交信息
    * @param:   [tradePair 交易对，例如：btcusdt]
    * @return:  com.china.chen.quantitativecoretrade.response.common.ResultBean<com.huobi.client.model.Trade>
    * @author chenjianjun
    * @date 10/22/19 3:05 PM
    * @version V1.0
    */
    @GetMapping(value = "/quantitative/data/trade")
    public ResultBean<Trade> tradeData(
            @RequestParam(value = "tradePair") String tradePair){

        TradePairEnum tradePairEnum = TradePairEnum.getEnumBykey(tradePair) ;
        Trade tradeData = TradeEventListerner.TRADE_RECORD_CACHE.get(tradePairEnum) ;
        return ResultBean.success(tradeData) ;
    }


    @GetMapping(value = "/quantitative/data/candlestick")
    public ResultBean<Candlestick> candlestickData(
            @RequestParam(value = "tradePair") String tradePair
            ,@RequestParam(value = "interval") String interval){

        TradePairEnum tradePairEnum = TradePairEnum.getEnumBykey(tradePair) ;
        KLineInteverEnum intervalEnum = KLineInteverEnum.getEnumBykey(interval) ;
        Map<CandlestickInterval,Candlestick> candlesticks =  CandlestickEventListerner.CANDLESTICK_RECORD_CACHE.get(tradePairEnum) ;

        Candlestick stick ;

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


        return ResultBean.success(stick) ;
    }
}
