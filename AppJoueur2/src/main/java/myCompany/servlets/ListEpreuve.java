package myCompany.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import myCompany.dao.*;
import myCompany.utils.SessionUtils;

import java.io.IOException;

@WebServlet("/ListEpreuve")
public class ListEpreuve extends HttpServlet {

    private EpreuveDao epreuveDao;

    @Override
    public void init() throws ServletException {
        DaoFactory dao2 = DaoFactory.getInstance();
        epreuveDao = new EpreuveDaoImpl(dao2);

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
        this.getServletContext().getRequestDispatcher("/WEB-INF/listEpreuve.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String entreUser = (request.getParameter("txtSearch"));
        HttpSession session = request.getSession(true);
        //  String deroulant = (request.getParameter("deroulant"));

        String firstT = "Pour l'année : ";
        String complementation = firstT + entreUser;
        request.setAttribute("anneeEntreee", complementation);

        if (session.getAttribute("ConnectedUser") == null) {
            //   boolean isExpired = true;
            //   session.setAttribute("isExpired", isExpired);
            response.sendRedirect(request.getContextPath() + "/login");

        }

        // la recherche
        if (request.getParameter("action1").equals("Rechercher")) {
            try {
                request.setAttribute("list2", epreuveDao.rechercher(entreUser));

            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
            this.getServletContext().getRequestDispatcher("/WEB-INF/listEpreuve.jsp").forward(request, response);
            // la deconnexion
        } else if (request.getParameter("action1").equals("Deconnexion")) {

            session.setAttribute("ConnectedUser", null);
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
    }
}
