package no.soprasteria.sikkerhet.owasp.ctf.api;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtil {

    static void assertThatResourceRequireTeamKey(String invalidTeamKey, String validTeamKey, WebTarget target) {
        assertThat(target
                .request()
                .get()
                .getStatus())
                .isEqualTo(401);

        assertThat(target
                .request()
                .header("X-TEAM-KEY", invalidTeamKey)
                .get()
                .getStatus())
                .isEqualTo(401);

        assertThat(target
                .request()
                .header("X-TEAM-KEY", validTeamKey)
                .get()
                .getStatus())
                .isEqualTo(200);
    }

    static void assertThatBasicAuthIsNeeded(String jsonBody, WebTarget target) {
        Response response = target.request().post(Entity.entity(jsonBody, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus()).isEqualTo(401);
        assertThat(response.getHeaderString("WWW-Authenticate")).isEqualTo("Basic realm=\"ss-ctf-server\"");
    }

    static String encode(String passord) {
        return "Basic " + Base64.getEncoder().encodeToString(passord.getBytes());
    }
}
