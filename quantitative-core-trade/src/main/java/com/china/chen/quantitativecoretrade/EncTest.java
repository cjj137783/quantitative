package com.china.chen.quantitativecoretrade;

import com.china.chen.quantitativecoretrade.constants.ApiKeyEnum;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.DES;

public class EncTest {
    public static void main(String args[]){
        DES des = new DES(Mode.CTS, Padding.PKCS5Padding, "".getBytes(), ApiKeyEnum.IV.getKey().getBytes());
        String realApiKeyEnc = des.encryptHex("") ;
        //String realSecretKeyEnc = des.encryptHex() ;

        String realApiKey = des.decryptStr(realApiKeyEnc) ;
        //String realSecretKey = des.decryptStr(ApiKeyEnum.SECRET_KEY.getKey()) ;

        System.out.println(realApiKeyEnc);
        System.out.println(realApiKey);
    }
}
