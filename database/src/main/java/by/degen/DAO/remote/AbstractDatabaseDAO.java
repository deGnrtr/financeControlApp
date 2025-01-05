package by.degen.DAO.remote;

import by.degen.DAO.DAOException;
import by.degen.DAO.Dao;
import java.sql.Connection;
import java.util.List;

public abstract class AbstractDatabaseDAO<E> implements Dao<E> {
    protected Connection connection;

    protected void setConnection(Connection connection){
        this.connection = connection;
    }

    @Override
    public abstract E findById(int id) throws DAOException;

    @Override
    public abstract E findByName(String name) throws DAOException;

    @Override
    public abstract List<E> findAll() throws DAOException;

    @Override
    public abstract void create(E entity) throws DAOException;

    @Override
    public abstract void update(E entity, int id) throws DAOException;

    @Override
    public abstract void delete(E entity) throws DAOException;

    @Override
    public abstract void delete(int id) throws DAOException;

//    protected void closeStatement(Statement statement) throws DAOException{
//        if (statement != null){
//            try {
//                statement.close();
//            } catch (SQLException e){
//                throw new DAOException("Can't close the Statement object", e);
//            }
//        }
//    }

}
