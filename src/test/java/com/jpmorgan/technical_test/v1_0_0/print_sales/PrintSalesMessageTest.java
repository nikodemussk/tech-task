package com.jpmorgan.technical_test.v1_0_0.print_sales;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class PrintSalesMessageTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;


  @InjectMocks
  private PrintSalesMessage printSalesMessage = new PrintSalesMessage();

  @BeforeEach
  void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  void testShouldPrintValidReportWhenGivenValidPrintSalesDetailObject() {
    //given
    PrintSalesDetail printSalesDetail = new PrintSalesDetail("product", "1234");
    List<PrintSalesDetail> printSalesDetails = new ArrayList<>();
    printSalesDetails.add(printSalesDetail);

    //when
    printSalesMessage.printReport(printSalesDetails);

    //then
    assertEquals("Total Sales:\r\nproduct: 1234\r\n", outContent.toString());
  }

  @Test
  void testShouldPrintValidAdjustmentWhenGivenValidPrintAdjustmentDetailObject() {
    //given
    PrintAdjustmentDetail printAdjustmentDetail = new PrintAdjustmentDetail("add", "product", new BigDecimal(1234));
    List<PrintAdjustmentDetail> printAdjustmentDetails = new ArrayList<>();
    printAdjustmentDetails.add(printAdjustmentDetail);

    //when
    printSalesMessage.printAdjustment(printAdjustmentDetails);

    //then
    assertEquals("Adjustment Sales:\r\nadd product 1234\r\n", outContent.toString());
  }

  @Test
  void testShouldNotPrintAdjustmentWhenGivenNullPrintAdjustmentDetailObject() {
    //given & when
    printSalesMessage.printAdjustment(null);

    //then
    assertEquals("No Adjustment Found\r\n", outContent.toString());
  }

  @Test
  void testShouldNotPrintReportWhenGivenNullPrintSalesDetailObject() {
    //given & when
    printSalesMessage.printReport(null);

    //then
    assertEquals("No Sales Found\r\n", outContent.toString());
  }
}