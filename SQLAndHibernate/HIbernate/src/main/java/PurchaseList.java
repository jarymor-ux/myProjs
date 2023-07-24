import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PurchaseList")
public class PurchaseList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "student_name")
    private String studentName;
    @Column(name = "course_name")
    private String courseName;
    private int price;
    @Column(name = "subscription_date")
    private Date subscriptionDate;
}
