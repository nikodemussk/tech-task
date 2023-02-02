package com.jpmorgan.technical_test.configuration;

import com.jpmorgan.technical_test.v1_0_0.print_sales.PrintMessage;
import com.jpmorgan.technical_test.v1_0_0.print_sales.PrintSalesMessage;
import com.jpmorgan.technical_test.v1_0_0.sales_message_adapter.MQListenerClient;
import com.jpmorgan.technical_test.v1_0_0.sales_message_adapter.SalesMessageAdapter;
import com.jpmorgan.technical_test.v1_0_0.sales_message_processing.SalesMessageProcessing;
import org.springframework.context.annotation.Bean;

public class SalesMessageConfig {

  @Bean
  public PrintMessage printMessage() {
    return new PrintSalesMessage();
  }

  @Bean
  public SalesMessageAdapter salesMessageAdapter(PrintMessage printMessage) {
    return new SalesMessageAdapter(new SalesMessageProcessing(printMessage));
  }

  @Bean
  public MQListenerClient mqListenerClient(SalesMessageAdapter salesMessageAdapter) {
    return new MQListenerClient(salesMessageAdapter);
  }
}
