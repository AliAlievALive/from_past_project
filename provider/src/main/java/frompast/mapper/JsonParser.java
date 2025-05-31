package frompast.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import frompast.mapper.dto.UserApplicationPermissionDto;

import java.io.IOException;
import java.util.List;

public class JsonParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<UserApplicationPermissionDto> parseJsonToList(String json) throws IOException {
        return objectMapper.readValue(json, new TypeReference<>() {});
    }
}