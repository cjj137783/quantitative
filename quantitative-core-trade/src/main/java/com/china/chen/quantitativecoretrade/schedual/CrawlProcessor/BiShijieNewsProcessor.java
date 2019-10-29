package com.china.chen.quantitativecoretrade.schedual.CrawlProcessor;

import com.china.chen.quantitativecoretrade.constants.CoreConstants;
import com.china.chen.quantitativecoretrade.utils.DingTalkUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * Created by cjj13 on 2017/12/3.
 */
@Component
@TargetUrl(CoreConstants.BI_SHI_JIE_URL)
public class BiShijieNewsProcessor implements PageProcessor {

	private static final Map<String,String> noticeMap = new HashMap() ;

	private static Boolean isStartedFlag = true ;

	public BiShijieNewsProcessor(){}


    private Site site = Site.me()
            .setRetryTimes(3).setCharset("UTF-8")
            .setSleepTime(100)
            .addHeader("Cache-Control","max-age=0")
            .addHeader("Accept","application/json, text/plain, */*")
            .addHeader("Accept-Encoding","gzip, deflate, sdch")
            .addHeader("Accept-Language","zh-CN")
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0")
            .addHeader("Connection","keep-alive");


    public void start(){
        Spider.create(new BiShijieNewsProcessor()).addUrl(CoreConstants.BI_SHI_JIE_URL).thread(1).run();
    }


    @Override
    public void process(Page page) {

    	Date nowDate = new Date() ;
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd") ;
		String nowDateStr = sdf.format(nowDate) ;

		String newsDateClass = "live livetop " + nowDateStr ;

		List<Selectable> nodes = page.getHtml().xpath("//section[@class=content]/div[@class=news]/div[@class=kuaixun_list]/div[@class='"+newsDateClass+"']/ul").nodes() ;
		if(nodes != null && nodes.size() > 0){
			for(Selectable node : nodes){
				Selectable liColorNode  = node.xpath("//li[@class='lh32 orange']") ;
				Boolean importantFlag = liColorNode.all().get(0).contains("color:#ff0000") ;

				String notice = node.xpath("//li/h2/a/text()").toString() ;
				String url = CoreConstants.BI_SHI_JIE_URL+node.xpath("//a/@href").get() ;
				/**如果不是第一次启动爬取并且缓存中也没有该新闻*/
				if(!isStartedFlag && noticeMap.get(notice) == null){
					if(!importantFlag){
						DingTalkUtils.sendMessage("【"+notice+"】" + url);
					}else{
						DingTalkUtils.sendMessage("【⭐️"+notice+"⭐️】" + url);
					}
				}
				noticeMap.put(notice,url) ;
			}
			isStartedFlag = false ;
		}

    }
	@Override
    public Site getSite() {
        return site;
    }


}
