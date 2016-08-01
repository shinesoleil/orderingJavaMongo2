package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.TestHelper;
import com.thoughtworks.ketsu.support.TestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TestRunner.class)
public class ProductsApiTest extends ApiSupport {

  @Test
  public void should_return_201_when_post_with_params() {
    Map<String, Object> info = TestHelper.productMap();

    Response post = post("products", info);
    assertThat(post.getStatus(), is(201));
  }

  @Test
  public void should_return_400_when_post_with_invalid_params() {
    Map<String, Object> info = TestHelper.productMap();
    info.replace("name", null);

    Response post = post("products", info);
    assertThat(post.getStatus(), is(400));

  }
}
