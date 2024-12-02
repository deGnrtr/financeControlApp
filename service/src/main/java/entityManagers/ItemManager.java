package entityManagers;

import by.degen.DAO.DAOException;
import by.degen.DAO.remote.ItemDatabaseDAO;
import by.degen.DAO.remote.Transaction;
import by.degen.entities.Currency;
import by.degen.entities.Item;
import by.degen.entities.TimePeriod;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

public class ItemManager {
    protected static Optional<Item> getItem(int requestedItem){
        ItemDatabaseDAO itemDAO = new ItemDatabaseDAO();
        Transaction transaction = new Transaction();
        transaction.initialize(itemDAO);
        Item item = null;
        try{
            item = itemDAO.findById(requestedItem);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
        }finally {
            transaction.end();
        }
        return Optional.ofNullable(item);
    }

    protected static Item createSaving(HashMap<String,String> params,int accountId){
        Item newItem = new Item("item_title", new BigDecimal(params.get("item_amount")), Currency.valueOf(params.get("item_currency")),
                TimePeriod.valueOf(params.get("item_time_period")), "icon");
        ItemDatabaseDAO itemDAO = new ItemDatabaseDAO();
        Transaction transaction = new Transaction();
        transaction.initialize(itemDAO);
        try{
            itemDAO.create(newItem, accountId);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
        }finally {
            transaction.end();
        }
        return newItem;
    }

    protected static void deleteItem(int itemToDelete){
        ItemDatabaseDAO itemDAO = new ItemDatabaseDAO();
        Transaction transaction = new Transaction();
        transaction.initialize(itemDAO);
        try{
            itemDAO.delete(itemToDelete);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
        }finally {
            transaction.end();
        }
    }

    protected static void updateItem(Item updatedItem, int itemToUpdate){
        ItemDatabaseDAO itemDAO = new ItemDatabaseDAO();
        Transaction transaction = new Transaction();
        transaction.initialize(itemDAO);
        try{
            itemDAO.update(updatedItem, itemToUpdate);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
        }finally {
            transaction.end();
        }
    }
}
