package shopping.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import shopping.SupportingApplication;
import shopping.domain.InventoryIncreased;
import shopping.domain.InventoryReduced;

@Entity
@Table(name = "Inventory_table")
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @PostPersist
    public void onPostPersist() {
        InventoryReduced inventoryReduced = new InventoryReduced();
        BeanUtils.copyProperties(this, inventoryReduced);
        inventoryReduced.publishAfterCommit();

        InventoryIncreased inventoryIncreased = new InventoryIncreased();
        BeanUtils.copyProperties(this, inventoryIncreased);
        inventoryIncreased.publishAfterCommit();
    }

    public static InventoryRepository repository() {
        InventoryRepository inventoryRepository = SupportingApplication.applicationContext.getBean(
            InventoryRepository.class
        );
        return inventoryRepository;
    }

    public static void reduceInventory(Shipped shipped) {
        Inventory inventory = new Inventory();
        /*
        LOGIC GOES HERE
        */
        // repository().save(inventory);

    }

    public static void updateStock(DeliveryCanceled deliveryCanceled) {
        Inventory inventory = new Inventory();
        /*
        LOGIC GOES HERE
        */
        // repository().save(inventory);

    }
    // keep

}
