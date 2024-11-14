package by.degen.DAO.local;

import by.degen.DAO.DAOException;
import by.degen.DAO.Dao;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalDAO<T> implements Dao<T> {
    protected List<T> entityList;

    public AbstractLocalDAO(List<T> list){
        this.entityList = list;
    }
    @Override
    public T findById(int id) throws DAOException {
        try {
            return entityList.get(id);
        } catch (NullPointerException e){
            throw new DAOException("No such element!");
        }
    }

    @Override
    public List<T> findAll() throws DAOException {
        try {
            return new ArrayList<>(entityList);
        } catch (NullPointerException e){
            throw new DAOException("No such elements!");
        }
    }

    @Override
    public abstract T findByName(String name) throws DAOException;

    @Override
    public void create(T entity) throws DAOException {
        entityList.add(entity);
    }

    @Override
    public abstract void update(T entity, int id) throws DAOException;

    @Override
    public void delete(T entity){
        entityList.removeIf(e -> e.equals(entity));
    }

    @Override
    public void delete(int id) throws DAOException {
        if (0 < id && id <= entityList.size()){
            entityList.remove(id);
        } else throw new DAOException("Index is out of bounds of the List!");

    }
}
