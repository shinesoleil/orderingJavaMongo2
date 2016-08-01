package com.thoughtworks.ketsu.domain.user;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.oid.MongoId;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class User implements Record{
  @Inject
  Jongo jongo;

  @MongoId
  private ObjectId id;
  private String name;

  public User() {
  }

  public User(ObjectId id, String name) {
    this.id = id;
    this.name = name;
  }

  public ObjectId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Optional<Order> placeOrder(Map<String, Object> info) {
    MongoCollection orders = jongo.getCollection("orders");
    info.put("_id", new ObjectId());
    info.put("user_id", id);
    ObjectId orderId = (ObjectId) orders.save(info).getUpsertedId();
    return Optional.ofNullable(orders.findOne(orderId).as(Order.class));
  }

  public Optional<Order> findOrderById(ObjectId orderId) {
    MongoCollection orders = jongo.getCollection("orders");

    return Optional.ofNullable(orders.findOne(orderId).as(Order.class));
  }

  @Override
  public Map<String, Object> toRefJson(Routes routes) {
    return null;
  }

  @Override
  public Map<String, Object> toJson(Routes routes) {
    return new HashMap() {{
      put("id", id.toString());
      put("url", new Routes().userUri(User.this));
      put("name", name);
    }};
  }
}
