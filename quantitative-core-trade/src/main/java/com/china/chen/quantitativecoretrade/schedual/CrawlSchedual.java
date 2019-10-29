package com.china.chen.quantitativecoretrade.schedual;

import com.china.chen.quantitativecoretrade.schedual.CrawlProcessor.BiShijieNewsProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class CrawlSchedual {
    @Autowired
    private BiShijieNewsProcessor biShijieNewsProcessor ;

    @Scheduled(cron = "0/30 * * * * ?")
    public void crawlNotice()  {
        biShijieNewsProcessor.start();
    }
}
