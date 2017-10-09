package no.soprasteria.sikkerhet.owasp.ctf.api.requestparameters;

public class TeamKeyParameter extends Base64EncodedParameter {

    public TeamKeyParameter(String value) {
        super(value);
    }

    public static TeamKeyParameter from(String value) {
        return new TeamKeyParameter(value);
    }

    @Override
    public String getParameterName() {
        return "teamKey";
    }
}
