package by.degen.DAO.mappers;

import by.degen.entities.*;
import java.math.BigDecimal;
import java.sql.*;

public class SavingMapper {
    public static Saving getSaving (ResultSet resultSet) throws SQLException {
        BigDecimal savingAmount = resultSet.getBigDecimal("saving_amount");
        Currency savingCurrency = Currency.valueOf(resultSet.getString("saving_currency"));
        BigDecimal interest = resultSet.getBigDecimal("interest");
        boolean deposit = resultSet.getBoolean("deposit");
        boolean capitalization = resultSet.getBoolean("capitalization");
        Saving requestedSaving = new Saving(savingAmount, savingCurrency, interest, deposit, capitalization);
        requestedSaving.setSavingId(resultSet.getInt("saving_id"));
        return requestedSaving;
    }

    public static void uploadSaving (Connection conn, Saving saving) throws SQLException {
        String sql = "INSERT INTO saving (saving_amount, " +
                "saving_currency, interest, deposit, capitalization) VALUES (?, CAST(? AS currency), ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setBigDecimal(1, saving.getAmount());
        statement.setString(2, saving.getCurrency().toString());
        statement.setBigDecimal(3, saving.getInterest());
        statement.setBoolean(4, saving.isDeposit());
        statement.setBoolean(5, saving.isCapitalization());
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        saving.setSavingId(resultSet.getInt(1));
        statement.close();
    }

    public static int updateSaving(Connection conn, Saving saving, int savingId) throws SQLException {
        String sql = "UPDATE saving SET (saving_amount, saving_currency, interest, deposit, capitalization) =" +
                "(?, CAST(? AS currency), ?, ?, ?) WHERE saving_id = (?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setBigDecimal(1, saving.getAmount());
        statement.setString(2, saving.getCurrency().toString());
        statement.setBigDecimal(3, saving.getInterest());
        statement.setBoolean(4, saving.isDeposit());
        statement.setBoolean(5, saving.isCapitalization());
        statement.setInt(6, savingId);
        statement.executeUpdate();
        int updatedRows = statement.executeUpdate();
        statement.close();
        return updatedRows;
    }
}
