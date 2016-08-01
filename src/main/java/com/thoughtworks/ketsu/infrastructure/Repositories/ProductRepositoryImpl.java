package com.thoughtworks.ketsu.infrastructure.Repositories;

import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
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

  @Override
  public List<Product> find() {
    MongoCollection products = jongo.getCollection("products");
    List<Product> productList = new ArrayList<>();

    MongoCursor<Product> cursor =  products.find().as(Product.class);
    while (cursor.hasNext()) {
      productList.add(cursor.next());
    }

    return productList;
  }
}
