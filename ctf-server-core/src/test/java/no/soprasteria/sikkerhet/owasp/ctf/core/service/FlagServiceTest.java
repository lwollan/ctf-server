package no.soprasteria.sikkerhet.owasp.ctf.core.service;

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
        assertThat(service.addFlag("flagId-name", "svar", 10l, "tips", "beskrivelse")).isNotNull();
    }

    @Test
    public void skal_finne_poeng_for_et_flag_som_er_lagt_til() {
        String flagId = service.addFlag("flagId-name", "svar", 10l, "tips", "beskrivelse");

        assertThat(service.getPoints(flagId)).isEqualTo(10l);
    }

    @Test
    public void skal_ikke_finne_poeng_for_et_flag_som_ikke_er_lagt_til() {
        assertThat(service.getPoints("flagId-id")).isEqualTo(0l);
    }

    @Test
    public void skal_vise_alle_flag_som_er_lagt_til() {
        service.addFlag("01 Flag 1", "svar-a", 10l, "tips", "beskrivelse");
        service.addFlag("02 Flag 2", "svar-b", 10l, "tips", "beskrivelse");

        assertThat(service.listFlag()).hasSize(2);
    }



}