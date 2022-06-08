package shopping.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import shopping.config.kafka.KafkaProcessor;
import shopping.domain.*;

@Service
public class PolicyHandler {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverShipped_ReduceInventory(@Payload Shipped shipped) {
        if (!shipped.validate()) return;
        Shipped event = shipped;
        System.out.println(
            "\n\n##### listener ReduceInventory : " + shipped.toJson() + "\n\n"
        );

        // Sample Logic //
        Inventory.reduceInventory(event);
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderPlaced_Ship(@Payload OrderPlaced orderPlaced) {
        if (!orderPlaced.validate()) return;
        OrderPlaced event = orderPlaced;
        System.out.println(
            "\n\n##### listener Ship : " + orderPlaced.toJson() + "\n\n"
        );

        // Sample Logic //
        Delivery.ship(event);
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCanceled_CancelDelivery(
        @Payload OrderCanceled orderCanceled
    ) {
        if (!orderCanceled.validate()) return;
        OrderCanceled event = orderCanceled;
        System.out.println(
            "\n\n##### listener CancelDelivery : " +
            orderCanceled.toJson() +
            "\n\n"
        );

        // Sample Logic //
        Delivery.cancelDelivery(event);
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryCanceled_UpdateStock(
        @Payload DeliveryCanceled deliveryCanceled
    ) {
        if (!deliveryCanceled.validate()) return;
        DeliveryCanceled event = deliveryCanceled;
        System.out.println(
            "\n\n##### listener UpdateStock : " +
            deliveryCanceled.toJson() +
            "\n\n"
        );

        // Sample Logic //
        Inventory.updateStock(event);
    }
    // keep

}
