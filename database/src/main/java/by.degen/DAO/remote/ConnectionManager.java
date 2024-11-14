package by.degen.DAO.remote;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    FileInputStream fileInputStream;
    Properties props = new Properties();
    private static ConnectionManager manager;

    private ConnectionManager(){}

    public static ConnectionManager getManager(){
        if (manager == null){
            manager = new ConnectionManager();
        }
        return manager;
    }

    public Connection createConnection() throws SQLException{
        try {
            fileInputStream = new FileInputStream("app/src/DAO/remote/connProp.properties");
            props.load(fileInputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
        return DriverManager.getConnection(props.getProperty("URL"), props);
    }



}
