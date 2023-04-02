package myCompany.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import myCompany.dao.*;
import myCompany.utils.SessionUtils;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/ListJoueur")
public class ListJoueur extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private JoueurDao joueurDao;


    public ListJoueur() {
        super();
    }

    @Override
    public void init() throws ServletException {
        DaoFactory dao2 = DaoFactory.getInstance();
        joueurDao = new JoueurDaoImpl(dao2);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Boolean result = (Boolean) session.getAttribute("isAdmin");
        session.setAttribute("isAdmin", result);

        if (!SessionUtils.isUserLoggedIn(request)) {
            response.sendRedirect("/tennis/login");
            return;
        }

        try {
            request.setAttribute("list2", joueurDao.lister());
        } catch (SQLException | DaoException e) {
            throw new RuntimeException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/testlistjoueur.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String entreUser = request.getParameter("txtSearch");
        HttpSession session = request.getSession(true);

        if (session.getAttribute("ConnectedUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");

        }

        // la recherche
        if (request.getParameter("action1").equals("Rechercher")) {
            try {
                request.setAttribute("list2", joueurDao.rechercher(entreUser));
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
            this.getServletContext().getRequestDispatcher("/WEB-INF/testlistjoueur.jsp").forward(request, response);
        } else if (request.getParameter("action1").equals("Deconnexion")) {

            session.setAttribute("ConnectedUser", null);
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }


    }
}
