package com.example.taobao20;

import java.math.BigDecimal;

public class KeepTwoDecimals {
    public Double solve(Double price){
        BigDecimal b = new BigDecimal(price);
        //保留2位小数
        Double f1 = b.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }
}
