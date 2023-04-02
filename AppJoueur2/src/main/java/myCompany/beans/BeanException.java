package myCompany.beans;

// ne pas oublier ; exception : c'est surveillée;
// et runtimeException c'ets non surveillé !
public class BeanException extends Exception {
    public BeanException(String message) {
        super(message);
    }

}
