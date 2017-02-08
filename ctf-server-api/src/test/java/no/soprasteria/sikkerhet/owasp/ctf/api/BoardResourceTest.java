package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import no.soprasteria.sikkerhet.owasp.ctf.service.BoardService;
import org.junit.Test;

import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoardResourceTest {

    @Test
    public void skal_returnere_et_eller_annet_map() throws Exception {
        Map<String, Object> expected = new HashMap<>();
        expected.put("score", Arrays.asList());
        expected.put("title", "Sopra Steria CtF 2017");
        expected.put("gameOn", false);

        BoardResource resource = new BoardResource();

        Application application = new CtFApplication();

        Map<String, Object> map = resource.get(application);

        assertThat(map).isEqualTo(expected);
    }

}