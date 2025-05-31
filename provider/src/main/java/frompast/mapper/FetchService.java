package frompast.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class FetchService {
    private static final String PROTOCOL = "http"; //System.getenv("ID_SERVICE_PROTOCOL");
    private static final String ADDRESS = "host.docker.internal"; //System.getenv("ID_SERVICE_ADDRESS");
    private static final String PORT = "8061"; //System.getenv("ID_SERVICE_PORT");
    private static final Logger log = LoggerFactory.getLogger(FetchService.class);

    private FetchService() {
    }

    public static String fetchRolesForUser(UUID userId, String accessToken) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            String url = String.format("%s://%s:%s/users/guid?guid=%s", PROTOCOL, ADDRESS, PORT, userId);
            log.info(url);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + accessToken)
                    .build();
            log.info("request {}", request);
            log.info("request header {}", request.headers());

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("status code {}", response.statusCode());
            log.info("body {}", response.body());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                log.info("Failed to fetch roles. Status code: {}", response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            log.error("Error fetching roles: {}", e.getMessage());
        }
        return "";
    }
}