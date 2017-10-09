package no.soprasteria.sikkerhet.owasp.ctf.api.requestparameters;

import org.junit.Test;

public class TeamNameParameterTest {

    @Test(expected = InvalidParameterException.class)
    public void should_not_accept_sql() {
        new TeamNameParameter("-- insert");
    }

    @Test
    public void should_accept_teamname_with_space_in_it() {
        new TeamNameParameter("Team Me");
    }

    @Test
    public void should_accept_single_word_teamname() {
        new TeamNameParameter("mememe");
    }

    @Test(expected = InvalidParameterException.class)
    public void should_not_accept_script_tags_in_teamname() {
        new TeamNameParameter("<script>alert('hepp')</script>");
    }
}