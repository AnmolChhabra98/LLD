package service;

import entities.Product;

public class ProductService {

  // creating product
  public Product createProduct(String id, String name, int quantity, String price) {
    Product product = new Product(id, name, quantity, price);
    InventoryService inventoryService = new InventoryService();
    inventoryService.increaseAvailableProducts(product, quantity);

    return product;
  }
}
