package com.thoughtworks.ketsu.infrastructure.records;

import com.google.inject.AbstractModule;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.infrastructure.Repositories.ProductRepositoryImpl;
import com.thoughtworks.ketsu.infrastructure.Repositories.UserRepositoryImpl;

public class Models extends AbstractModule {
  @Override
  protected void configure() {
    bind(ProductRepository.class).to(ProductRepositoryImpl.class);
    bind(UserRepository.class).to(UserRepositoryImpl.class);
  }
}
