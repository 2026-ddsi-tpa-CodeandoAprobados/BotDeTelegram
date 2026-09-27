package ar.edu.utn.dds.k3003.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

public class LogisticaClient {
    private final RestClient restClient;

    public LogisticaClient(@Value("${LOGISTICA_API_URL}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }


}
