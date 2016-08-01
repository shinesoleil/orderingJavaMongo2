package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.product.ProductRepository;
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
public class ProductsApiTest extends ApiSupport {

  @Inject
  ProductRepository productRepository;

  @Test
  public void should_return_url_location_when_post_with_params() {
    Map<String, Object> info = TestHelper.productMap();

    Response post = post("products", info);
    assertThat(post.getStatus(), is(201));
    assertThat(Pattern.matches(".*products/[0-9a-z]*.*", post.getLocation().toASCIIString()), is(true));
  }

  @Test
  public void should_return_400_when_post_with_invalid_params() {
    Map<String, Object> info = TestHelper.productMap();
    info.replace("name", null);

    Response post = post("products", info);
    assertThat(post.getStatus(), is(400));
  }

  @Test
  public void should_return_200_when_get_products() {
    Map<String, Object> info = TestHelper.productMap();
    productRepository.create(info);

    Response get = get("products");
    List<Map<String, Object>> mapList = get.readEntity(List.class);

    assertThat(get.getStatus(), is(200));
    assertThat(mapList.size(), is(1));
    assertThat(mapList.get(0).get("name"), is("desk"));
  }

  @Test
  public void should_return_product_json_when_get_by_id() {
    Map<String, Object> info = TestHelper.productMap();
    ObjectId id = productRepository.create(info).get().getId();

    Response get = get("products/" + id);
    Map<String, Object> map = get.readEntity(Map.class);

    assertThat(get.getStatus(), is(200));
    assertThat(map.get("name"), is("desk"));
  }
}
