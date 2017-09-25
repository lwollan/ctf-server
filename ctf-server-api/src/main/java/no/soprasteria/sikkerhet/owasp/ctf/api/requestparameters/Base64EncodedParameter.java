package no.soprasteria.sikkerhet.owasp.ctf.api.requestparameters;

import java.util.Base64;

public abstract class Base64EncodedParameter implements ValidatedInputParameter {

    public String value;

    public Base64EncodedParameter(String value) {
        try {
            Base64.getDecoder().decode(value);
        } catch (Exception e) {
            throw new InvalidParameterException(getParameterName());
        }
    }
}
