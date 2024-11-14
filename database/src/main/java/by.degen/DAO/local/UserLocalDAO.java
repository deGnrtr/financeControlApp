package by.degen.DAO.local;

import by.degen.DAO.DAOException;
import by.degen.entities.User;

import java.util.HashMap;
import java.util.List;

public class UserLocalDAO extends AbstractLocalDAO<User> {

    public UserLocalDAO(List<User> list) {
        super(list);
    }

    @Override
    public User findByName(String name) throws DAOException {
        User requestedUser = null;
        for (User temp : entityList) {
            if (temp.getUsername().equals(name)) {
                requestedUser = temp;
            }
        }
        if (requestedUser == null) {
            throw new DAOException("User doesn't exist!");
        } else return requestedUser;
    }

    @Override
    public void update(User entity, int Id) throws DAOException {
        for (User temp : entityList) {
            if (temp.getUserId() == Id) {
                entityList.set(entityList.indexOf(temp), entity);
            }
        }
    }
}
