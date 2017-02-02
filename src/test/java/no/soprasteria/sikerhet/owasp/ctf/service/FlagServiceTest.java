package no.soprasteria.sikerhet.owasp.ctf.service;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FlagServiceTest {

    private FlagService service;

    @Before
    public void oppsett() {
        service = new FlagService();

    }

    @Test
    public void skal_kunne_legge_til_et_flag() {
        service.addFlag("flag-id", "flag-name", "svar", 10l);
    }

    @Test
    public void skal_finne_poeng_for_et_flag_som_er_lagt_til() {
        service.addFlag("flag-id", "flag-name", "svar", 10l);

        assertThat(service.getPoints("flag-id")).isEqualTo(10l);
    }

    @Test
    public void skal_ikke_finne_poeng_for_et_flag_som_ikke_er_lagt_til() {
        assertThat(service.getPoints("flag-id")).isEqualTo(0l);
    }

    @Test
    public void skal_vise_alle_flag_som_er_lagt_til() {
        service.addFlag("flag-id-01", "01 Flag 1", "svar-a", 10l);
        service.addFlag("flag-id-02", "02 Flag 2", "svar-b", 10l);

        assertThat(service.listFlag()).hasSize(2);
    }
}