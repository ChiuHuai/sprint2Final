package util;

//import com.mchange.v2.c3p0.ComboPooledDataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static ConnectionManager instance;
    private Connection connection;

//    private static HikariDataSource dataSource;

    public ConnectionManager() {
        //setup connection

        try {
            Properties props = new Properties();
            props.load(new FileInputStream("src/main/resources/dbconfig.properties"));
            String url = props.getProperty("url");
            String username = props.getProperty("username");
            String password = props.getProperty("password");

            ComboPooledDataSource dataSource = new ComboPooledDataSource();
//            dataSource = new HikariDataSource();

            dataSource.setJdbcUrl(url);
            dataSource.setUser(username);
            dataSource.setPassword(password);

            dataSource.setMinPoolSize(3);
            dataSource.setAcquireIncrement(5);
            dataSource.setMaxPoolSize(20);

//            dataSource.setMinimumIdle(100);
//            dataSource.setMaximumPoolSize(2000);//The maximum number of connections, idle or busy, that can be present in the pool.
//            dataSource.setAutoCommit(false);
//            dataSource.setLoginTimeout(3);

//            this.connection = DriverManager.getConnection(url, username, password);
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    public static DataSource getDataSource(){
//        return dataSource;
//    }
    public static ConnectionManager getInstance() {
        try {
            if (instance == null || instance.connection.isClosed()) {
                //threadsafe
                synchronized (ConnectionManager.class) {
                    if (instance == null || instance.connection.isClosed()) {
                        instance = new ConnectionManager();
                    }

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

//    public void close() {
//        if (this.connection != null) {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }


}
