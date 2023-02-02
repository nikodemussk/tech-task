package com.jpmorgan.technical_test.v1_0_0.sales_message_adapter;

import java.util.logging.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class MQListenerClient implements MessageListener {

  private final Logger LOGGER = Logger.getLogger(MQListenerClient.class.getName());;

  private SalesMessageAdapter salesMessageAdapter;

  public MQListenerClient(SalesMessageAdapter salesMessageAdapter) {
    this.salesMessageAdapter = salesMessageAdapter;
  }

  @Override
  public void onMessage(Message message) {
    LOGGER.info(new String(message.getBody()));
    salesMessageAdapter.createSalesMessage(new String(message.getBody()));
  }



}
