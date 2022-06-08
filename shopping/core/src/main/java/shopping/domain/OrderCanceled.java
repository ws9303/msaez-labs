package shopping.domain;

import java.util.Date;
import lombok.Data;
import shopping.domain.*;
import shopping.infra.AbstractEvent;

@Data
public class OrderCanceled extends AbstractEvent {

    private Long id;

    public OrderCanceled() {
        super();
    }
    // keep

}
