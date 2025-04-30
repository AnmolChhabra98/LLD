package entities;

public class Product {
  public String id;
  public String name;
  public int quantity;
  public String price;

  public Product(String id, String name, int quantity, String price) {
    this.id = id;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
  }

  public Product(Product oldProduct) {
    this.id = oldProduct.id;
    this.name = oldProduct.name;
    this.quantity = oldProduct.quantity;
    this.price = oldProduct.price;
  }
}
