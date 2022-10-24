package util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static ComboPooledDataSource dataSource;

    static {
        //static block
        //code inside the static block is executed only once:
        // the first time the class is loaded into memory. and before Constructors.
        Properties props = new Properties();
        try {
//            props.load(new FileInputStream("src/main/resources/dbconfig.properties"));
            InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("dbconfig.properties");
            props.load(inputStream);
            String url = props.getProperty("url");
            String username = props.getProperty("username");
            String password = props.getProperty("password");

            dataSource = new ComboPooledDataSource();
//            dataSource.setJdbcUrl(url);
//            dataSource.setUser(username);
//            dataSource.setPassword(password);
//
//            dataSource.setMinPoolSize(3);
//            dataSource.setAcquireIncrement(5);
//            dataSource.setMaxPoolSize(20);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource(){
        return dataSource;
    }

    public static Connection getConnection()  {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
