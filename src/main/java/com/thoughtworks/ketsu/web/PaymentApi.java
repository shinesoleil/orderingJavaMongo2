package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Path("users/{userId}/orders/{orderId}/payment")
public class PaymentApi {

  @Context
  UserRepository userRepository;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createPayment(HashMap<String, Object> info,
                                @PathParam("userId") ObjectId userId,
                                @PathParam("orderId") ObjectId orderId) {

    User user = userRepository.findById(userId).get();
    Order order = user.findOrderById(orderId).get();
    Order paidOrder = order.pay(info);

    if (paidOrder.isPaid()) {
      return Response.created(new Routes().paymentUri(paidOrder)).build();
    } else {
      throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }
  }
}
