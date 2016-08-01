package com.thoughtworks.ketsu.domain.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order implements Record{
  @MongoId
  private ObjectId id;
  @JsonProperty("user_id")
  private ObjectId userId;
  private String name;
  private String address;
  private String phone;
  @JsonProperty("order_items")
  private List<OrderItem> orderItems;

  public Order() {
  }

  public ObjectId getId() {
    return id;
  }

  public ObjectId getUserId() {
    return userId;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getPhone() {
    return phone;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public Date getTime() {
    return id.getDate();
  }

  public double getTotalPrice() {
    double totalPrice = 0;

    for (OrderItem item: orderItems) {
      totalPrice += item.getAmount();
    }

    return totalPrice;
  }

  @Override
  public Map<String, Object> toRefJson(Routes routes) {
    return new HashMap() {{
      put("url", new Routes().orderUri(Order.this));
      put("name", name);
      put("address", address);
      put("phone", phone);
      put("total_price", getTotalPrice());
      put("created_at", getTime());
    }};
  }

  @Override
  public Map<String, Object> toJson(Routes routes) {
    return null;
  }
}
