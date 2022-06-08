package shopping.domain;

import java.util.Date;
import lombok.Data;
import shopping.domain.*;
import shopping.infra.AbstractEvent;

@Data
public class OrderPlaced extends AbstractEvent {

    private Long id;
    private String productId;
    private Integer qty;
    private String status;
    private String address;

    public OrderPlaced() {
        super();
    }
    // keep

}
