package myCompany.dao;

import myCompany.beans.Joueur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoueurDaoImpl implements JoueurDao {
    private final DaoFactory DAOF;

    public JoueurDaoImpl(DaoFactory daof) {
        this.DAOF = daof;
    }

    @Override
    public void ajouter(Joueur joueur) throws DaoException {
        Connection connexion = null;
        PreparedStatement statement = null;
        try {
            connexion = DAOF.getConnection();
            statement = connexion.prepareStatement("INSERT INTO JOUEUR(nom, prenom, sexe) VALUES(?, ?, ?)");
            statement.setString(1, joueur.getNom());
            statement.setString(2, joueur.getPrenom());
            statement.setString(3, joueur.getSexe());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la BDD");
        } finally {
            try {
                if (connexion != null) {
                    connexion.close();
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la BDD");
            }
        }
    }

    @Override
    public List<Joueur> lister() throws DaoException {
        List<Joueur> joueurs = new ArrayList<>();
        Connection connexion = null;
        PreparedStatement statement = null;

        try {
            connexion = DAOF.getConnection();
            statement = connexion.prepareStatement("SELECT * FROM JOUEUR");
            ResultSet resultat = statement.executeQuery();

            while (resultat.next()) {
                Long id = resultat.getLong("id");
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");
                String sexe = resultat.getString("sexe");

                Joueur joueur = new Joueur(id, nom, prenom, sexe);
                joueurs.add(joueur);
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la BDD");
        } finally {
            try {
                if (connexion != null) {
                    connexion.close();
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la BDD");
            }
        }
        return joueurs;
    }

    @Override
    public Joueur lecture(Long id) throws DaoException {
        Connection connexion = null;
        PreparedStatement statement = null;

        try {
            connexion = DAOF.getConnection();
            statement = connexion.prepareStatement("SELECT * FROM JOUEUR WHERE ID = ?;");
            statement.setLong(1, id);
            ResultSet resultat = statement.executeQuery();
            System.out.println("coucou");
            if (resultat.next()) {
                return new Joueur(
                        resultat.getLong("id"),
                        resultat.getString("nom"),
                        resultat.getString("prenom"),
                        resultat.getString("sexe")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la BDD");
        } finally {
            try {
                if (connexion != null) {
                    connexion.close();
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la BDD");
            }
        }
    }

    @Override
    public void modifier(Joueur joueur) throws DaoException {
        Connection connexion = null;
        PreparedStatement statement = null;
        try {
            connexion = DAOF.getConnection();
            statement = connexion.prepareStatement("UPDATE joueur SET nom = ?,prenom = ?,sexe = ? WHERE id = ?");
            statement.setString(1, joueur.getNom());
            statement.setString(2, joueur.getPrenom());
            statement.setString(3, joueur.getSexe());
            statement.setLong(4, joueur.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la BDD");
        } finally {
            try {
                if (connexion != null) {
                    connexion.close();
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la BDD");
            }
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        Connection connexion = null;
        PreparedStatement pst = null;
        PreparedStatement pst2 = null;
        PreparedStatement pst1 = null;

        try {
            connexion = DAOF.getConnection();
            pst = connexion.prepareStatement(" DELETE sV FROM score_vainqueur AS sV INNER JOIN match_tennis AS m ON sV.ID_MATCH = m.ID where m.ID_VAINQUEUR = ? OR m.ID_FINALISTE = ?");
            pst.setLong(1, id);
            pst.setLong(2, id);
            pst.executeUpdate();

            pst1 = connexion.prepareStatement(" DELETE m FROM match_tennis AS m where m.ID_VAINQUEUR = ? OR m.ID_FINALISTE = ?");
            pst1.setLong(1, id);
            pst1.setLong(2, id);
            pst1.executeUpdate();

            pst2 = connexion.prepareStatement(" DELETE j FROM joueur AS j where id = ?");
            pst2.setLong(1, id);
            pst2.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la BDD");
        } finally {
            try {
                if (connexion != null) {
                    connexion.close();
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la BDD");
            }
        }
    }

    @Override
    public List<Joueur> rechercher(String chaine) throws DaoException {

        List<Joueur> joueurs = new ArrayList<>();
        Connection connexion = null;
        PreparedStatement statement = null;

        try {
            connexion = DAOF.getConnection();
            statement = connexion.prepareStatement("SELECT * FROM JOUEUR WHERE NOM LIKE ? OR PRENOM LIKE ?");
            statement.setString(1, "%" + chaine + "%");
            statement.setString(2, "%" + chaine + "%");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String sexe = rs.getString("sexe");

                Joueur joueur = new Joueur(id, nom, prenom, sexe);
                joueurs.add(joueur);
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la BDD");
        } finally {
            try {
                if (connexion != null) {
                    connexion.close();
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la BDD");
            }
        }
        return joueurs;
    }
}

