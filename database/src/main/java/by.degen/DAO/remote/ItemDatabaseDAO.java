package by.degen.DAO.remote;

import by.degen.DAO.DAOException;
import by.degen.DAO.mappers.ItemMapper;
import by.degen.entities.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDatabaseDAO extends AbstractDatabaseDAO<Item>{

    @Override
    public Item findById(int id) throws DAOException {
        String quarry = "SELECT * FROM item WHERE item_id = (?)";
        try (PreparedStatement statement = connection.prepareStatement(quarry, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            result.next();
            return ItemMapper.getItem(result);
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    @Override
    public Item findByName(String name) throws DAOException {
        String quarry = "SELECT * FROM item WHERE title = (?)";
        try (PreparedStatement statement = connection.prepareStatement(quarry, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            result.next();
            return ItemMapper.getItem(result);
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    @Override
    public List<Item> findAll() throws DAOException {
        String quarry = "SELECT * FROM item";
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet result = statement.executeQuery(quarry);
            ArrayList<Item> allItems = new ArrayList<>();
            while (result.next()){
                allItems.add(ItemMapper.getItem(result));
            }
            return allItems;
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    @Override
    public void create(Item item) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public void create(Item item, int accountId) throws DAOException {
        try {
            ItemMapper.uploadItem(connection, item, accountId);
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    @Override
    public void update(Item item, int itemId) throws DAOException {
        try {
            if (ItemMapper.updateItem(connection, item, itemId) == 0){
                throw new DAOException("No target account found!");
            }
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    public void update(Item item) throws DAOException {
        update(item, item.getItemId());
    }

    @Override
    public void delete(Item item) throws DAOException {
        if (item.getItemId() != null) {
            delete(item.getItemId());
        } else {
            throw new DAOException("No target element found!");
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM item WHERE item_id = (?)")){
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }
}
