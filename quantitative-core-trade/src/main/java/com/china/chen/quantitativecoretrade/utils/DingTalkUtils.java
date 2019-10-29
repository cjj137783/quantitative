package com.china.chen.quantitativecoretrade.utils;

import com.china.chen.quantitativecoretrade.constants.CoreConstants;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DingTalkUtils {

    private static DingTalkClient client ;

    public static void sendMessage(Object... messages) {
        try{
            if(client == null){
                client = new DefaultDingTalkClient(CoreConstants.HOOK);
            }
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype("text");
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();

            StringBuffer msg = new StringBuffer() ;
            for(Object message : messages){
                msg.append(message) ;
            }
            text.setContent(msg.toString());
            request.setText(text);

            client.execute(request);
        }catch (Exception e){
            log.error("发送钉钉信息异常",e);
        }
    }


    public static void sendMarkDown(String title,Object... messages) {
        try{
            if(client == null){
                client = new DefaultDingTalkClient(CoreConstants.HOOK);
            }
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype("markdown");
            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            markdown.setTitle(title);

            StringBuffer msg = new StringBuffer() ;
            for(Object message : messages){
                msg.append("####"+message+"####\n") ;
            }
            markdown.setText(msg.toString());
            request.setMarkdown(markdown);
            client.execute(request);
        }catch (Exception e){
            log.error("发送钉钉信息异常",e);
        }
    }
}
