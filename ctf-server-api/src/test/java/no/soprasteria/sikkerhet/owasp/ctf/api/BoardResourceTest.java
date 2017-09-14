package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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
        Map<BoardResource.BoardResponseKeys, Object> respons = resource.getBoard(application);
        assertThat(respons).containsKey(BoardResource.BoardResponseKeys.score);
    }

    @Test
    public void score_board_skal_inneholde_spillnavn() throws Exception {
        Map<BoardResource.BoardResponseKeys, Object> respons = resource.getBoard(application);
        assertThat(respons).containsKey(BoardResource.BoardResponseKeys.title);
    }

    @Test
    public void score_board_skal_inneholde_spillstatus() throws Exception {
        Map<BoardResource.BoardResponseKeys, Object> respons = resource.getBoard(application);
        assertThat(respons).containsKey(BoardResource.BoardResponseKeys.gameOn);
    }

    @Test
    public void score_board_skal_inneholde_start_tid() throws Exception {
        Map<BoardResource.BoardResponseKeys, Object> respons = resource.getBoard(application);
        assertThat(respons).containsKey(BoardResource.BoardResponseKeys.start);
    }

    @Test
    public void score_board_skal_inneholde_slutt_tid() throws Exception {
        Map<BoardResource.BoardResponseKeys, Object> respons = resource.getBoard(application);
        assertThat(respons).containsKey(BoardResource.BoardResponseKeys.end);
    }
}