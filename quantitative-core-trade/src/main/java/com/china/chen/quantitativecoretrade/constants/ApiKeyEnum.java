package com.china.chen.quantitativecoretrade.constants;

/**
* @class_name: ApiKeyEnum
* @Description: 秘钥相关
* @author chenjianjun
* @date 10/26/19 9:53 PM
* @version V1.0
*/
public enum ApiKeyEnum {
    API_KEY("1fb7acb2a0b7cc9601f77e37ffe7cfc186af2e23682b6592509974b47cbb0418b54b0c1458bd0f04", "api key"),
    SECRET_KEY("9e40c171c09d71ebe2b9117c11bac0c792720b3eee31229827e8f729b24e68f9417bcd5d6dfba70b", "secret key"),
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
