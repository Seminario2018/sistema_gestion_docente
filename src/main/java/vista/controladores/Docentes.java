package vista.controladores;

import java.lang.reflect.Method;
import java.time.DateTimeException;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import controlador.ControlAuxiliar;
import controlador.ControlDocente;
import controlador.ControlInvestigacion;
import controlador.ControlPersona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoDocente;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import modelo.auxiliares.TipoCargo;
import modelo.auxiliares.TipoContacto;
import modelo.auxiliares.TipoDocumento;
import modelo.cargo.ICargo;
import modelo.division.IArea;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.docente.IIncentivo;
import modelo.persona.IContacto;
import modelo.persona.IDomicilio;
import modelo.persona.ITitulo;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
import modelo.usuario.Modulo;
import utilidades.Utilidades;
import vista.GestorPantalla;


public class Docentes extends ControladorVista implements Initializable {

    private IDocente docenteSeleccion;
    public static final String TITULO = "Docentes";

    // Controladores:
    private ControlPersona controlPersona = new ControlPersona();
    private ControlDocente controlDocente = new ControlDocente(this);
    private ControlInvestigacion controlInvestigacion = new ControlInvestigacion(this);

    // Recibir parámetros para cargar datos:
    public static final String REC_CARGO_DOCENTE = "recibircargo";
    public static final String REC_DOCENTE = "recibirdocente";

    // Tipos respuesta:
    private static final String TIPO_AREA = "area";
    private static final String TIPO_CARGO = "cargo";
    private static final String TIPO_DOCENTE = "docente";

    // Claves de pestañas:
    public static final String KEY_TAB = "pestaña";
    public static final int TAB_DATOS = 0;
    public static final int TAB_CONTACTOS = 1;
    public static final int TAB_DOMICILIOS = 2;
    public static final int TAB_TITULOS = 3;
    public static final int TAB_DOCENTE = 4;
    public static final int TAB_CARGOS = 5;
    public static final int TAB_INVESTIGACION = 6;
    public static final int TAB_INCENTIVOS = 7;
    public static final int TAB_OBSERVACIONES = 8;

// ----------------------------- Área General ------------------------------- //

    /** Muestra los datos del docente seleccionado en todos los controles */
    private void generalMostrarDocente() {
        generalVaciarControles();
        if (docenteSeleccion != null) {

            // Área General:
            if (docenteSeleccion.getPersona() != null) {
                txtDocumento.setText(String.valueOf(
                    docenteSeleccion.getPersona().getNroDocumento()));
                txtNombre.setText(
                    docenteSeleccion.getPersona().getNombreCompleto());
            }

            txtLegajo.setText(String.valueOf(
                docenteSeleccion.getLegajo()));

            datosMostrarPersona();                // Datos
            contactosActualizarTabla();           // Contactos
            domiciliosActualizarTabla();          // Domicilios
            titulosActualizarTabla();             // Títulos
            docenteMostrarDocente();              // Docente
            cargosActualizarTabla();              // Cargos
            investigacionActualizarTabla();       // Investigación
            incentivosActualizarTabla();          // Incentivos
            observacionesMostrarObservaciones();  // Observaciones
        }
    }

    /** Vacía los controles generales y los de todas las pestañas */
    private void generalVaciarControles() {
        txtDocumento.clear();
        txtLegajo.clear();
        txtNombre.clear();

        // Vaciar controles de las pestañas:
        vaciarTablas();
        datosVaciarControles();         // Datos
        contactosVaciarControles();     // Contactos
        domiciliosVaciarControles();    // Domicilios
        titulosVaciarControles();       // Títulos
        docenteVaciarControles();       // Docente
//        cargosVaciarControles();        // Cargos
        incentivosVaciarControles();    // Incentivos
        observacionesVaciarControles(); // Observaciones

        this.gestorPantalla.mensajeEstado("");
    }

    @FXML private Button btnBuscar;
    @FXML private void buscarDocente() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, false);
        args.put(Busqueda.KEY_TIPO, Docentes.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_DOCENTE);
        args.put(GestorPantalla.KEY_PADRE, Docentes.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Docentes.TITULO, args);
    }

    @FXML private Button btnNuevo;
    @FXML private void nuevoDocente() {
        docenteSeleccion = controlDocente.getIDocente();
        docenteSeleccion.setPersona(controlPersona.getIPersona());
        generalVaciarControles();
        modoNuevo();
    }

    @FXML private Button btnEliminar;
    @FXML private void eliminarDocente() {
        if (docenteSeleccion != null) {
            if (exitoEliminar(controlDocente.eliminarDocente(docenteSeleccion), TITULO, "Eliminar Docente")) {
                docenteSeleccion = null;
                modoVer();
                generalVaciarControles();
            }
        }
    }

    @FXML private Button btnGuardar;
    @FXML private void guardarDocente() {
        if (docenteSeleccion != null) {
            // Extracción de valores numéricos:
            int legajo, nroDocumento;

            try {
                legajo = Integer.parseInt(txtDocenteLegajo.getText());
            } catch (NumberFormatException e) {
                alertaError(TITULO,
                    "Guardar Docente",
                    "El número de legajo tiene que ser numérico");
                return;
            }

            try {
                nroDocumento = Integer.parseInt(txtDatosDocumento.getText());
            } catch (NumberFormatException e) {
                alertaError(TITULO,
                    "Guardar Docente",
                    "El número de documento tiene que ser numérico");
                return;
            }

            // Pestaña Datos:
            if (docenteSeleccion.getPersona() == null) {
                docenteSeleccion.setPersona(controlPersona.getIPersona());
            }

            docenteSeleccion.getPersona().setNroDocumento(
                nroDocumento);
            docenteSeleccion.getPersona().setApellido(
                txtDatosApellido.getText());
            docenteSeleccion.getPersona().setNombre(
                txtDatosNombre.getText());
            docenteSeleccion.getPersona().setTipoDocumento(
                cmbDatosTipoDocumento.getSelectionModel().getSelectedItem());
            docenteSeleccion.getPersona().setFechaNacimiento(
                dtpDatosFechaNacimiento.getValue());

            // Pestaña "Docente"
            docenteSeleccion.setLegajo(
                legajo);
            docenteSeleccion.setEstado(
                cmbDocenteEstado.getSelectionModel().getSelectedItem());
            docenteSeleccion.setCategoriaInvestigacion(
                cmbDocenteCategoria.getSelectionModel().getSelectedItem());

            // Pestaña "Observaciones"
            docenteSeleccion.setObservaciones(txtaObservaciones.getText());

            exitoGuardado(controlPersona.guardarPersona(docenteSeleccion.getPersona()), TITULO, "Guardar Persona");
            exitoGuardado(controlDocente.guardarDocente(docenteSeleccion), TITULO, "Guardar Docente");

            generalMostrarDocente();
            modoModificar();
        }
    }

    @FXML private Button btnDescartar;
    @FXML private void descartarDocente() {
        generalMostrarDocente();
    }

    @FXML private Button btnCosto;
    @FXML private void importarUltimoCosto() {
        this.gestorPantalla.lanzarPantalla(ImportarCosto.TITULO, null);
    }

    @FXML private TextField txtDocumento;
    @FXML private TextField txtLegajo;
    @FXML private TextField txtNombre;

 // ----------------------------- Pestaña Datos ------------------------------ //

    /** Inicializa los controles de la pestaña "Datos" */
    @FXML private void inicializarDatos() {

    }

    /** Muestra los datos de la persona del docente seleccionado: */
    private void datosMostrarPersona() {
        if (docenteSeleccion != null && docenteSeleccion.getPersona() != null) {
            txtDatosApellido.setText(
                docenteSeleccion.getPersona().getApellido());
            txtDatosNombre.setText(
                docenteSeleccion.getPersona().getNombre());
            cmbDatosTipoDocumento.getSelectionModel().select(
                docenteSeleccion.getPersona().getTipoDocumento());
            txtDatosDocumento.setText(String.valueOf(
                docenteSeleccion.getPersona().getNroDocumento()));
            dtpDatosFechaNacimiento.setValue(
                docenteSeleccion.getPersona().getFechaNacimiento());
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

    // Controles de la pestaña "Datos":
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
	    if (docenteSeleccion != null && docenteSeleccion.getPersona() != null) {
            for (IContacto contacto : docenteSeleccion.getPersona().getContactos()) {
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
//	    contactosActualizarTabla();
	}

	@FXML private Button btnContactosNuevo;
	@FXML private void nuevoContacto(ActionEvent event) {
	    if (docenteSeleccion != null && docenteSeleccion.getPersona() != null) {
    	    contactoSeleccion = controlPersona.getIContacto();
    	    contactosVaciarControles();
    	    contactosModoNuevo();
	    }
	}

	@FXML private Button btnContactosGuardar;
	@FXML public void guardarContacto(ActionEvent event) {
	    if (docenteSeleccion != null && docenteSeleccion.getPersona() != null && contactoSeleccion != null) {
    	    contactoSeleccion.setTipo(
    	        cmbContactosTipo.getSelectionModel().getSelectedItem());
    	    contactoSeleccion.setDato(
    	        txtContactosDato.getText());

    	    EstadoOperacion resultado = controlPersona.guardarContacto(
    	        docenteSeleccion.getPersona(), contactoSeleccion);
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
        if (docenteSeleccion != null && docenteSeleccion.getPersona() != null && contactoSeleccion != null) {
            EstadoOperacion resultado = controlPersona.quitarContacto(docenteSeleccion.getPersona(), contactoSeleccion);
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
        if (docenteSeleccion != null && docenteSeleccion.getPersona() != null) {
            for (IDomicilio domicilio: docenteSeleccion.getPersona().getDomicilios()) {
                filasDomicilios.add(new FilaDomicilio(domicilio));
            }
        }
    }

    /** Muestra los datos del domicilio seleccionado: */
    private void domiciliosMostrarDomicilio() {
        if (domicilioSeleccion != null) {
            cmbDomiciliosProvincia.getSelectionModel().select(
                domicilioSeleccion.getProvincia());
            txtDomiciliosCiudad.setText(
                domicilioSeleccion.getCiudad());
            txtDomiciliosCP.setText(
                domicilioSeleccion.getCodigoPostal());
            txtDomiciliosDireccion.setText(
                domicilioSeleccion.getDireccion());
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
//        domiciliosActualizarTabla();
    }

    @FXML private Button btnDomiciliosNuevo;
    @FXML public void nuevoDomicilio(ActionEvent event) {
        domicilioSeleccion = controlPersona.getIDomicilio();
        domiciliosVaciarControles();
        domiciliosModoNuevo();
    }

    @FXML private Button btnDomiciliosGuardar;
    @FXML public void guardarDomicilio(ActionEvent event) {
        if (docenteSeleccion != null && docenteSeleccion.getPersona() != null && domicilioSeleccion != null) {
            domicilioSeleccion.setProvincia(cmbDomiciliosProvincia.getSelectionModel().getSelectedItem());
            domicilioSeleccion.setCiudad(txtDomiciliosCiudad.getText());
            domicilioSeleccion.setCodigoPostal(txtDomiciliosCP.getText());
            domicilioSeleccion.setDireccion(txtDomiciliosDireccion.getText());

            exitoGuardado(controlPersona.guardarDomicilio(docenteSeleccion.getPersona(), domicilioSeleccion), TITULO, "Guardar Domicilio");
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
        if (docenteSeleccion != null && docenteSeleccion.getPersona() != null && domicilioSeleccion != null) {
            if (exitoEliminar(controlPersona.quitarDomicilio(docenteSeleccion.getPersona(), domicilioSeleccion), TITULO, "Eliminar Domicilio")) {
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
        if (docenteSeleccion != null && docenteSeleccion.getPersona() != null) {
            for (ITitulo titulo : docenteSeleccion.getPersona().getTitulos()) {
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
//        titulosActualizarTabla();
    }

    @FXML private TextField txtTitulosTitulo;

    @FXML private Button btnTitulosAgregar;
    @FXML public void agregarTitulo(ActionEvent event) {
        if (docenteSeleccion != null && docenteSeleccion.getPersona() != null) {
            tituloSeleccion = controlPersona.getITitulo();
            tituloSeleccion.setNombre(txtTitulosTitulo.getText());

            exitoGuardado(controlPersona.guardarTitulo(docenteSeleccion.getPersona(), tituloSeleccion), TITULO, "Guardar Título");
            titulosActualizarTabla();
        }
    }

    @FXML private Button btnTitulosQuitar;
    @FXML public void quitarTitulo(ActionEvent event) {
        if (docenteSeleccion != null && docenteSeleccion.getPersona() != null && tituloSeleccion != null) {
            if (exitoEliminar(controlPersona.quitarTitulo(docenteSeleccion.getPersona(), tituloSeleccion), TITULO, "Eliminar Título")) {
                tituloSeleccion = null;
                titulosVaciarControles();
            }
            titulosActualizarTabla();
        }
    }

    @FXML private Button btnTitulosMayor;
    @FXML public void mayorTitulo(ActionEvent event) {
        if (docenteSeleccion != null && docenteSeleccion.getPersona() != null && tituloSeleccion != null) {
            if (!tituloSeleccion.isEsMayor()) {
                /* Si el título seleccionado no es el mayor entonces desmarco
                   todos los títulos. */
                for (ITitulo titulo : docenteSeleccion.getPersona().getTitulos()) {
                    titulo.setEsMayor(false);
                }

                // Y marco el título actual como el mayor:
                tituloSeleccion.setEsMayor(true);

                // Y persisto los cambios en todos los títulos:
                boolean exito = true;
                for (ITitulo titulo : docenteSeleccion.getPersona().getTitulos()) {
                    EstadoOperacion resultado = controlPersona.guardarTitulo(docenteSeleccion.getPersona(), titulo);
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

// ----------------------------- Pestaña Docente ------------------------------ //
    @FXML public TextField txtDocenteLegajo;
    @FXML public ComboBox<EstadoDocente> cmbDocenteEstado;
    @FXML public ComboBox<CategoriaInvestigacion> cmbDocenteCategoria;

    /** Muestra los datos del docente seleccionado: */
    private void docenteMostrarDocente() {
        if (docenteSeleccion != null) {
            txtDocenteLegajo.setText(String.valueOf(
                docenteSeleccion.getLegajo()));
            cmbDocenteEstado.getSelectionModel().select(
                docenteSeleccion.getEstado());
            cmbDocenteCategoria.getSelectionModel().select(
                docenteSeleccion.getCategoriaInvestigacion());
        }
    }

    /** Vacía los controles de datos */
    private void docenteVaciarControles() {
        txtDocenteLegajo.clear();
        cmbDocenteEstado.getSelectionModel().clearSelection();
        cmbDocenteCategoria.getSelectionModel().clearSelection();
    }

    @FXML private void inicializarDocente() {
//        docenteMostrarDocente();
    }

 // ----------------------------- Pestaña Cargos ----------------------------- //
    private ICargoDocente cargoDocenteSeleccion;
    protected ObservableList<FilaCargo> filasCargos = FXCollections.observableArrayList();

    public class FilaCargo {
        private ICargoDocente cargoDocente;

        /**
         * Crea una FilaCargo a partir de un cargoDocente.
         * @param cd Cargo docente
         */
        public FilaCargo(ICargoDocente cd) {
            this.cargoDocente = cd;
        }
        public int getId() {
            return this.cargoDocente.getId();
        }
        public String getArea() {
            return this.cargoDocente.getArea().getDescripcion();
        }
        public String getCargo() {
            return this.cargoDocente.getCargo().getDescripcion();
        }
        public String getEstado() {
            return this.cargoDocente.getEstado().getDescripcion();
        }
        public ICargoDocente getInstanciaCargoDocente() {
            return this.cargoDocente;
        }
    }

    /** Refresca la tabla de cargos */
    public void cargosActualizarTabla() {
        filasCargos.clear();
        if (docenteSeleccion != null) {
            for (ICargoDocente cargoDocente : docenteSeleccion.getCargosDocentes()) {
                filasCargos.add(new FilaCargo(cargoDocente));
            }
        }
    }

    /** Muestra los datos del cargo seleccionado: */
    private void cargosMostrarCargoDocente() {
        if (cargoDocenteSeleccion != null) {
        	if (cargoDocenteSeleccion.getArea() != null) {
                txtCargosArea.setText(cargoDocenteSeleccion.getArea().getDescripcion());
            }
        	if (cargoDocenteSeleccion.getCargo() != null) {
                txtCargosCargo.setText(cargoDocenteSeleccion.getCargo().getDescripcion());
            }
        	if (cargoDocenteSeleccion.getEstado() != null) {
                cmbCargosEstado.getSelectionModel().select(cargoDocenteSeleccion.getEstado());
            }
        	if (cargoDocenteSeleccion.getTipoCargo() != null) {
                cmbCargosTipo.getSelectionModel().select(cargoDocenteSeleccion.getTipoCargo());
            }

            txtCargosDisp.setText(cargoDocenteSeleccion.getDisposicion());
            dtpCargosDispDesde.setValue(cargoDocenteSeleccion.getDispDesde());
            dtpCargosDispHasta.setValue(cargoDocenteSeleccion.getDispHasta());
            txtCargosRes.setText(cargoDocenteSeleccion.getResolucion());
            dtpCargosResDesde.setValue(cargoDocenteSeleccion.getResDesde());
            dtpCargosResHasta.setValue(cargoDocenteSeleccion.getResHasta());
            txtCargosCosto.setText(String.valueOf(cargoDocenteSeleccion.getUltimoCosto()));
            dtpCargosCosto.setValue(cargoDocenteSeleccion.getFechaUltCost());
        }
    }

    /** Vacía los controles de datos del cargo */
    private void cargosVaciarControles() {
        txtCargosArea.clear();
        txtCargosCargo.clear();
        cmbCargosEstado.getSelectionModel().clearSelection();
        cmbCargosTipo.getSelectionModel().clearSelection();
        txtCargosDisp.clear();
        dtpCargosDispDesde.setValue(null);
        dtpCargosDispHasta.setValue(null);
        txtCargosRes.clear();
        dtpCargosResDesde.setValue(null);
        dtpCargosResHasta.setValue(null);
        txtCargosCosto.clear();
        dtpCargosCosto.setValue(null);
    }

    private void cargosModoModificar() {
        if (this.permiso.getModificar()) {
            btnCargosGuardar.setVisible(true);
            btnCargosDescartar.setVisible(true);
            btnCargosEliminar.setVisible(true);
            txtCargosCodigo.setEditable(false);
            btnCargosArea.setVisible(true);
            btnCargosCargo.setVisible(true);
            txtCargosDisp.setEditable(true);
            dtpCargosDispDesde.setEditable(true);
            dtpCargosDispHasta.setEditable(true);
            txtCargosRes.setEditable(true);
            dtpCargosResDesde.setEditable(true);
            dtpCargosResHasta.setEditable(true);

            cmbCargosEstado.setDisable(false);
            cmbCargosTipo.setDisable(false);

            this.window.setTitle("Docentes - Modificar Cargo");
        }
    }

    private void cargosModoNuevo() {
        if (this.permiso.getCrear()) {
            cargosModoModificar();
            this.window.setTitle("Docentes - Nuevo Cargo");
            btnCargosEliminar.setVisible(false);
            txtCargosCodigo.setEditable(true);
        }
    }

    private void cargosModoVer() {
        btnCargosGuardar.setVisible(false);
        btnCargosDescartar.setVisible(false);
        btnCargosEliminar.setVisible(false);
        txtCargosCodigo.setEditable(false);
        btnCargosArea.setVisible(false);
        btnCargosCargo.setVisible(false);
        txtCargosDisp.setEditable(false);
        dtpCargosDispDesde.setEditable(false);
        dtpCargosDispHasta.setEditable(false);
        txtCargosRes.setEditable(false);
        dtpCargosResDesde.setEditable(false);
        dtpCargosResHasta.setEditable(false);

        cmbCargosEstado.setDisable(true);
        cmbCargosTipo.setDisable(true);
    }

    @FXML private void inicializarTablaCargos() {
//        cargosActualizarTabla();
    }

    @FXML private Button btnCargosNuevo;
    @FXML private void nuevoCargo() {
        if (docenteSeleccion != null) {
            cargoDocenteSeleccion = controlDocente.getICargoDocente();
            cargosVaciarControles();
            cargosModoNuevo();
        }
    }

    @FXML private Button btnCargosGuardar;
    @FXML private void guardarCargo() {
        if (docenteSeleccion != null && cargoDocenteSeleccion != null) {
        	String titulo = "Nuevo cargo";
        	String encabezado = "Error al crear el cargo";
        	
        	if ("".equals(txtCargosCodigo.getText())) {
        		alertaError(titulo, encabezado, "El código del cargo es obligatorio.");
        		return;
        	}
        	try {
        		int codigo = Integer.parseInt(txtCargosCodigo.getText());
        		// El código debe ser positivo
        		if (codigo < 0) throw new NumberFormatException();
        		cargoDocenteSeleccion.setId(codigo);
        	} catch (NumberFormatException e) {
        		alertaError(titulo, encabezado, "El código del cargo debe ser un número sin símbolos.");
        	} catch (Exception e) {
        		alertaError(titulo, encabezado, "Hubo un error al crear el cargo.");
        		e.printStackTrace();
        	}
        	
        	// Verificar que no exista si está en modo nuevo
        	if (this.txtCargosCodigo.isEditable()) {
        		List<ICargoDocente> listCargos = this.controlDocente.listarCargosDocente(null, cargoDocenteSeleccion); 
        		if (listCargos != null && !listCargos.isEmpty()) {
        			alertaError(titulo, encabezado, "El código de cargo ya está en uso");
        			return;
        		}
        	}
        	
            try {
                float ultimoCosto = Utilidades.stringToFloat(txtCargosCosto.getText());

                cargoDocenteSeleccion.setEstado(cmbCargosEstado.getValue());
                cargoDocenteSeleccion.setTipoCargo(cmbCargosTipo.getValue());
                cargoDocenteSeleccion.setDisposicion(txtCargosDisp.getText());
                cargoDocenteSeleccion.setDispDesde(dtpCargosDispDesde.getValue());
                cargoDocenteSeleccion.setDispHasta(dtpCargosDispHasta.getValue());
                cargoDocenteSeleccion.setResolucion(txtCargosRes.getText());
                cargoDocenteSeleccion.setResDesde(dtpCargosResDesde.getValue());
                cargoDocenteSeleccion.setResHasta(dtpCargosResHasta.getValue());

                cargoDocenteSeleccion.setUltimoCosto(ultimoCosto);
                cargoDocenteSeleccion.setFechaUltCost(dtpCargosCosto.getValue());

                exitoGuardado(controlDocente.guardarCargoDocente(docenteSeleccion, cargoDocenteSeleccion), TITULO, "Guardar Cargo");

                docenteMostrarDocente();
                cargosModoModificar();

            } catch (IllegalArgumentException e) {
                mensajeEstado(e.getMessage());
            } catch (Exception e) {
            	alertaError(titulo, encabezado, "Hubo un error al crear el cargo.");
            	e.printStackTrace();
            }
            cargosActualizarTabla();
        }
    }

    @FXML private Button btnCargosDescartar;
    @FXML private void descartarCargo() {
        cargosMostrarCargoDocente();
    }

    @FXML private Button btnCargosEliminar;
    @FXML private void eliminarCargo() {
        if (docenteSeleccion != null && cargoDocenteSeleccion != null) {
            if (exitoEliminar(controlDocente.quitarCargoDocente(docenteSeleccion, cargoDocenteSeleccion), TITULO, "Eliminar Cargo")) {
                cargoDocenteSeleccion = null;
                cargosVaciarControles();
                cargosModoVer();
            }
            docenteMostrarDocente();
            cargosActualizarTabla();
        }
    }
    
    @FXML private TextField txtCargosCodigo;

    @FXML private TextField txtCargosArea;
    @FXML private Button btnCargosArea;
    @FXML private void seleccionarArea() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, false);
        args.put(Busqueda.KEY_TIPO, "Areas");
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_AREA);
        args.put(GestorPantalla.KEY_PADRE, Docentes.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " Areas", args);
    }

    @FXML private TextField txtCargosCargo;
    @FXML private Button btnCargosCargo;
    @FXML private void seleccionarCargo() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, false);
        args.put(Busqueda.KEY_TIPO, "Cargos");
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_CARGO);
        args.put(GestorPantalla.KEY_PADRE, Docentes.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " Cargos", args);
    }

    @FXML protected TableView<FilaCargo> tblCargos;
    @FXML protected TableColumn<FilaCargo, Integer> colCargosId;
    @FXML protected TableColumn<FilaCargo, String> colCargosArea;
    @FXML protected TableColumn<FilaCargo, String> colCargosCargo;
    @FXML protected TableColumn<FilaCargo, String> colCargosEstado;

    @FXML private ComboBox<EstadoCargo> cmbCargosEstado;

    @FXML private ComboBox<TipoCargo> cmbCargosTipo;

    @FXML private TextField txtCargosDisp;
    @FXML private DatePicker dtpCargosDispDesde;
    @FXML private DatePicker dtpCargosDispHasta;

    @FXML private TextField txtCargosRes;
    @FXML private DatePicker dtpCargosResDesde;
    @FXML private DatePicker dtpCargosResHasta;

    @FXML private TextField txtCargosCosto;
    @FXML private DatePicker dtpCargosCosto;

 // ----------------------------- Pestaña Investigación ---------------------- //
    protected ObservableList<FilaInvestigacion> filasInvestigacion = FXCollections.observableArrayList();

    public class FilaInvestigacion {
        private String id;
        private String nombre;
        private String cargo;
        private String area;

        public FilaInvestigacion(String id, String nombre, String cargo, String area) {
            this.id = id;
            this.nombre = nombre;
            this.cargo = cargo;
            this.area = area;
        }

        public String getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public String getCargo() {
            return cargo;
        }

        public String getArea() {
            return area;
        }
    }

    /** Refresca la tabla de investigación */
    private void investigacionActualizarTabla() {
        filasInvestigacion.clear();
        if (docenteSeleccion != null) {
            if (docenteSeleccion.getCategoriaInvestigacion() != null) {
                txtInvestigacionCategoria.setText(
            			docenteSeleccion.getCategoriaInvestigacion().getDescripcion());
            }

            List<Hashtable<String,String>> integranteDe =
                controlInvestigacion.integranteDe(docenteSeleccion);

            if (integranteDe != null) {
                for (Hashtable<String, String> infoProyecto : integranteDe) {
	                filasInvestigacion.add(new FilaInvestigacion(
	                		infoProyecto.get("P.ID"),
		                    infoProyecto.get("P.Nombre"),
		                    infoProyecto.get("C.Descripcion"),
		                    infoProyecto.get("A.Descripcion")));
            }
            }
        }
    }

    @FXML private void inicializarInvestigacion() {
        /*
        if (docenteSeleccion != null) {
            CategoriaInvestigacion ci = docenteSeleccion.getCategoriaInvestigacion();
            txtInvestigacionCategoria.setText(ci.getDescripcion());
        }
        investigacionActualizarTabla();
        */
    }

    @FXML private TextField txtInvestigacionCategoria;
    @FXML protected TableView<FilaInvestigacion> tblInvestigacion;
    @FXML protected TableColumn<FilaInvestigacion, Integer> colInvestigacionId;
    @FXML protected TableColumn<FilaInvestigacion, String> colInvestigacionNombre;
    @FXML protected TableColumn<FilaInvestigacion, String> colInvestigacionArea;
    @FXML protected TableColumn<FilaInvestigacion, String> colInvestigacionCargo;

    @FXML private TabPane tabpDocentes;

// ----------------------------- Pestaña Incentivos ------------------------- //

    private IIncentivo incentivoSeleccion = null;
    protected ObservableList<FilaIncentivo> filasIncentivos = FXCollections.observableArrayList();

    public class FilaIncentivo {
        private IIncentivo incentivo;
        public FilaIncentivo(IIncentivo incentivo) {
            this.incentivo = incentivo;
        }
        public int getAnio() {
            return this.incentivo.getFecha().getValue();
        }
        public IIncentivo getInstanciaIncentivo() {
            return this.incentivo;
        }
    }

    /** Refresca la tabla de incentivos */
    private void incentivosActualizarTabla() {
        filasIncentivos.clear();
        if (docenteSeleccion != null) {
            for (IIncentivo incentivo: docenteSeleccion.getIncentivos()) {
                filasIncentivos.add(new FilaIncentivo(incentivo));
            }
        }
    }

    /** Muestra los datos del incentivo seleccionado: */
    private void incentivosMostrarIncentivo() {
        if (incentivoSeleccion != null) {
            txtIncentivosAnio.setText(incentivoSeleccion.getFecha().toString());
        }
    }

    /** Vacía los controles de datos del incentivo */
    private void incentivosVaciarControles() {
        txtIncentivosAnio.clear();
    }

    private void incentivosModoModificar() {
        if (this.permiso.getModificar()) {
            btnIncentivosGuardar.setVisible(true);
            btnIncentivosDescartar.setVisible(true);
            btnIncentivosEliminar.setVisible(true);
            txtIncentivosAnio.setEditable(true);
        }
    }

    private void incentivosModoNuevo() {
        if (this.permiso.getModificar()) {
            btnIncentivosGuardar.setVisible(true);
            btnIncentivosDescartar.setVisible(true);
            btnIncentivosEliminar.setVisible(false);
            txtIncentivosAnio.setEditable(true);
        }
    }

    private void incentivosModoVer() {
        btnIncentivosGuardar.setVisible(false);
        btnIncentivosDescartar.setVisible(false);
        txtIncentivosAnio.setEditable(false);
        btnIncentivosEliminar.setVisible(false);
    }

    @FXML public void inicializarTablaIncentivos() {
//        incentivosActualizarTabla();
    }

    @FXML private Button btnIncentivosNuevo;
    @FXML private void nuevoIncentivo() {
        if (docenteSeleccion != null) {
            incentivoSeleccion = controlDocente.getIIncentivo();
            incentivosVaciarControles();
            incentivosModoNuevo();
        }
    }

    @FXML private Button btnIncentivosGuardar;
    @FXML private void guardarIncentivo() {
        if (docenteSeleccion != null && incentivoSeleccion != null) {
            try {
                incentivoSeleccion.setFecha(Year.of(Integer.parseInt(txtIncentivosAnio.getText())));
                exitoGuardado(controlDocente.guardarIncentivo(docenteSeleccion, incentivoSeleccion), TITULO, "Guardar Incentivo");
                incentivosActualizarTabla();
                incentivosModoModificar();

            } catch (DateTimeException e) {
                alertaError(TITULO, "Guardar Incentivo", "El año ingresado es inválido");
            } catch (NumberFormatException e) {
                alertaError(TITULO, "Guardar Incentivo", "El año debe ser numérico");
            }
        }
    }

    @FXML private Button btnIncentivosDescartar;
    @FXML private void descartarIncentivo() {
        incentivosMostrarIncentivo();
    }

    @FXML private Button btnIncentivosEliminar;
    @FXML private void eliminarIncentivo() {
        if (docenteSeleccion != null && incentivoSeleccion != null) {

            if (exitoEliminar(controlDocente.quitarIncentivo(docenteSeleccion, incentivoSeleccion), TITULO, "Eliminar Incentivo")) {
                cargoDocenteSeleccion = null;
                incentivosVaciarControles();
                incentivosModoVer();
            }
            incentivosActualizarTabla();
        }
    }

    @FXML protected TextField txtIncentivosAnio;
    @FXML protected TableView<FilaIncentivo> tblIncentivos;
    @FXML protected TableColumn<FilaIncentivo, Integer> colIncentivosAnio;

// ----------------------------- Pestaña Observaciones ---------------------- //
    @FXML private TextArea txtaObservaciones;

    /** Vacía los controles de observaciones */
    private void observacionesVaciarControles() {
        txtaObservaciones.clear();
    }

    /** Muestra las observaciones del docente */
    private void observacionesMostrarObservaciones() {
        txtaObservaciones.setText(
            docenteSeleccion.getObservaciones());
    }

    @FXML private void inicializarObservaciones() {
        /*
        if (docenteSeleccion != null) {
            txtaObservaciones.setText(docenteSeleccion.getObservaciones());
        }
        */
    }

// ----------------------------- Resultado de Búsqueda ---------------------- //
    /** Recibir parámetros de otra pantalla */
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

        // Se recibe un Docente de otra pantalla. Llenar datos.
        Object oDocente = args.get(REC_DOCENTE);
        if (oDocente != null && oDocente instanceof IDocente) {
        	this.docenteSeleccion = (IDocente) oDocente;
        	generalMostrarDocente();
        	nuevoDocente();
        	modoNuevo();
        }

        // Se recibe un CargoDocente de otra pantalla. Llenar datos.
        Object oCargo = args.get(REC_CARGO_DOCENTE);
        if (oCargo != null && oCargo instanceof ICargoDocente) {
        	ICargoDocente cargoDocente = (ICargoDocente) oCargo;
        	this.docenteSeleccion = cargoDocente.getDocente();
        	generalMostrarDocente();
        	modoModificar();
        	nuevoCargo();
        	this.cargoDocenteSeleccion = cargoDocente;
        	cargosMostrarCargoDocente();
        	cargosModoNuevo();
        }

        Object pestana = args.get(KEY_TAB);
        if (pestana != null) {
            tabpDocentes.getSelectionModel().select((Integer) pestana);
        }
    }

    @SuppressWarnings("unused")
    private void setDocenteSeleccion(Object docenteSeleccion, String tipo) {
        if (docenteSeleccion instanceof IDocente) {
            this.docenteSeleccion = (IDocente) docenteSeleccion;
            /* Si se recibe una persona sin docente entonces paso
             * al modo nuevo docente:
             */
            if (this.docenteSeleccion.getLegajo() == -1) {
                modoNuevo();
            } else {
                modoModificar();
            }
            generalMostrarDocente();
        }
    }

    @SuppressWarnings("unused")
    private void setAreaSeleccion(Object areaSeleccion, String tipo) {
        if (areaSeleccion instanceof IArea) {
            this.cargoDocenteSeleccion.setArea((IArea) areaSeleccion);
            this.txtCargosArea.setText(((IArea) areaSeleccion).getDescripcion());
            this.btnCargosCargo.requestFocus();
        }
    }

    @SuppressWarnings("unused")
    private void setCargoSeleccion(Object cargoSeleccion, String tipo) {
        if (cargoSeleccion instanceof ICargo) {
            this.cargoDocenteSeleccion.setCargo((ICargo) cargoSeleccion);
            this.txtCargosCargo.setText(((ICargo) cargoSeleccion).getDescripcion());
            this.cmbCargosEstado.requestFocus();
        }
    }

// ----------------------------- Ocultamiento de controles ------------------ //
    @Override
    public void inicializar() {
        // Ocultar controles según roles del usuario:
        boolean crear = false;
        boolean modificar = false;
        boolean eliminar = false;
        boolean listar = false;

        for (IRol rol : this.usuario.getRoles()) {
            for (IPermiso permiso : rol.getPermisos()) {
                if (permiso.getModulo() == Modulo.DOCENTES) {
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
            btnCosto.setVisible(false);
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

            // Pestaña Docente:
            txtDocenteLegajo.setEditable(false);
            cmbDocenteEstado.setDisable(true);
            cmbDocenteCategoria.setDisable(true);

            // Pestaña Cargos:
            btnCargosNuevo.setVisible(false);
            btnCargosGuardar.setVisible(false);
            btnCargosDescartar.setVisible(false);
            btnCargosEliminar.setVisible(false);

            btnCargosArea.setVisible(false);
            btnCargosCargo.setVisible(false);
            cmbCargosEstado.setDisable(true);
            cmbCargosTipo.setDisable(true);
            txtCargosDisp.setEditable(false);
            dtpCargosDispDesde.setEditable(false);
            dtpCargosDispHasta.setEditable(false);
            txtCargosRes.setEditable(false);
            dtpCargosResDesde.setEditable(false);
            dtpCargosResHasta.setEditable(false);

            // Pestaña Incentivos:
            btnIncentivosNuevo.setVisible(false);
            btnIncentivosGuardar.setVisible(false);
            btnIncentivosDescartar.setVisible(false);
            txtIncentivosAnio.setEditable(false);

            // Pestaña Observaciones:
            txtaObservaciones.setEditable(false);
        }

        if (!eliminar) {
            btnEliminar.setVisible(false);
            btnCargosEliminar.setVisible(false);
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

        // Pestaña "Docente":
        this.cmbDocenteEstado.setItems(
            FXCollections.observableArrayList(
                ControlAuxiliar.listarEstadosDocente()));

        this.cmbDocenteCategoria.setItems(
            FXCollections.observableArrayList(
                ControlAuxiliar.listarCategoriasInvestigacion()));

        // Pestaña "Cargos":
        inicializarTabla("Cargos");
        tblCargos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cargoDocenteSeleccion = newSelection.getInstanciaCargoDocente();
                cargosMostrarCargoDocente();
                cargosModoModificar();
            }
        });

        cmbCargosEstado.setItems(
                FXCollections.observableArrayList(
                        ControlAuxiliar.listarEstadosCargo()));

        cmbCargosTipo.setItems(
                FXCollections.observableArrayList(
                        ControlAuxiliar.listarTiposCargo()));

        // Pestaña "Investigación":
        inicializarTabla("Investigacion");

        // Pestaña "Incentivos":
        inicializarTabla("Incentivos");
        tblIncentivos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                incentivoSeleccion = newSelection.getInstanciaIncentivo();
                incentivosMostrarIncentivo();
                incentivosModoModificar();
            }
        });
    }

    @Override
    public void modoModificar() {
        if (this.permiso.getModificar()) {
            // General:
            btnCosto.setVisible(true);
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

            // Pestaña Docente:
            txtDocenteLegajo.setEditable(true);
            cmbDocenteEstado.setDisable(false);
            cmbDocenteCategoria.setDisable(false);

            // Pestaña Cargos:
//            cargosModoVer();
            btnCargosNuevo.setVisible(true);

            // Pestaña Incentivos:
            incentivosModoVer();
            btnIncentivosNuevo.setVisible(true);

            // Pestaña Observaciones:
            txtaObservaciones.setEditable(true);
        }

        if (this.permiso.getEliminar()) {
            btnEliminar.setVisible(true);
        }

        this.window.setTitle(TITULO + " - Modificar Docente");
        if (this.docenteSeleccion != null) {
            this.gestorPantalla.mensajeEstado("Modificar al Docente " + this.docenteSeleccion.getLegajo());
        }
    }

    @Override
    public void modoNuevo() {
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

            // Pestaña "Docente":
            txtDocenteLegajo.setEditable(true);
            cmbDocenteEstado.setDisable(false);
            cmbDocenteCategoria.setDisable(false);

            this.window.setTitle(TITULO + " - Nuevo Docente");
            this.gestorPantalla.mensajeEstado("Nuevo Docente ");
        }
    }

    @Override
    public void modoVer() {
        // General:
        btnEliminar.setVisible(false);
        btnGuardar.setVisible(false);
        btnDescartar.setVisible(false);
        btnCosto.setVisible(true);

        // Pestaña Datos:
        txtDatosApellido.setEditable(false);
        txtDatosNombre.setEditable(false);
        cmbDatosTipoDocumento.setDisable(true);
        txtDatosDocumento.setEditable(false);
        dtpDatosFechaNacimiento.setDisable(false);

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

        // Pestaña "Docente":
        txtDocenteLegajo.setEditable(false);
        cmbDocenteEstado.setDisable(true);
        cmbDocenteCategoria.setDisable(true);

        // Pestaña Cargos:
        cargosModoVer();
        btnCargosNuevo.setVisible(false);

        // Pestaña Incentivos:
        incentivosModoVer();
        btnIncentivosNuevo.setVisible(false);

        // Pestaña Observaciones:
        txtaObservaciones.setEditable(false);

        this.window.setTitle(TITULO);
        this.gestorPantalla.mensajeEstado("");
    }
}
