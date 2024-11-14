package by.degen.DAO.mappers;

import by.degen.entities.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class ItemMapper {
    public static Item getItem (ResultSet resultSet) throws SQLException {
        String itemTitle = resultSet.getString("title");
        BigDecimal itemAmount = resultSet.getBigDecimal("item_amount");
        Currency itemCurrency = Currency.valueOf(resultSet.getString("item_currency"));
        TimePeriod itemPeriod = TimePeriod.valueOf(resultSet.getString("time_period"));
        String itemIcon = resultSet.getString("icon");
        Item requestedItem = new Item(itemTitle, itemAmount, itemCurrency, itemPeriod, itemIcon);
        requestedItem.setItemId(resultSet.getInt("item_id"));
        return requestedItem;
    }

    public static void uploadItem (Connection conn, Item item, int accountId) throws SQLException {
        String sql = "INSERT INTO item (title, item_amount, item_currency, time_period, icon, account_id) " +
                "VALUES (?, ?, CAST(? AS currency), CAST(? AS time_period), ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, item.getTitle());
        statement.setBigDecimal(2, item.getAmount());
        statement.setString(3, item.getCurrency().toString());
        statement.setString(4, item.getPeriod().toString());
        statement.setString(5, item.getIcon());
        statement.setInt(6, accountId);
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        item.setItemId(resultSet.getInt(1));
        statement.close();
    }

    public static void uploadItemBatch (Connection conn, List<Item> items, int accountId) throws SQLException {
        String sql = "INSERT INTO item (title, item_amount, item_currency, time_period, icon, account_id) " +
                "VALUES (?, ?, CAST(? AS currency), CAST(? AS time_period), ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for (Item item : items) {
            statement.setString(1, item.getTitle());
            statement.setBigDecimal(2, item.getAmount());
            statement.setString(3, item.getCurrency().toString());
            statement.setString(4, item.getPeriod().toString());
            statement.setString(5, item.getIcon());
            statement.setInt(6, accountId);
            statement.addBatch();
        }
        int[] uploaded = statement.executeBatch();
        ResultSet resultSet = statement.getGeneratedKeys();
        int i = 0;
        while (resultSet.next()){
            items.get(i).setItemId(resultSet.getInt(1));
            i++;
        }
        statement.close();
    }

    public static int updateItem (Connection conn, Item item, int itemId) throws SQLException {
        String sql = "UPDATE item SET (title, item_amount, item_currency, time_period, icon) = " +
                "(?, ?, CAST(? AS currency), CAST(? AS time_period), ?) WHERE item.item_id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, item.getTitle());
        statement.setBigDecimal(2, item.getAmount());
        statement.setString(3, item.getCurrency().toString());
        statement.setString(4, item.getPeriod().toString());
        statement.setString(5, item.getIcon());
        statement.setInt(6, itemId);
        int updatedRows = statement.executeUpdate();
        statement.close();
        return updatedRows;
    }

    public static int[] updateItemBatch (Connection conn, List<Item> items) throws SQLException  {
        String sql = "UPDATE item SET (title, item_amount, item_currency, time_period, icon) = " +
                "(?, ?, CAST(? AS currency), CAST(? AS time_period), ?) WHERE item.item_id = (?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        for (int i =0; i < items.size(); i++) {
            statement.setString(1, items.get(i).getTitle());
            statement.setBigDecimal(2, items.get(i).getAmount());
            statement.setString(3, items.get(i).getCurrency().toString());
            statement.setString(4, items.get(i).getPeriod().toString());
            statement.setString(5, items.get(i).getIcon());
            statement.setInt(6, items.get(i).getItemId());
            statement.addBatch();
        }
        int[] updatedRows = statement.executeBatch();
        statement.close();
        return updatedRows;
    }
}
