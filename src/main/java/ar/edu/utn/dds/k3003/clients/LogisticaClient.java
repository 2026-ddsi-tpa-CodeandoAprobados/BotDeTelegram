package ar.edu.utn.dds.k3003.clients;

import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.DonacionDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.EstadoDonacionEnum;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.InsigniaDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.logistica.DepositoDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.logistica.PaqueteDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.logistica.TipoAlgoritmoEnum;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class LogisticaClient {
    private final RestClient restClient;

    public LogisticaClient(@Value("${LOGISTICA_API_URL}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public String crearDeposito(TipoAlgoritmoEnum algoritmo, String nombre, String descripcion, Integer capacidadMaxima) {
        try {
            DepositoDTO deposito = new DepositoDTO(null, algoritmo, descripcion, nombre, capacidadMaxima, null);
            restClient.post()
                    .uri("/depositos")
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .body(deposito)
                    .retrieve()
                    .toBodilessEntity();
            return "Deposito creado exitosamente";
        } catch (Exception e) {
            return "Hubo un error al crear el deposito: " + e.getMessage();
        }
    }

    public String consultarTodosLosDepositos() {
        try {
            String jsonCrudo = restClient.get()
                    .uri("/despositos")
                    .retrieve()
                    .body(String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonCrudo);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
            return "Hubo un error al conectar con la API: " + e.getMessage();
        }
    }

    public String consultarDepositoPorID(String depositoID) {
        try {
            String jsonCrudo = restClient.get()
                    .uri("/depositos/{depositoID}", depositoID)
                    .retrieve()
                    .body(String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonCrudo);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
            return "Hubo un error o el ID no existe: " + e.getMessage();
        }
    }

        public String modificarAlgoritmo(String depositoID, TipoAlgoritmoEnum algoritmo){
            try {
                DepositoDTO deposito = new DepositoDTO(depositoID, algoritmo, null, null, null, null);
                restClient.patch()
                        .uri("/depositos/{depositoID}/algoritmo", depositoID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(deposito)
                        .retrieve()
                        .toBodilessEntity();
                return "Deposito modificada exitosamente";
            } catch (Exception e) {
                return "Hubo un error o el ID no existe: " + e.getMessage();
            }
        }


}
