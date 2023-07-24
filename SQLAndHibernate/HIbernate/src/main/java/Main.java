import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "select student_id, course_id from Subscriptions";

        List<Object[]> keys = session.createNativeQuery(sql).list();
        List<LinkedPurchaseList> purchaseList = new ArrayList<>();
        for(Object[] key : keys) {
            int studentId = (int) key[0];
            int courseId = (int) key[1];
            LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList(new LinkedPurchaseListKey(studentId, courseId));
            purchaseList.add(linkedPurchaseList);
            session.persist(linkedPurchaseList);
        }

        transaction.commit();
        session.close();
        sessionFactory.close();
    }
}
