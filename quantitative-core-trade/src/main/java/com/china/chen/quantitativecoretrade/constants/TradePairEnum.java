package com.china.chen.quantitativecoretrade.constants;

/**
* @class_name: TradePairEnum
* @Description: TODO
* @author chenjianjun
* @date 10/22/19 9:19 AM
* @version V1.0
*/
public enum TradePairEnum {
    HT_USDT("htusdt", "买入"),
    BTC_USDT("btcusdt", "买入"),
    ETH_USDT("ethsdt", "买入"),
    XRP_USDT("xrpusdt", "买入"),
    EOS_USDT("eosusdt", "买入"),

    ;

    private String key;
    private String value;

    TradePairEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static TradePairEnum getEnumBykey(String key){
        for (TradePairEnum e: TradePairEnum.values()) {
            if (e.getKey().equals(key)) {
                return e;
            }
        }
        return null;
    }
}
