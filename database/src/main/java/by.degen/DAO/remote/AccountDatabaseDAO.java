package by.degen.DAO.remote;

import by.degen.DAO.DAOException;
import by.degen.DAO.mappers.AccountMapper;
import by.degen.DAO.mappers.ItemMapper;
import by.degen.DAO.mappers.SavingMapper;
import by.degen.DAO.mappers.UserMapper;
import by.degen.entities.Account;
import by.degen.entities.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDatabaseDAO extends AbstractDatabaseDAO<Account>{

    @Override
    public Account findById(int id) throws DAOException {
        String quarry = "SELECT * FROM all_accounts" +
                "WHERE account_id = (?)";
        try (PreparedStatement statement = connection.prepareStatement(quarry, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            result.next();
            return AccountMapper.getAccount(result);
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    @Override
    public Account findByName(String name) throws DAOException {
        String quarry = "SELECT * FROM all_accounts" +
                "WHERE account_name = (?)";
        try (PreparedStatement statement = connection.prepareStatement(quarry, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            result.next();
            return AccountMapper.getAccount(result);
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    @Override
    public List<Account> findAll() throws DAOException {
        String quarry = "SELECT * FROM all_accounts";
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet result = statement.executeQuery(quarry);
            ArrayList<Account> allAccounts = new ArrayList<>();
            result.next();
            allAccounts.add(AccountMapper.getAccount(result));
            while (result.next()){
                if (allAccounts.getLast().getAccountId() != result.getInt("account_id")){
                    allAccounts.add(AccountMapper.getAccount(result));
                }
            }
            return allAccounts;
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    @Override
    public void create(Account account) throws DAOException {
        try {
            UserMapper.uploadUser(connection, account.getUser());
            SavingMapper.uploadSaving(connection, account.getSaving());
            AccountMapper.uploadAccount(connection, account);
            List<Item> items = account.getIncomes();
            items.addAll(account.getOutcomes());
            ItemMapper.uploadItemBatch(connection, items, account.getAccountId());
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    @Override
    public void update(Account account, int accountID) throws DAOException {
        try {
            List<Item> items = account.getIncomes();
            items.addAll(account.getOutcomes());
            int[] updatedItems = ItemMapper.updateItemBatch(connection, items);
            for (int i = 0; i < updatedItems.length; i++) {
                if (updatedItems[i] == 0){
                    ItemMapper.uploadItem(connection, items.get(i), accountID);
                }
            }
            SavingMapper.updateSaving(connection, account.getSaving(), account.getSaving().getSavingId());
            if (AccountMapper.updateAccount(connection, account, accountID) == 0){
                throw new DAOException("No target account found!");
            }
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    public void update(Account account) throws DAOException {
        update(account, account.getAccountId());
    }

    @Override
    public void delete(Account account) throws DAOException {
        if (account.getAccountId() != null) {
            delete(account.getAccountId());
        } else {
            throw new DAOException("No target element found!");
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM account WHERE account_id = (?)")){
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }
}
