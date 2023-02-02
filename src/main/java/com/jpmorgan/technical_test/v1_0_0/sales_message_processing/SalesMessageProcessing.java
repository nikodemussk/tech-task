package com.jpmorgan.technical_test.v1_0_0.sales_message_processing;

import com.jpmorgan.technical_test.v1_0_0.print_sales.PrintAdjustmentDetail;
import com.jpmorgan.technical_test.v1_0_0.print_sales.PrintMessage;
import com.jpmorgan.technical_test.v1_0_0.print_sales.PrintSalesDetail;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Component
public class SalesMessageProcessing implements SalesMessageProcess {

  public static final String ADD = "add";
  public static final String SUBTRACT = "subtract";
  public static final String MULTIPLY = "multiply";
  private final Logger LOGGER = Logger.getLogger(SalesMessageProcessing.class.getName());
  private int salesCounter;

  private PrintMessage printMessage;

  private HashMap<String, SalesRecord> salesData = new HashMap<>();
  private Stack<SalesMessageDetail> adjustmentData = new Stack<>();

  public SalesMessageProcessing(PrintMessage printMessage) {
    this.printMessage = printMessage;
  }

  public HashMap<String, SalesRecord> processMessage(SalesMessageDetail salesMessageDetails) {
    salesCounter++;
    switch (salesMessageDetails.messageType()) {
      case "Type 1": insertSingleItem(salesMessageDetails); break;
      case "Type 2": insertMultipleItem(salesMessageDetails); break;
      case "Type 3": modifySales(salesMessageDetails); break;
      default: LOGGER.severe("Unknown Type"); break;
    }

    if (salesCounter % 10 == 0) {
      List<PrintSalesDetail> printSalesDetail = new ArrayList<>();
      salesData.forEach((productName,value) -> printSalesDetail.add(new PrintSalesDetail(productName, value.totalPrice().toPlainString())));
      printMessage.printReport(printSalesDetail);
    }

    if (salesCounter == 50) {
      List<PrintAdjustmentDetail> printAdjustmentDetails = new ArrayList<>();
      adjustmentData.forEach(adjustment -> printAdjustmentDetails.add(
          new PrintAdjustmentDetail(
            adjustment.operation(),
            adjustment.productType(),
            adjustment.price())));
      printMessage.printAdjustment(printAdjustmentDetails);
      LOGGER.info("Pause listener and stop accepting new messages");
      System.exit(0);
    }

    return salesData;
  }

  private void insertSingleItem(SalesMessageDetail salesMessage) {
    if (salesData.containsKey(salesMessage.productType())) {
      SalesRecord salesRecord = salesData.get(salesMessage.productType());
      salesData.put(salesMessage.productType(), new SalesRecord(salesRecord.totalPrice().add(salesMessage.price()), salesRecord.totalSales()+1));
      return;
    }

    salesData.put(salesMessage.productType(), new SalesRecord(salesMessage.price(), 1));
  }


  private void insertMultipleItem(SalesMessageDetail salesMessage) {

    if (salesData.containsKey(salesMessage.productType())) {
      SalesRecord salesRecord = salesData.get(salesMessage.productType());
      salesData.put(
          salesMessage.productType(),
          new SalesRecord(salesRecord.totalPrice().add(salesMessage.price()
              .multiply(new BigDecimal(salesMessage.quantity()))),
              salesRecord.totalSales() + salesMessage.quantity().intValue()));
      return;
    }

    salesData.put(
        salesMessage.productType(),
        new SalesRecord(salesMessage.price()
            .multiply(new BigDecimal(salesMessage.quantity())),
            salesMessage.quantity().intValue()));
  }

  private void modifySales(SalesMessageDetail salesMessage) {
    if (Objects.isNull(salesData.get(salesMessage.productType())) ||
        !(ADD.equalsIgnoreCase(salesMessage.operation()) ||
        SUBTRACT.equalsIgnoreCase(salesMessage.operation()) ||
        MULTIPLY.equalsIgnoreCase(salesMessage.operation()))
    ) {
      return;
    }

    SalesRecord salesRecord = salesData.get(salesMessage.productType());
    adjustmentData.push(salesMessage);
    BigDecimal totalPrice = salesMessage.price().multiply(BigDecimal.valueOf(salesRecord.totalSales()));

    if (ADD.equalsIgnoreCase(salesMessage.operation())) {
      salesData.put(salesMessage.productType(), new SalesRecord(salesRecord.totalPrice().add(totalPrice), salesRecord.totalSales()));
    } else if (SUBTRACT.equalsIgnoreCase(salesMessage.operation())) {
      salesData.put(salesMessage.productType(), new SalesRecord(salesRecord.totalPrice().subtract(totalPrice), salesRecord.totalSales()));
    } else if (MULTIPLY.equalsIgnoreCase(salesMessage.operation())) {
      salesData.put(salesMessage.productType(), new SalesRecord(salesRecord.totalPrice().multiply(totalPrice), salesRecord.totalSales()));
    }
  }
}
