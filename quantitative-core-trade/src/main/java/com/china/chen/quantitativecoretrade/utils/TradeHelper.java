package com.china.chen.quantitativecoretrade.utils;

import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.BatchCancelResult;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.request.CancelOpenOrderRequest;

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
public class TradeHelper {

    private static final String IV = "22910321" ;

    private static final Integer SLEEP_INTERVAL = 1000 ;

    private static final String API_KEY = "" ;
    private static final String SECRET_KEY = "" ;

    private SyncRequestClient syncClient  ;


    /**
    * @Description: TODO
    * @param:   [secretSeed apiKey 和 secretKey 的解密秘钥]
    * @return:
    * @author chenjianjun
    * @date
    * @version V1.0
    */
    public TradeHelper(String secretSeed){
        DES des = new DES(Mode.CTS, Padding.PKCS5Padding, secretSeed.getBytes(), IV.getBytes());
        String realApiKey = des.decryptStr(API_KEY) ;
        String realSecretKey = des.decryptStr(SECRET_KEY) ;
        syncClient = SyncRequestClient.create(realApiKey,realSecretKey);
    }

    /**
    * @Description: 取消尚未成交的挂单
    * @param:   [tradePair 交易对信息, accountType 账户类型]
    * @return:  void
    * @author chenjianjun
    * @date 10/26/19 9:28 PM
    * @version V1.0
    */
    public void cancelOpenOrders(TradePairEnum tradePair, AccountType accountType) throws InterruptedException {
        CancelOpenOrderRequest request = new CancelOpenOrderRequest(tradePair.getKey(), AccountType.SPOT);
        BatchCancelResult result = syncClient.cancelOpenOrders(request);
        /**如果存在着未取消成功的订单，则重复执行取消*/
        while(result.getFailedCount() != 0){
            log.error("存在未取消成功的挂单，1s后将继续取消");
            Thread.sleep(SLEEP_INTERVAL);
            result = syncClient.cancelOpenOrders(request);
        }
    }
}
