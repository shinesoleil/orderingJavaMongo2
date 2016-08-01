package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.user.UserRepository;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("users/{userId}/orders/{orderId}/payment")
public class PaymentApi {

  @Context
  UserRepository userRepository;

  @POST
  public Response createPayment() {
    return Response.status(201).build();
  }

}
