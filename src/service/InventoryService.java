package service;

import entities.Order;
import entities.Product;
import java.util.HashMap;
import java.util.Map;

public class InventoryService {
  public static Map<String, Product> availableProducts = new HashMap<>();
  public static Map<String, Product> blockedProducts = new HashMap<>();

  public int getAvailableInventory(String productId) {
    return availableProducts.get(productId).quantity;
  }

  public void reduceBlockedProducts(Product product, int quantity) {
    String productId = product.id;
    blockedProducts.get(productId).quantity -= quantity;
  }

  public void increaseAvailableProducts(Product product, int quantity) {
    String productId = product.id;
    if(!availableProducts.containsKey(productId)) {
      availableProducts.put(productId, product);
      return;
    }

    availableProducts.get(productId).quantity += quantity;
  }

  public void blockInventory(Order order) {
    String productId = order.product.id;
    int blockQuantity = order.productQuantity;

    if(blockQuantity > availableProducts.get(productId).quantity)
      throw new RuntimeException("Not enough inventory");

    availableProducts.get(productId).quantity -= blockQuantity;
    blockedProducts.put(productId, new Product(order.product));
  }

  public void unblockInventory(Order order) {
    int unblockQuantity = order.productQuantity;
    availableProducts.get(order.product.id).quantity += unblockQuantity;
  }
}
