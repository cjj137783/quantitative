package com.china.chen.quantitativecoretrade.listener;

import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.huobi.client.SubscriptionListener;
import com.huobi.client.model.AccountChange;
import com.huobi.client.model.event.AccountEvent;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Acc implements SubscriptionListener<AccountEvent> {

    private static Map<String,BigDecimal> accMap = new HashMap<>() ;

    @Override
    public void onReceive(AccountEvent accountEvent) {
        if(accountEvent.getData() != null && !accountEvent.getData().isEmpty()){
            for(AccountChange accountChange : accountEvent.getData()){
                accMap.put(accountChange.getCurrency(),accountChange.getBalance()) ;
            }
        }
    }

    /**
    * @Description: 获取对应交易对的余额（基础币种）
    * @param:   [tradePairEnum]
    * @return:  java.math.BigDecimal
    * @author chenjianjun
    * @date 10/27/19 2:31 PM
    * @version V1.0
    */
    public static BigDecimal balance(TradePairEnum tradePairEnum){
        return accMap.get(tradePairEnum.getBaseCoinName()) ;
    }

    /**
    * @Description: 获取对应交易对的stock数量（交易币种）
    * @param:   [tradePairEnum]
    * @return:  java.math.BigDecimal
    * @author chenjianjun
    * @date 10/27/19 2:32 PM
    * @version V1.0
    */
    public static BigDecimal stock(TradePairEnum tradePairEnum){
        return accMap.get(tradePairEnum.getTradeCoinName()) ;
    }
}
