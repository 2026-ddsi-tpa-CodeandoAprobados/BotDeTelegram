package ar.edu.utn.dds.k3003.clients;

import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.DonacionDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.EstadoDonacionEnum;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.CategoriaDonadorEnum;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.InsigniaDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.MisionDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.TipoMisionEnum;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class IncentivosClient {
    private final RestClient restClient;

    public IncentivosClient(@Value("${INCENTIVOS_API_URL}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public String crearInsignia(String nombre, String descripcion) {
        try {
            InsigniaDTO insignia = new InsigniaDTO(null, nombre, descripcion);
            restClient.post()
                    .uri("/insignias")
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .body(insignia)
                    .retrieve()
                    .toBodilessEntity();
            return "Insignia creada exitosamente";
        } catch (Exception e) {
            return "Hubo un error al crear la insignia: " + e.getMessage();
        }
    }

    public String consultarTodasLasInsignias() {
        try {
            String jsonCrudo = restClient.get()
                    .uri("/insignias")
                    .retrieve()
                    .body(String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonCrudo);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
            return "Hubo un error al conectar con la API: " + e.getMessage();
        }
    }

    public String consultarInsigniaPorID(String insigniaID) {
        try {
            String jsonCrudo = restClient.get()
                    .uri("/insignias/{insigniaID}", insigniaID)
                    .retrieve()
                    .body(String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonCrudo);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
            return "Hubo un error o el ID no existe: " + e.getMessage();
        }
    }

    public String borrarInsignia(String insigniaID) {
        try {
            restClient.delete()
                    .uri("/insignias/{insigniaID}", insigniaID)
                    .retrieve()
                    .toBodilessEntity();
            return "Insignia borrada exitosamente";
        } catch (Exception e) {
            return "Hubo un error o el ID no existe: " + e.getMessage();
        }
    }

    public String crearMision(String nombre, String insigniaID, CategoriaDonadorEnum categoriaInicio, CategoriaDonadorEnum categoriaFin, TipoMisionEnum tipo) {
        try {
            MisionDTO mision = new MisionDTO(null, nombre, insigniaID, categoriaInicio, categoriaFin, tipo);
            restClient.post()
                    .uri("/misiones")
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .body(mision)
                    .retrieve()
                    .toBodilessEntity();
            return "Mision creada exitosamente";
        } catch (Exception e) {
            return "Hubo un error al crear la mision: " + e.getMessage();
        }
    }

    public String consultarMisionPorID(String misionID) {
        try {
            String jsonCrudo = restClient.get()
                    .uri("/misiones/{misionID}", misionID)
                    .retrieve()
                    .body(String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonCrudo);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
            return "Hubo un error o el ID no existe: " + e.getMessage();
        }
    }

    public String consultarTodasLasMisiones() {
        try {
            String jsonCrudo = restClient.get()
                    .uri("/misiones")
                    .retrieve()
                    .body(String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonCrudo);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
            return "Hubo un error al conectar con la API: " + e.getMessage();
        }
    }

    public String borrarMision(String misionID) {
        try {
            restClient.delete()
                    .uri("/misiones/{misionID}", misionID)
                    .retrieve()
                    .toBodilessEntity();
            return "Mision borrada exitosamente";
        } catch (Exception e) {
            return "Hubo un error o el ID no existe: " + e.getMessage();
        }
    }
}
