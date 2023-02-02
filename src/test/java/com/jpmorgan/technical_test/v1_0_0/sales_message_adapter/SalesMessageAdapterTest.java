package com.jpmorgan.technical_test.v1_0_0.sales_message_adapter;

import com.jpmorgan.technical_test.v1_0_0.sales_message_processing.SalesMessageDetail;
import com.jpmorgan.technical_test.v1_0_0.sales_message_processing.SalesMessageProcess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SalesMessageAdapterTest {

  @InjectMocks
  private SalesMessageAdapter salesMessageAdapter;

  @Mock
  private SalesMessageProcess salesMessageProcess;

  @Test
  void messageShouldSuccessWhenGivenValidMessage() {
    //given
    String message = "{\"productType\": \"product324\", \"price\": 100, \"messageType\": \"Type 1\", \"operation\": \"Add\" }";

    //when
    salesMessageAdapter.createSalesMessage(message);

    //then
    Mockito.verify(salesMessageProcess, Mockito.times(1)).processMessage(Mockito.any(SalesMessageDetail.class));
  }

  @Test
  void messageShouldNotBeProcessWhenGivenNullMessage() {
    //given & when
    salesMessageAdapter.createSalesMessage(null);

    //then
    Mockito.verify(salesMessageProcess, Mockito.times(0)).processMessage(Mockito.any(SalesMessageDetail.class));
  }

  @Test
  void messageShouldNotBeProcessWhenGivenInvalidMessage() {
    //given
    String message = "Invalid";

    //when
    salesMessageAdapter.createSalesMessage(message);

    //then
    Mockito.verify(salesMessageProcess, Mockito.times(0)).processMessage(Mockito.any(SalesMessageDetail.class));
  }
}