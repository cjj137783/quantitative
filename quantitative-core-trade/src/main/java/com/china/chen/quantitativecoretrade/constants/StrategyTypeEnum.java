package com.china.chen.quantitativecoretrade.constants;

/**
* @class_name: TradePairEnum
* @Description: TODO
* @author chenjianjun
* @date 10/22/19 9:19 AM
* @version V1.0
*/
public enum StrategyTypeEnum {
    BALANCE_STRATEGY("balance", "com.china.chen.quantitativecoretrade.strategy.BalanceStrategy"),

    ;

    private String key;
    private String value;

    StrategyTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static StrategyTypeEnum getEnumBykey(String key){
        for (StrategyTypeEnum e: StrategyTypeEnum.values()) {
            if (e.getKey().equals(key)) {
                return e;
            }
        }
        return null;
    }
}
