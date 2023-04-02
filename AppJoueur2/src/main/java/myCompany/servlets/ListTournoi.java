package myCompany.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import myCompany.dao.DaoException;
import myCompany.dao.DaoFactory;
import myCompany.dao.TournoiDao;
import myCompany.dao.TournoiImpl;
import myCompany.utils.SessionUtils;

import java.io.IOException;

@WebServlet("/ListTournoi")
public class ListTournoi extends HttpServlet {

    private TournoiDao tournoiDao;


    @Override
    public void init() throws ServletException {
        DaoFactory dao2 = DaoFactory.getInstance();
        tournoiDao = new TournoiImpl(dao2);
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
            request.setAttribute("list2", tournoiDao.lister());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/listTournoi.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String entreUser = request.getParameter("txtSearch");

        try {
            request.setAttribute("list2", tournoiDao.rechercher(entreUser));
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

        if (request.getParameter("action1").equals("Rechercher")) {
            try {
                request.setAttribute("list2", tournoiDao.rechercher(entreUser));
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
            this.getServletContext().getRequestDispatcher("/WEB-INF/listTournoi.jsp").forward(request, response);
            // la deconnexion
        } else if (request.getParameter("action1").equals("Deconnexion")) {

            session.setAttribute("ConnectedUser", null);
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
    }
}
