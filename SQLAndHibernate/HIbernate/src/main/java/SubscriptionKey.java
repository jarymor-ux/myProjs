import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SubscriptionKey implements Serializable {
//    @ManyToOne(cascade = CascadeType.ALL)
    @Column(name = "student_id")
    private int studentId;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public SubscriptionKey(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    //    @ManyToOne(cascade = CascadeType.ALL)
    @Column(name = "course_id")
    private int courseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionKey  that = (SubscriptionKey ) o;
        return studentId == that.studentId &&
                courseId == that.courseId;
    }
    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }
}
