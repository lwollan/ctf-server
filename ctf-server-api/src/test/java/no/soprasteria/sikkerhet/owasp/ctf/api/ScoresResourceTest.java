package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.AnswerService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.Svar;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ScoresResourceTest {

    private CtFApplication application;
    private ScoresResource resource;
    private AnswerService answerService;

    @Before
    public void oppsett() throws Exception {
        application = new CtFApplication();
        TestSetup.setupTestGame(application);

        resource = new ScoresResource();

        answerService = ApplicationContext.get(application, AnswerService.class);
    }

    @Test
    public void skal_liste_opp_alle_lag_som_har_svart() {

        answerService.giveAnswer("abc", "abc", "incorrect");
        answerService.giveAnswer("anotherTeam", "abc", "incorrect");
        Map<String, Map<String, List<Svar>>> list = resource.list(application);

        assertThat(list).containsOnlyKeys("abc", "anotherTeam");
    }

}
