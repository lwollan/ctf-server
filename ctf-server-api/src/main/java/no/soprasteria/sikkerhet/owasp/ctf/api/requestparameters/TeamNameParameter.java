package no.soprasteria.sikkerhet.owasp.ctf.api.requestparameters;

public class TeamNameParameter extends AlphaNumbericParameter {

    public TeamNameParameter(String value) {
        super(value);
    }

    public static TeamNameParameter from(String value) {
        return new TeamNameParameter(value);
    }

    @Override
    public String getParameterName() {
        return "teamName";
    }
}
