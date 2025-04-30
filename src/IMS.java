import entities.Product;
import service.InventoryService;
import service.OrderService;
import service.ProductService;
import service.UnblockingService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class IMS {

//  static {
    // v1
//    new Thread(UnblockingService::unblockingTasks).start();
// v2
//    UnblockingService.start();
//  }

  public Product createProduct(String productId, String name, int quantity, String price) {
    ProductService productService = new ProductService();
    return productService.createProduct(productId, name, quantity, price);
  }

  public int getAvailableInventory(String productId) {
    InventoryService inventoryService = new InventoryService();
    return inventoryService.getAvailableInventory(productId);
  }

  public void blockProduct(Product product, int blockedQuantity, String orderId) {
    OrderService orderService = new OrderService();
    orderService.createOrder(product, blockedQuantity, orderId);
  }

  public void confirmOrder(String orderId) {
    OrderService orderService = new OrderService();
    orderService.confirmOrder(orderId);
  }

//  v1
//  public static void main(String[] args) {
//    IMS ims = new IMS();
//    Product product = ims.createProduct("P123", "Laptop", 10, "1000");
//    System.out.println(ims.getAvailableInventory("P123"));
//    ims.blockProduct(product, 5, "ORD1");
//    ims.confirmOrder("ORD1");
//    System.out.println(ims.getAvailableInventory("P123"));
//  }

  // v2
//  public static void main(String[] args) {
//    IMS ims = new IMS();
//
//    // Test Case 1: Create a Product and Check Inventory
//    Product product = ims.createProduct("P001", "Phone", 20, "500");
//    System.out.println("Available Inventory after creation: " + ims.getAvailableInventory("P001")); // Expected: 20
//
//    // Test Case 2: Block Inventory for a New Order and Check Inventory
//    ims.blockProduct(product, 5, "ORD100");
//    System.out.println("Available Inventory after blocking 5 units: " + ims.getAvailableInventory("P001")); // Expected: 15
//
//    // Test Case 3: Confirm the Order and Verify Inventory
//    ims.confirmOrder("ORD100");
//    System.out.println("Available Inventory after confirming ORD100: " + ims.getAvailableInventory("P001")); // Expected: 15
//
//    // Test Case 4: Try to Block More Than Available Inventory
//    try {
//      ims.blockProduct(product, 100, "ORD101");
//    } catch (Exception e) {
//      System.out.println("Exception on blocking more than available: " + e.getMessage()); // Expected: Error message
//    }
//
//    // Test Case 5: Block Partial Inventory and Confirm Multiple Orders
//    ims.blockProduct(product, 10, "ORD102");
//    System.out.println("Available Inventory after blocking 10 units: " + ims.getAvailableInventory("P001")); // Expected: 5
//    ims.confirmOrder("ORD102");
//    System.out.println("Available Inventory after confirming ORD102: " + ims.getAvailableInventory("P001")); // Expected: 5
//
//    // Test Case 6: Block, Do Not Confirm (simulate timeout case manually for now)
//    ims.blockProduct(product, 5, "ORD103");
//    System.out.println("Available Inventory after blocking 5 units for ORD103 (not confirmed yet): " + ims.getAvailableInventory("P001"));
//    // Expected: 0 (inventory locked, not available)
//
//    // Test Case 7: Confirm an Invalid Order ID
//    try {
//      ims.confirmOrder("INVALID_ORDER");
//    } catch (Exception e) {
//      System.out.println("Exception on confirming invalid order: " + e.getMessage()); // Expected: Error message
//    }
//  }

  // v3
//  public static void main(String[] args) {
//    IMS ims = new IMS();
//
//    // Test Case 1: Create a Product and Check Inventory
//    Product product = ims.createProduct("P001", "Phone", 20, "500");
//    System.out.println("Available Inventory after creation: " + ims.getAvailableInventory("P001")); // Expected: 20
//
//    // Test Case 2: Block Inventory for a New Order and Check Inventory
//    ims.blockProduct(product, 5, "ORD100");
//    System.out.println("Available Inventory after blocking 5 units: " + ims.getAvailableInventory("P001")); // Expected: 15
//
//
//    System.out.println("Available Inventory after creation: " + ims.getAvailableInventory("P001")); // Expected: 20
//  }

  public static void main(String[] args) throws InterruptedException {
    UnblockingService unblockingService = new UnblockingService(new InventoryService());
    // Start background tasks
    unblockingService.start();

    IMS ims = new IMS();

    System.out.println("=== Inventory Management System Test Started ===\n");

    // Test Case 1: Create a Product
    Product product = ims.createProduct("P001", "Phone", 20, "500");
    System.out.println("[Test 1] Available Inventory after creation: " + ims.getAvailableInventory("P001")); // Expected: 20
    System.out.println();

    // Test Case 2: Block 5 units for ORD100
    ims.blockProduct(product, 5, "ORD100");
    System.out.println("[Test 2] Available Inventory after blocking 5 units (ORD100): " + ims.getAvailableInventory("P001")); // Expected: 15
    System.out.println();

    // Test Case 3: Block 10 units for ORD101
    ims.blockProduct(product, 10, "ORD101");
    System.out.println("[Test 3] Available Inventory after blocking 10 units (ORD101): " + ims.getAvailableInventory("P001")); // Expected: 5
    System.out.println();

    // Test Case 4: Confirm ORD100
    ims.confirmOrder("ORD100");
    System.out.println("[Test 4] Available Inventory after confirming ORD100: " + ims.getAvailableInventory("P001")); // Expected: 5
    System.out.println();

    // Test Case 5: Try blocking 6 units (should fail, only 5 left)
    try {
      ims.blockProduct(product, 6, "ORD102");
    } catch (RuntimeException e) {
      System.out.println("[Test 5] Expected failure on blocking 6 units (only 5 available): " + e.getMessage());
    }
    System.out.println();

    // Test Case 6: Block 5 units for ORD103
    ims.blockProduct(product, 5, "ORD103");
    System.out.println("[Test 6] Available Inventory after blocking 5 units (ORD103): " + ims.getAvailableInventory("P001")); // Expected: 0
    System.out.println();

    // Test Case 7: Try confirming a non-existing order
    try {
      ims.confirmOrder("ORD999");
    } catch (RuntimeException e) {
      System.out.println("[Test 7] Expected failure on confirming non-existent order: " + e.getMessage());
    }
    System.out.println();

    // Test Case 8: Wait for unblocker to run
    System.out.println("[Test 8] Waiting for unblocking of ORD101 and ORD103 (sleeping for 70 seconds)...");
    Thread.sleep(70000); // 70 seconds to ensure unblocker runs (because your unblock happens after 60 seconds)

    System.out.println("[Test 8] Available Inventory after auto-unblock: " + ims.getAvailableInventory("P001")); // Expected: 15
    System.out.println();

    // Test Case 9: Try blocking again after unblock
    ims.blockProduct(product, 10, "ORD104");
    System.out.println("[Test 9] Available Inventory after blocking 10 units (ORD104): " + ims.getAvailableInventory("P001")); // Expected: 5
    System.out.println();

    // Test Case 10: Confirm ORD104
    ims.confirmOrder("ORD104");
    System.out.println("[Test 10] Available Inventory after confirming ORD104: " + ims.getAvailableInventory("P001")); // Expected: 5
    System.out.println();

    // Shutting down background tasks
    unblockingService.shutdown();

    System.out.println("=== Inventory Management System Test Completed ===");
  }

}