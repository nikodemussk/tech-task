package com.jpmorgan.technical_test.v1_0_0.sales_message_adapter;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;

@ExtendWith(MockitoExtension.class)
class MQListenerClientTest {

  @InjectMocks
  private MQListenerClient mqListenerClient;

  @Mock
  private SalesMessageAdapter salesMessageAdapter;

  @Test
  void mqShouldTriggerCreateSalesMessageWhenIncomingMessageReceived() {
    //given
    Message message = new Message("text".getBytes(StandardCharsets.UTF_8));

    //when
    mqListenerClient.onMessage(message);

    //then
    Mockito.verify(salesMessageAdapter, Mockito.times(1)).createSalesMessage("text");
  }
}