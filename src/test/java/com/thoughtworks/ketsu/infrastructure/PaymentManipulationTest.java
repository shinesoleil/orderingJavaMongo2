package com.thoughtworks.ketsu.infrastructure;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.payment.Payment;
import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.support.TestHelper;
import com.thoughtworks.ketsu.support.TestRunner;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TestRunner.class)
public class PaymentManipulationTest {

  @Inject
  ProductRepository productRepository;

  @Inject
  UserRepository userRepository;

  private ObjectId userId;
  private User user;
  private Map<String, Object> orderInfo;
  private ObjectId orderId;
  private Order order;
  private Map<String, Object> paymentInfo;

  @Before
  public void setUp() {
    Map<String, Object> productInfo = TestHelper.productMap();
    Product product = productRepository.create(productInfo).get();
    ObjectId productId = product.getId();

    Map<String, Object> userInfo = TestHelper.userMap();
    user = userRepository.create(userInfo).get();
    userId = user.getId();

    orderInfo = TestHelper.orderMap(productId);
    orderId = user.placeOrder(orderInfo).get().getId();
    order = user.findOrderById(orderId).get();

    paymentInfo = TestHelper.paymentMap();
  }

  @Test
  public void should_create_payment_and_find_by_id() {
    order.pay(paymentInfo);
    Optional<Payment> paymentOptional = order.findPayment();

    assertThat(paymentOptional.isPresent(), is(true));
    assertThat(paymentOptional.get().getAmount(), is(1400.0));
    assertThat(paymentOptional.get().getPayType(), is("CASH"));
    assertThat(paymentOptional.get().getOrderId(), is(orderId));

  }
}
