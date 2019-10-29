package com.china.chen.quantitativecoretrade.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeStatisticsInfo {
    /**出售总成交额*/
    private BigDecimal sellSumAmount ;
    /**购买总成交额*/
    private BigDecimal buySumAmount ;
    /**出售总成交数量*/
    private BigDecimal sellSumQuantity ;
    /**购买总成交数量*/
    private BigDecimal buySumQuantity ;
    /**均价*/
    private BigDecimal averagePrice ;
}
