package com.china.chen.quantitativecoretrade.controller;



import com.alibaba.fastjson.JSONObject;
import com.china.chen.quantitativecoretrade.constants.StrategyTypeEnum;
import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.china.chen.quantitativecoretrade.init.RequestClientInit;
import com.china.chen.quantitativecoretrade.init.SubscribeClientInit;
import com.china.chen.quantitativecoretrade.response.common.MessageException;
import com.china.chen.quantitativecoretrade.response.common.ResultBean;
import com.china.chen.quantitativecoretrade.response.common.ResultCode;
import com.china.chen.quantitativecoretrade.strategy.BaseStrategy;
import com.china.chen.quantitativecoretrade.utils.ThreadPoolsUtils;
import com.china.chen.quantitativecoretrade.listener.Trade;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class QuantitativeController {


    @Autowired
    private RequestClientInit requestClientInit ;
    @Autowired
    private SubscribeClientInit subscribeClientInit ;

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

        /**参数校验*/
        validParam(strategyName,secretSeed,tradePair) ;
        /**初始化运行上下文信息*/
        initContext(secretSeed) ;

        StrategyTypeEnum strategyTypeEnum =  StrategyTypeEnum.getEnumBykey(strategyName) ;

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

    private void validParam(String strategyName, String secretSeed, String tradePair) {
        StrategyTypeEnum strategyTypeEnum =  StrategyTypeEnum.getEnumBykey(strategyName) ;
        if(strategyTypeEnum == null){
            throw new MessageException(ResultCode.BAD_REQUEST.getCode(),"未找到对应的策略名称");
        }

        if(StringUtils.isEmpty(secretSeed)){
            throw new MessageException(ResultCode.BAD_REQUEST.getCode(),"未传递秘钥seed");
        }

        TradePairEnum tradePairEnum =  TradePairEnum.getEnumBykey(tradePair) ;
        if(tradePairEnum == null){
            throw new MessageException(ResultCode.BAD_REQUEST.getCode(),"未找到对应的交易对信息");
        }
    }

    private void initContext(String secretSeed) {
        subscribeClientInit.init(secretSeed);
        requestClientInit.init(secretSeed);

        subscribeClientInit.subscribeAccountChange();

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
    public ResultBean<Boolean> stopStrategy(
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



    /**
     * @Description: 循环下单，知道下单成功，在交易所出现问题的时候可以使用
     * @param:   [strategyName]
     * @return:  com.china.chen.quantitativecoretrade.response.common.ResultBean<java.lang.Boolean>
     * @author chenjianjun
     * @date 10/26/19 9:44 PM
     * @version V1.0
     */
    @PostMapping("/loopTrade")
    public ResultBean<Boolean> loopTrade(
            @RequestParam(value = "secretSeed") String secretSeed
            ,@RequestParam(value = "tradePair") String tradePair
            ,@RequestParam(value = "direction") String direction
            ,@RequestParam(value = "price") BigDecimal price
            ,@RequestParam(value = "amount") BigDecimal amount) {
        /**初始化运行上下文信息*/
        initContext(secretSeed) ;
        TradePairEnum tradePairEnum = TradePairEnum.getEnumBykey(tradePair) ;

        while(true){

            Long orderId = -1L ;

            try {
                if(StringUtils.equals(direction,"sell")){
                    orderId = Trade.sell(tradePairEnum,price, amount);
                }else if(StringUtils.equals(direction,"buy")){
                    orderId = Trade.buy(tradePairEnum,price, amount);
                }

                if(orderId != -1){
                    break ;
                }
               Thread.sleep(1000);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        }


        return ResultBean.success();
    }
}
