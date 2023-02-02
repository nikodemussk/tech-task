package com.jpmorgan.technical_test.v1_0_0.sales_message_processing;

import com.jpmorgan.technical_test.v1_0_0.print_sales.PrintMessage;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SalesMessageProcessingTest {

  @InjectMocks
  private SalesMessageProcessing salesMessageProcessing;

  @Mock
  private PrintMessage printMessage;

  @Test
  void testShouldReturnSuccessWhenGivenValidType1Data() {
    //given
    SalesMessageDetail salesMessageDetail = new SalesMessageDetail("product", BigDecimal.valueOf(1234), null, "Type 1", null);
    //when
    HashMap<String, SalesRecord> salesData = salesMessageProcessing.processMessage(salesMessageDetail);
    //then
    Assertions.assertEquals(BigDecimal.valueOf(1234), salesData.get("product").totalPrice());
  }

  @Test
  void testShouldReturnSuccessWhenGivenValidType2Data() {
    //given
    SalesMessageDetail salesMessageDetail = new SalesMessageDetail("product", BigDecimal.valueOf(1234), new BigInteger("2"), "Type 2", null);
    //when
    HashMap<String, SalesRecord> salesData = salesMessageProcessing.processMessage(salesMessageDetail);
    //then
    Assertions.assertEquals(BigDecimal.valueOf(2468), salesData.get("product").totalPrice());
  }

  @Test
  void testShouldReturnSuccessWhenGivenValidType3DataOperationAdd() {
    //given
    SalesMessageDetail addData = new SalesMessageDetail("product", BigDecimal.valueOf(1234), new BigInteger("2"), "Type 2", null);
    SalesMessageDetail salesMessageDetail = new SalesMessageDetail("product", BigDecimal.valueOf(2), null, "Type 3", "add");
    //when
    salesMessageProcessing.processMessage(addData);
    HashMap<String, SalesRecord> salesData = salesMessageProcessing.processMessage(salesMessageDetail);
    //then
    Assertions.assertEquals(BigDecimal.valueOf(2472), salesData.get("product").totalPrice());
  }

  @Test
  void testShouldReturnSuccessWhenGivenValidType3DataOperationSubtract() {
    //given
    SalesMessageDetail addData = new SalesMessageDetail("product", BigDecimal.valueOf(1234), new BigInteger("2"), "Type 2", null);
    SalesMessageDetail salesMessageDetail = new SalesMessageDetail("product", BigDecimal.valueOf(2), null, "Type 3", "subtract");
    //when
    salesMessageProcessing.processMessage(addData);
    HashMap<String, SalesRecord> salesData = salesMessageProcessing.processMessage(salesMessageDetail);
    //then
    Assertions.assertEquals(BigDecimal.valueOf(2464), salesData.get("product").totalPrice());
  }

  @Test
  void testShouldReturnSuccessWhenGivenValidType3DataOperationMultiply() {
    //given
    SalesMessageDetail addData = new SalesMessageDetail("product", BigDecimal.valueOf(1234), new BigInteger("2"), "Type 2", null);
    SalesMessageDetail salesMessageDetail = new SalesMessageDetail("product", BigDecimal.valueOf(2), null, "Type 3", "multiply");
    //when
    salesMessageProcessing.processMessage(addData);
    HashMap<String, SalesRecord> salesData = salesMessageProcessing.processMessage(salesMessageDetail);
    //then
    Assertions.assertEquals(BigDecimal.valueOf(9872), salesData.get("product").totalPrice());
  }

  @Test
  void testShouldTriggerPrintReportWhenSalesCounterReach10() {
    //given
    SalesMessageDetail addData = new SalesMessageDetail("product", BigDecimal.valueOf(1234), new BigInteger("2"), "Type 2", null);

    //when
    for (int i = 0; i < 21; i++)
      salesMessageProcessing.processMessage(addData);

    //then
    Mockito.verify(printMessage, Mockito.times(2)).printReport(Mockito.any());
  }

//  @Test
//  @Order(8)
//  void testShouldTriggerPrintAdjustmentWhenSalesCounterReach50() {
//    //given
//    SalesMessageDetail addData = new SalesMessageDetail("product", BigDecimal.valueOf(1234), new BigInteger("2"), "Type 2", null);
//
//    //when
//    for (int i = 0; i < 50; i++)
//      salesMessageProcessing.processMessage(addData);
//
//    //then
//    Mockito.verify(printMessage, Mockito.times(1)).printReport(Mockito.any());
//  }
}