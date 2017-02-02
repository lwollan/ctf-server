package no.soprasteria.sikerhet.owasp.ctf.api;

import no.soprasteria.sikerhet.owasp.ctf.service.BoardService;
import org.junit.Test;

import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoardResourceTest {

    @Test
    public void skal_returnere_et_eller_annet_map() {
        Map<String, Object> expected = new HashMap<>();
        expected.put("score", new HashMap<>());
        expected.put("title", "Sopra Steria CtF 2017");

        BoardResource resource = new BoardResource();

        Application application = lagApplicationContext();

        Map<String, Object> map = resource.get(application);

        assertThat(map).isEqualTo(expected);
    }

    private static Application lagApplicationContext() {
        Application application = mock(Application.class);
        HashMap<String, Object> applicationProperties = new HashMap<>();
        BoardService boardService = mock(BoardService.class);
        applicationProperties.put("no.soprasteria.sikerhet.owasp.ctf.service.BoardService", boardService);
        when(application.getProperties()).thenReturn(applicationProperties);
        HashMap<String, Long> score = new HashMap<>();
        when(boardService.getScore()).thenReturn(score);
        return application;
    }
}