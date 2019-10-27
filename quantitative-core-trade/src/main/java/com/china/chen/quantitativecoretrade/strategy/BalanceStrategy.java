package com.china.chen.quantitativecoretrade.strategy;

import com.china.chen.quantitativecoretrade.constants.CoreConstants;
import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.china.chen.quantitativecoretrade.listener.Acc;
import com.china.chen.quantitativecoretrade.listener.Ticker;
import com.china.chen.quantitativecoretrade.listener.Trade;
import com.china.chen.quantitativecoretrade.utils.DingTalkUtils;
import com.huobi.client.model.enums.AccountType;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    /****************************策略参数*************************/
    /**阀值*/
    private static final String PARAM_THRESHOLD = "threshold" ;
    /**最小交易金额*/
    private static final String PARAM_MIN_BASE_AMOUNT = "minBaseAmount" ;
    /**最小交易币种数量*/
    private static final String PARAM_MIN_TRADE_AMOUNT = "minTradeAmount" ;


    /**
    * @Description: TODO
    * @param:   [
     * tradePair
     * secretSeed
     * strategyParam 策略参数
     * ]
    * @return:  void
    * @author chenjianjun
    * @date 10/26/19 11:32 PM
    * @version V1.0
    */
    @Override
    public void start(TradePairEnum tradePair
            , Map<String,Object> strategyParam) throws Exception {

        /**取消所有未成交挂单*/
        Trade.cancelOpenOrders(tradePair,AccountType.SPOT);

        /**最小交易stock*/
        BigDecimal MinStock = new BigDecimal(strategyParam.get(PARAM_MIN_TRADE_AMOUNT)+"") ;
        /**最小交易金额*/
        BigDecimal minAmount = new BigDecimal(strategyParam.get(PARAM_MIN_BASE_AMOUNT)+"") ;

        /*买卖超额*/
        BigDecimal spread = Ticker.sell(tradePair).subtract(Ticker.buy(tradePair)) ;
        /*账户余额与当前持仓价值的差值的 0.5倍*/
        BigDecimal diffAsset = (Acc.balance(tradePair).subtract(Acc.stock(tradePair).multiply(Ticker.sell(tradePair))))
                .divide(new BigDecimal(2)) ;

        BigDecimal ratio = diffAsset.divide(Acc.balance(tradePair),6,RoundingMode.DOWN) ;

        /*如果 ratio的绝对值小于指定阈值*/
        if (ratio.abs().floatValue() < Float.parseFloat(strategyParam.get(PARAM_THRESHOLD)+"")) {
            return ;
        }

        /*如果 ratio大于 0*/
        if (ratio.compareTo(BigDecimal.ZERO) > 0) {
            /*计算下单价格*/
            BigDecimal buyPrice = Ticker.sell(tradePair).add(spread).setScale(CoreConstants.BASE_COIN_PRECISION,RoundingMode.DOWN);
            /*计算下单量*/
            BigDecimal buyAmount = diffAsset.divide(buyPrice,CoreConstants.QUOTE_COIN_PRECISION,RoundingMode.DOWN) ;
            /*如果下单量小于最小交易量*/
            if (buyAmount.compareTo(MinStock) < 0 || diffAsset.compareTo(minAmount) < 0) {
                return ;
            }
            /*买入下单*/
            Trade.buy(tradePair,buyPrice, buyAmount);
            /*【 balance + (stock) * sellPrice 】*/
            BigDecimal nowAmount = Acc.balance(tradePair).add(Acc.stock(tradePair).multiply(Ticker.sell(tradePair))) ;
            DingTalkUtils.sendMessage("当前账户余额【", nowAmount, "】", "购买BTC-> 单价【", buyPrice, "】，数量【", buyAmount, "】");
        } else {
            /*计算下单价格*/
            BigDecimal sellPrice = Ticker.buy(tradePair).subtract(spread)
                    .setScale(CoreConstants.BASE_COIN_PRECISION,RoundingMode.DOWN);
            /*计算下单量*/
            BigDecimal sellAmount = diffAsset.negate().divide(sellPrice,CoreConstants.QUOTE_COIN_PRECISION,RoundingMode.DOWN);

            /*如果下单量小于最小交易量*/
            if (sellAmount.compareTo(MinStock) < 0  || diffAsset.negate().compareTo(minAmount) < 0) {
                return ;
            }
            /*卖出下单*/
            Trade.sell(tradePair,sellPrice, sellAmount);
            /*当前账户余额 【 balance + stock * sellProce 】*/
            BigDecimal nowAmount = Acc.balance(tradePair).add(Acc.stock(tradePair).multiply(Ticker.sell(tradePair))) ;
            DingTalkUtils.sendMessage("当前账户余额【",nowAmount,"】","出售BTC-> 单价【",sellPrice,"】，数量【",sellAmount,"】") ;
        }
        return ;
    }

    @Override
    public Boolean continueRunning() {
        return runningFlag;
    }



    @Override
    public void stopRunning() {
        runningFlag = false ;
    }

    @Override
    public void startRunning() {
        runningFlag = true ;
    }
}
