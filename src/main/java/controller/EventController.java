package controller;

import mapper.toJson.MapperToJson;
import service.EventService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/v1/events/*")
public class EventController extends HttpServlet {

    private final EventService eventService = new EventService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var pathInfo = req.getPathInfo();

        if(pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        var split = pathInfo.split("/");

        if(split.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        var events = eventService.getByUserId(Long.parseLong(split[1]));

        MapperToJson.mapToJson(resp, events);
    }

}
