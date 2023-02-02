package com.jpmorgan.technical_test.v1_0_0.print_sales;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class PrintSalesMessage implements PrintMessage {

  @Override
  public void printReport(List<PrintSalesDetail> sales) {
    if (Objects.isNull(sales)) {
      System.out.println("No Sales Found");
      return;
    }
    System.out.println("Total Sales:");
    sales.forEach(salesData ->
        System.out.println(salesData.product() + ": " + salesData.totalValue()));
  }

  @Override
  public void printAdjustment(List<PrintAdjustmentDetail> adjustmentDetails) {
    if (Objects.isNull(adjustmentDetails)) {
      System.out.println("No Adjustment Found");
      return;
    }
    System.out.println("Adjustment Sales:");
    adjustmentDetails.forEach(adjustmentDetail ->
        System.out.println(adjustmentDetail.operation() + " " + adjustmentDetail.productType() + " " + adjustmentDetail.value()));

  }
}
