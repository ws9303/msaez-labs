package shopping.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import shopping.SupportingApplication;
import shopping.domain.DeliveryCanceled;
import shopping.domain.Shipped;

@Entity
@Table(name = "Delivery_table")
@Data
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String orderId;

    private String status;

    @PostPersist
    public void onPostPersist() {
        Shipped shipped = new Shipped();
        BeanUtils.copyProperties(this, shipped);
        shipped.publishAfterCommit();

        DeliveryCanceled deliveryCanceled = new DeliveryCanceled();
        BeanUtils.copyProperties(this, deliveryCanceled);
        deliveryCanceled.publishAfterCommit();
    }

    public static DeliveryRepository repository() {
        DeliveryRepository deliveryRepository = SupportingApplication.applicationContext.getBean(
            DeliveryRepository.class
        );
        return deliveryRepository;
    }

    public static void ship(OrderPlaced orderPlaced) {
        Delivery delivery = new Delivery();
        /*
        LOGIC GOES HERE
        */
        // repository().save(delivery);

    }

    public static void cancelDelivery(OrderCanceled orderCanceled) {
        Delivery delivery = new Delivery();
        /*
        LOGIC GOES HERE
        */
        // repository().save(delivery);

    }
    // keep

}
