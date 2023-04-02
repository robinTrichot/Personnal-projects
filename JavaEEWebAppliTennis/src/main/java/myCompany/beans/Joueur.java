package myCompany.beans;

public class Joueur {
    private Long id;
    private String nom;
    private String prenom;
    private String sexe;

    public Joueur(Long id, String nom, String prenom, String sexe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
    }

    public Joueur(String nom, String prenom, String sexe) {
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
    }

    public Joueur() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws BeanException {
        if (nom.length() > 20) {
            throw new BeanException("le nom est trop grand ! (20 caractères maximum !)");
        } else this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) throws BeanException {
        if (prenom.length() > 20) {
            throw new BeanException("le nom est trop grand ! (20 caractères maximum !)");
        } else this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
}
