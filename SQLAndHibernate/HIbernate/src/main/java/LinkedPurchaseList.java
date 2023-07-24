import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "LinkedPurchaseList")
public class LinkedPurchaseList {
    public LinkedPurchaseList(LinkedPurchaseListKey linkedPurchaseListKey) {
        this.linkedPurchaseListKey = linkedPurchaseListKey;
    }

    @EmbeddedId
    LinkedPurchaseListKey linkedPurchaseListKey;
}
