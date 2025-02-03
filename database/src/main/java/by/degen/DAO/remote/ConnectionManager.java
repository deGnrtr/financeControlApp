package by.degen.DAO.remote;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static final Properties CONN_PROPERTIES = new Properties();
    public static Connection connection;
//    private ConnectionManager manager;

    private ConnectionManager(){}

    /*public ConnectionManager getManager(){
        if (manager == null){
            manager = new ConnectionManager();
        }
        return manager;
    }*/

    public static Connection createConnection() throws SQLException{
        try {
            InputStream InputStream = ConnectionManager.class.getResourceAsStream("/connProp.properties");
            CONN_PROPERTIES.load(InputStream);
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(CONN_PROPERTIES.getProperty("URL"), CONN_PROPERTIES);
        }catch (ClassNotFoundException c){
            c.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return connection;
    }



}
