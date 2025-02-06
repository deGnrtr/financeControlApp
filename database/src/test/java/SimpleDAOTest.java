import by.degen.DAO.DAOException;
import by.degen.DAO.remote.AccountDatabaseDAO;
import by.degen.DAO.remote.Transaction;
import by.degen.entities.Account;

public class SimpleDAOTest {
    public void test (){
        AccountDatabaseDAO accountDAO = new AccountDatabaseDAO();
        Transaction transaction = new Transaction();
        transaction.initialize(accountDAO);
        Account account = null;
        try{
            account = accountDAO.findById(13);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
        }finally {
            transaction.end();
        }
        System.out.println(account.getName());
    }
}
