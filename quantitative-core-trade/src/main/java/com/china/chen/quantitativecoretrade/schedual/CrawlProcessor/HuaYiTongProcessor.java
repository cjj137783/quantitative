package com.china.chen.quantitativecoretrade.schedual.CrawlProcessor;

import com.alibaba.fastjson.JSON;
import com.china.chen.quantitativecoretrade.dto.HuaYiTongResponse;
import com.china.chen.quantitativecoretrade.utils.DingTalkUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

/**
 * Created by cjj13 on 2017/12/3.
 */
@Component
@Slf4j
public class HuaYiTongProcessor implements PageProcessor {

	private static final Map<String,String> noticeMap = new HashMap() ;

	private static Boolean isStartedFlag = true ;

	public HuaYiTongProcessor(){}


    private Site site = Site.me()
            .setRetryTimes(3).setCharset("UTF-8")
            .setSleepTime(1000)
            .addHeader("token","5286260_token_06f4782da86d535acf552a7f4f0c0689_token_15756269765")
            .addHeader("UUID","4DB04074-CCD5-430F-8939-852F089D784B")
            .addHeader("User-Agent","HXGYAPP/1 CFNetwork/1120 Darwin/19.0.0")
            .addHeader("Client-Version","5.5.9")
            .addHeader("Accept-Language","zh-cn")
            .addHeader("Content-Type","application/json") ;




    public void start(){
    	Spider spider = Spider.create(new HuaYiTongProcessor()) ;
		Request request = new Request("https://hytapi.cd120.com/hxgyAppV2/appointment/deptDoctorsList") ;
		request.setMethod(HttpConstant.Method.POST);

		request.setRequestBody(HttpRequestBody.json("{\"search\":\"\",\"deptCode\":\"3000-JH\",\"hisCode\":\"HID0101\",\"searchType\":\"\",\"deptCodeList\":[],\"hospitalArea\":\"HID0101\",\"serviceDate\":\"\",\"todayScheduleStatus\":\"0\",\"doctorCodeList\":[]}","utf-8"));
		spider.addRequest(request) ;

		spider.thread(1).run();
    }


    @Override
    public void process(Page page) {

		String doctorInfo = page.getRawText() ;
		log.info(doctorInfo);
		HuaYiTongResponse responseInfo = JSON.parseObject(doctorInfo,HuaYiTongResponse.class) ;
		if(responseInfo != null && responseInfo.getDoctorsList() != null && responseInfo.getDoctorsList().size() > 0){
			for(HuaYiTongResponse.DoctorInfo info : responseInfo.getDoctorsList()){
				/**说明有号*/
				if(!StringUtils.equals(info.getIsAvailable(),"0")){
					DingTalkUtils.sendMarkDown("华医通有号通知","医生姓名："+info.getDoctorName(),"专家等级："+info.getTitle(),"专长："+info.getProfession());
				}
			}
		}

    }
	@Override
    public Site getSite() {
        return site;
    }


}
