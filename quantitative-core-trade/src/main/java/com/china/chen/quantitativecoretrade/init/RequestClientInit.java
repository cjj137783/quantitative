package com.china.chen.quantitativecoretrade.init;

import com.china.chen.quantitativecoretrade.constants.ApiKeyEnum;
import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.BatchCancelResult;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.request.CancelOpenOrderRequest;
import com.huobi.client.model.request.NewOrderRequest;

import org.springframework.stereotype.Service;

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
@Service
public class RequestClientInit {

    private static SyncRequestClient syncClient  ;


    public RequestClientInit(){}

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


    public static SyncRequestClient getClient(){
        return syncClient ;
    }
}
