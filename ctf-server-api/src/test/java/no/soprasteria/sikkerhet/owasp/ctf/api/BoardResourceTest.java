package no.soprasteria.sikkerhet.owasp.ctf.api;

import no.soprasteria.sikkerhet.owasp.ctf.CtFApplication;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Map;

import static no.soprasteria.sikkerhet.owasp.ctf.api.TestSetup.setupTestGame;
import static org.assertj.core.api.Assertions.assertThat;

public class BoardResourceTest {

    private BoardResource resource;
    private static CtFApplication testApplication;

    @BeforeClass
    public static void foerst() throws Exception {
        testApplication = new CtFApplication();
        setupTestGame(testApplication);
    }

    @AfterClass
    public static void sist() throws Exception {
        testApplication.shutdown();
    }

    @Before
    public void oppsett() {
        resource = new BoardResource();
    }

    @Test
    public void score_board_skal_inneholde_resultater() throws Exception {
        Response response = resource.getBoard(testApplication);
        @SuppressWarnings("unchecked")
        Map<BoardResource.BoardResponseKeys, Object> map = (Map<BoardResource.BoardResponseKeys, Object>) response.getEntity();
        assertThat(map).containsKey(BoardResource.BoardResponseKeys.score);
    }

    @Test
    public void score_board_skal_inneholde_spillnavn() throws Exception {
        Response response = resource.getBoard(testApplication);
        @SuppressWarnings("unchecked")
        Map<BoardResource.BoardResponseKeys, Object> map = (Map<BoardResource.BoardResponseKeys, Object>) response.getEntity();
        assertThat(map).containsKey(BoardResource.BoardResponseKeys.title);
    }

    @Test
    public void score_board_skal_inneholde_spillstatus() throws Exception {
        Response response = resource.getBoard(testApplication);
        @SuppressWarnings("unchecked")
        Map<BoardResource.BoardResponseKeys, Object> map = (Map<BoardResource.BoardResponseKeys, Object>) response.getEntity();
        assertThat(map).containsKey(BoardResource.BoardResponseKeys.gameOn);
    }

    @Test
    public void score_board_skal_inneholde_start_tid() throws Exception {
        Response response = resource.getBoard(testApplication);
        @SuppressWarnings("unchecked")
        Map<BoardResource.BoardResponseKeys, Object> map = (Map<BoardResource.BoardResponseKeys, Object>) response.getEntity();
        assertThat(map).containsKey(BoardResource.BoardResponseKeys.start);
    }

    @Test
    public void score_board_skal_inneholde_slutt_tid() throws Exception {
        Response response = resource.getBoard(testApplication);
        @SuppressWarnings("unchecked")
        Map<BoardResource.BoardResponseKeys, Object> map = (Map<BoardResource.BoardResponseKeys, Object>) response.getEntity();
        assertThat(map).containsKey(BoardResource.BoardResponseKeys.end);
    }
}