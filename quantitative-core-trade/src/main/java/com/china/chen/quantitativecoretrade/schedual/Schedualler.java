package com.china.chen.quantitativecoretrade.schedual;


import com.china.chen.quantitativecoretrade.schedual.CrawlProcessor.BiShijieNewsProcessor;
import com.china.chen.quantitativecoretrade.schedual.CrawlProcessor.HuaYiTongProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class Schedualler {
    @Autowired
    private BiShijieNewsProcessor biShijieNewsProcessor ;
    @Autowired
    private HuaYiTongProcessor huaYiTongProcessor ;

    @Scheduled(cron = "0/30 * * * * ?")
    public void crawlNotice()  {
        /**启动爬虫*/
        biShijieNewsProcessor.start();

        huaYiTongProcessor.start();

    }
}
