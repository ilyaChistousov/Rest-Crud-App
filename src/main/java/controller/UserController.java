package controller;

import dto.UserDto;
import mapper.toJson.MapperToJson;
import service.UserService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/v1/users/*")
public class UserController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    public void init() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var pathInfo = req.getPathInfo();

        if(pathInfo == null || pathInfo.equals("/")) {
            var allUsers = userService.getAll();
            MapperToJson.mapToJson(resp, allUsers);
            return;
        }

        var path = pathInfo.split("/");

        if(path.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        var maybeUser = userService.getById(Long.parseLong(path[1]));

        if(maybeUser.isPresent()) {
            MapperToJson.mapToJson(resp, maybeUser.get());
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var pathInfo = req.getPathInfo();

        if(pathInfo == null || pathInfo.equals("/")) {
            var reader = req.getReader();
            var buffer = new StringBuilder();

            while(reader.ready()) {
                buffer.append(reader.readLine());
            }

            var userDto = MapperToJson.GSON.fromJson(buffer.toString(), UserDto.class);

            userService.save(userDto);

            MapperToJson.mapToJson(resp, userDto);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var pathInfo = req.getPathInfo();

        if(pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        var split = pathInfo.split("/");

        if(split.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        var buffer = new StringBuilder();
        var reader = req.getReader();

        while(reader.ready()) {
            buffer.append(reader.readLine());
        }

        var userDto = MapperToJson.GSON.fromJson(buffer.toString(), UserDto.class);

        userService.update(userDto);

        MapperToJson.mapToJson(resp, userDto);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        userService.delete(Long.parseLong(split[1]));
    }

}
