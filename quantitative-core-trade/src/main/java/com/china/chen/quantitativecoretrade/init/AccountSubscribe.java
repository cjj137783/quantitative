package com.china.chen.quantitativecoretrade.init;

import com.china.chen.quantitativecoretrade.constants.ApiKeyEnum;
import com.china.chen.quantitativecoretrade.constants.CoreConstants;
import com.china.chen.quantitativecoretrade.listener.Acc;
import com.huobi.client.SubscriptionClient;
import com.huobi.client.SubscriptionOptions;
import com.huobi.client.model.enums.BalanceMode;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.DES;


/**
* @class_name: AccountSubscribe
* @Description: 订阅资产变动信息
* @author chenjianjun
* @date 10/27/19 2:22 PM
* @version V1.0
*/
public class AccountSubscribe {

    private static SubscriptionClient subscriptionClient ;

   public static void subscribeAccount(String secretSeed){

       if(subscriptionClient == null){
           SubscriptionOptions options = new SubscriptionOptions();
           options.setUri(CoreConstants.URL);

           DES des = new DES(Mode.CTS, Padding.PKCS5Padding, secretSeed.getBytes(), ApiKeyEnum.IV.getKey().getBytes());
           String realApiKey = des.decryptStr(ApiKeyEnum.API_KEY.getKey()) ;
           String realSecretKey = des.decryptStr(ApiKeyEnum.SECRET_KEY.getKey()) ;

           subscriptionClient = SubscriptionClient.create(
                   realApiKey, realSecretKey, options);


           subscriptionClient.subscribeAccountEvent(BalanceMode.AVAILABLE, new Acc());
       }

   }
}
