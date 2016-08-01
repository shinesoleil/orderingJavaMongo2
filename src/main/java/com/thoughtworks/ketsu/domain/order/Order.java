package com.thoughtworks.ketsu.domain.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;

import java.util.Date;

public class Order {
  @MongoId
  private ObjectId id;
  @JsonProperty("user_id")
  private ObjectId userId;
  private String name;
  private String address;
  private String phone;
  private Date time;

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

  public Date getTime() {
    return time;
  }
}
