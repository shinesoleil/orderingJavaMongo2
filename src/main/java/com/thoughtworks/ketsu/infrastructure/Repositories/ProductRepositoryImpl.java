package com.thoughtworks.ketsu.infrastructure.Repositories;

import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

  @Inject
  Jongo jongo;

  @Override
  public Optional<Product> create(Map<String, Object> info) {
    MongoCollection products = jongo.getCollection("products");
    info.put("_id", new ObjectId());
    ObjectId id = (ObjectId) products.save(info).getUpsertedId();

    return Optional.ofNullable(products.findOne(id).as(Product.class));
  }

  @Override
  public Optional<Product> findById(ObjectId id) {
    MongoCollection products = jongo.getCollection("products");

    return Optional.ofNullable(products.findOne(id).as(Product.class));
  }
}
