package com.thoughtworks.ketsu.domain.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.Map;

public class Payment implements Record{
  private ObjectId orderId;
  private ObjectId userId;
  @JsonProperty("pay_type")
  private String payType;
  @JsonProperty("pay_time")
  private String payTime;
  private double amount;

  public Payment() {
  }

  public Payment(ObjectId orderId, ObjectId userId, String payType, String payTime, double amount) {
    this.orderId = orderId;
    this.userId = userId;
    this.payType = payType;
    this.payTime = payTime;
    this.amount = amount;
  }

  public ObjectId getOrderId() {
    return orderId;
  }

  public String getPayType() {
    return payType;
  }

  public String getPayTime() {
    return payTime;
  }

  public double getAmount() {
    return amount;
  }

  public ObjectId getUserId() {
    return userId;
  }

  @Override
  public Map<String, Object> toRefJson(Routes routes) {
    return null;
  }

  @Override
  public Map<String, Object> toJson(Routes routes) {
    return new HashMap() {{
      put("url", new Routes().paymentUri(Payment.this));
      put("order_url", new Routes().orderUriOfPayment(Payment.this));
      put("pay_type", payType);
      put("pay_time", payTime);
      put("amount", amount);
    }};
  }
}
