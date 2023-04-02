package myCompany.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import myCompany.dao.DaoException;
import myCompany.dao.DaoFactory;
import myCompany.dao.JoueurDao;
import myCompany.dao.JoueurDaoImpl;
import myCompany.utils.SessionUtils;
import java.io.IOException;

@WebServlet("/SupprimerJoueur")
public class SupprimerJoueur extends HttpServlet {

    private JoueurDao joueurDao;
    private Long id;

    public void init() throws ServletException {
        DaoFactory dao2 = DaoFactory.getInstance();
        joueurDao = new JoueurDaoImpl(dao2);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Boolean result = (Boolean) session.getAttribute("isAdmin");
        session.setAttribute("isAdmin", result);

        if (!SessionUtils.isUserLoggedIn(request)) {
            response.sendRedirect("/tennis/login");
            return;
        }

        if (!result) {
            response.sendRedirect("/tennis/login");
            return;
        } else {
            id = Long.valueOf(request.getParameter("id"));
            try {
                joueurDao.delete(id);
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
        }
        response.sendRedirect(request.getContextPath() + "/ListJoueur");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
