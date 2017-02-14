package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class BoardResourceTest {

    private BoardResource resource;
    private static CtFApplication application;

    @BeforeClass
    public static void foerst() throws Exception {
        application = new CtFApplication();
    }

    @AfterClass
    public static void sist() throws Exception {
        application.shutdown();
    }
    @Before
    public void oppsett() {
        resource = new BoardResource();
    }

    @Test
    public void score_board_skal_inneholde_resultater() throws Exception {
        Map<String, Object> respons = resource.getBoard(application);
        assertThat(respons).containsKey("score");
    }

    @Test
    public void score_board_skal_inneholde_spillnavn() throws Exception {
        Map<String, Object> respons = resource.getBoard(application);
        assertThat(respons).containsKey("title");
    }

    @Test
    public void score_board_skal_inneholde_spillstatus() throws Exception {
        Map<String, Object> respons = resource.getBoard(application);
        assertThat(respons).containsKey("gameOn");
    }

    @Test
    public void score_board_skal_inneholde_start_tid() throws Exception {
        Map<String, Object> respons = resource.getBoard(application);
        assertThat(respons).containsKey("start");
    }

    @Test
    public void score_board_skal_inneholde_slutt_tid() throws Exception {
        Map<String, Object> respons = resource.getBoard(application);
        assertThat(respons).containsKey("end");
    }
}