package by.degen.DAO.local;

import by.degen.DAO.DAOException;
import by.degen.entities.Item;
import java.util.List;

public class ItemLocalDao extends AbstractLocalDAO<Item>{

    Item operatedItem = null;

    public ItemLocalDao(List<Item> list) {
        super(list);
    }

    @Override
    public Item findByName(String name) throws DAOException {
        Item requestedItem = null;
        for (Item temp : entityList) {
            if (temp.getTitle().equals(name)) {
                requestedItem = temp;
            }
        }
        if (requestedItem == null) {
            throw new DAOException("Item doesn't exist!");
        } else return requestedItem;
    }

    @Override
    public void update(Item entity, int Id) throws DAOException {
        for (Item temp : entityList) {
            if (temp.getItemId() == Id) {
                entityList.set(entityList.indexOf(temp), entity);
            }
        }
    }
}
