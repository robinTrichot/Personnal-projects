package myCompany.beans;

public class Epreuve {
    private int id;
    private int annee;
    private char type_epreuve;
    private int id_tournoi;
    private String nom;
    private String nomT;
    private String prenom;

    public Epreuve(int id, int annee, char type_epreuve, int id_tournoi) {
        this.id = id;
        this.annee = annee;
        this.type_epreuve = type_epreuve;
        this.id_tournoi = id_tournoi;
    }

    public Epreuve(int annnee, String nom, String prenom) {
        this.annee = annnee;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Epreuve(String nomT, String nom, String prenom) {
        this.nomT = nomT;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getNomT() {
        return nomT;
    }

    public void setNomT(String nomT) {
        this.nomT = nomT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public char getType_epreuve() {
        return type_epreuve;
    }

    public void setType_epreuve(char type_epreuve) {
        this.type_epreuve = type_epreuve;
    }

    public int getId_tournoi() {
        return id_tournoi;
    }

    public void setId_tournoi(int id_tournoi) {
        this.id_tournoi = id_tournoi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
