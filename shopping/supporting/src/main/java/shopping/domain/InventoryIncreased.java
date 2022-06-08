package shopping.domain;

import java.util.Date;
import lombok.Data;
import shopping.domain.*;
import shopping.infra.AbstractEvent;

@Data
public class InventoryIncreased extends AbstractEvent {

    private Long id;

    public InventoryIncreased() {
        super();
    }
    // keep

}
