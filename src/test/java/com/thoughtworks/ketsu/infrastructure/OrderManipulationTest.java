package com.thoughtworks.ketsu.infrastructure;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
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
public class OrderManipulationTest {

  @Inject
  UserRepository userRepository;

  @Test
  public void should_create_order_and_find_by_id() {
    Map<String, Object> userInfo = TestHelper.userMap();
    User user = userRepository.create(userInfo).get();
    ObjectId userId = user.getId();

    Map<String, Object> orderInfo = TestHelper.orderMap();
    ObjectId orderId = user.placeOrder(orderInfo).get().getId();
    Optional<Order> orderOptional = user.findOrderById(orderId);

    assertThat(orderOptional.isPresent(), is(true));
    assertThat(orderOptional.get().getUserId(), is(userId));

  }
}
