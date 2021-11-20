package conf;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionProvider {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/iasa-java";
    static final String USER = "postgres";
    static final String PASS = "123";

//    private static BasicDataSource ds = new BasicDataSource();
//
//    static {
//        ds.setUrl(DB_URL);
//        ds.setUsername(USER);
//        ds.setPassword(PASS);
//        ds.setMinIdle(5);
//        ds.setMaxIdle(10);
//        ds.setMaxOpenPreparedStatements(100);
//    }

    public static Connection getConnection() throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:postgresql://localhost:5432/iasa-java");
        ds.setUsername("postgres");
        ds.setPassword("123");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
        return ds.getConnection();
    }
}
