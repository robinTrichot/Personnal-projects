package myCompany.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import myCompany.dao.*;
import myCompany.utils.SessionUtils;

import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/ListMatch")
public class ListMatch extends HttpServlet {
    private MatchDao matchDao;

    @Override
    public void init() throws ServletException {
        DaoFactory dao2 = DaoFactory.getInstance();
        matchDao = new MatchDaoImpl(dao2);
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

        try {
            request.setAttribute("list2", matchDao.lister());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/listMatch.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String entreUser = request.getParameter("txtSearch");
        HttpSession session = request.getSession(true);

        if (session.getAttribute("ConnectedUser") == null) {
            //   boolean isExpired = true;
            //   session.setAttribute("isExpired", isExpired);
            response.sendRedirect(request.getContextPath() + "/login");

        }
        if (request.getParameter("action1").equals("Rechercher")) {
            try {
                if (entreUser.equals(null) || (entreUser.length() == 0)) {
                    request.setAttribute("list2", matchDao.rechercher(entreUser));
                    request.setAttribute("listVD", matchDao.compteVicDef(entreUser));
                    request.setAttribute("nomJoueur", entreUser);

                } else {
                    entreUser = entreUser.substring(0, 1).toUpperCase() + entreUser.substring(1);
                    request.setAttribute("list2", matchDao.rechercher(entreUser));
                    request.setAttribute("listVD", matchDao.compteVicDef(entreUser));
                    request.setAttribute("nomJoueur", entreUser);
                }




            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
            this.getServletContext().getRequestDispatcher("/WEB-INF/listMatch.jsp").forward(request, response);
            // la deconnexion
        } else if (request.getParameter("action1").equals("Deconnexion")) {

            session.setAttribute("ConnectedUser", null);
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
    }
}
