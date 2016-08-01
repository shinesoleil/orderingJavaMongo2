package com.thoughtworks.ketsu.domain.user;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;

public class User {
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
}
