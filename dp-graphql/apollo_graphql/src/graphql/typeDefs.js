import {gql} from 'apollo-server';

const typeDefs = gql`
    scalar Date
    scalar Long
    scalar Double
    scalar Integer
    
    type Delivery {
        id: Long!
        orderId: Long 
        quantity: Integer 
        productId: Long 
        productName: String 
        customerId: String 
        customerName: String 
        deliveryAddress: String 
        deliveryState: String 
        orders: [Order]
        order(orderId: Long): Order
        products: [Product]
        product(productId: Long): Product
    }
  
    
    type Order {
        id: Long! 
        productId: Long
        productName: String
        quantity: Integer
        price: Integer
        customerId: String
        customerName: String
        customerAddr: String
        state: String
        products: [Product]
        product(productId: Long): Product
        deliveries: [Delivery]
        delivery(deliveryId: Long): Delivery
    }
    
    type Product {
        id: Long! 
        name: String
        price: Integer
        stock: Integer
        imageUrl: String
    }
    
    type Query {
      orders: [Order]
      order(orderId: Long!): Order
      deliveries: [Delivery]
      delivery(deliveryId: Long!): Delivery
      products: [Product]
      product(productId: Long!): Product
    }
`;

export default typeDefs;

