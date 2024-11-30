package by.degen.DAO.remote;

import by.degen.DAO.Dao;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Transaction {
    Connection connection;
    public <T> void initialize (AbstractDatabaseDAO<?> ... daos){
        if (connection == null){
            try {
                connection = ConnectionManager.getManager().createConnection();
                connection.setAutoCommit(false);
           }catch (SQLException e){
               e.printStackTrace();
           }
        }
        for (AbstractDatabaseDAO<?> dao : daos) {
            dao.setConnection(connection);
        }
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
