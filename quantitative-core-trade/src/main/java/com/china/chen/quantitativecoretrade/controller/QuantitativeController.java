package com.china.chen.quantitativecoretrade.controller;



import com.china.chen.quantitativecoretrade.constants.StrategyTypeEnum;
import com.china.chen.quantitativecoretrade.response.common.ResultBean;
import com.china.chen.quantitativecoretrade.response.common.ResultCode;
import com.china.chen.quantitativecoretrade.strategy.BaseStrategy;
import com.china.chen.quantitativecoretrade.utils.ThreadPoolsUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QuantitativeController {

    @GetMapping("/strategy/{name}")
    public ResultBean<Boolean> strategy(@PathVariable(value = "name") String strategyName) {

        StrategyTypeEnum strategyTypeEnum =  StrategyTypeEnum.getEnumBykey(strategyName) ;
        if(strategyTypeEnum == null){
            return ResultBean.fail(ResultCode.NO_STRATEGY.getCode(),ResultCode.NO_STRATEGY.getMsg());
        }

        ThreadPoolsUtils.STRATEGY_POOL.submit(() ->{
            try {
                Class clazz = Class.forName(strategyTypeEnum.getValue()) ;
                BaseStrategy strategy = (BaseStrategy)clazz.newInstance() ;
                strategy.start();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }) ;

        return ResultBean.success();
    }
}
