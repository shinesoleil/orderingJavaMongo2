package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.TestHelper;
import com.thoughtworks.ketsu.support.TestRunner;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TestRunner.class)
public class PaymentApiTest extends ApiSupport{

  @Inject
  ProductRepository productRepository;

  @Inject
  UserRepository userRepository;

  @Test
  public void should_return_url_location_when_post_payment_with_params() {
    Map<String, Object> productInfo = TestHelper.productMap();
    Product product = productRepository.create(productInfo).get();
    ObjectId productId = product.getId();

    Map<String, Object> userInfo = TestHelper.userMap();
    User user = userRepository.create(userInfo).get();
    ObjectId userId = user.getId();

    Map<String, Object> orderInfo = TestHelper.orderMap(productId);
    ObjectId orderId = user.placeOrder(orderInfo).get().getId();
    Order order = user.findOrderById(orderId).get();

    Map<String, Object> paymentInfo = TestHelper.paymentMap();

    Response post = post("users/" + userId + "/orders/" + orderId + "/payment", paymentInfo);

    assertThat(post.getStatus(), is(201));
    assertThat(
      Pattern.matches(".*users/[0-9a-z]+/orders/[0-9a-z]+/payment", post.getLocation().toASCIIString()),
      is(true));
  }

  @Test
  public void should_return_payment_json_when_get_payment_by_order_id() {
    Map<String, Object> productInfo = TestHelper.productMap();
    Product product = productRepository.create(productInfo).get();
    ObjectId productId = product.getId();

    Map<String, Object> userInfo = TestHelper.userMap();
    User user = userRepository.create(userInfo).get();
    ObjectId userId = user.getId();

    Map<String, Object> orderInfo = TestHelper.orderMap(productId);
    ObjectId orderId = user.placeOrder(orderInfo).get().getId();
    Order order = user.findOrderById(orderId).get();

    Map<String, Object> paymentInfo = TestHelper.paymentMap();
    order.pay(paymentInfo);

    Response get = get("users/" + userId + "/orders/" + orderId + "/payment");
    Map<String, Object> map = get.readEntity(Map.class);

    assertThat(map.get("pay_type"), is("CASH"));
  }

  @Test
  public void should_return_404_when_get_payment_by_id_not_found() {
    Map<String, Object> productInfo = TestHelper.productMap();
    Product product = productRepository.create(productInfo).get();
    ObjectId productId = product.getId();

    Map<String, Object> userInfo = TestHelper.userMap();
    User user = userRepository.create(userInfo).get();
    ObjectId userId = user.getId();

    Map<String, Object> orderInfo = TestHelper.orderMap(productId);
    ObjectId orderId = user.placeOrder(orderInfo).get().getId();

    Response get = get("users/" + userId + "/orders/" + orderId + "/payment");

    assertThat(get.getStatus(), is(404));
  }
}
