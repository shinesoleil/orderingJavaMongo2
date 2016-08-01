package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.web.exception.InvalidParameterException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Path("products")
public class ProductsApi {

  @Context
  ProductRepository productRepository;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createProduct(HashMap<String, Object> info) {
    List<String> invalidParams = new ArrayList<>();
    if (info.get("name") == null) {
      invalidParams.add("name");
    }
    if (info.get("description") == null) {
      invalidParams.add("description");
    }
    if (info.get("price") == null) {
      invalidParams.add("price");
    }
    if (!invalidParams.isEmpty()) {
      throw new InvalidParameterException(invalidParams);
    }

    Optional<Product> productOptional = productRepository.create(info);

    if (productOptional.isPresent()) {
      return Response.status(201).build();
    } else {
      throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }
  }

}
