package ship;

    import org.jsonbuddy.JsonArray;
    import org.jsonbuddy.JsonFactory;
    import org.jsonbuddy.JsonObject;
    import org.jsonbuddy.parse.JsonParser;

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.io.OutputStreamWriter;
    import java.io.PrintWriter;
    import java.io.Reader;
    import java.net.URL;
    import java.net.URLConnection;
    import java.util.ArrayList;
    import java.util.List;



public class BattleshipSolver {

    private static final String BASE_URL="https://afternoon-hamlet-65489.herokuapp.com/";
    private static final String PLAYER_ID="404eb354-aaa3-49d2-af12-889497dad682";

    public static void main(String[] args) throws Exception {
        new BattleshipSolver().solve();
        //String res = httpPost(BASE_URL + "game/shoot/" + PLAYER_ID, "[[0,0],[1,0]]");
        //System.out.println(res);
    }

    private void solve() throws Exception {
        int x=0;
        int y=0;
        int shotsLeft=20;

        while (true) {
            JsonArray shots = JsonFactory.jsonArray();
            for (int i=0;i<shotsLeft;i++) {
                shots.add(JsonFactory.jsonArray().add(x).add(y));
                x++;
                if (x >= 20) {
                    x=0;
                    y++;
                    if (y >= 20) {
                        System.out.println("FAIL");
                        break;
                    }
                }
            }
            String res = httpPost(BASE_URL + "game/shoot/" + PLAYER_ID,shots.toJson());
            System.out.println(res);
            JsonObject result = JsonParser.parseToObject(res);
            if (result.requiredBoolean("gameFinished")) {
                break;
            }
            shotsLeft = (int) result.requiredLong("shotsNextRound");

        }
    }


    private static String httpPost(String url, String answer) throws Exception {
        URLConnection conn = new URL(url).openConnection();
        conn.setDoOutput(true);
        try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"))) {
            printWriter.append(answer);
        }
        return toString(conn.getInputStream());
    }

    private static String toString(InputStream inputStream) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"))) {
            StringBuilder result = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                result.append((char)c);
            }
            return result.toString();
        }
    }
}

