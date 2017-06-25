package no.soprasteria.sikkerhet.owasp.ctf.games;

import no.soprasteria.sikkerhet.owasp.ctf.games.implementations.SommerstudentGame;
import no.soprasteria.sikkerhet.owasp.ctf.service.FlagService;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SommerstudentGameTest {

    @Test
    public void should_read_all_11_flags_from_file() throws IOException {
        FlagService flagService = mock(FlagService.class);
        new SommerstudentGame(flagService);

        verify(flagService, Mockito.times(11)).addFlag(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
    }
}
