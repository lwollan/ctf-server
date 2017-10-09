package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardResourceIT extends JerseyTest {

    @BeforeClass
    public static void oppsett() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
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
    public void skal_svare_paa_forventet_path() {
        Response response = target("public/board").request().get();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void svaret_skal_vaere_json() {
        Response response = target("public/board").request().get();

        assertThat(response.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON_TYPE);
    }

    @Test
    public void svaret_skal_vaere_et_map() {
        target("public/board").request().get(new GenericType<Map<String, Object>>() {});
    }

    @Test
    public void det_skal_finnes_resultater_i_svaret() {
        Map<String, Object> respons = target("public/board").request().get(new GenericType<Map<String, Object>>() {
        });

        assertThat(respons).containsKey("score");
    }

    @Test
    public void skal_inneholde_starttidspunkt_i_respons() {
        Map<String, Object> respons = target("public/board").request().get(new GenericType<Map<String, Object>>() {
        });

        assertThat(respons).containsKey("start");
        assertThat(LocalDateTime.parse(respons.get("start").toString())).isNotNull();
    }

    @Test
    public void skal_inneholde_stoptidspunkt_i_respons() {
        Map<String, Object> respons = target("public/board").request().get(new GenericType<Map<String, Object>>() {
        });

        assertThat(respons).containsKey("end");
        assertThat(LocalDateTime.parse(respons.get("end").toString())).isNotNull();
    }

    @Test
    public void skal_inneholde_spill_status_i_respons() {
        Map<String, Object> respons = target("public/board").request().get(new GenericType<Map<String, Object>>() {
        });

        assertThat(respons).containsKey("gameOn");
    }
}