package com.china.chen.quantitativecoretrade.controller;


import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.china.chen.quantitativecoretrade.listener.TradeEventListerner;
import com.china.chen.quantitativecoretrade.response.common.ResultBean;
import com.huobi.client.model.Trade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
