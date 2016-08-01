package com.thoughtworks.ketsu.domain.user;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;

import java.util.HashMap;
import java.util.Map;

public class User implements Record{
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
