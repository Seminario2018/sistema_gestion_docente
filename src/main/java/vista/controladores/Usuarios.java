package vista.controladores;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import controlador.ControlPersona;
import controlador.ControlUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import modelo.auxiliares.TipoContacto;
import modelo.auxiliares.TipoDocumento;
import modelo.persona.IContacto;
import modelo.persona.IDomicilio;
import modelo.persona.ITitulo;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
import modelo.usuario.IUsuario;
import modelo.usuario.Modulo;
import vista.GestorPantalla;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Usuarios extends ControladorVista implements Initializable {

    private IUsuario usuarioSeleccion = null;
    private static final String TITULO = "Usuarios";

    // Controladores:
    private ControlPersona controlPersona = new ControlPersona();
    private ControlUsuario controlUsuario = new ControlUsuario(this);

    // Tipos Respuesta:
    private static final String TIPO_USUARIO = "Usuario";

    // Claves de pestañas:
    public static final String KEY_TAB = "pestaña";
    public static final int TAB_DATOS = 0;
    public static final int TAB_CONTACTOS = 1;
    public static final int TAB_DOMICILIOS = 2;
    public static final int TAB_TITULOS = 3;
    public static final int TAB_USUARIO = 4;
    public static final int TAB_ROLES = 5;

// -------------------------------- General --------------------------------- //

    /** Muestra los datos del usuario seleccionado en todos los controles */
    private void generalMostrarUsuario() {
        generalVaciarControles();
        if (usuarioSeleccion != null) {

            // Área General:
            if (usuarioSeleccion.getPersona() != null) {
                txtDocumento.setText(String.valueOf(
                    usuarioSeleccion.getPersona().getNroDocumento()));
            }

            txtUsuario.setText(usuarioSeleccion.getUser());

            datosMostrarPersona();          // Datos
            contactosActualizarTabla();     // Contactos
            domiciliosActualizarTabla();    // Domicilios
            titulosActualizarTabla();       // Títulos
            usuarioMostrarUsuario();        // Usuario
            actualizarTablasRoles();        // Roles
        }
    }

    /** Vacía los controles generales y los de todas las pestañas */
    private void generalVaciarControles() {
        txtDocumento.clear();
        txtUsuario.clear();

        vaciarTablas();
        datosVaciarControles();         // Datos
        contactosVaciarControles();     // Contactos
        domiciliosVaciarControles();    // Domicilios
        titulosVaciarControles();       // Títulos
        usuarioVaciarControles();       // Usuario

        this.gestorPantalla.mensajeEstado("");
    }

	@FXML private Button btnBuscar;
	@FXML public void buscarUsuario(ActionEvent event) {
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, false);
        args.put(Busqueda.KEY_TIPO, Usuarios.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_USUARIO);
        args.put(GestorPantalla.KEY_PADRE, Usuarios.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Usuarios.TITULO, args);
	}

	@FXML private Button btnNuevo;
	@FXML public void nuevoUsuario(ActionEvent event) {
	    usuarioSeleccion = controlUsuario.getIUsuario();
	    usuarioSeleccion.setPersona(controlPersona.getIPersona());
        generalVaciarControles();

        actualizarTablasRoles();

        modoNuevo();
	}

	@FXML private Button btnGuardar;
	@FXML public void guardarUsuario(ActionEvent event) {
	    if (usuarioSeleccion != null) {

	        int nroDocumento;

	        try {
                nroDocumento = Integer.parseInt(txtDatosDocumento.getText());
            } catch (NumberFormatException e) {
                alertaError(TITULO,
                    "Guardar Docente",
                    "El número de documento tiene que ser numérico");
                return;
            }

	        // Pestaña Datos:
	        if (usuarioSeleccion.getPersona() == null) {
                usuarioSeleccion.setPersona(controlPersona.getIPersona());
            }

	        usuarioSeleccion.getPersona().setNroDocumento(
                nroDocumento);
            usuarioSeleccion.getPersona().setApellido(
                txtDatosApellido.getText());
            usuarioSeleccion.getPersona().setNombre(
                txtDatosNombre.getText());
            usuarioSeleccion.getPersona().setTipoDocumento(
                cmbDatosTipoDocumento.getSelectionModel().getSelectedItem());
            usuarioSeleccion.getPersona().setFechaNacimiento(
                dtpDatosFechaNacimiento.getValue());

            // Pestaña "Usuario"
            usuarioSeleccion.setUser(txtUsuarioUsuario.getText());
            usuarioSeleccion.setDescripcion(txtUsuarioDescripcion.getText());

            String contrasena = txtUsuarioContrasena.getText();
            String confirmar = txtUsuarioConfirmar.getText();

            if (contrasena.equals(confirmar)) {
                if (!contrasena.equals("")) {
                    usuarioSeleccion.setPass(contrasena);
                }

                List<IRol> rolesNuevos = new ArrayList<IRol>();
                for (FilaRol fila : filasRolesUsuario) {
                    rolesNuevos.add(fila.getInstanciaRol());
                }
                usuarioSeleccion.setGrupos(rolesNuevos);

                EstadoOperacion resultado = controlUsuario.guardarUsuario(usuarioSeleccion);
                exitoGuardado(controlPersona.guardarPersona(usuarioSeleccion.getPersona()), TITULO, "Guardar Persona");
                exitoGuardado(resultado, TITULO, "Guardar Usuario");

                generalMostrarUsuario();
                modoModificar();

            } else {
                alertaError(TITULO,
                    "Guardar Usuario",
                    "Las contraseña no coincide con su confirmación.");
            }
	    }
	}

	@FXML private Button btnDescartar;
	@FXML public void descartarUsuario(ActionEvent event) {
	    generalMostrarUsuario();
	}

	@FXML private Button btnEliminar;
	@FXML public void eliminarUsuario(ActionEvent event) {
	    if (usuarioSeleccion != null) {
	        if (exitoEliminar(controlUsuario.eliminarUsuario(usuarioSeleccion), TITULO, "Eliminar Usuario")) {
	            usuarioSeleccion = null;
	            modoVer();
	            generalVaciarControles();
	        }
	    }
	}

	@FXML private TextField txtDocumento;
    @FXML private TextField txtUsuario;

// ----------------------------- Pestaña Datos ------------------------------ //

    /** Inicializa los controles de la pestaña "Datos" */
    @FXML private void inicializarDatos() {

    }

    /** Muestra los datos de la persona seleccionada: */
    private void datosMostrarPersona() {
        if (usuarioSeleccion != null && usuarioSeleccion.getPersona() != null) {
            txtDatosApellido.setText(
                usuarioSeleccion.getPersona().getApellido());
            txtDatosNombre.setText(
                usuarioSeleccion.getPersona().getNombre());
            cmbDatosTipoDocumento.getSelectionModel().select(
                usuarioSeleccion.getPersona().getTipoDocumento());
            txtDatosDocumento.setText(String.valueOf(
                usuarioSeleccion.getPersona().getNroDocumento()));
            dtpDatosFechaNacimiento.setValue(
                usuarioSeleccion.getPersona().getFechaNacimiento());
        }
    }

    /** Vacía los controles de datos */
    private void datosVaciarControles() {
        txtDatosApellido.clear();
        txtDatosNombre.clear();
        cmbDatosTipoDocumento.getSelectionModel().clearSelection();
        txtDatosDocumento.clear();
        dtpDatosFechaNacimiento.setValue(null);
    }

    @FXML private TextField txtDatosApellido;
    @FXML private TextField txtDatosNombre;
    @FXML private ComboBox<TipoDocumento> cmbDatosTipoDocumento;
    @FXML private TextField txtDatosDocumento;
    @FXML private DatePicker dtpDatosFechaNacimiento;

 // --------------------------- Pestaña Contactos ---------------------------- //

    private IContacto contactoSeleccion = null;
    protected ObservableList<FilaContacto> filasContactos = FXCollections.observableArrayList();

    public class FilaContacto {
        private IContacto contacto;
        public FilaContacto(IContacto contacto) {
            this.contacto = contacto;
        }
        public String getTipo() {
            return this.contacto.getTipo().getDescripcion();
        }
        public String getDato() {
            return this.contacto.getDato();
        }
        public IContacto getInstanciaContacto() {
            return this.contacto;
        }
    }

    /** Refresca la tabla de contactos */
    private void contactosActualizarTabla() {
        filasContactos.clear();
        if (usuarioSeleccion.getPersona() != null) {
            for (IContacto contacto : usuarioSeleccion.getPersona().getContactos()) {
                filasContactos.add(
                    new FilaContacto(contacto));
            }
        }
    }

    /** Muestra los datos del contacto seleccionado: */
    private void contactosMostrarContacto() {
        if (contactoSeleccion != null) {
            cmbContactosTipo.getSelectionModel().select(contactoSeleccion.getTipo());;
            txtContactosDato.setText(contactoSeleccion.getDato());
        }
    }

    /** Vacía los controles de datos del contacto */
    private void contactosVaciarControles() {
        cmbContactosTipo.getSelectionModel().clearSelection();
        txtContactosDato.clear();
    }

    private void contactosModoModificar() {
        if (this.permiso.getModificar()) {
            btnContactosGuardar.setVisible(true);
            btnContactosDescartar.setVisible(true);
            btnContactosEliminar.setVisible(true);

            cmbContactosTipo.setDisable(false);
            txtContactosDato.setEditable(true);
        }
    }

    private void contactosModoNuevo() {
        if (this.permiso.getModificar()) {
            contactosModoModificar();
            btnContactosEliminar.setVisible(false);
        }
    }

    private void contactosModoVer() {
        btnContactosDescartar.setVisible(false);
        btnContactosEliminar.setVisible(false);
        btnContactosGuardar.setVisible(false);
        cmbContactosTipo.setDisable(true);
        txtContactosDato.setEditable(false);
    }

    @FXML private void inicializarContactos() {
        inicializarTabla("Contactos");
        tblContactos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                contactoSeleccion = newSelection.getInstanciaContacto();
                contactosMostrarContacto();
                contactosModoModificar();
            }
        });

        contactosActualizarTabla();

        cmbContactosTipo.setItems(
            FXCollections.observableArrayList(
                TipoContacto.getLista()));
    }

    @FXML private Button btnContactosNuevo;
    @FXML public void nuevoContacto(ActionEvent event) {
        if (usuarioSeleccion.getPersona() != null) {
            contactoSeleccion = controlPersona.getIContacto();
            contactosVaciarControles();
            contactosModoNuevo();
        }
    }

    @FXML private Button btnContactosGuardar;
    @FXML public void guardarContacto(ActionEvent event) {
        if (usuarioSeleccion.getPersona() != null && contactoSeleccion != null) {
            contactoSeleccion.setTipo(cmbContactosTipo.getSelectionModel().getSelectedItem());
            contactoSeleccion.setDato(txtContactosDato.getText());

            EstadoOperacion resultado = controlPersona.guardarContacto(usuarioSeleccion.getPersona(), contactoSeleccion);
            exitoGuardado(resultado, TITULO, "Guardar Contacto");
            contactosModoModificar();
            contactosActualizarTabla();
        }
    }

    @FXML private Button btnContactosDescartar;
    @FXML public void descartarContacto(ActionEvent event) {
        contactosMostrarContacto();
    }

    @FXML private Button btnContactosEliminar;
    @FXML public void eliminarContacto(ActionEvent event) {
        if (usuarioSeleccion.getPersona() != null && contactoSeleccion != null) {
            EstadoOperacion resultado = controlPersona.quitarContacto(usuarioSeleccion.getPersona(), contactoSeleccion);
            if (exitoEliminar(resultado, TITULO, "Eliminar Contacto")) {
                contactoSeleccion = null;
                contactosModoVer();
                contactosVaciarControles();
            }
            contactosActualizarTabla();
        }
    }

    @FXML protected TableView<FilaContacto> tblContactos;
    @FXML protected TableColumn<FilaContacto, String> colContactosTipo;
    @FXML protected TableColumn<FilaContacto, String> colContactosDato;

    @FXML private ComboBox<TipoContacto> cmbContactosTipo;
    @FXML private TextField txtContactosDato;

// -------------------------- Pestaña Domicilios ---------------------------- //

    private IDomicilio domicilioSeleccion = null;
    protected ObservableList<FilaDomicilio> filasDomicilios = FXCollections.observableArrayList();

    public class FilaDomicilio {
        private IDomicilio domicilio;
        public FilaDomicilio(IDomicilio domicilio) {
            this.domicilio = domicilio;
        }
        public String getProvincia() {
            return this.domicilio.getProvincia();
        }
        public String getCiudad() {
            return this.domicilio.getCiudad();
        }
        public String getCP() {
            return this.domicilio.getCodigoPostal();
        }
        public String getDireccion() {
            return this.domicilio.getDireccion();
        }
        public IDomicilio getInstanciaDomicilio() {
            return this.domicilio;
        }
    }

    /** Refresca la tabla de domicilios */
    private void domiciliosActualizarTabla() {
        filasDomicilios.clear();
        if (usuarioSeleccion.getPersona() != null) {
            for (IDomicilio domicilio: usuarioSeleccion.getPersona().getDomicilios()) {
                filasDomicilios.add(
                    new FilaDomicilio(domicilio));
            }
        }
    }

    /** Muestra los datos del domicilio seleccionado: */
    private void domiciliosMostrarDomicilio() {
        if (domicilioSeleccion != null) {
            cmbDomiciliosProvincia.getSelectionModel().select(domicilioSeleccion.getProvincia());
            txtDomiciliosCiudad.setText(domicilioSeleccion.getCiudad());
            txtDomiciliosCP.setText(domicilioSeleccion.getCodigoPostal());
            txtDomiciliosDireccion.setText(domicilioSeleccion.getDireccion());
        }
    }

    /** Vacía los controles de datos del domicilio */
    private void domiciliosVaciarControles() {
        cmbDomiciliosProvincia.getSelectionModel().clearSelection();
        txtDomiciliosCiudad.clear();
        txtDomiciliosCP.clear();
        txtDomiciliosDireccion.clear();
    }

    private void domiciliosModoModificar() {
        if (this.permiso.getModificar()) {
            btnDomiciliosGuardar.setVisible(true);
            btnDomicilioDescartar.setVisible(true);
            btnDomiciliosEliminar.setVisible(true);
            cmbDomiciliosProvincia.setDisable(false);
            txtDomiciliosCiudad.setEditable(true);
            txtDomiciliosCP.setEditable(true);
            txtDomiciliosDireccion.setEditable(true);
        }
    }

    private void domiciliosModoNuevo() {
        if (this.permiso.getModificar()) {
            domiciliosModoModificar();
            btnDomiciliosEliminar.setVisible(false);
        }
    }

    private void domiciliosModoVer() {
        btnDomiciliosGuardar.setVisible(false);
        btnDomicilioDescartar.setVisible(false);
        btnDomiciliosEliminar.setVisible(false);

        cmbDomiciliosProvincia.setDisable(true);
        txtDomiciliosCiudad.setEditable(false);
        txtDomiciliosCP.setEditable(false);
        txtDomiciliosDireccion.setEditable(false);
    }

    @FXML private void inicializarDomicilios() {
        inicializarTabla("Domicilios");
        tblDomicilios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                domicilioSeleccion = newSelection.getInstanciaDomicilio();
                domiciliosMostrarDomicilio();
                domiciliosModoModificar();
            }
        });

        domiciliosActualizarTabla();

        // Llenar combobox de provincias:
        List<String> provincias = new ArrayList<String>();
        provincias.add("Buenos Aires");
        provincias.add("Catamarca");
        provincias.add("Chaco");
        provincias.add("Chubut");
        provincias.add("Córdoba");
        provincias.add("Corrientes");
        provincias.add("Entre Ríos");
        provincias.add("Formosa");
        provincias.add("Jujuy");
        provincias.add("La Pampa");
        provincias.add("La Rioja");
        provincias.add("Mendoza");
        provincias.add("Misiones");
        provincias.add("Neuquén");
        provincias.add("Rio Negro");
        provincias.add("Salta");
        provincias.add("San Juan");
        provincias.add("San Luis");
        provincias.add("Santa Cruz");
        provincias.add("Santa Fe");
        provincias.add("Santiago del Estero");
        provincias.add("Tierra del Fuego");
        provincias.add("Tucumán");

        cmbDomiciliosProvincia.setItems(
            FXCollections.observableArrayList(
                provincias));
    }

    @FXML private Button btnDomiciliosNuevo;
    @FXML public void nuevoDomicilio(ActionEvent event) {
        domicilioSeleccion = controlPersona.getIDomicilio();
        domiciliosVaciarControles();
        domiciliosModoNuevo();
    }

    @FXML private Button btnDomiciliosGuardar;
    @FXML public void guardarDomicilio(ActionEvent event) {
        if (usuarioSeleccion.getPersona() != null && domicilioSeleccion != null) {
            domicilioSeleccion.setProvincia(cmbDomiciliosProvincia.getSelectionModel().getSelectedItem());
            domicilioSeleccion.setCiudad(txtDomiciliosCiudad.getText());
            domicilioSeleccion.setCodigoPostal(txtDomiciliosCP.getText());
            domicilioSeleccion.setDireccion(txtDomiciliosDireccion.getText());

            exitoGuardado(controlPersona.guardarDomicilio(usuarioSeleccion.getPersona(), domicilioSeleccion), TITULO, "Guardar Domicilio");
            domiciliosModoModificar();
            domiciliosActualizarTabla();
        }
    }

    @FXML private Button btnDomicilioDescartar;
    @FXML public void descartarDomicilio(ActionEvent event) {
        domiciliosMostrarDomicilio();
    }

    @FXML private Button btnDomiciliosEliminar;
    @FXML public void eliminarDomicilio(ActionEvent event) {
        if (usuarioSeleccion.getPersona() != null && domicilioSeleccion != null) {
            if (exitoEliminar(controlPersona.quitarDomicilio(usuarioSeleccion.getPersona(), domicilioSeleccion), TITULO, "Eliminar Domicilio")) {
                domicilioSeleccion = null;
                domiciliosModoVer();
                domiciliosVaciarControles();
            }
            domiciliosActualizarTabla();
        }
    }

    @FXML protected TableView<FilaDomicilio> tblDomicilios;
    @FXML protected TableColumn<FilaDomicilio, String> colDomiciliosProvincia;
    @FXML protected TableColumn<FilaDomicilio, String> colDomiciliosCiudad;
    @FXML protected TableColumn<FilaDomicilio, String> colDomiciliosCP;
    @FXML protected TableColumn<FilaDomicilio, String> colDomiciliosDireccion;

    @FXML private ComboBox<String> cmbDomiciliosProvincia;
    @FXML private TextField txtDomiciliosCiudad;
    @FXML private TextField txtDomiciliosCP;
    @FXML private TextField txtDomiciliosDireccion;

// ---------------------------- Pestaña Títulos ----------------------------- //

    private ITitulo tituloSeleccion = null;
    protected ObservableList<FilaTitulo> filasTitulos = FXCollections.observableArrayList();

    public class FilaTitulo {
        private ITitulo titulo;
        public FilaTitulo(ITitulo titulo) {
            this.titulo = titulo;
        }
        public String getMayor() {
            return this.titulo.isEsMayor() ? "Mayor" : "";
        }
        public String getTitulo() {
            return this.titulo.getNombre();
        }
        public ITitulo getInstanciaTitulo() {
            return this.titulo;
        }
    }

    /** Refresca la tabla de títulos */
    private void titulosActualizarTabla() {
        filasTitulos.clear();
        if (usuarioSeleccion.getPersona() != null) {
            for (ITitulo titulo : usuarioSeleccion.getPersona().getTitulos()) {
                filasTitulos.add(new FilaTitulo(titulo));
            }
        }
    }

    /** Muestra los datos del titulo seleccionado: */
    private void titulosMostrarTitulo() {
        if (tituloSeleccion != null) {
            txtTitulosTitulo.setText(tituloSeleccion.getNombre());
        }
    }

    /** Vacía los campos de datos del título */
    private void titulosVaciarControles() {
        txtTitulosTitulo.clear();
    }

    @FXML private void inicializarTitulos() {
        inicializarTabla("Titulos");
        tblTitulos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tituloSeleccion = newSelection.getInstanciaTitulo();
                titulosMostrarTitulo();
            }
        });
        titulosActualizarTabla();
    }

    @FXML private TextField txtTitulosTitulo;

    @FXML private Button btnTitulosAgregar;
    @FXML public void agregarTitulo(ActionEvent event) {
        if (usuarioSeleccion.getPersona() != null) {
            tituloSeleccion = controlPersona.getITitulo();
            tituloSeleccion.setNombre(txtTitulosTitulo.getText());

            exitoGuardado(controlPersona.guardarTitulo(usuarioSeleccion.getPersona(), tituloSeleccion), TITULO, "Guardar Título");
            titulosActualizarTabla();
        }
    }

    @FXML private Button btnTitulosQuitar;
    @FXML public void quitarTitulo(ActionEvent event) {
        if (usuarioSeleccion.getPersona() != null && tituloSeleccion != null) {
            if (exitoEliminar(controlPersona.quitarTitulo(usuarioSeleccion.getPersona(), tituloSeleccion), TITULO, "Eliminar Título")) {
                tituloSeleccion = null;
                titulosVaciarControles();
            }
            titulosActualizarTabla();
        }
    }

    @FXML private Button btnTitulosMayor;
    @FXML public void mayorTitulo(ActionEvent event) {
        // DONE Marcar como título mayor al título seleccionado, sobreescribiendo el anterior
        if (usuarioSeleccion.getPersona() != null && tituloSeleccion != null) {
            if (!tituloSeleccion.isEsMayor()) {
                /* Si el título seleccionado no es el mayor entonces desmarco
                   todos los títulos. */
                for (ITitulo titulo : usuarioSeleccion.getPersona().getTitulos()) {
                    titulo.setEsMayor(false);
                }

                // Y marco el título actual como el mayor:
                tituloSeleccion.setEsMayor(true);

                // Y persisto los cambios en todos los títulos:
                boolean exito = true;
                for (ITitulo titulo : usuarioSeleccion.getPersona().getTitulos()) {
                    EstadoOperacion resultado = controlPersona.guardarTitulo(usuarioSeleccion.getPersona(), titulo);
                    if (resultado.getEstado() != CodigoEstado.UPDATE_OK) {
                        exito = false;
                    }
                }

                if (exito) {
                    gestorPantalla.mensajeEstado("El título fue seleccionado como el mayor.");
                } else {
                    alertaError(TITULO, "Seleccionar mayor título", "El título no pudo modificarse.");
                }

                titulosActualizarTabla();
            }
        }
    }

    @FXML protected TableView<FilaTitulo> tblTitulos;
    @FXML protected TableColumn<FilaTitulo, String> colTitulosTitulo;
    @FXML protected TableColumn<FilaTitulo, String> colTitulosMayor;

// --------------------------------- Usuario -------------------------------- //
    @FXML private TextField txtUsuarioUsuario;
    @FXML private PasswordField txtUsuarioContrasena;
    @FXML private PasswordField txtUsuarioConfirmar;
    @FXML private TextField txtUsuarioDescripcion;

    /** Muestra los datos del usuario seleccionado: */
    private void usuarioMostrarUsuario() {
        if (usuarioSeleccion != null) {
            txtUsuarioUsuario.setText(
                usuarioSeleccion.getUser());
            txtUsuarioDescripcion.setText(
                usuarioSeleccion.getDescripcion());
        }
    }

    /** Vacía los controles de datos */
    private void usuarioVaciarControles() {
        txtUsuarioUsuario.clear();
        txtUsuarioContrasena.clear();
        txtUsuarioConfirmar.clear();
        txtUsuarioDescripcion.clear();
    }

    @FXML private void inicializarUsuario() {

    }

// --------------------------------- Roles ---------------------------------- //

	private FilaRol filaRolDisponibleSeleccion = null;
	private FilaRol filaRolUsuarioSeleccion = null;

	protected ObservableList<FilaRol> filasRolesDisponibles = FXCollections.observableArrayList();
	protected ObservableList<FilaRol> filasRolesUsuario = FXCollections.observableArrayList();

	private void actualizarTablasRoles() {
	    if (usuarioSeleccion != null) {
	        filasRolesDisponibles.clear();
	        filasRolesUsuario.clear();

	        List<IRol> rolesDisponibles = controlUsuario.listarGrupo(null);
	        List<IRol> rolesUsuario = usuarioSeleccion.getRoles();

	        rolesDisponibles.removeAll(rolesUsuario);

	        for (IRol rol : rolesDisponibles) {
	            if (rol.getId() != 0) {
	                filasRolesDisponibles.add(new FilaRol(rol));
	            }
	        }

	        for (IRol rol : rolesUsuario) {
	            filasRolesUsuario.add(new FilaRol(rol));
	        }
	    }
	}

	public class FilaRol {
	    private IRol rol;

	    public FilaRol(IRol rol) {
	        this.rol = rol;
	    }

	    public String getRol() {
	        return this.rol.getNombre();
	    }

	    public IRol getInstanciaRol() {
	        return this.rol;
	    }
	}

	@FXML private Button btnRolesAgregar;
	@FXML public void agregarRol(ActionEvent event) {
	    if (usuarioSeleccion != null) {
            if (filaRolDisponibleSeleccion != null) {
            	filasRolesUsuario.add(filaRolDisponibleSeleccion);
                filasRolesDisponibles.remove(filaRolDisponibleSeleccion);

	        } else { alertaError(TITULO, "Agregar Rol", "No hay un rol seleccionado"); }
	    }
	}

	@FXML private Button btnRolesQuitar;
	@FXML public void quitarRol(ActionEvent event) {
	    if (usuarioSeleccion != null) {
	        if (filaRolUsuarioSeleccion != null) {
	            filasRolesDisponibles.add(filaRolUsuarioSeleccion);
	            filasRolesUsuario.remove(filaRolUsuarioSeleccion);

	        } else { alertaError(TITULO, "Quitar Rol", "No hay un rol seleccionado"); }
	    }
	}

	@FXML protected TableView<FilaRol> tblRolesDisponibles;
	@FXML protected TableColumn<FilaRol, String> colRolesDisponiblesRol; // Descripción del rol

	@FXML protected TableView<FilaRol> tblRolesUsuario;
	@FXML protected TableColumn<FilaRol, String> colRolesUsuarioRol; // Descripción del rol

	// ----------------------------- Resultado de Búsqueda ---------------------- //

	/**
     * Recibir parámetros de la pantalla de búsqueda
     */
    @Override
    public void recibirParametros(Map<String, Object> args) {
        Object oSeleccion = args.get(Busqueda.KEY_SELECCION);
        if (oSeleccion != null) {
            String seleccion = (String) oSeleccion;
            Object valor = args.get(Busqueda.KEY_VALOR);
            String tipo_respuesta = (String) args.get(Busqueda.KEY_TIPO_RESPUESTA);
            try {
                String metodo = "set" + seleccion + "Seleccion";
                Method m = this.getClass().getDeclaredMethod(metodo, Object.class, String.class);
                m.invoke(this, valor, tipo_respuesta);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unused")
    private void setUsuarioSeleccion(Object usuario, String tipo) {
        if (usuario instanceof IUsuario) {
            usuarioSeleccion = (IUsuario) usuario;
            modoModificar();
            generalMostrarUsuario();
        }
    }

	// ----------------------------- Ocultamiento de controles ------------------ //
	@Override
    public void inicializar() {
        /* Ocultar controles según roles del usuario: */
        boolean crear = false;
        boolean modificar = false;
        boolean eliminar = false;
        boolean listar = false;

        for (IRol rol : this.usuario.getRoles()) {
            for (IPermiso permiso : rol.getPermisos()) {
                if (permiso.getModulo() == Modulo.USUARIOS) {
                    this.permiso = permiso;
                    crear |= permiso.getCrear();
                    modificar |= permiso.getModificar();
                    eliminar |= permiso.getEliminar();
                    listar |= permiso.getListar();
                }
            }
        }

        if (!crear) {
            btnNuevo.setVisible(false);
        }

        if (!modificar) {
            // General:
            btnDescartar.setVisible(false);
            btnGuardar.setVisible(false);

            // Pestaña Datos:
            txtDatosApellido.setEditable(false);
            txtDatosNombre.setEditable(false);
            cmbDatosTipoDocumento.setDisable(true);
            txtDatosDocumento.setEditable(false);
            dtpDatosFechaNacimiento.setEditable(false);

            // Pestaña Contactos:
            btnContactosNuevo.setVisible(false);
            btnContactosGuardar.setVisible(false);
            btnContactosDescartar.setVisible(false);
            btnContactosEliminar.setVisible(false);

            cmbContactosTipo.setDisable(true);
            txtContactosDato.setEditable(false);

            // Pestaña Domicilios:
            btnDomiciliosNuevo.setVisible(false);
            btnDomiciliosGuardar.setVisible(false);
            btnDomicilioDescartar.setVisible(false);
            btnDomiciliosEliminar.setVisible(false);

            cmbDomiciliosProvincia.setDisable(true);
            txtDomiciliosCiudad.setEditable(false);
            txtDomiciliosCP.setEditable(false);
            txtDomiciliosDireccion.setEditable(false);

            // Pestaña Títulos:
            txtTitulosTitulo.setEditable(false);
            btnTitulosAgregar.setVisible(false);
            btnTitulosQuitar.setVisible(false);
            btnTitulosMayor.setVisible(false);

            // Pestaña Usuario:
            txtUsuarioUsuario.setEditable(false);
            txtUsuarioContrasena.setEditable(false);
            txtUsuarioConfirmar.setEditable(false);
            txtUsuarioDescripcion.setEditable(false);

            // Pestaña Roles:
            btnRolesAgregar.setVisible(false);
            btnRolesQuitar.setVisible(false);
        }

        if (!eliminar) {
            btnEliminar.setVisible(false);
        }

        if (!listar) {
        }

        modoVer();

        // =================== Inicializar controles: ====================
        // Pestaña "Datos":
        cmbDatosTipoDocumento.setItems(
            FXCollections.observableArrayList(
                TipoDocumento.getLista()));

        // Pestaña "Contactos":
        inicializarTabla("Contactos");
        tblContactos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                contactoSeleccion = newSelection.getInstanciaContacto();
                contactosMostrarContacto();
                contactosModoModificar();
            }
        });
        cmbContactosTipo.setItems(
            FXCollections.observableArrayList(
                TipoContacto.getLista()));

        // Pestaña "Domicilios":
        inicializarTabla("Domicilios");
        tblDomicilios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                domicilioSeleccion = newSelection.getInstanciaDomicilio();
                domiciliosMostrarDomicilio();
                domiciliosModoModificar();
            }
        });

        List<String> provincias = new ArrayList<String>();
        provincias.add("Buenos Aires");
        provincias.add("Catamarca");
        provincias.add("Chaco");
        provincias.add("Chubut");
        provincias.add("Córdoba");
        provincias.add("Corrientes");
        provincias.add("Entre Ríos");
        provincias.add("Formosa");
        provincias.add("Jujuy");
        provincias.add("La Pampa");
        provincias.add("La Rioja");
        provincias.add("Mendoza");
        provincias.add("Misiones");
        provincias.add("Neuquén");
        provincias.add("Rio Negro");
        provincias.add("Salta");
        provincias.add("San Juan");
        provincias.add("San Luis");
        provincias.add("Santa Cruz");
        provincias.add("Santa Fe");
        provincias.add("Santiago del Estero");
        provincias.add("Tierra del Fuego");
        provincias.add("Tucumán");

        cmbDomiciliosProvincia.setItems(
            FXCollections.observableArrayList(
                provincias));

        // Pestaña "Títulos":
        inicializarTabla("Titulos");
        tblTitulos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tituloSeleccion = newSelection.getInstanciaTitulo();
                titulosMostrarTitulo();
            }
        });

        // Pestaña "Roles":
        inicializarTabla("RolesDisponibles");
        tblRolesDisponibles.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, filaSelection) -> {
            if (filaSelection != null) {
                filaRolDisponibleSeleccion = filaSelection;
            }
        });

        inicializarTabla("RolesUsuario");
        tblRolesUsuario.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, filaSelection) -> {
            if (filaSelection != null) {
                filaRolUsuarioSeleccion = filaSelection;
            }
        });
    }

	@Override
    protected void modoModificar() {
        if (this.permiso.getModificar()) {
            // General:
            btnDescartar.setVisible(true);
            btnGuardar.setVisible(true);

            // Pestaña Datos:
            txtDatosApellido.setEditable(true);
            txtDatosNombre.setEditable(true);
            cmbDatosTipoDocumento.setDisable(false);
            txtDatosDocumento.setEditable(true);
            dtpDatosFechaNacimiento.setEditable(true);

            // Pestaña Contactos:
            contactosModoVer();
            btnContactosNuevo.setVisible(true);

            // Pestaña Domicilios:
            domiciliosModoVer();
            btnDomiciliosNuevo.setVisible(true);

            // Pestaña Títulos:
            txtTitulosTitulo.setEditable(true);
            btnTitulosAgregar.setVisible(true);
            btnTitulosQuitar.setVisible(true);
            btnTitulosMayor.setVisible(true);

            // Pestaña Usuario:
            txtUsuarioUsuario.setEditable(true);
            txtUsuarioContrasena.setEditable(true);
            txtUsuarioConfirmar.setEditable(true);
            txtUsuarioDescripcion.setEditable(true);

            // Pestaña Roles:
            btnRolesAgregar.setVisible(true);
            btnRolesQuitar.setVisible(true);
        }

        if (this.permiso.getEliminar()) {
            btnEliminar.setVisible(true);
        }

        this.window.setTitle(TITULO + " - Modificar Usuario");
        if (this.usuarioSeleccion != null) {
            this.gestorPantalla.mensajeEstado("Modificar al Usuario " + this.usuarioSeleccion.getUser());
        }
    }

    @Override
    protected void modoNuevo() {
        if (this.permiso.getCrear()) {
            // General:
            btnNuevo.setVisible(true);
            btnGuardar.setVisible(true);
            btnDescartar.setVisible(true);

            // Pestaña "Datos":
            txtDatosApellido.setEditable(true);
            txtDatosNombre.setEditable(true);
            cmbDatosTipoDocumento.setDisable(false);
            txtDatosDocumento.setEditable(true);
            dtpDatosFechaNacimiento.setEditable(true);

            // Pestaña Usuario:
            txtUsuarioUsuario.setEditable(true);
            txtUsuarioContrasena.setEditable(true);
            txtUsuarioConfirmar.setEditable(true);
            txtUsuarioDescripcion.setEditable(true);
        }

        this.window.setTitle(TITULO + " - Nuevo Usuario");
        this.gestorPantalla.mensajeEstado("Nuevo Usuario ");
    }

    @Override
    protected void modoVer() {
        // General:
        btnDescartar.setVisible(false);
        btnGuardar.setVisible(false);

        // Pestaña Datos:
        txtDatosApellido.setEditable(false);
        txtDatosNombre.setEditable(false);
        cmbDatosTipoDocumento.setDisable(false);
        txtDatosDocumento.setEditable(false);
        dtpDatosFechaNacimiento.setEditable(false);

        // Pestaña Contactos:
        contactosModoVer();
        btnContactosNuevo.setVisible(false);

        // Pestaña Domicilios:
        domiciliosModoVer();
        btnDomiciliosNuevo.setVisible(false);

        // Pestaña Títulos:
        txtTitulosTitulo.setEditable(false);
        btnTitulosAgregar.setVisible(false);
        btnTitulosQuitar.setVisible(false);
        btnTitulosMayor.setVisible(false);

        // Pestaña Usuario:
        txtUsuarioUsuario.setEditable(false);
        txtUsuarioContrasena.setEditable(false);
        txtUsuarioConfirmar.setEditable(false);
        txtUsuarioDescripcion.setEditable(false);

        // Pestaña Roles:
        btnRolesAgregar.setVisible(false);
        btnRolesQuitar.setVisible(false);

        this.window.setTitle(TITULO);
        this.gestorPantalla.mensajeEstado("");
    }
}


