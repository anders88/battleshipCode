package servlet;

import game.FunctionalError;
import game.GameCentral;
import game.Player;
import game.ShotResult;
import org.jsonbuddy.JsonArray;
import org.jsonbuddy.JsonConversionException;
import org.jsonbuddy.JsonFactory;
import org.jsonbuddy.JsonNode;
import org.jsonbuddy.parse.JsonParseException;
import org.jsonbuddy.parse.JsonParser;
import org.jsonbuddy.pojo.JsonGenerator;
import ship.Position;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = Optional.ofNullable(req.getPathInfo()).orElse("");
        switch (PathComputation.computePost(pathInfo)) {
            case SHOT:
                handleShot(pathInfo,req.getReader(),resp.getWriter());
                return;
            case ADD_PLAYER:
                addPlayer(req.getReader(),resp.getWriter());
                break;
            case UNKNOWN:
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Unknown path");
                return;

        }
    }

    private void addPlayer(BufferedReader reader, PrintWriter writer) {
        String name = JsonParser.parseToObject(reader).requiredString("name");
        Player player = GameCentral.instance().addPlayer(name);
        JsonFactory.jsonObject().put("id",player.getId()).toJson(writer);
    }

    private void handleShot(String pathInfo, BufferedReader reader, PrintWriter writer) {
        int lastIndexOf = pathInfo.lastIndexOf("/");
        if (lastIndexOf >= pathInfo.length()-1) {
            throw new FunctionalError("Illegal id for shot");
        }
        String id = pathInfo.substring(lastIndexOf+1);
        JsonArray parsed;
        try {
            parsed = JsonParser.parseToArray(reader);
        } catch (JsonParseException ex) {
            throw new FunctionalError("Illegal shot input. " + ex.getMessage());
        }
        List<Position> shotList = parsed.nodeStream().map(jsonNode -> {
            if (!(jsonNode instanceof JsonArray)) {
                throw new FunctionalError("Shot has illegal format");
            }
            JsonArray shotCoord = (JsonArray) jsonNode;
            if (shotCoord.size() != 2) {
                throw new FunctionalError("Shot has illegal format");
            }
            try {
                int x = shotCoord.requiredNumber(0).intValue();
                int y = shotCoord.requiredNumber(1).intValue();
                return new Position(x, y);
            } catch (JsonConversionException ex) {
                throw new FunctionalError("Shot has illegal format");
            }
        }).collect(Collectors.toList());
        ShotResult shotResult = GameCentral.instance().shot(id, shotList);
        JsonGenerator.generate(shotResult).toJson(writer);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            super.service(req, resp);
        } catch (FunctionalError error) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,error.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GameCentral.instance().playerData().toJson(resp.getWriter());
    }
}
