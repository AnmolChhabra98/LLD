package entities;

import java.util.Date;

public class Order {
  public String id;
  public String status;
  public Product product;
  public int productQuantity;
  public Date createdAt;

  public Order(String id, Product product, int productQuantity) {
      this.id = id;
      this.product = product;
      this.productQuantity = productQuantity;
      this.status = "pending";
      this.createdAt = new Date();
  }
}
