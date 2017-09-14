package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.ApplicationContext;
import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.FlagService;
import no.soprasteria.sikkerhet.owasp.ctf.core.service.TeamService;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class FlagResourceIT extends JerseyTest {

    private String validTeamKey;
    private String flagId;
    private String flag;

    @BeforeClass
    public static  void oppsett() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Override
    protected Application configure() {
        try {
            CtFApplication application = new CtFApplication();
            validTeamKey = ApplicationContext.get(application, TeamService.class).addNewTeam("integration-test").orElse("");
            Map<String, String> firstFlag = ApplicationContext.get(application, FlagService.class).listFlag().get(0);
            flag = firstFlag.get("flag");
            flagId = firstFlag.get("flagId");

            return application;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void skal_kreve_teamkey_paa_list_flag() {
        TestUtil.assertThatResourceRequireTeamKey("something_weird", validTeamKey, target("flag/list"));
    }

    @Test
    public void skal_kreve_teamkey_paa_flag_tip() {
        TestUtil.assertThatResourceRequireTeamKey("something_weird", validTeamKey, target("flag/tip/someflag"));
    }

    @Test
    public void skal_kreve_teamkey_paa_flag_besvarelse() {
        assertThat(target("flag")
                .request()
                .post(Entity.entity("", MediaType.APPLICATION_JSON_TYPE))
                .getStatus())
            .isEqualTo(401);

        assertThat(target("flag")
                .request()
                .header("X-TEAM-KEY", "something_weird")
                .post(Entity.entity("", MediaType.APPLICATION_JSON_TYPE))
                .getStatus())
            .isEqualTo(401);

        Map<String, String> flagSvar = new HashMap<>();
        flagSvar.put("flagId", "something");
        flagSvar.put("flagName", "noidea");

        assertThat(target("flag")
                .request()
                .header("X-TEAM-KEY", validTeamKey)
                .post(Entity.entity(flagSvar, MediaType.APPLICATION_JSON_TYPE))
                .getStatus())
                .isEqualTo(400);
    }


    @Test
    public void riktig_svar_skal_gi_accepted_respons_og_tom_body() {
        Map<String, String> body = new HashMap<>();
        body.put("flagId", flagId);
        body.put("flagName", flag);

        Response response = target("flag")
                .request()
                .header("X-TEAM-KEY", validTeamKey)
                .post(Entity.entity(body, MediaType.APPLICATION_JSON_TYPE));


        assertThat(response.readEntity(String.class)).isEmpty();
    }

    @Test
    public void skal_gi_en_liste_med_flag_og_noekler() {
        List<Map<String, String>> response = target("flag/list")
                .request()
                .header("X-TEAM-KEY", validTeamKey)
                .get()
                .readEntity(new GenericType<List<Map<String, String>>>() {
                });

        response.forEach(entry -> assertThat(entry).containsKeys("flagId" , "flagName", "flagAnswered"));
    }

    @Test
    public void skal_gi_tips_tip_flag() {
        Response response = target("flag/tip/" + flagId)
                .request()
                .header("X-TEAM-KEY", validTeamKey)
                .get();

        Map<String, String> responseMap = response.readEntity(new GenericType<Map<String, String>>() {
        });

        assertThat(responseMap).containsKey("tips");
    }

    @Test
    public void ukjent_flagid_skal_gi_tomt_svar() {
        Response response = target("flag/tip/something")
                .request()
                .header("X-TEAM-KEY", validTeamKey)
                .get();

        Map<String, String> responseMap = response.readEntity(new GenericType<Map<String, String>>() {
        });

        assertThat(responseMap).isEmpty();
    }
}