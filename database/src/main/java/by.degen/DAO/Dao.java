package by.degen.DAO;

import java.util.HashMap;
import java.util.List;

public interface Dao<T> {
    public T findById(int id) throws DAOException;
    public T findByName(String name) throws DAOException;
    public List<T> findAll() throws DAOException;
    public void create(T entity) throws DAOException;
    public void update(T entity, int id) throws DAOException;
    public void delete(T entity) throws DAOException;
    public void delete(int id) throws DAOException;
}
