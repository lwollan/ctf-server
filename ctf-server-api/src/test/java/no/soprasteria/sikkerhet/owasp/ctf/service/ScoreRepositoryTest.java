package no.soprasteria.sikkerhet.owasp.ctf.service;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ScoreRepositoryTest {

    private ScoreRepository repository;

    @Before
    public void oppset() {
        repository = new ScoreRepository();
    }

    @Test
    public void skal_kunne_sette_poeng_for_et_team() {
        repository.put("key", 10l);
    }

    @Test
    public void skal_kunne_hente_poeng_for_et_team() {
        repository.put("key", 10l);

        assertThat(repository.get("key")).isEqualTo(10l);
    }
}