package controller;

import dto.FileDto;
import mapper.toJson.MapperToJson;
import service.FileService;
import service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/v1/files/*")
public class FileController extends HttpServlet {

    private final FileService fileService = new FileService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var pathInfo = req.getPathInfo();
        if(pathInfo == null || pathInfo.equals("/")) {
            var allFiles = fileService.getAll();
            MapperToJson.mapToJson(resp, allFiles);
            return;
        }
        var path = pathInfo.split("/");
        if(path.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        var maybeFile = fileService.getById(Long.parseLong(path[1]));
        if(maybeFile.isPresent()) {
            MapperToJson.mapToJson(resp, maybeFile.get());
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        var buffer = new StringBuilder();
        var reader = req.getReader();
        while(reader.ready()) {
            buffer.append(reader.readLine());
        }
        var fileDto = MapperToJson.GSON.fromJson(buffer.toString(), FileDto.class);
        var maybeUser = userService.getById(Long.parseLong(split[1]));
        if(maybeUser.isPresent()) {
            fileService.save(fileDto, Long.parseLong(split[1]));
            MapperToJson.mapToJson(resp, fileDto);
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
        var fileDto = MapperToJson.GSON.fromJson(buffer.toString(), FileDto.class);
        fileService.update(fileDto);
        MapperToJson.mapToJson(resp, fileDto);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        var maybeFile = fileService.getById(Long.parseLong(split[1]));
        if (maybeFile.isPresent()) {
            fileService.delete(Long.parseLong(split[1]));
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
