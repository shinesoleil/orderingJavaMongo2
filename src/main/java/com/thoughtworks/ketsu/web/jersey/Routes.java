package com.thoughtworks.ketsu.web.jersey;


import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.user.User;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class Routes {

  public Routes(UriInfo uriInfo) {
  }

  public Routes() {
  }

  public URI productUri(Product product) {
    return URI.create("products/" + product.getId());
  }

  public URI userUri(User user) {
    return URI.create("users/" + user.getId());
  }
}
