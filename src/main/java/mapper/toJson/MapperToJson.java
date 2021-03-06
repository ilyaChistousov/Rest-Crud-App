package mapper.toJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class MapperToJson {

    public static final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
            new LocalDateTimeAdapter().nullSafe()).create();

    @SneakyThrows
    public static void mapToJson(HttpServletResponse response, Object object) {
        response.setContentType("application/json");
        var toJson = GSON.toJson(object);
        var writer = response.getWriter();
        writer.write(toJson);
    }
}
