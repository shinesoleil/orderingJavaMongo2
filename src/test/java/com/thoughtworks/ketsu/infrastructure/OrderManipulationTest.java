package com.thoughtworks.ketsu.infrastructure;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.support.TestHelper;
import com.thoughtworks.ketsu.support.TestRunner;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TestRunner.class)
public class OrderManipulationTest {

  @Inject
  ProductRepository productRepository;

  @Inject
  UserRepository userRepository;

  @Test
  public void should_create_order_with_orderItems_and_find_by_id() {
    Map<String, Object> productInfo = TestHelper.productMap();
    Product product = productRepository.create(productInfo).get();
    ObjectId productId = product.getId();

    Map<String, Object> userInfo = TestHelper.userMap();
    User user = userRepository.create(userInfo).get();
    ObjectId userId = user.getId();

    Map<String, Object> orderInfo = TestHelper.orderMap(productId);
    ObjectId orderId = user.placeOrder(orderInfo).get().getId();
    Optional<Order> orderOptional = user.findOrderById(orderId);

    assertThat(orderOptional.isPresent(), is(true));
    assertThat(orderOptional.get().getUserId(), is(userId));
    assertThat(orderOptional.get().getOrderItems().get(0).getQuantity(), is(2));
    assertThat(orderOptional.get().getOrderItems().get(0).getAmount(), is(1222.0));
  }

  @Test
  public void should_find_all_orders() {
    Map<String, Object> productInfo = TestHelper.productMap();
    Product product = productRepository.create(productInfo).get();
    ObjectId productId = product.getId();

    Map<String, Object> userInfo = TestHelper.userMap();
    User user = userRepository.create(userInfo).get();
    ObjectId userId = user.getId();

    Map<String, Object> orderInfo = TestHelper.orderMap(productId);
    user.placeOrder(orderInfo);

    List<Order> orders = user.find();

    assertThat(orders.size(), is(1));
    assertThat(orders.get(0).getUserId(), is(userId));
    assertThat(orders.get(0).getOrderItems().get(0).getAmount(), is(1222.0));
  }
}
