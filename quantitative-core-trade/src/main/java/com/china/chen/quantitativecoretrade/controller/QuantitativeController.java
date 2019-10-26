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
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
public class QuantitativeController {



    @GetMapping("/hello/themeleaf")
    public ModelAndView themaleaf(ModelAndView model) {
        model.addObject("name", "Joshua");
        model.setViewName("main");
        return model;
    }
}
