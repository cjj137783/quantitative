package com.china.chen.quantitativecoretrade.strategy;

import com.china.chen.quantitativecoretrade.constants.TradePairEnum;

import java.util.Map;

public interface BaseStrategy {

    /**
    * @Description: 开始执行策略
    * @param:   [tradePair, secretSeed,strategyParam 策略相关的参数]
    * @return:  void
    * @author chenjianjun
    * @date 10/26/19 9:36 PM
    * @version V1.0
    */
    void start(TradePairEnum tradePair, Map<String,Object> strategyParam) throws Exception;

    /**
    * @Description: 是否继续运行该策略
    * @param:   []
    * @return:  java.lang.Boolean
    * @author chenjianjun
    * @date 10/26/19 9:36 PM
    * @version V1.0
    */
    Boolean continueRunning() ;

    /**
    * @Description: 停止运行策略
    * @param:   []
    * @return:  void
    * @author chenjianjun
    * @date 10/26/19 9:41 PM
    * @version V1.0
    */
    void stopRunning() ;
}
