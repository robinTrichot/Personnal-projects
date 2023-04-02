package myCompany.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import myCompany.beans.User;

public class SessionUtils {
    public static boolean isUserLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        return (session != null && session.getAttribute("ConnectedUser") != null);
    }

    public static boolean isAdmin(User connectedUser) {
        //  HttpSession session = request.getSession();

        if (connectedUser.getProfil() == 43) {
            //     session.setAttribute("Admin", connectedUser);
            return true;
        } else {
            //    session.setAttribute("UUser", connectedUser);
            return false;
        }
    }

    public static double ratio(double victoire, double defaite) {
        return (victoire / defaite);

    }

}