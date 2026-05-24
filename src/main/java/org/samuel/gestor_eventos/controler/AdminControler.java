package org.samuel.gestor_eventos.controler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import org.samuel.gestor_eventos.enums.*;

import org.samuel.gestor_eventos.interfaces.creacion.FactoryCompras;
import org.samuel.gestor_eventos.interfaces.creacion.FactoryEventos;
import org.samuel.gestor_eventos.interfaces.creacion.FactoryUsuarios;

import org.samuel.gestor_eventos.modelos.*;
import org.samuel.gestor_eventos.services.AdminService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.samuel.gestor_eventos.controler.TipoFormulario.*;

public class AdminControler {

    private Scene escenaAnterior;
    private AdminService adminService = new AdminService();

    @FXML
    private Button btnVolver;

    public void setEscenaAnterior(Scene escena) {
        this.escenaAnterior = escena;
    }

    @FXML
    private void volver() {
        if (escenaAnterior != null) {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.setScene(escenaAnterior);
        }
    }

    // ==================== USUARIOS ====================

    @FXML
    private void abrirCrearUsuario() {
        abrirFormulario(USUARIO);
    }

    @FXML
    private void abrirBorrarUsuario() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Eliminar Usuario");
            dialog.setHeaderText("Eliminar usuario por ID");
            dialog.setContentText("ID:");
            var resultado = dialog.showAndWait();
            if (resultado.isEmpty()) return;

            int id = Integer.parseInt(resultado.get());
            Usuario usuario = adminService.buscarUsuario(id);
            if (usuario == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Usuario no encontrado");
                alert.show();
                return;
            }

            Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
            confirmar.setTitle("Eliminar Usuario");
            confirmar.setHeaderText(usuario.getNombre());
            confirmar.setContentText("¿Desea eliminar este usuario?");
            var respuesta = confirmar.showAndWait();
            if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
                adminService.eliminarUsuario(id);
                Alert ok = new Alert(Alert.AlertType.INFORMATION);
                ok.setHeaderText(null);
                ok.setContentText("Usuario eliminado correctamente");
                ok.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirBuscarUsuario() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Buscar Usuario");
            dialog.setHeaderText("Buscar usuario por ID");
            dialog.setContentText("ID:");
            var resultado = dialog.showAndWait();
            if (resultado.isEmpty()) return;

            int id = Integer.parseInt(resultado.get());
            Usuario usuario = adminService.buscarUsuario(id);
            if (usuario == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Usuario no encontrado");
                alert.show();
                return;
            }

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Usuario encontrado");
            info.setHeaderText(usuario.getNombre());
            info.setContentText("Correo: " + usuario.getCorroElectronico() + "\nTeléfono: " + usuario.getNumeroTelefono());
            info.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void listarUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/tabla-usuarios.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================== EVENTOS ====================

    @FXML
    private void abrirActualizarEvento() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Actualizar Evento");
            dialog.setHeaderText("Cancelar/Publicar evento");
            dialog.setContentText("Ingrese ID del evento:");
            var resultado = dialog.showAndWait();
            if (resultado.isEmpty()) return;

            int id = Integer.parseInt(resultado.get());
            Evento evento = adminService.buscarEvento(id);

            if (evento == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Evento no encontrado");
                alert.show();
                return;
            }

            ChoiceDialog<String> opciones = new ChoiceDialog<>("CANCELAR", "CANCELAR", "PUBLICAR");
            opciones.setTitle("Estado Evento");
            opciones.setHeaderText("Seleccione acción");
            opciones.setContentText("Acción:");
            var accion = opciones.showAndWait();
            if (accion.isEmpty()) return;

            if (accion.get().equals("CANCELAR")) {
                adminService.cancelarEvento(id);
                // Obtener todas las compras de ese evento para reembolsar
                reembolsarComprasDeEvento(id);
                Alert ok = new Alert(Alert.AlertType.INFORMATION);
                ok.setHeaderText(null);
                ok.setContentText("Evento cancelado. Se notificó a los usuarios y se reembolsaron las compras.");
                ok.show();
            } else {
                adminService.publicarEvento(id);
            }

            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setHeaderText(null);
            ok.setContentText("Evento actualizado correctamente");
            ok.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirGestionCompras() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/gestion-compras.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestión de Compras");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error al abrir la gestión de compras: " + e.getMessage());
            alert.show();
        }
    }

    private void reembolsarComprasDeEvento(int idEvento) {
        for (Compra compra : RepositorioAdmin.getInstance().getCompras()) {
            if (compra.getEventoAsociado().getId() == idEvento &&
                    (compra.getEstado() == EstadoCompras.PAGADA ||
                            compra.getEstado() == EstadoCompras.CONFIRMADA)) {
                compra.setEstado(EstadoCompras.REEMBOLSADA);
                // Liberar asientos
                compra.getEntradas().forEach(e -> e.getAsiento().setEstado(EstadoAsiento.DISPONIBLE));
            }
        }
    }

    @FXML
    private void abrirCrearEvento() {
        abrirFormulario(EVENTO);
    }

    @FXML
    private void abrirBorrarEvento() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Eliminar Evento");
            dialog.setHeaderText("Eliminar evento por ID");
            dialog.setContentText("ID:");
            var resultado = dialog.showAndWait();
            if (resultado.isEmpty()) return;

            int id = Integer.parseInt(resultado.get());
            Evento evento = adminService.buscarEvento(id);
            if (evento == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Evento no encontrado");
                alert.show();
                return;
            }

            Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
            confirmar.setTitle("Confirmar eliminación");
            confirmar.setHeaderText("Eliminar evento");
            confirmar.setContentText("¿Desea eliminar " + evento.getNombre() + "?");
            var respuesta = confirmar.showAndWait();
            if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
                adminService.eliminarEvento(id);
                Alert ok = new Alert(Alert.AlertType.INFORMATION);
                ok.setHeaderText(null);
                ok.setContentText("Evento eliminado correctamente");
                ok.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirBuscarEvento() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Buscar Evento");
            dialog.setHeaderText("Buscar evento por ID");
            dialog.setContentText("ID:");
            var resultado = dialog.showAndWait();
            if (resultado.isEmpty()) return;

            int id = Integer.parseInt(resultado.get());
            Evento evento = adminService.buscarEvento(id);
            if (evento == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Evento no encontrado");
                alert.show();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Evento encontrado");
            alert.setHeaderText(evento.getNombre());
            alert.setContentText("Ciudad: " + evento.getCiudad() + "\nEstado: " + evento.getEstado() + "\nFecha: " + evento.getFecha());
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void listarEventos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/tabla-eventos.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================== ZONAS ====================

    @FXML
    private void abrirCrearZona() {
        abrirFormulario(ZONA);
    }

    @FXML
    private void abrirBorrarZona() {
        // Implementar similar a usuario
    }

    // ==================== RECINTOS ====================

    @FXML
    private void abrirCrearRecinto() {
        abrirFormulario(RECINTO);
    }

    @FXML
    private void abrirMetricas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/metricas.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirReportes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/reportes.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Generador de Reportes");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error al abrir el generador de reportes: " + e.getMessage());
            alert.show();
        }
    }

    // ==================== ABRIR FORMULARIO ====================
    private void abrirFormulario(TipoFormulario tipo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/formulario.fxml"));
            Parent root = loader.load();
            FormularioControler c = loader.getController();

            switch (tipo) {
                case USUARIO -> {
                    c.setTitulo("Crear Usuario");
                    c.agregarTexto("nombre", "Nombre");
                    c.agregarTexto("correo", "Correo");
                    c.agregarTexto("telefono", "Teléfono");
                    c.agregarTexto("password", "Password");
                }
                case EVENTO -> {
                    c.setTitulo("Crear Evento");
                    c.agregarTexto("nombre", "Nombre evento");
                    c.agregarTexto("descripcion", "Descripción");
                    c.agregarTexto("ciudad", "Ciudad");
                    c.agregarFecha("fecha");

                    // Seleccionar Recinto
                    List<String> nombresRecintos = RepositorioAdmin.getInstance().getRecintos()
                            .stream().map(Recinto::getNombre).collect(Collectors.toList());
                    c.agregarComboBox("nombreRecinto", "Seleccione Recinto", nombresRecintos);

                    // Seleccionar Zonas (múltiple)
                    // NOTA: Esto se debe cargar dinámicamente después de seleccionar el recinto
                }

                case RECINTO -> {
                    c.setTitulo("Crear Recinto");
                    c.agregarTexto("nombre", "Nombre");
                    c.agregarTexto("direccion", "Dirección");
                    c.agregarTexto("ciudad", "Ciudad");
                }
                case ZONA -> {
                    c.setTitulo("Crear Zona");
                    // Cargar nombres de recintos
                    List<String> nombresRecintos = RepositorioAdmin.getInstance().getRecintos()
                            .stream().map(Recinto::getNombre).collect(Collectors.toList());
                    c.agregarComboBox("nombreRecinto", "Seleccione Recinto", nombresRecintos);

                    // Agregar selección de sector
                    List<String> sectores = Arrays.stream(Sector.values())
                            .map(Enum::name)
                            .collect(Collectors.toList());
                    c.agregarComboBox("sector", "Seleccione Sector", sectores);

                    c.agregarTexto("nombre", "Nombre de zona");
                    c.agregarTexto("capacidad", "Capacidad");
                    c.agregarTexto("precio", "Precio base");
                }
                case INCIDENCIA -> {
                    c.setTitulo("Incidencia");
                    c.agregarTexto("descripcion", "Descripción");
                }
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            Map<String, String> datos = c.getDatos();
            procesar(tipo, datos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirIncidencias() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/incidencias.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestión de Incidencias");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error al abrir la gestión de incidencias: " + e.getMessage());
            alert.show();
        }
    }

    // ==================== PROCESAR DATOS ====================
    private void procesar(TipoFormulario tipo, Map<String, String> datos) {
        switch (tipo) {
            case USUARIO -> {
                try {
                    int id = RepositorioAdmin.getInstance().getUsuarios().size() + 1;
                    Usuario usuario = new Usuario(
                            datos.get("nombre"), id, datos.get("correo"),
                            datos.get("telefono"), new ArrayList<>(), datos.get("password")
                    );
                    RepositorioAdmin.getInstance().getUsuarios().add(usuario);
                    System.out.println("Usuario creado correctamente");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            case EVENTO -> {
                try {
                    int id = RepositorioAdmin.getInstance().getEventos().size() + 1;

                    // Obtener el recinto seleccionado del ComboBox
                    String nombreRecinto = datos.get("nombreRecinto");
                    Recinto recintoSeleccionado = null;
                    for (Recinto r : RepositorioAdmin.getInstance().getRecintos()) {
                        if (r.getNombre().equals(nombreRecinto)) {
                            recintoSeleccionado = r;
                            break;
                        }
                    }

                    if (recintoSeleccionado == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setContentText("Recinto no encontrado");
                        alert.show();
                        return;
                    }

                    Evento evento = new Evento(
                            CategoriaEvento.CONCIERTO, id, datos.get("nombre"),
                            "Actividad general", datos.get("descripcion"),
                            datos.get("ciudad"), java.time.LocalDate.parse(datos.get("fecha")),
                            "18:00", "No reembolso",
                            recintoSeleccionado,  // ✅ Usa el recinto seleccionado
                            EstadoEvento.ACTIVO
                    );
                    RepositorioAdmin.getInstance().getEventos().add(evento);
                    System.out.println("Evento creado correctamente");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            case RECINTO -> {
                try {
                    int id = RepositorioAdmin.getInstance().getRecintos().size() + 1;
                    Recinto recinto = new Recinto(
                            id, datos.get("direccion"), new ArrayList<>(),
                            datos.get("ciudad"), datos.get("nombre")
                    );
                    RepositorioAdmin.getInstance().getRecintos().add(recinto);
                    System.out.println("Recinto creado correctamente");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case ZONA -> {
                try {
                    // Buscar recinto por nombre seleccionado en ComboBox
                    String nombreRecinto = datos.get("nombreRecinto");
                    Recinto recintoSeleccionado = null;
                    for (Recinto r : RepositorioAdmin.getInstance().getRecintos()) {
                        if (r.getNombre().equals(nombreRecinto)) {
                            recintoSeleccionado = r;
                            break;
                        }
                    }
                    if (recintoSeleccionado == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setContentText("Recinto no encontrado");
                        alert.show();
                        return;
                    }

                    // Obtener sector seleccionado
                    String sectorStr = datos.get("sector");
                    Sector sector = Sector.valueOf(sectorStr);

                    int idZona = RepositorioAdmin.getInstance().getZonas().size() + 1;
                    Zona zona = new Zona.ZonaBuilder(
                            Double.parseDouble(datos.get("precio")), idZona,
                            sector,  // ✅ Usa el sector seleccionado
                            datos.get("nombre"),
                            Integer.parseInt(datos.get("capacidad"))
                    ).builder();
                    zona.generarAsientosAutomaticos();
                    recintoSeleccionado.guardarComponente(zona);
                    RepositorioAdmin.getInstance().getZonas().add(zona);

                    Alert ok = new Alert(Alert.AlertType.INFORMATION);
                    ok.setHeaderText(null);
                    ok.setContentText("Zona " + datos.get("nombre") + " creada correctamente en " + recintoSeleccionado.getNombre() + " - Sector: " + sector);
                    ok.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            case INCIDENCIA -> {
                System.out.println("Incidencia registrada");
            }
        }
    }

    // ==================== MÉTODOS FALTANTES ====================

    // ---- USUARIOS ----
    @FXML
    private void abrirActualizarUsuario() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Actualizar Usuario");
            dialog.setHeaderText("Actualizar datos de usuario");
            dialog.setContentText("ID del usuario a actualizar:");

            var resultado = dialog.showAndWait();
            if (resultado.isEmpty()) return;

            int id = Integer.parseInt(resultado.get());
            Usuario usuario = adminService.buscarUsuario(id);
            if (usuario == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Usuario no encontrado");
                alert.show();
                return;
            }

            // Abrir formulario con datos del usuario
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/formulario.fxml"));
            Parent root = loader.load();
            FormularioControler c = loader.getController();

            c.setTitulo("Actualizar Usuario: " + usuario.getNombre());
            c.agregarTexto("nombre", "Nombre (actual: " + usuario.getNombre() + ")");
            c.agregarTexto("correo", "Correo (actual: " + usuario.getCorroElectronico() + ")");
            c.agregarTexto("telefono", "Teléfono (actual: " + usuario.getNumeroTelefono() + ")");
            c.agregarTexto("password", "Nueva contraseña (dejar vacío para mantener)");

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Map<String, String> datos = c.getDatos();

            // Actualizar usuario
            String nuevoNombre = datos.get("nombre");
            String nuevoCorreo = datos.get("correo");
            String nuevoTelefono = datos.get("telefono");
            String nuevaPassword = datos.get("password");

            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                usuario.setNombre(nuevoNombre);
            }
            if (nuevoCorreo != null && !nuevoCorreo.trim().isEmpty()) {
                usuario.setCorroElectronico(nuevoCorreo);
            }
            if (nuevoTelefono != null && !nuevoTelefono.trim().isEmpty()) {
                usuario.setNumeroTelefono(nuevoTelefono);
            }
            if (nuevaPassword != null && !nuevaPassword.trim().isEmpty()) {
                usuario.setPassword(nuevaPassword);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Usuario actualizado correctamente");
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---- ZONAS ----
    @FXML
    private void abrirActualizarZona() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Actualizar Zona");
            dialog.setHeaderText("Actualizar datos de zona");
            dialog.setContentText("ID de la zona a actualizar:");

            var resultado = dialog.showAndWait();
            if (resultado.isEmpty()) return;

            int id = Integer.parseInt(resultado.get());
            Zona zona = null;
            for (Zona z : RepositorioAdmin.getInstance().getZonas()) {
                if (z.getId() == id) {
                    zona = z;
                    break;
                }
            }

            if (zona == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Zona no encontrada");
                alert.show();
                return;
            }

            // Abrir formulario con datos de la zona
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/formulario.fxml"));
            Parent root = loader.load();
            FormularioControler c = loader.getController();

            c.setTitulo("Actualizar Zona: " + zona.getNombre());
            c.agregarTexto("nombre", "Nombre (actual: " + zona.getNombre() + ")");
            c.agregarTexto("capacidad", "Capacidad (actual: " + zona.getCapacidad() + ")");
            c.agregarTexto("precio", "Precio base (actual: " + zona.getPrecioBase() + ")");

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Map<String, String> datos = c.getDatos();

            // Actualizar zona
            String nuevoNombre = datos.get("nombre");
            String nuevaCapacidad = datos.get("capacidad");
            String nuevoPrecio = datos.get("precio");

            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                zona.setNombre(nuevoNombre);
            }
            if (nuevaCapacidad != null && !nuevaCapacidad.trim().isEmpty()) {
                try {
                    int capacidad = Integer.parseInt(nuevaCapacidad);
                    if (capacidad > 0) {
                        zona.setCapacidad(capacidad);
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Capacidad debe ser un número válido");
                    alert.show();
                }
            }
            if (nuevoPrecio != null && !nuevoPrecio.trim().isEmpty()) {
                try {
                    double precio = Double.parseDouble(nuevoPrecio);
                    if (precio > 0) {
                        zona.setPrecioBase(precio);
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Precio debe ser un número válido");
                    alert.show();
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Zona actualizada correctamente");
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void abrirBuscarZona() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Buscar Zona");
            dialog.setHeaderText("Buscar zona por ID");
            dialog.setContentText("ID de la zona:");

            var resultado = dialog.showAndWait();
            if (resultado.isEmpty()) return;

            int id = Integer.parseInt(resultado.get());
            Zona zona = null;
            for (Zona z : RepositorioAdmin.getInstance().getZonas()) {
                if (z.getId() == id) {
                    zona = z;
                    break;
                }
            }

            if (zona == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Zona no encontrada");
                alert.show();
                return;
            }

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Zona encontrada");
            info.setHeaderText(zona.getNombre());
            info.setContentText("Sector: " + zona.getSector() + "\nCapacidad: " + zona.getCapacidad() +
                    "\nPrecio base: $" + zona.getPrecioBase() +
                    "\nRecinto: " + obtenerNombreRecinto(zona));
            info.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String obtenerNombreRecinto(Zona zona) {
        for (Recinto r : RepositorioAdmin.getInstance().getRecintos()) {
            if (r.getConjuntoZonas().contains(zona)) {
                return r.getNombre();
            }
        }
        return "No asignado";
    }

    // ---- RECINTOS ----
    @FXML
    private void abrirActualizarRecinto() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Actualizar Recinto");
            dialog.setHeaderText("Actualizar datos de recinto");
            dialog.setContentText("ID del recinto a actualizar:");

            var resultado = dialog.showAndWait();
            if (resultado.isEmpty()) return;

            int id = Integer.parseInt(resultado.get());
            Recinto recinto = null;
            for (Recinto r : RepositorioAdmin.getInstance().getRecintos()) {
                if (r.getId() == id) {
                    recinto = r;
                    break;
                }
            }

            if (recinto == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Recinto no encontrado");
                alert.show();
                return;
            }

            // Abrir formulario con datos del recinto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/formulario.fxml"));
            Parent root = loader.load();
            FormularioControler c = loader.getController();

            c.setTitulo("Actualizar Recinto: " + recinto.getNombre());
            c.agregarTexto("nombre", "Nombre (actual: " + recinto.getNombre() + ")");
            c.agregarTexto("direccion", "Dirección (actual: " + recinto.getDireccion() + ")");
            c.agregarTexto("ciudad", "Ciudad (actual: " + recinto.getCiudad() + ")");

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Map<String, String> datos = c.getDatos();

            // Actualizar recinto
            String nuevoNombre = datos.get("nombre");
            String nuevaDireccion = datos.get("direccion");
            String nuevaCiudad = datos.get("ciudad");

            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                recinto.setNombre(nuevoNombre);
            }
            if (nuevaDireccion != null && !nuevaDireccion.trim().isEmpty()) {
                recinto.setDireccion(nuevaDireccion);
            }
            if (nuevaCiudad != null && !nuevaCiudad.trim().isEmpty()) {
                recinto.setCiudad(nuevaCiudad);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Recinto actualizado correctamente");
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirBorrarRecinto() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Eliminar Recinto");
            dialog.setHeaderText("Eliminar recinto por ID");
            dialog.setContentText("ID del recinto:");

            var resultado = dialog.showAndWait();
            if (resultado.isEmpty()) return;

            int id = Integer.parseInt(resultado.get());
            Recinto recinto = null;
            for (Recinto r : RepositorioAdmin.getInstance().getRecintos()) {
                if (r.getId() == id) {
                    recinto = r;
                    break;
                }
            }

            if (recinto == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Recinto no encontrado");
                alert.show();
                return;
            }

            Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
            confirmar.setTitle("Confirmar eliminación");
            confirmar.setHeaderText("Eliminar recinto");
            confirmar.setContentText("¿Desea eliminar " + recinto.getNombre() + "?");
            var respuesta = confirmar.showAndWait();
            if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
                RepositorioAdmin.getInstance().getRecintos().remove(recinto);
                Alert ok = new Alert(Alert.AlertType.INFORMATION);
                ok.setHeaderText(null);
                ok.setContentText("Recinto eliminado correctamente");
                ok.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void listarZonas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/tabla-zonas.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Lista de Zonas");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void listarRecintos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/tabla-recintos.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Lista de Recintos");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirBuscarRecinto() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Buscar Recinto");
            dialog.setHeaderText("Buscar recinto por ID");
            dialog.setContentText("ID del recinto:");

            var resultado = dialog.showAndWait();
            if (resultado.isEmpty()) return;

            int id = Integer.parseInt(resultado.get());
            Recinto recinto = null;
            for (Recinto r : RepositorioAdmin.getInstance().getRecintos()) {
                if (r.getId() == id) {
                    recinto = r;
                    break;
                }
            }

            if (recinto == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Recinto no encontrado");
                alert.show();
                return;
            }

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Recinto encontrado");
            info.setHeaderText(recinto.getNombre());
            info.setContentText("Dirección: " + recinto.getDireccion() + "\nCiudad: " + recinto.getCiudad() +
                    "\nZonas: " + recinto.getConjuntoZonas().size());
            info.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}