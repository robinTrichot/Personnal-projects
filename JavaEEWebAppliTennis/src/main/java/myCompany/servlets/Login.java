package myCompany.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import myCompany.beans.User;
import myCompany.dao.DaoFactory;
import myCompany.dao.UserDaoImpl;
import myCompany.utils.HashClass;
import myCompany.utils.SessionUtils;

import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDaoImpl userdaoimpl;

    public Login() {
        super();
    }

    @Override
    public void init() throws ServletException {
        DaoFactory dao2 = DaoFactory.getInstance();
        userdaoimpl = new UserDaoImpl(dao2);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.getAttribute("isExpired");
        this.getServletContext().getRequestDispatcher("/WEB-INF/testlog.jsp").forward(request, response);
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("txtLogin");
        String password = request.getParameter("txtPassword");
        User connectedUser = userdaoimpl.isValidLogin(login, password);

        System.out.println(HashClass.sha1(password));
        if (connectedUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("ConnectedUser", connectedUser);
            int resultProfil = connectedUser.getProfil();
            request.setAttribute("resultProfil", resultProfil);
            boolean result = SessionUtils.isAdmin(connectedUser);
            session.setAttribute("isAdmin", result);
            response.sendRedirect(request.getContextPath() + "/ListJoueur");
        } else {
            doGet(request, response);
        }
    }
}