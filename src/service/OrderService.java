package service;

import entities.Order;
import entities.Product;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderService {
//  public static Map<String, Order> orders = new HashMap<>(); // v1
// using concurrent HM as we are modifying it in unblocking service and at the same time a
// confirmation order request comes in IMS class or
  public static Map<String, Order> orders = new ConcurrentHashMap<>();

  public void confirmOrder(String orderId) {
    if(!orders.containsKey(orderId)) {
      throw new RuntimeException("Invalid orderId! Order not found with id: " + orderId);
    }

    Order order = orders.get(orderId);
    order.status = "purchased";

    InventoryService inventoryService = new InventoryService();
    inventoryService.reduceBlockedProducts(order.product, order.productQuantity);
  }



  public void createOrder(Product product, int productQuantity, String orderId) {
    InventoryService inventoryService = new InventoryService();
    Order order = new Order(orderId, product, productQuantity);
    inventoryService.blockInventory(order);

    orders.put(orderId, order);
  }
}
