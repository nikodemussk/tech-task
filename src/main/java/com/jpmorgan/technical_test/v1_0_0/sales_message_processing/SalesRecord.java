package com.jpmorgan.technical_test.v1_0_0.sales_message_processing;

import java.math.BigDecimal;

public record SalesRecord(BigDecimal totalPrice, Integer totalSales) {

}
