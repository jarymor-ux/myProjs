import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/skillbox?allowPublicKeyRetrieval=true&useSSL=false";
        String usr = "root";
        String pass = "testtest";
        try {
            Connection connection = DriverManager.getConnection(url, usr, pass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery
                    ("SELECT" +
                            " course_name," +
                            " COUNT(*) / (DATEDIFF(MAX(subscription_date)," +
                            " MIN(subscription_date)) / 30) AS average_purchases_per_month" +
                            " FROM" +
                            " PurchaseList" +
                            " WHERE" +
                            " YEAR(subscription_date) = 2018" +
                            " GROUP BY" +
                            " course_name;");
            while (resultSet.next()){
                String courseName = resultSet.getString("course_name");
                double averagePurchasesPerMonth = resultSet.getDouble("average_purchases_per_month");
                System.out.println(courseName + " - " + averagePurchasesPerMonth);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}


//Напишите код, который выведет среднее количество покупок в месяц для каждого курса за 2018 год.
// Учитывайте диапазон месяцев, в течение которых были продажи. Подробнее в примере ниже
// (раздел «Советы и рекомендации»).