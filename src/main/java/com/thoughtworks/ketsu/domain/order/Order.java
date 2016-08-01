package com.thoughtworks.ketsu.domain.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.ketsu.domain.payment.Payment;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.oid.MongoId;

import javax.inject.Inject;
import java.util.*;

public class Order implements Record{
  @Inject
  Jongo jongo;

  @MongoId
  private ObjectId id;
  @JsonProperty("user_id")
  private ObjectId userId;
  private String name;
  private String address;
  private String phone;
  @JsonProperty("order_items")
  private List<OrderItem> orderItems;
  private Payment payment;

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

  public Payment getPayment() {
    return new Payment(id, payment.getPayType(), payment.getPayTime(), payment.getAmount());
  }

  public double getTotalPrice() {
    double totalPrice = 0;

    for (OrderItem item: orderItems) {
      totalPrice += item.getAmount();
    }

    return totalPrice;
  }

  public void pay(Map<String, Object> paymentInfo) {
    MongoCollection orders = jongo.getCollection("orders");
    paymentInfo.put("pay_time", new Date());
    orders.update(id).with("{$set: {payment: #}}", paymentInfo);
  }

  public Optional<Payment> findPayment() {
    MongoCollection orders = jongo.getCollection("orders");
    Order order = orders.findOne(id).as(Order.class);

    return Optional.ofNullable(order.getPayment());
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
    return new HashMap() {{
      put("url", new Routes().orderUri(Order.this));
      put("name", name);
      put("address", address);
      put("phone", phone);
      put("total_price", getTotalPrice());
      put("created_at", getTime());
      put("order_items", orderItems);
    }};
  }
}
