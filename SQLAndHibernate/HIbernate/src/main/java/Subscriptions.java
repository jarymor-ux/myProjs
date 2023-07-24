import jakarta.persistence.*;
import java.util.Date;
@Entity
@Table(name = "Subscriptions")
public class Subscriptions {
    @EmbeddedId
    private SubscriptionKey  subscriptionKey;
    @Column(name = "subscription_date")
    private Date subscriptionDate;
}
