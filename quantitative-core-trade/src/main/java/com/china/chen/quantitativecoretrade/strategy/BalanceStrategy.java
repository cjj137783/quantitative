package com.china.chen.quantitativecoretrade.strategy;

import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.china.chen.quantitativecoretrade.utils.TradeHelper;
import com.huobi.client.model.enums.AccountType;

import java.util.Map;

import lombok.Data;
/**
* @class_name: BalanceStrategy
* @Description:
 * 基于资产和stock的平衡策略，通过使资产和
 * stock在某种程度上保持平衡来稳定获利
* @author chenjianjun
* @date 10/26/19 9:37 PM
* @version V1.0
*/

@Data
public class BalanceStrategy implements BaseStrategy{

    public volatile Boolean runningFlag ;

    @Override
    public void start(TradePairEnum tradePair
            ,String secretSeed
            , Map<String,Object> strategyParam) throws Exception {
        TradeHelper tradeHelper = new TradeHelper(secretSeed) ;

        /**取消所有未成交挂单*/
        tradeHelper.cancelOpenOrders(tradePair,AccountType.SPOT);


    }

    @Override
    public Boolean continueRunning() {
        return runningFlag;
    }

    @Override
    public void stopRunning() {
        runningFlag = false ;
    }
}
