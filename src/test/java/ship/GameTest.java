package ship;

import org.assertj.core.api.Assertions;
import org.jsonbuddy.JsonArray;
import org.jsonbuddy.JsonFactory;
import org.jsonbuddy.JsonObject;
import org.jsonbuddy.parse.JsonParser;
import org.junit.Test;
import org.mockito.Mockito;
import servlet.GameServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameTest {
    private GameServlet gameServlet = new GameServlet();


    @Test
    public void shouldPlayGame() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(stringWriter));
        when(req.getPathInfo()).thenReturn("/addPlayer");
        when(req.getMethod()).thenReturn("POST");
        String opprettBrukerJson = JsonFactory.jsonObject().put("name","Darth").toJson();
        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(opprettBrukerJson)));

        gameServlet.service(req,resp);

        String id = JsonParser.parseToObject(stringWriter.toString()).requiredString("id");

        int shotsLeft = 20;
        int x = 0;
        int y = 0;
        while (true) {
            if (y >= 20) {
                Assertions.fail("Hole board gone");
            }
            JsonArray shotList = JsonFactory.jsonArray();
            for (int i=0;i<shotsLeft;i++) {
                shotList.add(JsonFactory.jsonArray().add(x).add(y));
                x++;
                if (x >= 20) {
                    x=0;
                    y++;
                    if (y >= 20) {
                        break;
                    }
                }
            }
            req = mock(HttpServletRequest.class);
            resp = mock(HttpServletResponse.class);

            stringWriter = new StringWriter();
            when(resp.getWriter()).thenReturn(new PrintWriter(stringWriter));
            when(req.getPathInfo()).thenReturn("/shoot/" + id);
            when(req.getMethod()).thenReturn("POST");
            when(req.getReader()).thenReturn(new BufferedReader(new StringReader(shotList.toJson())));

            gameServlet.service(req,resp);

            JsonObject shotResult = JsonParser.parseToObject(stringWriter.toString());
            if (shotResult.requiredBoolean("gameFinished")) {
                break;
            }
            shotsLeft = (int) shotResult.requiredLong("shotsNextRound");

        }

    }
}
