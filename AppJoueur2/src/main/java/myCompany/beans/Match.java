package myCompany.beans;

public class Match {
    private int id;
    private int id_epreuve;
    private String id_vainqueur;
    private String id_finaliste;
    public Match(int id, int id_epreuve, String id_vainqueur, String id_finaliste) {
        this.id = id;
        this.id_epreuve = id_epreuve;
        this.id_vainqueur = id_vainqueur;
        this.id_finaliste = id_finaliste;
    }
    public Match(String id_vainqueur, String id_finaliste) {
        this.id_vainqueur = id_vainqueur;
        this.id_finaliste = id_finaliste;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId_epreuve() {
        return id_epreuve;
    }

    public void setId_epreuve(int id_epreuve) {
        this.id_epreuve = id_epreuve;
    }

    public String getId_vainqueur() {
        return id_vainqueur;
    }

    public void setId_vainqueur(String id_vainqueur) {
        this.id_vainqueur = id_vainqueur;
    }

    public String getId_finaliste() {
        return id_finaliste;
    }

    public void setId_finaliste(String id_finaliste) {
        this.id_finaliste = id_finaliste;
    }
}
