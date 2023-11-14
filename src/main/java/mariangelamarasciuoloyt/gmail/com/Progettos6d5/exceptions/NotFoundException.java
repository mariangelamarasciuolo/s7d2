package mariangelamarasciuoloyt.gmail.com.Progettos6d5.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(int id) {
        super("Elemento con id " + id + " non trovato");
    }

    public NotFoundException(String message) {
        super(message);
    }

}