package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.TestHelper;
import com.thoughtworks.ketsu.support.TestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TestRunner.class)
public class UsersApiTest extends ApiSupport{

  @Test
  public void should_return_uri_location_when_post_user_with_params() {
    Map<String, Object> info = TestHelper.userMap();

    Response post = post("users", info);

    assertThat(post.getStatus(), is(201));
    assertThat(Pattern.matches(".*users/[0-9a-z]*.*", post.getLocation().toASCIIString()), is(true));

  }

  @Test
  public void should_return_400_when_post_with_invalid_params() {
    Map<String, Object> info = TestHelper.userMap();
    info.replace("name", null);

    Response post = post("users", info);
    assertThat(post.getStatus(), is(400));
  }
}
