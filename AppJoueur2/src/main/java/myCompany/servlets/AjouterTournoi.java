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

@WebServlet("/AjouterTournoi")
public class AjouterTournoi extends HttpServlet {
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
        if (!result) {
            response.sendRedirect("/tennis/login");
            return;
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/ajouterTournoi.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Tournoi t1 = new Tournoi();
        try {

            t1.setNom(request.getParameter("txtNom"));
            t1.setCode(request.getParameter("txtPrenom"));
            tournoiDao.ajouter(t1);

            response.sendRedirect(request.getContextPath() + "/ListTournoi");
        } catch (BeanException | DaoException e) {
            request.setAttribute("erreur", e.getMessage());
            this.getServletContext().getRequestDispatcher("/WEB-INF/ajouterTournoi.jsp").forward(request, response);
        }
    }
}