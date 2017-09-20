package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

import static javax.ws.rs.client.Entity.json;
import static no.soprasteria.sikkerhet.owasp.ctf.api.TestUtil.encode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.bridge.SLF4JBridgeHandler.install;
import static org.slf4j.bridge.SLF4JBridgeHandler.removeHandlersForRootLogger;

public class TeamResourceIT extends JerseyTest {

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
            return application;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void skal_vaere_mulig_aa_legge_til_nytt_lag() {
        assertThat(addTeam("teamnavn").getStatus()).isEqualTo(200);
    }

    @Test
    public void nytt_lag_skal_faa_team_key() {
        Map<String, String> response = target("team/add/teamnavn").request().post(json("")).readEntity(new GenericType<Map<String, String>>() {
        });

        assertThat(response).containsKey("X-TEAM-KEY");
    }

    @Test
    public void det_skal_ikke_vaere_mulig_aa_opprette_to_lag_med_samme_navn() {
        assertThat(addTeam("teamnavn").getStatus()).isEqualTo(200);
        assertThat(addTeam("teamnavn").getStatus()).isEqualTo(409);
    }

    @Test
    public void sletting_av_team_skal_krever_basic_auth() {
        Response response = target("team").path("key").request().delete();
        assertThat(response.getStatus()).isEqualTo(401);
        assertThat(response.getHeaderString("WWW-Authenticate")).isEqualTo("Basic realm=\"ss-ctf-server\"");
    }

    @Test
    public void something() {

    }

    @Test
    public void det_skal_vaere_mulig_aa_slette_et_team() {
        String teamName = UUID.randomUUID().toString();

        Response addResponse = addTeam(teamName);
        assertThat(addResponse.getStatus()).isEqualTo(200);

        Map<String, Object> teamStatus = getTeamStatus(teamName);
        assertThat(teamStatus).isNotEmpty();

        Response deleteResponse = deleteTeam(teamName);
        assertThat(deleteResponse.getStatus()).isEqualTo(200);

        Map<String, Object> teamStatusAfterDelete = getTeamStatus(teamName);
        assertThat(teamStatusAfterDelete).isEmpty();
    }

    private Response addTeam(String teamName) {
        return target("team")
                .path("add")
                .path(teamName)
                .request()
                .post(json(""));
    }

    private Response deleteTeam(String teamName) {
        return target("team")
                .path(teamName)
                .request()
                .header("Authorization", encode("passord"))
                .delete();
    }

    private Map<String, Object> getTeamStatus(String teamName) {
        return target("team")
                .path(teamName)
                .request()
                .header("Authorization", encode("passord"))
                .get()
                .readEntity(new GenericType<Map<String, Object>>() {});
    }
}