package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.order.Order;
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

@Path("users/{userId}/orders")
public class OrdersApi {

  @Context
  UserRepository userRepository;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createOrder(HashMap<String, Object> info,
                              @PathParam("userId")ObjectId userId) {

    List<String> invalidParams = new ArrayList<>();
    if (info.get("name") == null) {
      invalidParams.add("name");
    }
    //to add more
    if (!invalidParams.isEmpty()) {
      throw new InvalidParameterException(invalidParams);
    }

    User user = userRepository.findById(userId).get();
    Optional<Order> orderOptional = user.placeOrder(info);

    if (orderOptional.isPresent()) {
      return Response.created(new Routes().orderUri(orderOptional.get())).build();
    } else {
      throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Order> findOrders(@PathParam("userId")ObjectId userId) {
    User user = userRepository.findById(userId).get();

    return user.find();
  }
}
