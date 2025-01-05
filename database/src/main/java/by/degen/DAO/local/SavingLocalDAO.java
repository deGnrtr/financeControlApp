package by.degen.DAO.local;

import by.degen.DAO.DAOException;
import by.degen.entities.Saving;
import java.util.List;

public class SavingLocalDAO extends AbstractLocalDAO<Saving>{

    public SavingLocalDAO(List<Saving> list) {
        super(list);
    }

    @Override
    public Saving findByName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Saving entity, int Id) throws DAOException {
        for (Saving temp : entityList) {
            if (temp.getSavingId() == Id) {
                entityList.set(entityList.indexOf(temp), entity);
            }
        }
    }
}
