package no.soprasteria.sikkerhet.owasp.ctf.api.requestparameters;

import org.junit.Test;

public class TeamNameParameterTest {

    @Test(expected = InvalidParameterException.class)
    public void should_not_accept_sql() {
        new TeamNameParameter("-- insert");
    }

}