package com.jpmorgan.technical_test.v1_0_0.sales_message_adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmorgan.technical_test.v1_0_0.sales_message_processing.SalesMessageDetail;
import com.jpmorgan.technical_test.v1_0_0.sales_message_processing.SalesMessageProcess;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Component
public class SalesMessageAdapter {

  public SalesMessageAdapter(SalesMessageProcess salesMessageProcess) {
    this.salesMessageProcess = salesMessageProcess;
  }

  private final Logger LOGGER = Logger.getLogger(SalesMessageAdapter.class.getName());;

  private final SalesMessageProcess salesMessageProcess;

  public void createSalesMessage(String message) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();

      Optional.of(objectMapper.readValue(message, SalesMessageResponse.class))
          .map(salesMessageResponse -> new SalesMessageDetail(
              salesMessageResponse.productType(),
              salesMessageResponse.price(),
              salesMessageResponse.quantity(),
              salesMessageResponse.messageType(),
              salesMessageResponse.operation()))
          .map(salesMessageProcess::processMessage);
    } catch (Exception e) {
      LOGGER.severe(e.getMessage());
    }
  }
}
