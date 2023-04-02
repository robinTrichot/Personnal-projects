package myCompany.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import myCompany.beans.BeanException;
import myCompany.beans.Joueur;
import myCompany.dao.DaoException;
import myCompany.dao.DaoFactory;
import myCompany.dao.JoueurDao;
import myCompany.dao.JoueurDaoImpl;
import myCompany.utils.SessionUtils;


import java.io.IOException;

@WebServlet("/ModifierJoueur")
public class ModifierJoueur extends HttpServlet {
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
        } else {
            Joueur j1 = null;
            try {
                j1 = joueurDao.lecture(Long.valueOf(request.getParameter("id")));
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("j1", j1);
            id = Long.valueOf(request.getParameter("id"));
            this.getServletContext().getRequestDispatcher("/WEB-INF/modifierJoueur.jsp").forward(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Joueur j1 = new Joueur();


        try {
            j1.setNom(request.getParameter("txtNom"));
            j1.setPrenom(request.getParameter("txtPrenom"));
            j1.setSexe(request.getParameter("opSexe"));
            j1.setId(id);
            joueurDao.modifier(j1);
            response.sendRedirect(request.getContextPath() + "/ListJoueur");
        } catch (BeanException | DaoException e) {
            request.setAttribute("erreur", e.getMessage());
            this.getServletContext().getRequestDispatcher("/WEB-INF/modifierJoueur.jsp").forward(request, response);
        }
    }
}
