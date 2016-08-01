package com.thoughtworks.ketsu.web;

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
public class UsersApiTest extends ApiSupport{

  @Inject
  UserRepository userRepository;
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

  @Test
  public void should_return_user_json_when_get_by_id() {
    Map<String, Object> info = TestHelper.userMap();
    ObjectId userId = userRepository.create(info).get().getId();

    Response get = get("users/" + userId);
    Map<String, Object> map = get.readEntity(Map.class);

    assertThat(get.getStatus(), is(200));
    assertThat(map.get("name"), is("firstUser"));
  }

  @Test
  public void should_return_404_when_find_by_id_not_found() {
    ObjectId id = ObjectId.createFromLegacyFormat(1, 1, 1);
    Response get = get("users/" + id);

    assertThat(get.getStatus(), is(404));
  }
}
