package com.china.chen.quantitativecoretrade.listener;

import com.china.chen.quantitativecoretrade.constants.ApiKeyEnum;
import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.BatchCancelResult;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.request.CancelOpenOrderRequest;
import com.huobi.client.model.request.NewOrderRequest;

import java.math.BigDecimal;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.DES;
import lombok.extern.slf4j.Slf4j;

/**
* @class_name: TradeHelper
* @Description: 交易相关帮助类
* @author chenjianjun
* @date 10/26/19 9:02 PM
* @version V1.0
*/
@Slf4j
public class Trade {

    private static final Integer SLEEP_INTERVAL = 1000 ;

    private static SyncRequestClient syncClient  ;


    public Trade(){}

    /**
     * @Description: TODO
     * @param:   [secretSeed apiKey 和 secretKey 的解密秘钥]
     * @return:
     * @author chenjianjun
     * @date
     * @version V1.0
     */
    public void init(String secretSeed){

        if(syncClient == null){
            DES des = new DES(Mode.CTS, Padding.PKCS5Padding, secretSeed.getBytes(), ApiKeyEnum.IV.getKey().getBytes());
            String realSecretKey = des.decryptStr(ApiKeyEnum.SECRET_KEY.getKey()) ;
            String realApiKey = des.decryptStr(ApiKeyEnum.API_KEY.getKey()) ;
            syncClient = SyncRequestClient.create(realApiKey,realSecretKey);
        }
    }


    /**
    * @Description: 取消尚未成交的挂单
    * @param:   [tradePair 交易对信息, accountType 账户类型]
    * @return:  void
    * @author chenjianjun
    * @date 10/26/19 9:28 PM
    * @version V1.0
    */
    public static void cancelOpenOrders(TradePairEnum tradePair, AccountType accountType) throws InterruptedException {
         /**如果存在着未取消成功的订单，则重复执行取消*/
        while(true){
            try{
                CancelOpenOrderRequest request = new CancelOpenOrderRequest(tradePair.getKey(), accountType);
                BatchCancelResult result = syncClient.cancelOpenOrders(request);
                if(result.getFailedCount() == 0){
                    return;
                }else{
                    log.error("存在未取消成功的挂单，1s后将继续取消");
                }
                Thread.sleep(SLEEP_INTERVAL);
            }catch (Exception e){
                log.error("取消进行中订单异常",e);
            }
        }
    }


    public static void buy(TradePairEnum tradePair,BigDecimal price,BigDecimal amount){
        NewOrderRequest request = new NewOrderRequest(
                tradePair.getKey(), AccountType.SPOT, OrderType.BUY_LIMIT, amount, price);
        syncClient.createOrder(request);
    }


    public static void sell(TradePairEnum tradePair,BigDecimal price,BigDecimal amount){
        NewOrderRequest request = new NewOrderRequest(
                tradePair.getKey(), AccountType.SPOT, OrderType.SELL_LIMIT, amount, price);
        syncClient.createOrder(request);
    }
}
