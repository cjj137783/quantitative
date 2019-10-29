package com.china.chen.quantitativecoretrade.utils;

import com.china.chen.quantitativecoretrade.constants.CoreConstants;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;

import java.util.Collections;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DingTalkUtils {

    private static DingTalkClient client ;

    public static void sendMessage(String keyword,Object... messages) {
        try{
            if(client == null){
                client = new DefaultDingTalkClient(CoreConstants.HOOK);
            }
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype("text");
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();

            StringBuffer msg = new StringBuffer() ;
            msg.append(keyword) ;
            for(Object message : messages){
                msg.append(message) ;
            }
            text.setContent(msg.toString());
            request.setText(text);
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setAtMobiles(Collections.singletonList("15881101943"));
            request.setAt(at);

            client.execute(request);
        }catch (Exception e){
            log.error("发送钉钉信息异常",e);
        }
    }
}
