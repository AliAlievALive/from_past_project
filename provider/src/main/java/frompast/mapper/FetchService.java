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
    private static final String PROTOCOL = "http";
    private static final String ADDRESS = "host.docker.internal";
    private static final String PORT = "8061";
    private static final Logger log = LoggerFactory.getLogger(FetchService.class);

    private FetchService() {
    }

    public static void userLoged(UUID userId, String accessToken) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            String url = String.format("%s://%s:%s/users/guid?guid=%s", PROTOCOL, ADDRESS, PORT, userId);
            log.info(url);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + accessToken)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            log.info("request {}", request);
            log.info("request header {}", request.headers());

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("status code {}", response.statusCode());

            if (response.statusCode() == 200) {
                response.body();
            } else {
                log.info("Failed to fetch roles. Status code: {}", response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            log.error("Error fetching roles: {}", e.getMessage());
        }
    }
}