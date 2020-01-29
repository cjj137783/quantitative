package com.china.chen.quantitativecoretrade.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HuaYiTongResponse {
    private List<DoctorInfo> doctorsList ;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DoctorInfo {
        /**医生姓名*/
        String doctorName ;
        /**头衔*/
        String title;
        /**专业*/
        String profession;
        /**是否有号*/
        String isAvailable;
    }
}
