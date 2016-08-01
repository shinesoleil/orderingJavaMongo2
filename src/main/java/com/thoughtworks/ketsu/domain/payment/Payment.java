package com.thoughtworks.ketsu.domain.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

public class Payment {
  private ObjectId orderId;
  @JsonProperty("pay_type")
  private String payType;
  @JsonProperty("pay_time")
  private String payTime;
  private double amount;

  public Payment() {
  }

  public Payment(ObjectId orderId, String payType, String payTime, double amount) {
    this.orderId = orderId;
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
}
