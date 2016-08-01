package com.thoughtworks.ketsu.domain.user;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.jongo.marshall.jackson.oid.MongoId;

import javax.inject.Inject;
import java.util.*;

public class User implements Record{
  @Inject
  ProductRepository productRepository;

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

  public void addAmount(Map<String, Object> info) {
    for (Map<String, Object> orderItem: (List<Map<String, Object>>)info.get("order_items")) {
      double price = productRepository.findById(new ObjectId(String.valueOf(orderItem.get("product_id")))).get().getPrice();
      int quantity = (Integer) orderItem.get("quantity");
      orderItem.put("amount", price * quantity);
    }
  }

  public Optional<Order> placeOrder(Map<String, Object> info) {
    MongoCollection orders = jongo.getCollection("orders");
    info.put("_id", new ObjectId());
    info.put("user_id", id);
    addAmount(info);
    ObjectId orderId = (ObjectId) orders.save(info).getUpsertedId();
    return Optional.ofNullable(orders.findOne(orderId).as(Order.class));
  }

  public Optional<Order> findOrderById(ObjectId orderId) {
    MongoCollection orders = jongo.getCollection("orders");

    return Optional.ofNullable(orders.findOne(orderId).as(Order.class));
  }

  public List<Order> find() {
    MongoCollection orders = jongo.getCollection("orders");
    MongoCursor<Order> cursor =  orders.find().as(Order.class);
    List<Order> orderList = new ArrayList<>();

    while (cursor.hasNext()) {
      orderList.add(cursor.next());
    }

    return orderList;
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
