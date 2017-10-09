package no.soprasteria.sikkerhet.owasp.ctf.api.requestparameters;

public class FlagIdParameter extends UUIDParameter {

    public FlagIdParameter(String value) {
        super(value);
    }

    @Override
    public String getParameterName() {
        return "flagId";
    }

    public static FlagIdParameter from(String value) {
        return new FlagIdParameter(value);
    }


}
