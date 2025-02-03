package by.degen.servlets;

import by.degen.entities.Account;
import by.degen.entityManagers.AccountManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet (urlPatterns = "/test", name = "Base servlet for test")
public class BaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String outputTest;
        int id = Integer.parseInt(req.getParameter("accountId"));
        Optional<Account> accountWrapped = AccountManager.getAccount(id);
        if (accountWrapped.isPresent()){
            Account account = accountWrapped.get();
            outputTest = account.getName();
        } else outputTest = "placeholder";
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter result = resp.getWriter();
        result.print(outputTest);
        result.flush();
    }
}
