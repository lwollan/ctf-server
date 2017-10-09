package no.soprasteria.sikkerhet.owasp.ctf.api.requestparameters;

public abstract class AlphaNumbericParameter implements ValidatedInputParameter {

    public String value;

    public AlphaNumbericParameter(String value) {
        if (value.matches("^\\w+( \\w+)*$")) {
            this.value = value;
        } else {
            throw new InvalidParameterException(getParameterName());
        }
    }

}
