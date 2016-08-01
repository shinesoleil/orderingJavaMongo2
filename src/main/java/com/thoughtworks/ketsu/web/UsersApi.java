package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.web.exception.InvalidParameterException;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Path("users")
public class UsersApi {

  @Context
  UserRepository userRepository;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createUser(HashMap<String, Object> info) {
    List<String> invalidParams = new ArrayList<>();
    if (info.get("name") == null) {
      invalidParams.add("name");
    }
    if (!invalidParams.isEmpty()) {
      throw new InvalidParameterException(invalidParams);
    }

    Optional<User> userOptional = userRepository.create(info);

    if (userOptional.isPresent()) {
      return Response.created(new Routes().userUri(userOptional.get())).build();
    } else {
      throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }
  }

  @GET
  @Path("{userId}")
  @Produces(MediaType.APPLICATION_JSON)
  public User findUserById(@PathParam("userId")ObjectId userId) {
    return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Cannot find the user by id"));
  }

}
