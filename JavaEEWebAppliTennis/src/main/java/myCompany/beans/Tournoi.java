package myCompany.beans;

public class Tournoi {
    private Long id;
    private String nom;
    private String code;

    public Tournoi(Long id, String nom, String code) {
        this.id = id;
        this.nom = nom;
        this.code = code;
    }

    public Tournoi(String nom, String code) {
        this.nom = nom;
        this.code = code;
    }

    public Tournoi() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) throws BeanException {
        if (code.length() > 2) {
            throw new BeanException("le Code ne doit comporter que 2 caractères");
        } else this.code = code;
    }
}
