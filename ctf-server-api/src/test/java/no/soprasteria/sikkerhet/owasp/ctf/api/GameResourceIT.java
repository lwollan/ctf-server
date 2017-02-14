package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.Map;

import static no.soprasteria.sikkerhet.owasp.ctf.api.TestUtil.encode;
import static org.assertj.core.api.Assertions.assertThat;


public class GameResourceIT extends JerseyTest {

    @BeforeClass
    public static  void oppsett() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Override
    protected Application configure() {
        try {
            CtFApplication application = new CtFApplication();
            return application;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void skal_returnere_en_liste_med_spill() {
        List<Map<String, Object>> entity = target("game").request().get().readEntity(new GenericType<List<Map<String, Object>>>() {
        });
        assertThat(entity).hasSize(1);
        assertThat(entity.get(0)).containsKeys("flags", "game", "gameOn", "score");
    }

    @Test
    public void trenger_basic_auth_for_aa_starte_spill() {
        TestUtil.assertThatBasicAuthIsNeeded("", target("game/start"));
    }

    @Test
    public void trenger_basic_auth_for_aa_stoppe_spill() {
        TestUtil.assertThatBasicAuthIsNeeded("", target("game/stop"));
    }

    @Test
    public void trenger_basic_auth_for_aa_resette_spill() {
        TestUtil.assertThatBasicAuthIsNeeded("", target("game/sys64764"));
    }

    @Test
    public void skal_vaere_mulig_aa_starte_spill() {
        assertThat(target("game/start")
                .request()
                .header("Authorization", encode("passord"))
                .post(Entity.json(""))
                .getStatus())
            .isEqualTo(204);
    }

    @Test
    public void skal_vaere_mulig_aa_stoppe_spill() {
        assertThat(target("game/stop")
                .request()
                .header("Authorization", encode("passord"))
                .post(Entity.json(""))
                .getStatus())
                .isEqualTo(204);
    }

    @Test
    public void skal_vaere_mulig_aa_nullstille_spill() {
        assertThat(target("game/sys64764")
                .request()
                .header("Authorization", encode("passord"))
                .post(Entity.json(""))
                .getStatus())
                .isEqualTo(204);
    }
}