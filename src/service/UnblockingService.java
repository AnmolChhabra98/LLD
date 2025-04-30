package service;

import entities.Order;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UnblockingService {

  private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
  // not using static because it will make it class dependent and need to initialize inline before the
  // now it can be initialized from constructor
  private final InventoryService inventoryService;

  public UnblockingService(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  public void start() {
//    InventoryService inventoryService = new InventoryService();

    // v1
//    while (true) {
//      for(Map.Entry<String, Order> entry : OrderService.orders.entrySet()) {
//        Order order = entry.getValue();
//        long diff = new Date().getTime() - order.createdAt.getTime();
//        if("pending".equalsIgnoreCase(order.status) && TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS) > 60) {
//          inventoryService.unblockInventory(order);
//          System.out.println("Unblocked product id " + order.product.id + ", Available inventory: " + inventoryService.getAvailableInventory(order.product.id));
//          order.status = "paused";
//        }
//      }
//      try {
//        Thread.sleep(1000); // Sleep for 1 second to allow time to move
//      } catch (InterruptedException e) {
//        throw new RuntimeException(e);
//      }
//    }

    // can create a seprate map for pending orders
    scheduler.scheduleAtFixedRate(() -> {
      Thread.currentThread().setName("Unblocker Thread");
      System.out.println("Running unblocking tasks: | Thread: " + Thread.currentThread().getName() + " Time: " + new Date());
      for(Map.Entry<String, Order> entry : OrderService.orders.entrySet()) {
        Order order = entry.getValue();
        long diff = new Date().getTime() - order.createdAt.getTime();
        if("pending".equalsIgnoreCase(order.status) && TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS) > 60) {
          inventoryService.unblockInventory(order);
          System.out.println("Unblocked product id " + order.product.id + ", Available inventory: " + inventoryService.getAvailableInventory(order.product.id));
          order.status = "paused";
        }
      }
    }, 0, 5, TimeUnit.SECONDS);
  }

  public void shutdown() {
    scheduler.shutdown();
    System.out.println("Unblocker Thread is shutting down");
  }
}
