package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.api.requestparameters.Base64EncodedParameter;

public class TeamKeyHeaderParameter extends Base64EncodedParameter {

    public TeamKeyHeaderParameter(String value) {
        super(value);
    }

    public static TeamKeyHeaderParameter valueOf(String value) {
        return new TeamKeyHeaderParameter(value);
    }

    @Override
    public String getParameterName() {
        return "X-TEAM-KEY";
    }
}
