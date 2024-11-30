package entityManagers;

import by.degen.DAO.DAOException;
import by.degen.DAO.remote.AccountDatabaseDAO;
import by.degen.DAO.remote.Transaction;
import by.degen.DAO.remote.UserDatabaseDAO;
import by.degen.entities.Account;
import by.degen.entities.User;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

public class UserManager {
    protected static Optional<User> getUser(int requestedUser){
        UserDatabaseDAO userDAO = new UserDatabaseDAO();
        Transaction transaction = new Transaction();
        transaction.initialize(userDAO);
        User user = null;
        try{
            user = userDAO.findById(requestedUser);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
        }finally {
            transaction.end();
        }
        return Optional.ofNullable(user);
    }

    protected static void createUser(HashMap<String,String> params){
        User newUser = new User(params.get("username"), params.get("password"));
        Account newAccount = new Account(params.get("accountName"), newUser, new Date());
        AccountDatabaseDAO accountDAO = new AccountDatabaseDAO();
        Transaction transaction = new Transaction();
        transaction.initialize(accountDAO);
        try{
            accountDAO.create(newAccount);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
        }finally {
            transaction.end();
        }
    }

    protected static void deleteUser(int userToDelete){
        UserDatabaseDAO userDAO = new UserDatabaseDAO();
        Transaction transaction = new Transaction();
        transaction.initialize(userDAO);
        try{
            userDAO.delete(userToDelete);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
        }finally {
            transaction.end();
        }
    }

    protected static void updateUser(User updatedUser, int userToUpdate){
        UserDatabaseDAO userDAO = new UserDatabaseDAO();
        Transaction transaction = new Transaction();
        transaction.initialize(userDAO);
        try{
            userDAO.update(updatedUser, userToUpdate);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
        }finally {
            transaction.end();
        }
    }
}
