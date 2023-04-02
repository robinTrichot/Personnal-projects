package myCompany.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import myCompany.beans.BeanException;
import myCompany.beans.Tournoi;
import myCompany.dao.DaoException;
import myCompany.dao.DaoFactory;
import myCompany.dao.TournoiDao;
import myCompany.dao.TournoiImpl;
import myCompany.utils.SessionUtils;

import java.io.IOException;

@WebServlet("/ModifierTournoi")
public class ModifierTournoi extends HttpServlet {
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
            response.sendRedirect("/tennis/login");
            return;
        } else {
            Tournoi t1 = null;
            try {
                t1 = tournoiDao.lecture(Long.valueOf(request.getParameter("id")));
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("t1", t1);
            id = Long.valueOf(request.getParameter("id"));
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/modifierTournoi.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Tournoi t1 = new Tournoi();
        try {
            t1.setNom(request.getParameter("txtNom"));
            t1.setCode(request.getParameter("txtCode"));
            t1.setId(id);
            tournoiDao.modifier(t1);
        } catch (BeanException | DaoException e) {
            request.setAttribute("erreur", e.getMessage());
            this.getServletContext().getRequestDispatcher("/WEB-INF/modifierTournoi.jsp").forward(request, response);
        }
        response.sendRedirect(request.getContextPath() + "/ListTournoi");
    }
}
