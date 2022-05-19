package com.example.template;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
@FeignClient(name ="delivery", url="${api.url.delivery}")
public interface DeliveryService {

    @RequestMapping(method = RequestMethod.POST, value = "/deliveries", consumes = "application/json")
    void startDelivery(Delivery delivery);

    @RequestMapping(method = RequestMethod.PATCH, value = "/deliveries/{id}", consumes = "application/json")
    void updateDelivery(@PathVariable("id") Long id, Delivery delivery);

}
