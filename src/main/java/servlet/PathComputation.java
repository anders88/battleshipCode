package servlet;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class PathComputation {
    public static Path computePost(String pathInfo) {

        if (pathInfo.startsWith("/shoot")) {
            return Path.SHOT;
        }
        if (pathInfo.startsWith("/addPlayer")) {
            return Path.ADD_PLAYER;
        }
        return Path.UNKNOWN;
    }
}
