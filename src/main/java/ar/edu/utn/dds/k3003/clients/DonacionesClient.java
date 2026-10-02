package ar.edu.utn.dds.k3003.clients;

import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class DonacionesClient {
    private final RestClient restClient;

    public DonacionesClient(@Value("${DONACIONES_API_URL}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public String registrarDonacion(String donadorID, String depositoID, String descripcion, String productoID, Integer cantidad, EstadoDonacionEnum estado) {
        try {
            DonacionDTO donacion = new DonacionDTO(null, donadorID, depositoID, descripcion, productoID, cantidad, estado);
            restClient.post()
                    .uri("/donaciones")
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .body(donacion)
                    .retrieve()
                    .toBodilessEntity();
            return "Donacion registrada exitosamente";
        } catch (Exception e) {
            return "Hubo un error al registrar la donacion: " + e.getMessage();
        }
    }

    public String consultarTodasLasDonaciones() {
        try {
            String jsonCrudo = restClient.get()
                    .uri("/donaciones")
                    .retrieve()
                    .body(String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonCrudo);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
            return "Hubo un error al conectar con la API: " + e.getMessage();
        }
    }

    public String consultarDonacionPorID(String donacionID) {
        try {
            String jsonCrudo = restClient.get()
                    .uri("/donaciones/{donacionID}", donacionID)
                    .retrieve()
                    .body(String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonCrudo);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
            return "Hubo un error o el ID no existe: " + e.getMessage();
        }
    }

    public String modificarEstado(String donacionID, EstadoDonacionEnum estado){
        try {
            DonacionDTO donacion = new DonacionDTO(donacionID, null, null, null, null, null, estado);
            restClient.patch()
                    .uri("/donaciones/{donacionID}/estado", donacionID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(donacion)
                    .retrieve()
                    .toBodilessEntity();
            return "Donacion modificada exitosamente";
        } catch (Exception e) {
            return "Hubo un error o el ID no existe: " + e.getMessage();
        }
    }

    public String crearCategoria(String nombre, String descripcion, String subcategoriaID) {
        try {
            CategoriaDTO categoria = new CategoriaDTO(null, nombre, descripcion, subcategoriaID);
            restClient.post()
                    .uri("/categorias")
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .body(categoria)
                    .retrieve()
                    .toBodilessEntity();
            return "Categoria creada correctamente";
        } catch (Exception e) {
            return "Hubo un error al crear la categoria: " + e.getMessage();
        }
    }

    public String consultarTodasLasCategorias() {
        try {
            String jsonCrudo = restClient.get()
                    .uri("/categorias")
                    .retrieve()
                    .body(String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonCrudo);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
            return "Hubo un error al conectar con la API: " + e.getMessage();
        }
    }

    public String borrarCategoria(String categoriaID) {
        try {
            restClient.delete()
                    .uri("/categorias/{categoriaID}", categoriaID)
                    .retrieve()
                    .toBodilessEntity();
            return "Categoria borrada exitosamente";
        } catch (Exception e) {
            return "Hubo un error o el ID no existe: " + e.getMessage();
        }
    }

    public String crearIdentificador(TipoIdentificadorEnum tipo, String descripcion) {
        try {
            IdentificadorDTO identificador = new IdentificadorDTO(null, tipo, descripcion);
            restClient.post()
                    .uri("/identificadores")
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .body(identificador)
                    .retrieve()
                    .toBodilessEntity();
            return "Identificador creado correctamente";
        } catch (Exception e) {
            return "Hubo un error al crear la categoria: " + e.getMessage();
        }
    }

    public String consultarTodosLosIdentificadores() {
        try {
            String jsonCrudo = restClient.get()
                    .uri("/identificadores")
                    .retrieve()
                    .body(String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonCrudo);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
            return "Hubo un error al conectar con la API: " + e.getMessage();
        }
    }

    public String borrarIdentificador(String identificadorID) {
        try {
            restClient.delete()
                    .uri("/identificadores/{identificadorID}", identificadorID)
                    .retrieve()
                    .toBodilessEntity();
            return "Identificador borrado exitosamente";
        } catch (Exception e) {
            return "Hubo un error o el ID no existe: " + e.getMessage();
        }
    }
}
