package com.china.chen.quantitativecoretrade.constants;

/**
* @class_name: TradePairEnum
* @Description: TODO
* @author chenjianjun
* @date 10/22/19 9:19 AM
* @version V1.0
*/
public enum TradePairEnum {
    HT_USDT("htusdt", "ht","usdt"),
    BTC_USDT("btcusdt", "btc","usdt"),
    ETH_USDT("ethsdt", "eth","usdt"),
    XRP_USDT("xrpusdt", "xrp","usdt"),
    EOS_USDT("eosusdt", "eos","usdt"),

    ;

    private String key;
    private String tradeCoinName;
    private String baseCoinName;

    TradePairEnum(String key, String tradeCoinName,String baseCoinName) {
        this.key = key;
        this.tradeCoinName = tradeCoinName;
        this.baseCoinName = baseCoinName ;
    }


    public String getKey() {
        return key;
    }

    public String getBaseCoinName() {
        return baseCoinName;
    }

    public String getTradeCoinName(){return  tradeCoinName ;}

    public static TradePairEnum getEnumBykey(String key){
        for (TradePairEnum e: TradePairEnum.values()) {
            if (e.getKey().equals(key)) {
                return e;
            }
        }
        return null;
    }
}
