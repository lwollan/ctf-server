package no.soprasteria.sikkerhet.owasp.ctf.api.requestparameters;

public class InvalidParameterException extends RuntimeException {

    public InvalidParameterException(String parametername) {
        super(parametername + " is invalid.");
    }
}
