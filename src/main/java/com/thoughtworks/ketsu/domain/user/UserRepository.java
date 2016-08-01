package com.thoughtworks.ketsu.domain.user;

import org.bson.types.ObjectId;

import java.util.Map;
import java.util.Optional;

public interface UserRepository {

  Optional<User> create(Map<String, Object> info);

  Optional<User> findById(ObjectId userId);
}

