package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameResourceTest {

    @Test
    public void skal_liste_tilgjengelige_spill() throws Exception {
        GameResource gameResource = new GameResource();

        assertThat(gameResource.list(new CtFApplication())).isNotEmpty();
    }

    @Test
    public void skal_kunne_starte_et_spill() throws Exception {
        GameResource gameResource = new GameResource();
        CtFApplication application = new CtFApplication();

        assertThat(gameResource.list(application).get(0).get(GameResource.Keys.gameOn)).isEqualTo(Boolean.FALSE);
        gameResource.startGame(application);

        assertThat(gameResource.list(application).get(0).get(GameResource.Keys.gameOn)).isEqualTo(Boolean.TRUE);
    }

}