package com.thoughtworks.ketsu.infrastructure;

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
public class UserRepositoryTest {

  @Inject
  UserRepository userRepository;

  @Test
  public void should_create_user_and_find_by_id() {
    Map<String, Object> info = TestHelper.userMap();

    ObjectId userId = userRepository.create(info).get().getId();
    Optional<User> userOptional = userRepository.findById(userId);

    assertThat(userOptional.isPresent(), is(true));
    assertThat(userOptional.get().getName(), is("firstUser"));

  }
}
