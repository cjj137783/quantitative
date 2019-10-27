package com.china.chen.quantitativecoretrade.controller;



import com.alibaba.fastjson.JSONObject;
import com.china.chen.quantitativecoretrade.constants.StrategyTypeEnum;
import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.china.chen.quantitativecoretrade.init.AccountSubscribe;
import com.china.chen.quantitativecoretrade.response.common.ResultBean;
import com.china.chen.quantitativecoretrade.response.common.ResultCode;
import com.china.chen.quantitativecoretrade.strategy.BaseStrategy;
import com.china.chen.quantitativecoretrade.utils.ThreadPoolsUtils;
import com.china.chen.quantitativecoretrade.listener.Trade;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class QuantitativeController {

    /**
    * @class_name: QuantitativeController
    * @Description: 开始执行策略
    * @author chenjianjun
    * @date 10/26/19 9:43 PM
    * @version V1.0
    */
    @GetMapping("/start-strategy")
    public ResultBean<Boolean> startStrategy(
            @RequestParam(value = "strategyName") String strategyName
            , @RequestParam(value = "secretSeed") String secretSeed
            , @RequestParam(value = "tradePair") String tradePair
            , @RequestParam(value = "strategyParam") String strategyParam) {

        Map<String,Object> params = JSONObject.parseObject(strategyParam,Map.class);
        /**初始化运行上下文信息*/
        initContext(secretSeed) ;



        StrategyTypeEnum strategyTypeEnum =  StrategyTypeEnum.getEnumBykey(strategyName) ;
        if(strategyTypeEnum == null){
            return ResultBean.fail(ResultCode.NO_STRATEGY.getCode(),ResultCode.NO_STRATEGY.getMsg());
        }

        ThreadPoolsUtils.STRATEGY_POOL.submit(() ->{
            try {
                Class clazz = Class.forName(strategyTypeEnum.getValue()) ;
                BaseStrategy strategy = (BaseStrategy)clazz.newInstance() ;
                TradePairEnum tradePairEnum = TradePairEnum.getEnumBykey(tradePair) ;
                strategy.startRunning();
                while(strategy.continueRunning()){
                    strategy.start(tradePairEnum,params);

                    Thread.sleep(500);
                }
            } catch (ClassNotFoundException e) {
                log.error("",e);
            } catch (IllegalAccessException e) {
                log.error("",e);
            } catch (InstantiationException e) {
                log.error("",e);
            } catch (Exception e) {
                log.error("",e);
            }
        }) ;

        return ResultBean.success();
    }

    private void initContext(String secretSeed) {
        AccountSubscribe.subscribeAccount(secretSeed);
        Trade tradeHelper = new Trade() ;
        tradeHelper.init(secretSeed);
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
