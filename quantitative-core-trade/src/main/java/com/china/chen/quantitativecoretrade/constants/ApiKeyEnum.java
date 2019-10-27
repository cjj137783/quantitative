package com.china.chen.quantitativecoretrade.constants;

/**
* @class_name: ApiKeyEnum
* @Description: 秘钥相关
* @author chenjianjun
* @date 10/26/19 9:53 PM
* @version V1.0
*/
public enum ApiKeyEnum {
    API_KEY("xxxxxxx", "api key，经过加密的"),
    SECRET_KEY("yyyyyyy", "secret key ，经过加密的"),
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
