package com.thoughtworks.ketsu.domain.order;

import org.bson.types.ObjectId;

public class OrderItem {
  private ObjectId productId;
  private int quantity;
  private double amount;

  public OrderItem() {
  }

  public ObjectId getProductId() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public double getAmount() {
    return amount;
  }
}
