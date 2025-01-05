package by.degen.entityManagers;

import by.degen.DAO.DAOException;
import by.degen.DAO.remote.AccountDatabaseDAO;
import by.degen.DAO.remote.Transaction;
import by.degen.entities.Account;
import by.degen.entities.User;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

public class AccountManager {
    protected static Optional<Account> getAccount(int requestedAccount){
        AccountDatabaseDAO accountDAO = new AccountDatabaseDAO();
        Transaction transaction = new Transaction();
        transaction.initialize(accountDAO);
        Account account = null;
        try{
            account = accountDAO.findById(requestedAccount);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
        }finally {
            transaction.end();
        }
        return Optional.ofNullable(account);
    }

    protected static Account createAccount(HashMap<String,String> params, User user){
        Account newAccount = new Account(params.get("accountName"), user, new Date());
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
        return newAccount;
    }

    protected static void deleteAccount(int accountToDelete){
        AccountDatabaseDAO accountDAO = new AccountDatabaseDAO();
        Transaction transaction = new Transaction();
        transaction.initialize(accountDAO);
        try{
            accountDAO.delete(accountToDelete);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
        }finally {
            transaction.end();
        }
    }

    protected static void updateAccount(Account updatedAccount, int accountToUpdate){
        AccountDatabaseDAO accountDAO = new AccountDatabaseDAO();
        Transaction transaction = new Transaction();
        transaction.initialize(accountDAO);
        try{
            accountDAO.update(updatedAccount, accountToUpdate);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
        }finally {
            transaction.end();
        }
    }
}
