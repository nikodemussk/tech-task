package com.jpmorgan.technical_test.v1_0_0.sales_message_adapter;

import java.math.BigDecimal;
import java.math.BigInteger;

public record SalesMessageResponse(String productType,
                                   BigDecimal price,
                                   BigInteger quantity,
                                   String messageType,
                                   String operation) {

  public SalesMessageResponse(String productType, BigDecimal price, BigInteger quantity) {
    this(productType, price, quantity, null, null);

  }
}
