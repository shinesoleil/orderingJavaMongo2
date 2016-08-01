package com.thoughtworks.ketsu.domain.product;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;

public class Product {
  @MongoId
  private ObjectId id;
  private String name;
  private String description;
  private double price;

  public Product() {
  }

  public Product(ObjectId id, String name, String description, double price) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public ObjectId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public double getPrice() {
    return price;
  }
}
