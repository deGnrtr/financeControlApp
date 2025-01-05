package by.degen.DAO.mappers;

import by.degen.DAO.DAOException;
import by.degen.entities.Account;
import by.degen.entities.Item;
import by.degen.entities.Saving;
import by.degen.entities.User;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountMapper {
    public static Account getAccount(ResultSet result) throws DAOException {
        try {
            String accountName = result.getString("account_name");
            User user = UserMapper.getUser(result);
            Date lastSeen = LastSeenDateMapper.getDate(result);
            Saving saving = SavingMapper.getSaving(result);
            String note = result.getString("note");
            List<Item> incomes = new ArrayList<>();
            List<Item> outcomes = new ArrayList<>();
            Account requstedAccount = new Account(accountName, user, lastSeen, incomes, outcomes, saving, note);
            requstedAccount.setAccountId(result.getInt("account_id"));
            int currentPosition = result.getRow();
            result.beforeFirst();
            while (result.next()) {
                Item item = ItemMapper.getItem(result);
                if (item.getAmount().signum() > 0 && result.getInt("account_id") == requstedAccount.getAccountId()) {
                    requstedAccount.setIncomes(item);
                } else if (result.getInt("account_id") == requstedAccount.getAccountId()){
                   requstedAccount.setOutcomes(item);
                }
            }
            result.absolute(currentPosition);
            return requstedAccount;
        } catch (SQLException e) {
            throw new DAOException("SQLException!" + e.getMessage(), e);
        }
    }

    public static void uploadAccount(Connection conn, Account account) throws SQLException {
        String sql = "INSERT INTO account (account_name, user_id, saving_id, last_seen, note) " +
                "VALUES (?, ?, ?, CAST(? AS timestamptz), ?)";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, account.getName());
        statement.setInt(2, account.getUser().getUserId());
        if (account.getSaving().isPresent()) {
            statement.setInt(3, account.getSaving().get().getSavingId());
        }
        statement.setString(4, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss X").format(account.getLastSeen()));
        statement.setString(5, account.getNote());
        statement.executeUpdate();
        ResultSet result = statement.getGeneratedKeys();
        result.next();
        account.setAccountId(result.getInt(1));
        statement.close();
    }

    public static int updateAccount(Connection conn, Account account, int accountId) throws SQLException {
        String sql = "UPDATE account SET account_name = ?" +
                ", user_id = ?, saving_id = ?, last_seen = CAST(? AS timestamptz), note = ? WHERE account_id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, account.getName());
        statement.setInt(2, account.getUser().getUserId());
        statement.setInt(3, account.getSaving().get().getSavingId());
        statement.setString(4, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(account.getLastSeen()));
        statement.setString(5, account.getNote());
        statement.setInt(6, accountId);
        statement.executeUpdate();
        int updatedRows = statement.executeUpdate();
        statement.close();
        return updatedRows;
    }
}
