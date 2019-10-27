package com.china.chen.quantitativecoretrade.constants;

/**
* @class_name: ApiKeyEnum
* @Description: 秘钥相关
* @author chenjianjun
* @date 10/26/19 9:53 PM
* @version V1.0
*/
public enum ApiKeyEnum {
    API_KEY("778160d56298647127d8274eb2f72b44349182c2280c025ff53147a9c478c7f6b54c", "api key"),
    SECRET_KEY("ba617d3918c30291053a6e1843b8e76e32075755d69d03f242f0c345c2ef0326", "secret key"),
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
