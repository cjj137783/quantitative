package com.china.chen.quantitativecoretrade.constants;

/**
* @class_name: TradePairEnum
* @Description: TODO
* @author chenjianjun
* @date 10/22/19 9:19 AM
* @version V1.0
*/
public enum CoinEnum {
    BTC("btc", "BTC"),
    ETH("eth", "ETH"),
    USDT("usdt", "USDT"),
    XRP("xrp", "XRP"),
    HT("ht", "HT"),

    ;

    private String key;
    private String value;

    CoinEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static CoinEnum getEnumBykey(String key){
        for (CoinEnum e: CoinEnum.values()) {
            if (e.getKey().equals(key)) {
                return e;
            }
        }
        return null;
    }
}
