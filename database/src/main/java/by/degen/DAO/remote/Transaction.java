package by.degen.DAO.remote;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction {

    Connection connection;
    public void initialize (AbstractDatabaseDAO<?> ... daos){
        if (connection == null) {
            try {
                connection = ConnectionManager.createConnection();
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (AbstractDatabaseDAO<?> dao : daos)
            dao.setConnection(connection);

    }

    public void end(){
        try {
            connection.setAutoCommit(true);
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void rollback(){
        try {
            connection.rollback();
        }catch (SQLException e){
            System.out.println("Transaction cancelled!");
            e.printStackTrace();
        }
    }

    public void commit(){
        try {
            connection.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
