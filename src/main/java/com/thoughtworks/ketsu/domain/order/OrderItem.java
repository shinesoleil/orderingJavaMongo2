package com.thoughtworks.ketsu.domain.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.Map;

public class OrderItem implements Record {
  @JsonProperty("product_id")
  private String productId;
  private int quantity;
  private double amount;

  public OrderItem() {
  }

  public String getProduct_id() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public double getAmount() {
    return amount;
  }

  @Override
  public Map<String, Object> toRefJson(Routes routes) {
    return new HashMap() {{
      put("product_id", productId);
      put("quantity", quantity);
      put("amount", amount);
    }};
  }

  @Override
  public Map<String, Object> toJson(Routes routes) {
    return new HashMap() {{
      put("product_id", productId);
      put("quantity", quantity);
      put("amount", amount);
    }};
  }
}
