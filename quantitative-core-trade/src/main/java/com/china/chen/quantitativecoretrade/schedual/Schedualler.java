package com.china.chen.quantitativecoretrade.schedual;

import com.china.chen.quantitativecoretrade.constants.TradePairEnum;
import com.china.chen.quantitativecoretrade.dto.TradeStatisticsInfo;
import com.china.chen.quantitativecoretrade.listener.TradeEventListerner;
import com.china.chen.quantitativecoretrade.schedual.CrawlProcessor.BiShijieNewsProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class Schedualler {
    @Autowired
    private BiShijieNewsProcessor biShijieNewsProcessor ;

    @Scheduled(cron = "0/30 * * * * ?")
    public void crawlNotice()  {
        /**启动爬虫*/
        biShijieNewsProcessor.start();
    }

}
