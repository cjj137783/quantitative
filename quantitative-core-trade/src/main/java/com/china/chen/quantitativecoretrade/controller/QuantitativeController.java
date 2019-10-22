package com.china.chen.quantitativecoretrade.controller;


import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.china.chen.quantitativecoretrade.response.common.ResultBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuantitativeController {


    @GetMapping(value = "/quantitative/startRun")
    public ResultBean<Boolean> startRun(
            @RequestParam(value = "tradePair") String tradePair
            ,@RequestParam(value = "jsScript") String jsScript){
        TradePairEnum tradePairEnum = TradePairEnum.getEnumBykey(tradePair) ;


        return ResultBean.success(true) ;
    }
}
