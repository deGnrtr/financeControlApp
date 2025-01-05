package by.degen.DAO.remote;

import by.degen.DAO.DAOException;
import by.degen.DAO.mappers.SavingMapper;
import by.degen.entities.Saving;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SavingDatabaseDAO extends AbstractDatabaseDAO<Saving>{

    @Override
    public Saving findById(int id) throws DAOException {
        String quarry = "SELECT * FROM saving WHERE saving_id = (?)";
        try (PreparedStatement statement = connection.prepareStatement(quarry, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            result.next();
            return SavingMapper.getSaving(result);
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    @Override
    public Saving findByName(String name) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Saving> findAll() throws DAOException {
        String quarry = "SELECT * FROM saving";
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet result = statement.executeQuery(quarry);
            ArrayList<Saving> allSavings = new ArrayList<>();
            while (result.next()){
                allSavings.add(SavingMapper.getSaving(result));
            }
            return allSavings;
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    @Override
    public void create(Saving saving) throws DAOException {
        try {
            SavingMapper.uploadSaving(connection, saving);
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    @Override
    public void update(Saving saving, int savingID) throws DAOException {
        try {
            if (SavingMapper.updateSaving(connection, saving, savingID) == 0){
                throw new DAOException("No target account found!");
            }
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    public void update(Saving saving) throws DAOException {
        update(saving, saving.getSavingId());
    }

    @Override
    public void delete(Saving saving) throws DAOException {
        if (saving.getSavingId() != null) {
            delete(saving.getSavingId());
        } else {
            throw new DAOException("No target element found!");
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM saving WHERE saving_id = (?)")){
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }
}
