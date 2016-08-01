package com.thoughtworks.ketsu.infrastructure;

import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.support.TestHelper;
import com.thoughtworks.ketsu.support.TestRunner;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TestRunner.class)
public class ProductRepositoryTest {

  @Inject
  ProductRepository productRepository;

  @Test
  public void should_create_product_and_find_product_by_id() {
    Map<String, Object> info = TestHelper.productMap();

    ObjectId productId = productRepository.create(info).get().getId();

    Optional<Product> productOptional = productRepository.findById(productId);

    assertThat(productOptional.isPresent(), is(true));
    assertThat(productOptional.get().getName(), is("desk"));
  }

}
