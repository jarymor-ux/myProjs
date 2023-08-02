import entity.LinkedPurchaseList;
import entity.LinkedPurchaseListKey;
import entity.PurchasesList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class Main {
    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        logger = LogManager.getRootLogger();
        Transaction transaction = null;

        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<PurchasesList> allPurchaseList = session.createQuery("From PurchasesList").getResultList();
            for (PurchasesList purchasesList : allPurchaseList
            ) {
                Integer idStudent = purchasesList.getStudent().getId();
                Integer idCourse = purchasesList.getCourse().getId();
                LinkedPurchaseList linkedPurchaseList = session.get(LinkedPurchaseList.class,
                        new LinkedPurchaseListKey(idStudent, idCourse));
                logger.debug("debug");
                if (linkedPurchaseList.getId() == null) {
                    Query queryInsertIntoLinkedPurchaseList = session.createSQLQuery("INSERT INTO linked_purchase_list" +
                            "(student_id,course_id)" +
                            "VALUES " +
                            "(" + idStudent + "," + idCourse + ");");
                    queryInsertIntoLinkedPurchaseList.executeUpdate();
                    logger.info("Test");
                }
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}