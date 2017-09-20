package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.Svar;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.bridge.SLF4JBridgeHandler.install;
import static org.slf4j.bridge.SLF4JBridgeHandler.removeHandlersForRootLogger;

public class ScoresResourceIT extends JerseyTest {

    private String teamKey;

    @BeforeClass
    public static  void oppsett() {
        removeHandlersForRootLogger();
        install();
    }

    @Override
    protected Application configure() {
        try {
            CtFApplication application = new CtFApplication();
            TestSetup.setupTestGame(application);

            teamKey = TestSetup.setupTestTeamWithAnswers(application, "testTeam");
            return application;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void somehitngsomethingdarkside() {
        Response response = target("public/scores/list").request().get();

        assertThat(response.getStatus()).isEqualTo(200);

        Map<String, Map<String, List<Svar>>> entity = response.readEntity(new GenericType<Map<String, Map<String, List<Svar>>>>() {
        });

        assertThat(entity).isNotNull();
    }

}