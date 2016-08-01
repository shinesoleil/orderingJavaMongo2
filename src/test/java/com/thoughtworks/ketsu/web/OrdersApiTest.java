package com.thoughtworks.ketsu.web;

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
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TestRunner.class)
public class OrdersApiTest extends ApiSupport{

  @Inject
  ProductRepository productRepository;

  @Inject
  UserRepository userRepository;

  @Test
  public void should_return_url_location_when_post_with_params() {
    Map<String, Object> productInfo = TestHelper.productMap();
    Product product = productRepository.create(productInfo).get();
    ObjectId productId = product.getId();

    Map<String, Object> userInfo = TestHelper.userMap();
    User user = userRepository.create(userInfo).get();
    ObjectId userId = user.getId();

    Map<String, Object> orderInfo = TestHelper.orderMap(productId);

    Response post = post("users/" + userId + "/orders", orderInfo);

    assertThat(post.getStatus(), is(201));
    assertThat(Pattern.matches(".*/users/[0-9a-z]*/orders/[0-9a-z]*.*", post.getLocation().toASCIIString()),
      is(true));
  }

  @Test
  public void should_return_400_when_post_with_invalid_params() {
    Map<String, Object> productInfo = TestHelper.productMap();
    Product product = productRepository.create(productInfo).get();
    ObjectId productId = product.getId();

    Map<String, Object> userInfo = TestHelper.userMap();
    User user = userRepository.create(userInfo).get();
    ObjectId userId = user.getId();

    Map<String, Object> orderInfo = TestHelper.orderMap(productId);
    orderInfo.replace("name", null);

    Response post = post("users/" + userId + "/orders", orderInfo);
    assertThat(post.getStatus(), is(400));
  }


  @Test
  public void should_return_list_order_json_when_get_orders() {
    Map<String, Object> productInfo = TestHelper.productMap();
    Product product = productRepository.create(productInfo).get();
    ObjectId productId = product.getId();

    Map<String, Object> userInfo = TestHelper.userMap();
    User user = userRepository.create(userInfo).get();
    ObjectId userId = user.getId();

    Map<String, Object> orderInfo = TestHelper.orderMap(productId);
    user.placeOrder(orderInfo);

    Response get = get("users/" + userId + "/orders");
    List<Map<String, Object>> mapList = get.readEntity(List.class);

    assertThat(get.getStatus(), is(200));
    assertThat(mapList.size(), is(1));
    assertThat(mapList.get(0).get("name"), is("firstOrder"));
  }

  @Test
  public void should_return_order_json_when_get_by_id() {
    Map<String, Object> productInfo = TestHelper.productMap();
    Product product = productRepository.create(productInfo).get();
    ObjectId productId = product.getId();

    Map<String, Object> userInfo = TestHelper.userMap();
    User user = userRepository.create(userInfo).get();
    ObjectId userId = user.getId();

    Map<String, Object> orderInfo = TestHelper.orderMap(productId);
    ObjectId orderId = user.placeOrder(orderInfo).get().getId();

    Response get = get("users/" + userId + "/orders/" + orderId);
    Map<String, Object> map = get.readEntity(Map.class);

    assertThat(get.getStatus(), is(200));
    assertThat(map.get("name"), is("firstOrder"));
  }

  @Test
  public void should_return_404_when_get_by_id_not_found() {
    Map<String, Object> userInfo = TestHelper.userMap();
    User user = userRepository.create(userInfo).get();
    ObjectId userId = user.getId();

    Response get = get("users/" + userId + "/orders/" + ObjectId.createFromLegacyFormat(1, 1, 1));

    assertThat(get.getStatus(), is(404));

  }
}
