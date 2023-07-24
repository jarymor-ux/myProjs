import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Courses")
public class Course {
    @Id
    private int id;
    private String name;
    private int duration;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CourseType type;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    private Teacher teacher;
    @Column(name = "students_count")
    private int studentsCount;
    private int price;
    @Column(name = "price_per_hour")
    private float pricePerOur;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Subscriptions",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")}
    )
    private List<Student> students;
}
