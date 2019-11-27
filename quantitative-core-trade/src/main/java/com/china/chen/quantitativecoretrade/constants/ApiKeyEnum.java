package com.china.chen.quantitativecoretrade.constants;

/**
* @class_name: ApiKeyEnum
* @Description: 秘钥相关
* @author chenjianjun
* @date 10/26/19 9:53 PM
* @version V1.0
*/
public enum ApiKeyEnum {
    API_KEY("ed6a32209bf56260d92caec991c03f819f5eeccf75f27f6e9349fef259083fad9039803809f3c340", "api key"),
    SECRET_KEY("75a38558f281d9cd46bad4943f2be2b2cbbee45f7493aab422eee69b63d17abd47796fe51bfdd960", "secret key"),
    IV("22910321", "加密向量"),

    ;

    private String key;
    private String value;

    ApiKeyEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static ApiKeyEnum getEnumBykey(String key){
        for (ApiKeyEnum e: ApiKeyEnum.values()) {
            if (e.getKey().equals(key)) {
                return e;
            }
        }
        return null;
    }
}
