package shopping.domain;

import java.util.Date;
import lombok.Data;
import shopping.domain.*;
import shopping.infra.AbstractEvent;

@Data
public class Shipped extends AbstractEvent {

    private Long id;
    private String productId;

    public Shipped() {
        super();
    }
    // keep

}
