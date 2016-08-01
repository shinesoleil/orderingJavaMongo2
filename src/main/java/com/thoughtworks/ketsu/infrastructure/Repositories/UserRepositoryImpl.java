package com.thoughtworks.ketsu.infrastructure.Repositories;

import com.google.inject.Injector;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

  @Inject
  Jongo jongo;

  @Inject
  Injector injector;

  @Override
  public Optional<User> create(Map<String, Object> info) {
    MongoCollection users = jongo.getCollection("users");
    info.put("_id", new ObjectId());
    ObjectId userId = (ObjectId) users.save(info).getUpsertedId();

    User user = users.findOne(userId).as(User.class);
    injector.injectMembers(user);

    return Optional.ofNullable(user);
  }

  @Override
  public Optional<User> findById(ObjectId userId) {
    MongoCollection users = jongo.getCollection("users");

    User user = users.findOne(userId).as(User.class);
    injector.injectMembers(user);

    return Optional.ofNullable(user);
  }
}
