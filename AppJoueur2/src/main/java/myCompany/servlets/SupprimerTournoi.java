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


@WebServlet("/SupprimerTournoi")
public class SupprimerTournoi extends HttpServlet {

    private TournoiDao tournoiDao;

    private Long id;

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


        if (!result) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        } else {
            try {
                id = Long.valueOf(request.getParameter("id"));
                tournoiDao.delete(id);
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
        }
        response.sendRedirect(request.getContextPath() + "/ListTournoi");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
