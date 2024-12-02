package entityManagers;

import by.degen.DAO.DAOException;
import by.degen.DAO.remote.AccountDatabaseDAO;
import by.degen.DAO.remote.SavingDatabaseDAO;
import by.degen.DAO.remote.Transaction;
import by.degen.entities.Currency;
import by.degen.entities.Saving;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

public class SavingManager {
    protected static Optional<Saving> getSaving(int requestedSaving){
        SavingDatabaseDAO savingDAO = new SavingDatabaseDAO();
        Transaction transaction = new Transaction();
        transaction.initialize(savingDAO);
        Saving saving = null;
        try{
            saving = savingDAO.findById(requestedSaving);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
        }finally {
            transaction.end();
        }
        return Optional.ofNullable(saving);
    }

    protected static Saving createSaving(HashMap<String,String> params, int accountId){
        Saving newSaving = new Saving(new BigDecimal(0), Currency.valueOf(params.get("saving_currency")), 
                new BigDecimal(params.get("interest")), Boolean.valueOf(params.get("deposit")), Boolean.valueOf(params.get("capitalisation")));
        SavingDatabaseDAO savingDAO = new SavingDatabaseDAO();
        Transaction transaction = new Transaction();
        transaction.initialize(savingDAO);
        try{
            savingDAO.create(newSaving);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
        }finally {
            transaction.end();
        }
        return newSaving;
    }

    protected static void deleteSaving(int savingToDelete){
        SavingDatabaseDAO savingDAO = new SavingDatabaseDAO();
        Transaction transaction = new Transaction();
        transaction.initialize(savingDAO);
        try{
            savingDAO.delete(savingToDelete);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
        }finally {
            transaction.end();
        }
    }

    protected static void updateSaving(Saving updatedSaving, int savingToUpdate){
        SavingDatabaseDAO savingDAO = new SavingDatabaseDAO();
        Transaction transaction = new Transaction();
        transaction.initialize(savingDAO);
        try{
            savingDAO.update(updatedSaving, savingToUpdate);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
        }finally {
            transaction.end();
        }
    }
}
