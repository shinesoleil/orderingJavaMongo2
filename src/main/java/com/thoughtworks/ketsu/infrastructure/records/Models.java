package com.thoughtworks.ketsu.infrastructure.records;

import com.google.inject.AbstractModule;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.infrastructure.Repositories.ProductRepositoryImpl;

public class Models extends AbstractModule {
  @Override
  protected void configure() {
    bind(ProductRepository.class).to(ProductRepositoryImpl.class);
  }
}
