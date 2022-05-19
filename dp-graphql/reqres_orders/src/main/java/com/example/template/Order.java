package com.example.template;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private int price;
    private String customerId;
    private String customerName;
    private String customerAddr;
    private String state = "OrderPlaced";

    @PrePersist
    private void orderCheck(){
        RestTemplate restTemplate = Application.applicationContext.getBean(RestTemplate.class);
        Environment env = Application.applicationContext.getEnvironment();

        if( productId == null ){
            throw new RuntimeException();
        }

        int price = 0;
        String productName = null;

        // 1. 주문에 대한 상품 조회 - API
        String productUrl = env.getProperty("api.url.product") + "/product/" + productId;

        ResponseEntity<String> productEntity = restTemplate.getForEntity(productUrl, String.class);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(productEntity.getBody()).getAsJsonObject();

        price = jsonObject.get("price").getAsInt();
        productName = jsonObject.get("name").getAsString();
        if( jsonObject.get("stock").getAsInt() < getQuantity()){
            throw new OrderException("No Available stock!");
        }

        this.setPrice(price);
        this.setProductName(productName);
    }

    /**
     * 주문이 들어옴
     */
    @PostPersist
    private void callDeliveryStart(){

        Delivery delivery = new Delivery();
        delivery.setOrderId(this.getId());
        delivery.setCustomerId(this.getCustomerId());
        delivery.setCustomerName(this.getCustomerName());
        delivery.setDeliveryAddress(this.getCustomerAddr());
        delivery.setProductId(this.getProductId());
        delivery.setProductName(this.getProductName());
        delivery.setQuantity(this.getQuantity());

        // 배송 서비스에 배송 시작 API 요청
//        // 1. rest call
//        RestTemplate restTemplate = Application.applicationContext.getBean(RestTemplate.class);
//        Environment env = Application.applicationContext.getEnvironment();
//        String deliveryUrl = env.getProperty("api.url.delivery") + "/deliveries";
//        try {
//            ResponseEntity<String> responseEntity = restTemplate.postForEntity(deliveryUrl, delivery, String.class);
//            String response = responseEntity.getBody();
//        } catch (Exception ex) {
//            // 배송 실패시 주문 삭제
//        }

        // 2. feign client call - 기존 monolith 의 수정이 없다.
        // 배송 시작
        DeliveryService deliveryService = Application.applicationContext.getBean(DeliveryService.class);
        deliveryService.startDelivery(delivery);
    }

    /**
     * 주문이 취소됨
     */
    @PreUpdate
    private void callDeliveryStop(){
        if( "OrderCancelled".equals(this.getState())){
            // 배송 ID 조회
            RestTemplate restTemplate = Application.applicationContext.getBean(RestTemplate.class);
            Environment env = Application.applicationContext.getEnvironment();
            String deliveryUrl = env.getProperty("api.url.delivery") + "/deliveries/search/findDeliveryIdByOrderId?orderId=" + this.getId();
            ResponseEntity<Delivery> productEntity = restTemplate.getForEntity(deliveryUrl, Delivery.class);
            Delivery delivery = productEntity.getBody();

            // 배송 서비스에 배송 중지 API 요청
            // 배송 취소
            DeliveryService deliveryService = Application.applicationContext.getBean(DeliveryService.class);
            delivery.setDeliveryState(DeliveryStatus.DeliveryCancelled.name());
            deliveryService.updateDelivery(delivery.getId(), delivery);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}