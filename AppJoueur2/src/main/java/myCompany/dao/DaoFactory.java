package myCompany.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
    private String url;
    private String username;
    private String password;

    public DaoFactory(String url, String username, String password) {
        super();
        this.url = url;
        this.username = username;
        this.password = password;
    }

    // Chargement du pilote MySQL, récupération d'un objet de type Class
    public static DaoFactory getInstance() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        //Instance Dao Factory
        DaoFactory instance = new DaoFactory("jdbc:mariadb://localhost:3306/tennis", "root", "");
        return instance;
    }

    //Créer la connection
    public Connection getConnection() throws SQLException {
        Connection connexion = DriverManager.getConnection(url, username, password);
        return connexion;
    }
}
