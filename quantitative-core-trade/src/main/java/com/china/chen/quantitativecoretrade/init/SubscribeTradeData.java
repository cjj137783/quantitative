package com.china.chen.quantitativecoretrade.init;

import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.china.chen.quantitativecoretrade.listener.TradeEventListerner;
import com.huobi.client.SubscriptionClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SubscribeTradeData implements CommandLineRunner {

    @Override
    public void run(String... args)  {

        /**订阅交易数据*/
        SubscriptionClient subscriptionClient = SubscriptionClient.create();
        /**遍历需要接受交易数据的交易对*/
        for(TradePairEnum tradePairEnum : TradePairEnum.values()){
            subscriptionClient.subscribeTradeEvent(tradePairEnum.getKey(),new TradeEventListerner());
        }
    }
}
