package com.china.chen.quantitativecoretrade.controller;



import com.china.chen.quantitativecoretrade.constants.StrategyTypeEnum;
import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.china.chen.quantitativecoretrade.response.common.ResultBean;
import com.china.chen.quantitativecoretrade.response.common.ResultCode;
import com.china.chen.quantitativecoretrade.strategy.BaseStrategy;
import com.china.chen.quantitativecoretrade.utils.ThreadPoolsUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class QuantitativeController {

    /**
    * @class_name: QuantitativeController
    * @Description: 开始执行策略
    * @author chenjianjun
    * @date 10/26/19 9:43 PM
    * @version V1.0
    */
    @GetMapping("/start-strategy/{name}")
    public ResultBean<Boolean> startStrategy(@PathVariable(value = "name") String strategyName
            , @RequestParam(value = "secretSeed") String secretSeed
            , @RequestParam(value = "tradePair") String tradePair
            , @RequestParam(value = "strategyParam") Map<String,Object> strategyParam) {

        StrategyTypeEnum strategyTypeEnum =  StrategyTypeEnum.getEnumBykey(strategyName) ;
        if(strategyTypeEnum == null){
            return ResultBean.fail(ResultCode.NO_STRATEGY.getCode(),ResultCode.NO_STRATEGY.getMsg());
        }

        ThreadPoolsUtils.STRATEGY_POOL.submit(() ->{
            try {
                Class clazz = Class.forName(strategyTypeEnum.getValue()) ;
                BaseStrategy strategy = (BaseStrategy)clazz.newInstance() ;
                TradePairEnum tradePairEnum = TradePairEnum.getEnumBykey(tradePair) ;

                while(strategy.continueRunning()){
                    strategy.start(tradePairEnum,secretSeed,strategyParam);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }) ;

        return ResultBean.success();
    }


    /**
    * @Description: 停止执行策略
    * @param:   [strategyName]
    * @return:  com.china.chen.quantitativecoretrade.response.common.ResultBean<java.lang.Boolean>
    * @author chenjianjun
    * @date 10/26/19 9:44 PM
    * @version V1.0
    */
    @GetMapping("/stop-strategy/{name}")
    public ResultBean<Boolean> startStrategy(
            @PathVariable(value = "name") String strategyName) {

        StrategyTypeEnum strategyTypeEnum =  StrategyTypeEnum.getEnumBykey(strategyName) ;
        if(strategyTypeEnum == null){
            return ResultBean.fail(ResultCode.NO_STRATEGY.getCode(),ResultCode.NO_STRATEGY.getMsg());
        }

        try {
            Class clazz = Class.forName(strategyTypeEnum.getValue());
            BaseStrategy strategy = (BaseStrategy)clazz.newInstance() ;
            strategy.stopRunning();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


        return ResultBean.success();
    }
}
