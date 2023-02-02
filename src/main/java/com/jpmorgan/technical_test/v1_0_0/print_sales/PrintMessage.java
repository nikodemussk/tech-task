package com.jpmorgan.technical_test.v1_0_0.print_sales;

import java.util.List;

public interface PrintMessage {

  void printReport(List<PrintSalesDetail> list);
  void printAdjustment(List<PrintAdjustmentDetail> list);
}
