package by.degen.DAO.mappers;

import by.degen.DAO.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LastSeenDateMapper {
    public static Date getDate (ResultSet resultSet) throws DAOException, SQLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String lastSeenDate = resultSet.getString("last_seen");
            return dateFormat.parse(lastSeenDate);
        } catch (ParseException e) {
            throw new DAOException("Can't parse the date!", e);
        }
    }
}
