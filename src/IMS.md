classDiagram
direction BT
class IMS {
  + getAvailableInventory(String) int
  + main(String[]) void
  + createProduct(String, String, int, String) Product
  + blockProduct(Product, int, String) void
  + confirmOrder(String) void
}
class Inventory {
  ~ int id
  + Map~Integer, Product~ products
  ~ String status
}
class InventoryService {
  + Map~String, Product~ blockedProducts
  + Map~String, Product~ availableProducts
  + getAvailableInventory(String) int
  + unblockInventory(Order) void
  + reduceBlockedProducts(Product, int) void
  + increaseAvailableProducts(Product, int) void
  + blockInventory(Order) void
}
class Order {
  + int productQuantity
  + String id
  + String status
  + Date createdAt
  + Product product
}
class OrderService {
  + Map~String, Order~ orders
  + createOrder(Product, int, String) void
  + confirmOrder(String) void
}
class Product {
  + int quantity
  + String id
  + String price
  + String name
}
class ProductService {
  + createProduct(String, String, int, String) Product
}
class UnblockingService {
  - ScheduledExecutorService scheduler
  + unblockingTasks() void
}

Inventory "1" *--> "products *" Product 
InventoryService  ..>  Product : «create»
InventoryService "1" *--> "availableProducts *" Product 
Order "1" *--> "product 1" Product 
OrderService  ..>  InventoryService : «create»
OrderService "1" *--> "orders *" Order 
OrderService  ..>  Order : «create»
ProductService  ..>  InventoryService : «create»
ProductService  ..>  Product : «create»
UnblockingService  ..>  InventoryService : «create»
