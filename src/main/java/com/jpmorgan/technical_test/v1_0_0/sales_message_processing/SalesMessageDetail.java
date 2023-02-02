package com.jpmorgan.technical_test.v1_0_0.sales_message_processing;

import java.math.BigDecimal;
import java.math.BigInteger;

public record SalesMessageDetail(
    String productType,
    BigDecimal price,
    BigInteger quantity,
    String messageType,
    String operation) {
}
