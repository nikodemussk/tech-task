package com.jpmorgan.technical_test.v1_0_0.print_sales;

import java.math.BigDecimal;

public record PrintAdjustmentDetail(String operation, String productType, BigDecimal value) {

}
