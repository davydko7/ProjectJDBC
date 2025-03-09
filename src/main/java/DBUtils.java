import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    private static final String DB_URL = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:init.sql'";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "";

    // Приватный конструктор, чтобы никто не создавал экземпляры DBUtils
    private DBUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных", e);
        }
    }
}