import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection
{
    private static Connection connection;

    
    private static final String MYSQL_HOST = "localhost:3306";

    //private static final String MYSQL_HOST = "localhost:3306";

    // Доступ логин и пароль к базе данных
    private static final String MYSQL_USER = "bestuser";
    private static final String MYSQL_PASSWORD = "bestuser";

    // Название базы данных
    private static final String MYSQL_DATABASE = "my_db";


    
    public static Connection getConnection()
    {
        if (connection == null)
        {
            try
            {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://" + MYSQL_HOST + "/" + MYSQL_DATABASE + "?serverTimezone=UTC",
                        MYSQL_USER, MYSQL_PASSWORD
                );
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT NOT NULL, " +
                        "birthDate DATE NOT NULL, " +
                        "`count` INT NOT NULL, " +
                        "PRIMARY KEY(id)," +
                        "UNIQUE KEY `name_birthDate` (`name`(50),`birthDate`)," +
                        "KEY `count` (`count`))");
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return connection;
    }


    public static void multiInsertVotersCount(List<Voter> voters)
            throws SQLException
    {
        ArrayList<String> q_values = new ArrayList<>();
        ArrayList<Object> p_values = new ArrayList<>();

        for (Voter v : voters)
        {
            q_values.add("(?, ?, 1)");
            p_values.add(v.getName());
            p_values.add(v.getBirthDayString());
        }

        String sql = "INSERT INTO voter_count (`name`, `birthDate`, `count`) " +
                "VALUES " + String.join(",", q_values) +
                "ON DUPLICATE KEY UPDATE `count`=`count` + 1"
                ;

        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        int count = 0;

        for (Object o : p_values)
        {
            count += 1;
            if (o instanceof Integer)
            {
                stmt.setInt(count, (int)o);
            }
            if (o instanceof String)
            {
                stmt.setString(count, (String)o);
            }
        }

        stmt.executeUpdate();
    }


    public static void printVoterCounts() throws SQLException
    {
        String sql = "SELECT name, birthDate, `count` "+
                "FROM voter_count WHERE `count` > 1 ORDER BY `name`";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while (rs.next())
        {
            System.out.println("\t" + rs.getString("name") + " (" +
                    rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
    }
}
