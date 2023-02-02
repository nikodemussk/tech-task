package com.jpmorgan.technical_test.v1_0_0.sales_message_processing;

import java.util.HashMap;

public interface SalesMessageProcess {

    HashMap<String, SalesRecord> processMessage(SalesMessageDetail salesMessageDetails);
}
