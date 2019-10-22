package com.china.chen.quantitativecoretrade.constants;

/**
* @class_name: TradePairEnum
* @Description: TODO
* @author chenjianjun
* @date 10/22/19 9:19 AM
* @version V1.0
*/
public enum KLineInteverEnum {
    MINUTE_1("1m", "1分钟"),
    MINUTE_5("5m", "5分钟"),
    MINUTE_15("15m", "15分钟"),
    MINUTE_30("30m", "30分钟"),
    MONTH_1("1M", "1月"),
    MINUTE_60("60m", "60分钟"),
    WEEK_1("1w", "1周"),
    YEAR_1("1y", "1年"),

    ;

    private String key;
    private String value;

    KLineInteverEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static KLineInteverEnum getEnumBykey(String key){
        for (KLineInteverEnum e: KLineInteverEnum.values()) {
            if (e.getKey().equals(key)) {
                return e;
            }
        }
        return null;
    }
}
