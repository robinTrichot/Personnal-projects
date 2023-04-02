package myCompany.dao;

import myCompany.beans.User;
import myCompany.utils.HashClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl {
    private DaoFactory daof;

    public UserDaoImpl(DaoFactory daof) {
        super();
        this.daof = daof;
    }

    public User isValidLogin(String login, String password) {
        Connection connexion = null;
        PreparedStatement statment = null;
        password = HashClass.sha1(password);


        try {
            connexion = daof.getConnection();
            statment = connexion.prepareStatement("SELECT * FROM connexion WHERE login = ? AND password =?");
            statment.setString(1, login);
            statment.setString(2, password);
            ResultSet rs = statment.executeQuery();
            if (rs.next()) {
                int PFnAME = rs.getInt("profil");
                System.out.println(PFnAME);
                String password1 = rs.getString(2);
                System.out.println(password1);
                return new User(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getInt("profil"));
            } else {
                System.out.println("Mauvais login ou password ! ");
                return null;
            }
            // si on met pas d'exception clssique ici il demande un return à al toute fin ça n'aurait pas de sens;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
