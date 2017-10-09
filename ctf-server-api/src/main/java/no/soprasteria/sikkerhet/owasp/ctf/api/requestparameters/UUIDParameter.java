package no.soprasteria.sikkerhet.owasp.ctf.api.requestparameters;

import java.util.UUID;

public abstract class UUIDParameter implements ValidatedInputParameter {

    public String value;

    public UUIDParameter(String value) {
        try {
            this.value = UUID.fromString(value).toString();
        } catch (Exception e){
            throw new InvalidParameterException(getParameterName());

        }
    }

}
