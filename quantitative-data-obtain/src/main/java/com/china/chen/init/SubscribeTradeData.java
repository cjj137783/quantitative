package com.china.chen.init;

import com.huobi.client.SubscriptionClient;
import com.huobi.client.model.Trade;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SubscribeTradeData implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        SubscriptionClient subscriptionClient = SubscriptionClient.create();
        subscriptionClient.subscribeTradeEvent("htusdt", tradeEvent -> {

            System.out.println("------------Subscribe Trade Event-------------");
            tradeEvent.getTradeList().forEach(trade -> {
                System.out.println("id:" + trade.getTradeId() + " price:" + trade.getPrice() + " amount:" + trade.getAmount() + " direction:" + trade.getDirection());
            });
        });
    }
}
