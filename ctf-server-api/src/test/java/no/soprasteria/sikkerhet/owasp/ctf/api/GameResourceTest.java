package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class GameResourceTest {

    private CtFApplication application;

    @Before
    public void oppsett() throws Exception {
        application = new CtFApplication();
        TestSetup.setupTestGame(application);

    }

    /**
    @Test
    public void skal_liste_tilgjengelige_spill() throws Exception {
        GameResource gameResource = new GameResource();
        assertThat(gameResource.list(application)).isNotEmpty();
    }

    @Test
    public void skal_kunne_starte_et_spill() throws Exception {
        GameResource gameResource = new GameResource();

        assertThat(gameResource.list(application).get(0).get(GameResource.GameResourceResponseKeys.gameOn)).isEqualTo(Boolean.FALSE);
        gameResource.startGame(application);

        assertThat(gameResource.list(application).get(0).get(GameResource.GameResourceResponseKeys.gameOn)).isEqualTo(Boolean.TRUE);
    }
    */

}