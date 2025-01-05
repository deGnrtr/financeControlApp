package by.degen.DAO.local;

import by.degen.entities.Account;
import by.degen.DAO.DAOException;
import java.util.List;

public class AccountLocalDAO extends AbstractLocalDAO<Account>{

    public AccountLocalDAO(List<Account> list) {
        super(list);
    }

    @Override
    public Account findByName(String name) throws DAOException {
        Account requestedAccount = null;
        for (Account temp : entityList) {
            if (temp.getName().equals(name)) {
                requestedAccount = temp;
            }
        }
        if (requestedAccount == null) {
            throw new DAOException("Account doesn't exist!");
        } else return requestedAccount;
    }

    @Override
    public void update(Account entity, int Id) throws DAOException {
        for (Account temp : entityList) {
            if (temp.getAccountId() == Id) {
                entityList.set(entityList.indexOf(temp), entity);
            }
        }
    }


}
