package ar.edu.utn.dds.k3003.service;

import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.*;
import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.*;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.*;
import ar.edu.utn.dds.k3003.catedra.dtos.logistica.*;
import ar.edu.utn.dds.k3003.clients.DonacionesClient;
import ar.edu.utn.dds.k3003.clients.DonadoresYEntidadesClient;
import ar.edu.utn.dds.k3003.clients.IncentivosClient;
import ar.edu.utn.dds.k3003.clients.LogisticaClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Component
public class Grupo7Bot extends TelegramLongPollingBot {

    private final DonadoresYEntidadesClient donadoresYEntidadesClient;
    private final DonacionesClient donacionesClient;
    private final LogisticaClient logisticaClient;
    private final IncentivosClient incentivosClient;

    @Value("${TOKEN_BOT}")
    private String botToken;

    @Value("${NAME_BOT}")
    private String botUsername;

    public Grupo7Bot(DonadoresYEntidadesClient donadoresYEntidadesClient,
                     DonacionesClient donacionesClient,
                     LogisticaClient logisticaClient,
                     IncentivosClient incentivosClient) {
        this.donadoresYEntidadesClient = donadoresYEntidadesClient;
        this.donacionesClient = donacionesClient;
        this.logisticaClient = logisticaClient;
        this.incentivosClient = incentivosClient;
    }

    @PostConstruct
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final String messageTextReceived = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            String respuesta = procesarComando(messageTextReceived);
            enviarMensaje(chatId.toString(), respuesta);
        }
    }

    private String procesarComando(String comando) {
        switch (comando) {
            case "/start" -> {
                return "Bienvenido al sistema! ¿Qué tipo de usuario sos?\n" +
                        "/donadores\n" +
                        "/donaciones\n" +
                        "/logistica\n" +
                        "/incentivos\n" +
                        "/Admin";
            }
            case "/donadores" -> {
                return "Opciones de Donador (Ingresá el comando para ejecutar):\n" +
                        "/registrar_donador - Registrar un donador\n" +
                        "/mis_estadisticas [ID] - Consultar sus estadísticas\n" +
                        "/consultar_donadores - Ver todos los donadores\n" +
                        "/consultar_donador_id [ID] - Buscar donador por ID";
            }
            case "/donaciones" -> {
                return "Opciones de Donaciones (Ingresá el comando para ejecutar):\n" +
                        "/registrar_donacion - Registrar una donacion\n" +
                        "/consultar_donaciones - Ver todas las donaciones\n" +
                        "/consultar_donacion_id [ID] - Buscar una donacion por ID";
            }
            case "/logistica" -> {
                return "Opciones de Logistica (Ingresá el comando para ejecutar):\n" +
                        "/crear_deposito - Crear un deposito\n" +
                        "/consultar_depositos - Ver todos los depositos\n" +
                        "/consultar_deposito_id [ID] - Buscar un deposito por ID";
            }
            case "/incentivos" -> {
                return "Opciones de Incentivos (Ingresá el comando para ejecutar):\n" +
                        "/crear_mision - Crear una mision\n" +
                        "/consultar_misiones - Ver todas las misiones\n" +
                        "/consultar_mision_id [ID] - Buscar una mision por ID";
            }
            case "/Admin" -> {
                return "Opciones de Admin (Ingresá el comando para ejecutar):\n\n" +

                        "DONADORES Y ENTIDADES\n\n" +
                        "/crear_entidad - Crear una entidad\n" +
                        "/editar_entidad - Editar razon social de una entidad\n" +
                        "/consultar_entidades - Ver todas las entidades\n" +
                        "/consultar_entidad_id [ID] - Buscar entidad por ID\n" +
                        "/alta_necesidad - Alta de una necesidad\n" +
                        "/borrar_necesidad [ID] - Borrar necesidad\n" +
                        "/modificar_necesidad - Modificar la cantidad objetivo de una necesidad\n" +
                        "/consultar_necesidades - Ver todas las necesidades\n" +
                        "/consultar_necesidad [ID] - Ver todas las necesidad de un producto\n\n" +

                        "DONACIONES\n\n"+
                        "/modificar_estado - Modificar el estado de una donacion\n" +
                        "/crear_categoria - Crear una categoria\n" +
                        "/consultar_categorias - Ver todas las categorias\n" +
                        "/borrar_categoria [ID] - Borrar una categoria\n" +
                        "/crear_identificador - Crear un identificador\n" +
                        "/consultar_identificadores - Ver todos los identificadores\n" +
                        "/borrar_identificador [ID] - Borrar un identificador\n\n" +

                        "LOGISTICA\n\n" +
                        "/modificar_algoritmo - Modificar el algoritmo de un deposito\n" +
                        "/modificar_deposito - Modifica nombre, direccion y cantidad maxima de un deposito\n" +
                        "/borrar_deposito [ID] - Borrar un deposito\n" +
                        "/consultar_stock [ID] - Ver el stock disponible de un produto\n" +
                        "/consultar_asignaciones - Ver todas las asignaciones\n" +
                        "/consultar_asignacion_id - Buscar una asignacion por ID\n" +
                        "/consultar_paquetes - Ver todos los paquetes\n\n" +

                        "INCENTIVOS\n\n" +
                        "/borrar_mision [ID] - Borrar una mision\n" +
                        "/crear_insignia - Crear insignia\n" +
                        "/consultar_insignias - Ver todas las insignias\n" +
                        "/consultar_insignia_id [ID] - Buscar una insignia por ID\n" +
                        "/borrar_insignia [ID] - Borrar una insignia";
            }
        }
//DONADORES Y ENTIDADES
        if (comando.startsWith("/registrar_donador")) {
            String datosCrudos = comando.replace("/registrar_donador", "").trim();

            if (datosCrudos.isEmpty()) {
                return "Para registrarte, enviá tus datos separados por coma.\n" +
                        "Ejemplo: `/registro_donador Nombre, Apellido, Edad, Email, DNI, Domicilio`";
            }
            String[] datos = datosCrudos.split(",");

            try {
                return donadoresYEntidadesClient.registrarDonador(datos[0].trim(),
                            datos[1].trim(),
                            Integer.parseInt(datos[2].trim()),
                            datos[3].trim(),
                            datos[4].trim(),
                            datos[5].trim());
                } catch (NumberFormatException e) {
                    return "Faltan o sobran datos. Asegurate de enviar los 6 datos separados por comas";
                }
        }

        if (comando.startsWith("/consultar_donador_id")) {
            String[] datos = comando.split(" ",2);
            try {
                return donadoresYEntidadesClient.consultarDonadorPorID(datos[1].trim());
            } catch (NumberFormatException e){
                return "Comando incompleto. Usa el formato: /consultar_donador_id [ID]";
            }
        }

        if (comando.startsWith("/consultar_donadores")){
            return donadoresYEntidadesClient.consultarTodosLosDonadores();
        }

        if (comando.startsWith("/mis_estadisticas")) {
            String[] datos = comando.split(" ",2);
            try {
                return donadoresYEntidadesClient.consultarEstadisticasDeUnDonador(datos[1].trim());
            } catch (NumberFormatException e) {
                return "Comando incompleto. Usa el formato: /mis_estadisticas [ID]";
            }
        }

        if(comando.startsWith("/crear_entidad")){
            String datosCrudos = comando.replace("/crear_entidad", "").trim();

            if (datosCrudos.isEmpty()) {
                return "Para crear una entidad, enviá los datos separados por coma.\n" +
                        "Ejemplo: /crear_entidad Razon social, Domicilio, Telefono, Correo";
            }
            String[] datos = datosCrudos.split(",",4);

            try {
                return donadoresYEntidadesClient.crearEntidad(datos[0].trim(),
                            datos[1].trim(),
                            datos[2].trim(),
                            datos[3].trim());
                } catch (NumberFormatException e) {
                    return "Faltan o sobran datos. Asegurate de enviar los 4 datos separados por comas";
                }
        }

        if(comando.startsWith("/editar_entidad")){
            String datosCrudos = comando.replace("/editar_entidad", "").trim();
            if (datosCrudos.isEmpty()) {
                return "Para modificar una entidad benefica, enviá el ID y la nueva razon social separados por coma.\n" +
                        "Ejemplo: /editar_entidad ID, Razon social";
            }
            String[] datos = datosCrudos.split(",",2);
            try{
                return donadoresYEntidadesClient.editarEntidad(datos[0].trim(), datos[1].trim());
            } catch (NumberFormatException e) {
                return "Comando incompleto. Usa el formato: /editar_entidad [ID]";
            }
        }

        if(comando.startsWith("/consultar_entidades")){
            return donadoresYEntidadesClient.consultarTodasLasEntidades();
        }

        if (comando.startsWith("/consultar_entidad_id")) {
            String[] datos = comando.split(" ",2);
            try {
                return donadoresYEntidadesClient.consultarEntidadPorID(datos[1].trim());
            } catch (NumberFormatException e){
                return "Comando incompleto. Usa el formato: /consultar_donador_id [ID]";
            }
        }

        if(comando.startsWith("/alta_necesidad")){
            String datosCrudos = comando.replace("/alta_necesidad", "").trim();

            if (datosCrudos.isEmpty()) {
                return "Para dar de alta una necesidad, enviá los datos separados por coma.\n" +
                        "Ejemplo: /alta_necesidad EntidadID, Nivel de urgencia, Descripcion, Cantidad objetivo, ProductoSolicitadoID, Tipo de necesidad";
            }
            String[] datos = datosCrudos.split(",",6);

            try {
                return donadoresYEntidadesClient.altaNecesidad(
                        datos[0].trim(),
                        Integer.parseInt(datos[1].trim()),
                        datos[2].trim(),
                        Integer.parseInt(datos[3].trim()),
                        datos[4].trim(),
                        TipoNecesidadMaterialEnum.valueOf(datos[5].trim()));
            } catch (NumberFormatException e) {
                return "Faltan o sobran datos. Asegurate de enviar los 6 datos separados por comas";
            }
        }

        if(comando.startsWith("/borrar_necesidad")){
            String[] datos = comando.split(" ",2);
            try {
                return donadoresYEntidadesClient.borrarNecesidad(datos[1].trim());
            } catch (NumberFormatException e){
                return "Comando incompleto. Usa el formato: /borrar_necesidad [ID]";
            }
        }

        if(comando.startsWith("/modificar_necesidad")){
            String datosCrudos = comando.replace("/modificar_necesidad", "").trim();

            if (datosCrudos.isEmpty()) {
                return "Para modificar una necesidad, enviá el ID y la nueva cantidad objetivo separados por coma.\n" +
                        "Ejemplo: /modificar_necesidad ID, Cantidad objetivo";
            }
            String[] datos = datosCrudos.split(",",2);
            try{
                return donadoresYEntidadesClient.modificarNecesidad(datos[0].trim(), Integer.parseInt(datos[1].trim()));
            } catch (NumberFormatException e) {
                return "Comando incompleto. Usa el formato: /modificar_necesidad [ID]";
            }
        }

        if (comando.startsWith("/consultar_necesidades")){
            return donadoresYEntidadesClient.consultarTodasLasNecesidades();
        }

        if(comando.startsWith("/consultar_necesidad")) {
            String[] datos = comando.split(" ",2);
            try {
                return donadoresYEntidadesClient.consultarNecesidadPorID(datos[1].trim());
            } catch (NumberFormatException e){
                return "Comando incompleto. Usa el formato: /consultar_necesidad [ID]";
            }
        }

//DONACIONES

        if (comando.startsWith("/registrar_donacion")) {
            String datosCrudos = comando.replace("/registrar_donacion", "").trim();

            if (datosCrudos.isEmpty()) {
                return "Para crear una donacion, enviá los datos separados por coma.\n" +
                        "Ejemplo: `/registrar_donacion DonadorID, DepositoID, Descripcion, ProductoID, Cantidad, Estado de una Donacion`";
            }
            String[] datos = datosCrudos.split(",");

            try {
                return donacionesClient.registrarDonacion(datos[0].trim(),
                        datos[1].trim(),
                        datos[2].trim(),
                        datos[3].trim(),
                        Integer.parseInt(datos[4].trim()),
                        EstadoDonacionEnum.valueOf(datos[5].trim()));

            } catch (NumberFormatException e) {
                return "Faltan o sobran datos. Asegurate de enviar los 6 datos separados por comas";
            }
        }

        if (comando.startsWith("/consultar_donacion_id")) {
            String[] datos = comando.split(" ",2);
            try {
                return donacionesClient.consultarDonacionPorID(datos[1].trim());
            } catch (NumberFormatException e){
                return "Comando incompleto. Usa el formato: /consultar_donacion_id [ID]";
            }
        }

        if (comando.startsWith("/consultar_donaciones")){
            return donacionesClient.consultarTodasLasDonaciones();
        }

        if(comando.startsWith("/modificar_estado")){
            String datosCrudos = comando.replace("/modificar_estado", "").trim();

            if (datosCrudos.isEmpty()) {
                return "Para modificar el estado de una donacion, enviá el ID y el nuevo estado separados por coma.\n" +
                        "Ejemplo: /modificar_estado ID, Estado";
            }
            String[] datos = datosCrudos.split(",",2);
            try{
                return donacionesClient.modificarEstado(datos[0].trim(), EstadoDonacionEnum.valueOf(datos[1].trim()));
            } catch (NumberFormatException e) {
                return "Comando incompleto. Usa el formato: /modificar_estado";
            }
        }

        if (comando.startsWith("/crear_categoria")) {
            String datosCrudos = comando.replace("/crear_categoria", "").trim();

            if (datosCrudos.isEmpty()) {
                return "Para crear una donacion, enviá los datos separados por coma.\n" +
                        "Ejemplo: `/crar_categoria Nombre, Descripcion`";
            }
            String[] datos = datosCrudos.split(",");

            try {
                return donacionesClient.crearCategoria(datos[0].trim(),
                        datos[1].trim(),
                        datos[2].trim());

            } catch (NumberFormatException e) {
                return "Faltan o sobran datos. Asegurate de enviar los 2 datos separados por comas";
            }
        }

        if (comando.startsWith("/consultar_categorias")){
            return donacionesClient.consultarTodasLasCategorias();
        }

        if(comando.startsWith("/borrar_categoria")){
            String[] datos = comando.split(" ",2);
            try {
                return donacionesClient.borrarCategoria(datos[1].trim());
            } catch (NumberFormatException e){
                return "Comando incompleto. Usa el formato: /borrar_categoria [ID]";
            }
        }

        if (comando.startsWith("/crear_identificador")) {
            String datosCrudos = comando.replace("/crear_identificador", "").trim();

            if (datosCrudos.isEmpty()) {
                return "Para crear un identificador, enviá los datos separados por coma.\n" +
                        "Ejemplo: `/crear_identificador Descripcion, Tipo`";
            }
            String[] datos = datosCrudos.split(",");

            try {
                return donacionesClient.crearIdentificador(
                        TipoIdentificadorEnum.valueOf(datos[0].trim()),
                        datos[1].trim());

            } catch (NumberFormatException e) {
                return "Faltan o sobran datos. Asegurate de enviar los 2 datos separados por comas";
            }
        }

        if (comando.startsWith("/consultar_identificadores")){
            return donacionesClient.consultarTodosLosIdentificadores();
        }

        if(comando.startsWith("/borrar_identificador")){
            String[] datos = comando.split(" ",2);
            try {
                return donacionesClient.borrarIdentificador(datos[1].trim());
            } catch (NumberFormatException e){
                return "Comando incompleto. Usa el formato: /borrar_identificador [ID]";
            }
        }

//INCENTIVOS

        if (comando.startsWith("/crear_insignia")) {
            String datosCrudos = comando.replace("/crear_insignia", "").trim();

            if (datosCrudos.isEmpty()) {
                return "Para crear una insignia, enviá los datos separados por coma.\n" +
                        "Ejemplo: `/crear_insignia Nombre, Descripcion`";
            }
            String[] datos = datosCrudos.split(",");

            try {
                return incentivosClient.crearInsignia(datos[0].trim(), datos[1].trim());
            } catch (NumberFormatException e) {
                return "Faltan o sobran datos. Asegurate de enviar los 2 datos separados por comas";
            }
        }

        if (comando.startsWith("/consultar_insignia_id")) {
            String[] datos = comando.split(" ",2);
            try {
                return incentivosClient.consultarInsigniaPorID(datos[1].trim());
            } catch (NumberFormatException e){
                return "Comando incompleto. Usa el formato: /consultar_insignia_id [ID]";
            }
        }

        if (comando.startsWith("/consultar_insignias")){
            return incentivosClient.consultarTodasLasInsignias();
        }

        if (comando.startsWith("/borrar_insignia")){
            String[] datos = comando.split(" ",2);
            try {
                return incentivosClient.borrarInsignia(datos[1].trim());
            } catch (NumberFormatException e){
                return "Comando incompleto. Usa el formato: /borrar_insignia [ID]";
            }
        }

        if(comando.startsWith("/crear_mision")) {
            String datosCrudos = comando.replace("/crear_mision", "").trim();

            if (datosCrudos.isEmpty()) {
                return "Para crear una mision, enviá los datos separados por coma.\n" +
                        "Ejemplo: `/crear_mision Nombre, InsigniaID, Categoria de Inicio, Categoria de Fin, Tipo de Mision`";
            }
            String[] datos = datosCrudos.split(",");

            try {
                return incentivosClient.crearMision(datos[0].trim(),
                        datos[1].trim(),
                        CategoriaDonadorEnum.valueOf(datos[2].trim()),
                        CategoriaDonadorEnum.valueOf(datos[3].trim()),
                        TipoMisionEnum.valueOf(datos[4].trim()));
            } catch (NumberFormatException e) {
                return "Faltan o sobran datos. Asegurate de enviar los 5 datos separados por comas";
            }
        }

        if (comando.startsWith("/consultar_mision_id")) {
            String[] datos = comando.split(" ",2);
            try {
                return incentivosClient.consultarMisionPorID(datos[1].trim());
            } catch (NumberFormatException e){
                return "Comando incompleto. Usa el formato: /consultar_mision_id [ID]";
            }
        }

        if (comando.startsWith("/consultar_misiones")){
            return incentivosClient.consultarTodasLasMisiones();
        }

        if (comando.startsWith("/borrar_mision")){
            String[] datos = comando.split(" ",2);
            try {
                return incentivosClient.borrarMision(datos[1].trim());
            } catch (NumberFormatException e){
                return "Comando incompleto. Usa el formato: /borrar_mision [ID]";
            }
        }

//LOGISTICA

        if (comando.startsWith("/crear_deposito")) {
            String datosCrudos = comando.replace("/crear_deposito", "").trim();

            if (datosCrudos.isEmpty()) {
                return "Para crear un deposito, enviá los datos separados por coma.\n" +
                        "Ejemplo: `/crear_deposito Nombre, Direccion, Capacidad Maxima`";
            }
            String[] datos = datosCrudos.split(",");

            try {
                return logisticaClient.crearDeposito(
                        datos[0].trim(),
                        datos[1].trim(),
                        Integer.parseInt(datos[2].trim())
                );
            } catch (NumberFormatException e) {
                return "Faltan o sobran datos. Asegurate de enviar los 4 datos separados por comas";
            }
        }

        if (comando.startsWith("/consultar_deposito_id")) {
            String[] datos = comando.split(" ",2);
            try {
                return logisticaClient.consultarDepositoPorID(datos[1].trim());
            } catch (NumberFormatException e){
                return "Comando incompleto. Usa el formato: /consultar_deposito_id [ID]";
            }
        }

        if (comando.startsWith("/consultar_deposito")){
            return logisticaClient.consultarTodosLosDepositos();
        }

        if(comando.startsWith("/modificar_algoritmo")){
            String datosCrudos = comando.replace("/modificar_algoritmo", "").trim();

            if (datosCrudos.isEmpty()) {
                return "Para modificar el algoritmo de un deposito, enviá el ID y el nuevo algoritmo separados por coma.\n" +
                        "Ejemplo: /modificar_algoritmo ID, Algoritmo";
            }
            String[] datos = datosCrudos.split(",",4);
            try{
                return logisticaClient.modificarAlgoritmo(datos[0].trim(), TipoAlgoritmoEnum.valueOf(datos[1].trim()));
            } catch (NumberFormatException e) {
                return "Comando incompleto. Usa el formato: /modificar_algoritmo [ID]";
            }
        }

        if(comando.startsWith("/modificar_deposito")){
            String datosCrudos = comando.replace("/modificar_deposito", "").trim();

            if (datosCrudos.isEmpty()) {
                return "Para modificar un deposito, enviá el ID y el nuevo nombre, direccion o capacidad maxima separados por coma.\n" +
                        "Ejemplo: /modificar_deposito ID, Nombre, Direccion, Capacidad maxima";
            }
            String[] datos = datosCrudos.split(",",2);
            try{
                return logisticaClient.modificarDesposito(datos[0].trim(), datos[1].trim(), datos[2].trim(), Integer.parseInt(datos[3].trim()));
            } catch (NumberFormatException e) {
                return "Comando incompleto. Usa el formato: /modificar_deposito";
            }
        }

        if (comando.startsWith("/borrar_deposito")){
            String[] datos = comando.split(" ",2);
            try {
                return logisticaClient.borrarDeposito(datos[1].trim());
            } catch (NumberFormatException e){
                return "Comando incompleto. Usa el formato: /borrar_deposito [ID]";
            }
        }

        if (comando.startsWith("/consultar_stock")) {
            String[] datos = comando.split(" ",2);
            try {
                return logisticaClient.consultarStockPorID(datos[1].trim());
            } catch (NumberFormatException e){
                return "Comando incompleto. Usa el formato: /consultar_stock [ID]";
            }
        }

        if (comando.startsWith("/consultar_asignaciones")){
            return logisticaClient.consultarTodasLasAsignaciones();
        }

        if (comando.startsWith("/consultar_asignacion_id")) {
            String[] datos = comando.split(" ",2);
            try {
                return logisticaClient.consultarAsignacionPorID(datos[1].trim());
            } catch (NumberFormatException e){
                return "Comando incompleto. Usa el formato: /consultar_asignacion_id [ID]";
            }
        }

        if (comando.startsWith("/consultar_paquetes")){
            return logisticaClient.consultarTodosLosPaquetes();
        }

        return "Comando no reconocido. Usá /start para ver el menú inicial.";
    }

    private void enviarMensaje(String chatId, String texto) {
        SendMessage message = new SendMessage(chatId, texto);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Error al enviar mensaje: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}