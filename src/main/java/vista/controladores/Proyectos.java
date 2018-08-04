package vista.controladores;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import controlador.ControlInvestigacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.auxiliares.EstadoProyecto;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.investigacion.IIntegrante;
import modelo.investigacion.IProrroga;
import modelo.investigacion.IProyecto;
import modelo.investigacion.IRendicion;
import modelo.investigacion.ISubsidio;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
import modelo.usuario.Modulo;
import vista.GestorPantalla;
/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Proyectos extends ControladorVista implements Initializable {

    public void setProyectoSeleccion(Object proyecto, String tipo) {
        if (proyecto instanceof IProyecto) {
            proyectoSeleccion = (IProyecto) proyecto;
            modoModificar();
            generalMostrarProyecto();
        }
    }

    public void setDocenteSeleccion(Object docente, String tipo) {
        if (docente instanceof IDocente) {
            switch (tipo) {
                case TIPO_DIRECTOR:
                    setDirectorSeleccion((IDocente) docente);
                    break;
                case TIPO_CODIRECTOR:
                    setCodirectorSeleccion((IDocente) docente);
                    break;
                default:
                    throw new RuntimeException("Tipo de docente no esperado.");
            }
        }
    }

    private void setCodirectorSeleccion(IDocente codirector) {
        if (proyectoSeleccion != null) {
            proyectoSeleccion.setCodirector(codirector);
            txtDatosCodirector.setText(proyectoSeleccion.getCodirector().getPersona().getNombreCompleto());
        }
    }

    private void setDirectorSeleccion(IDocente director) {
        if (proyectoSeleccion != null) {
            proyectoSeleccion.setDirector(director);
            txtDatosDirector.setText(proyectoSeleccion.getDirector().getPersona().getNombreCompleto());
        }
    }

    @SuppressWarnings("unused")
    private void setCargoDocenteSeleccion(Object cargoDocente, String tipo) {
        if (cargoDocente instanceof ICargoDocente) {
            integranteSeleccion.setCargoDocente((ICargoDocente) cargoDocente);
        }
    }

	@FXML private ScrollPane mainPane;

	public static final String TITULO = "Proyectos";
	private ControlInvestigacion controlInvestigacion = new ControlInvestigacion(this);

	// Tipos respuesta:
	private static final String TIPO_CARGODOCENTE = "CargoDocente";
	private static final String TIPO_CODIRECTOR = "Codirector";
	private static final String TIPO_DIRECTOR = "Director";
	private static final String TIPO_PROYECTO = "Proyecto";

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	@Override
	public void inicializar() {
	    /* Ocultar controles según roles del usuario: */
	    boolean crear = false;
	    boolean modificar = false;
	    boolean eliminar = false;
	    boolean listar = false;

	    for (IRol rol : this.usuario.getRoles()) {
	        for (IPermiso permiso : rol.getPermisos()) {
	            if (permiso.getModulo() == Modulo.PROYECTOS) {
	                this.permiso = permiso;
	                crear |= permiso.getCrear();
	                modificar |= permiso.getModificar();
	                eliminar |= permiso.getEliminar();
	                listar |= permiso.getListar();
	            }
	        }
	    }

	    if (!crear) {
	        btnProyectosNuevo.setVisible(false);
	    }

	    if (!modificar) {
	        // General:
            btnProyectosDescartar.setVisible(false);
            btnProyectosGuardar.setVisible(false);

	        // Pestaña Datos:
//	        btnDatosGuardar.setVisible(false);
//	        btnDatosDescartar.setVisible(false);

	        txtDatosNombre.setEditable(false);
	        txtDatosDirector.setEditable(false);
	        btnDatosDirector.setVisible(false);
	        txtDatosCodirector.setEditable(false);
	        btnDatosCodirector.setVisible(false);
	        cmbDatosEstado.setDisable(true);
	        dtpDatosPresentacion.setEditable(false);
	        dtpDatosAprobacion.setEditable(false);
	        dtpDatosInicio.setEditable(false);
	        dtpDatosFinalizacion.setEditable(false);

	        // Pestaña Integrantes:
	        btnIntegrantesNuevo.setVisible(false);
	        btnIntegrantesGuardar.setVisible(false);
	        btnIntegrantesDescartar.setVisible(false);
	        btnIntegrantesEliminar.setVisible(false);

	        btnIntegrantesDocente.setVisible(false);
	        txtIntegrantesApellido.setEditable(false);
	        txtIntegrantesNombre.setEditable(false);
	        txtIntegrantesCargo.setEditable(false);
	        txtIntegrantesInstitucion.setEditable(false);
	        txtIntegrantesHoras.setEditable(false);

	        // Pestaña Subsidios:
	        btnSubsidiosNuevo.setVisible(false);
            btnSubsidiosGuardar.setVisible(false);
            btnSubsidiosDescartar.setVisible(false);
            btnSubsidiosEliminar.setVisible(false);

            txtSubsidiosAnio.setVisible(false);
            txtSubsidiosMonto.setEditable(false);
            txtSubsidiosDisp.setEditable(false);
            txtaSubsidiosObservaciones.setEditable(false);

	        // Pestaña Rendiciones:
            btnRendicionesNueva.setVisible(false);
            btnRendicionesGuardar.setVisible(false);
            btnRendicionesDescartar.setVisible(false);
            btnRendicionesEliminar.setVisible(false);

            cmbRendicionesAnio.setDisable(true);
            dtpRendicionesFecha.setEditable(false);
            txtRendicionesMonto.setEditable(false);
            txtaRendicionesObservaciones.setEditable(false);

            // Pestaña Prórrogas:
            btnProrrogasNueva.setVisible(false);
            btnProrrogasGuardar.setVisible(false);
            btnProrrogasDescartar.setVisible(false);
            btnProrrogasEliminar.setVisible(false);

            dtpProrrogasFinalizacion.setEditable(false);
            txtProrrogasDisp.setEditable(false);

            // Pestaña Resumen:
//            btnResumenGuardar.setVisible(false);
//            btnResumenDescartar.setVisible(false);

            txtaResumen.setEditable(false);
	    }

	    if (!eliminar) {
	        btnProyectosEliminar.setVisible(false);
	    }

	    if (!listar) {
	    }

	    modoVer();
	}

	@Override
	public void modoModificar() {
	    if (this.permiso.getModificar()) {
	        // General:
	        btnProyectosDescartar.setVisible(true);
            btnProyectosGuardar.setVisible(true);
	        btnProyectosNuevo.setVisible(true);

	        // Pestaña Datos:
//            btnDatosGuardar.setVisible(true);
//            btnDatosDescartar.setVisible(true);

            txtDatosNombre.setEditable(true);
            txtDatosDirector.setEditable(true);
            btnDatosDirector.setVisible(true);
            txtDatosCodirector.setEditable(true);
            btnDatosCodirector.setVisible(true);
            cmbDatosEstado.setDisable(false);
            dtpDatosPresentacion.setEditable(true);
            dtpDatosAprobacion.setEditable(true);
            dtpDatosInicio.setEditable(true);
            dtpDatosFinalizacion.setEditable(true);

            // Pestaña Integrantes:
            integrantesModoVer();
            btnIntegrantesNuevo.setVisible(true);

            // Pestaña Subsidios:
            subsidiosModoVer();
            btnSubsidiosNuevo.setVisible(true);

            // Pestaña Rendiciones:
            rendicionesModoVer();
            btnRendicionesNueva.setVisible(true);

            // Pestaña Prórrogas:
            prorrogasModoVer();
            btnProrrogasNueva.setVisible(true);

            // Pestaña Resumen:
//            btnResumenGuardar.setVisible(true);
//            btnResumenDescartar.setVisible(true);

            txtaResumen.setEditable(true);
	    }

	    if (this.permiso.getEliminar()) {
	        btnProyectosEliminar.setVisible(true);
	    }

	    this.window.setTitle(TITULO + " - Modificar Proyecto");
	    if (this.proyectoSeleccion != null) {
	        this.gestorPantalla.mensajeEstado("Modificar al Proyecto " + this.proyectoSeleccion.getNombre());
	    }
	}

	@Override
    public void modoNuevo() {
	    if (this.permiso.getCrear()) {
	        btnProyectosDescartar.setVisible(true);
            btnProyectosGuardar.setVisible(true);
            btnProyectosNuevo.setVisible(false);
//            btnDatosGuardar.setVisible(true);
//            btnDatosDescartar.setVisible(true);
            txtDatosNombre.setEditable(true);
            btnDatosDirector.setVisible(true);
            btnDatosCodirector.setVisible(true);
            cmbDatosEstado.setDisable(false);
            dtpDatosPresentacion.setEditable(true);
            dtpDatosAprobacion.setEditable(true);
            dtpDatosInicio.setEditable(true);
            dtpDatosFinalizacion.setEditable(true);

            this.window.setTitle(TITULO + " - Nuevo Proyecto");
            this.gestorPantalla.mensajeEstado("Nuevo Proyecto");
        }
	}

	@Override
	public void modoVer() {
	    // General:
	    btnProyectosDescartar.setVisible(false);
	    btnProyectosEliminar.setVisible(false);
	    btnProyectosGuardar.setVisible(false);

	    // Pestaña Datos:
//        btnDatosGuardar.setVisible(false);
//        btnDatosDescartar.setVisible(false);

        txtDatosNombre.setEditable(false);
        txtDatosDirector.setEditable(false);
        btnDatosDirector.setVisible(false);
        txtDatosCodirector.setEditable(false);
        btnDatosCodirector.setVisible(false);
        cmbDatosEstado.setDisable(true);
        dtpDatosPresentacion.setEditable(false);
        dtpDatosAprobacion.setEditable(false);
        dtpDatosInicio.setEditable(false);
        dtpDatosFinalizacion.setEditable(false);

        // Pestaña Integrantes:
        integrantesModoVer();
        btnIntegrantesNuevo.setVisible(false);

        // Pestaña Subsidios:
        subsidiosModoVer();
        btnSubsidiosNuevo.setVisible(false);

        // Pestaña Rendiciones:
        rendicionesModoVer();
        btnRendicionesNueva.setVisible(false);

        // Pestaña Prórrogas:
        prorrogasModoVer();
        btnProrrogasNueva.setVisible(false);

        // Pestaña Resumen:
//        btnResumenGuardar.setVisible(false);
//        btnResumenDescartar.setVisible(false);

        txtaResumen.setEditable(false);

        this.window.setTitle(TITULO);
        this.gestorPantalla.mensajeEstado("");
	}

// -------------------------------- General --------------------------------- //

	private IProyecto proyectoSeleccion = null;

	@FXML private TextField txtProyectosNombre;

	/** Muestra los datos del proyecto seleccionado en los controles generales */
    private void generalMostrarProyecto() {
        if (proyectoSeleccion != null) {
            txtProyectosNombre.setText(proyectoSeleccion.getNombre());

            datosMostrarProyecto();
            integrantesActualizarTabla();
            subsidiosActualizarTabla();
            rendicionesActualizarTabla();
            prorrogasActualizarTabla();
            resumenMostrarResumen();
        }
    }

	/** Vacía los controles generales y los de todas las pestañas */
    private void generalVaciarControles() {
        txtProyectosNombre.clear();

        // Vaciar controles de las pestañas:
        vaciarTablas();
        datosVaciarControles();
        subsidiosVaciarControles();
        rendicionesVaciarControles();
        prorrogasVaciarControles();
        resumenVaciarControles();

        this.gestorPantalla.mensajeEstado("");
    }

	@FXML private Button btnProyectosBuscar;
	@FXML void buscarProyecto(ActionEvent event) {
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, false);
        args.put(Busqueda.KEY_TIPO, Proyectos.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_PROYECTO);
        args.put(GestorPantalla.KEY_PADRE, Proyectos.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Proyectos.TITULO, args);
	}

	@FXML private Button btnProyectosNuevo;
	@FXML void nuevoProyecto(ActionEvent event) {
	    this.proyectoSeleccion = controlInvestigacion.getIProyecto();
        generalVaciarControles();
        modoNuevo();
	}

	@FXML private Button btnProyectosEliminar;
    @FXML void eliminarProyecto(ActionEvent event) {
        if (proyectoSeleccion != null) {
            if (exitoEliminar(controlInvestigacion.eliminarProyecto(proyectoSeleccion), TITULO, "Eliminar Proyecto")) {
                proyectoSeleccion = null;
                generalVaciarControles();
                modoVer();
            }
        }
    }

    @FXML private Button btnProyectosGuardar;
    @FXML private void guardarProyecto(ActionEvent event) {
        if (proyectoSeleccion != null) {
            proyectoSeleccion.setNombre(txtDatosNombre.getText());
            proyectoSeleccion.setEstado(cmbDatosEstado.getSelectionModel().getSelectedItem());
            proyectoSeleccion.setFechaPresentacion(dtpDatosPresentacion.getValue());
            proyectoSeleccion.setFechaAprobacion(dtpDatosAprobacion.getValue());
            proyectoSeleccion.setFechaInicio(dtpDatosInicio.getValue());
            proyectoSeleccion.setFechaFin(dtpDatosFinalizacion.getValue());

            proyectoSeleccion.setResumen(txtaResumen.getText());

            exitoGuardado(controlInvestigacion.guardarProyecto(proyectoSeleccion), TITULO, "Guardar Proyecto");
            generalMostrarProyecto();

            modoModificar();
        }
    }

    @FXML private Button btnProyectosDescartar;
    @FXML private void descartarProyecto(ActionEvent event) {
        generalMostrarProyecto();
    }

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

// ----------------------------- Pestaña Datos ------------------------------ //

    /** Muestra los datos del proyecto seleccionado */
    private void datosMostrarProyecto() {
        if (proyectoSeleccion != null) {
            txtDatosNombre.setText(proyectoSeleccion.getNombre());

            IDocente director = proyectoSeleccion.getDirector();
            txtDatosDirector.setText(
                director.getLegajo() + " - " +
                director.getPersona().getNombreCompleto());

            IDocente codirector = proyectoSeleccion.getCodirector();
            if (codirector == null) {
                txtDatosCodirector.setText("");
            } else {
                txtDatosCodirector.setText(
                    codirector.getLegajo() + " - " +
                    codirector.getPersona().getNombreCompleto());
            }

            cmbDatosEstado.getSelectionModel().select(proyectoSeleccion.getEstado());
            dtpDatosPresentacion.setValue(proyectoSeleccion.getFechaPresentacion());
            dtpDatosAprobacion.setValue(proyectoSeleccion.getFechaAprobacion());
            dtpDatosInicio.setValue(proyectoSeleccion.getFechaInicio());
            dtpDatosFinalizacion.setValue(proyectoSeleccion.getFechaFin());
        }
    }

    /** Vacía los controles de datos */
    private void datosVaciarControles() {
        txtDatosNombre.clear();
        txtDatosDirector.clear();
        txtDatosCodirector.clear();
        cmbDatosEstado.getSelectionModel().clearSelection();
        dtpDatosPresentacion.setValue(null);
        dtpDatosAprobacion.setValue(null);
        dtpDatosInicio.setValue(null);
        dtpDatosFinalizacion.setValue(null);
    }

    @FXML private void inicializarDatos() {
        cmbDatosEstado.setItems(
                FXCollections.observableArrayList(
                        EstadoProyecto.getLista()));

        datosMostrarProyecto();
    }

//	@FXML private Button btnDatosGuardar;
//	@FXML void guardarProyecto(ActionEvent event) {
//	    if (proyectoSeleccion != null) {
//	        proyectoSeleccion.setNombre(txtDatosNombre.getText());
//	        proyectoSeleccion.setEstado(cmbDatosEstado.getSelectionModel().getSelectedItem());
//	        proyectoSeleccion.setFechaPresentacion(dtpDatosPresentacion.getValue());
//	        proyectoSeleccion.setFechaAprobacion(dtpDatosAprobacion.getValue());
//	        proyectoSeleccion.setFechaInicio(dtpDatosInicio.getValue());
//	        proyectoSeleccion.setFechaFin(dtpDatosFinalizacion.getValue());
//
//	        exitoGuardado(controlInvestigacion.guardarProyecto(proyectoSeleccion), TITULO, "Guardar Proyecto");
//	        generalMostrarProyecto();
//
//	        modoModificar();
//	    }
//	}
//
//	@FXML private Button btnDatosDescartar;
//    @FXML void descartarProyecto(ActionEvent event) {
//        datosMostrarProyecto();
//    }

    @FXML private TextField txtDatosDirector;
    @FXML private Button btnDatosDirector;
	@FXML void buscarDirector(ActionEvent event) {
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, true);
        args.put(Busqueda.KEY_TIPO, Docentes.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_DIRECTOR);
        args.put(GestorPantalla.KEY_PADRE, Proyectos.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Docentes.TITULO, args);
	}

	@FXML private TextField txtDatosCodirector;
	@FXML private Button btnDatosCodirector;
	@FXML void buscarCodirector(ActionEvent event) {
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, true);
        args.put(Busqueda.KEY_TIPO, Docentes.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_CODIRECTOR);
        args.put(GestorPantalla.KEY_PADRE, Proyectos.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Docentes.TITULO, args);
	}

    @FXML private TextField txtDatosNombre;
    @FXML private ComboBox<EstadoProyecto> cmbDatosEstado;
    @FXML private TextField txtDatosDisp;
    @FXML private DatePicker dtpDatosPresentacion;
    @FXML private DatePicker dtpDatosAprobacion;
    @FXML private DatePicker dtpDatosInicio;
    @FXML private DatePicker dtpDatosFinalizacion;

// -------------------------- Pestaña Integrantes --------------------------- //

	private IIntegrante integranteSeleccion = null;
	protected ObservableList<FilaIntegrante> filasIntegrantes = FXCollections.observableArrayList();

	public class FilaIntegrante {
        private IIntegrante integrante;

        public FilaIntegrante(IIntegrante integrante) {
            this.integrante = integrante;
        }

        public String getApellido() {
            return this.integrante.getApellido();
        }

        public String getNombre() {
            return this.integrante.getNombre();
        }

        public String getCargo() {
            return this.integrante.getCargo();
        }

        public String getInstitucion() {
            return this.integrante.getInstitucion();
        }

        public int getHoras() {
            return this.integrante.getHorasSemanales();
        }

        public IIntegrante getInstanciaIntegrante() {
            return this.integrante;
        }
    }

	/** Refresca la tabla de integrantes */
    private void integrantesActualizarTabla() {
        filasIntegrantes.clear();
        if (proyectoSeleccion != null) {
            for (IIntegrante integrante : proyectoSeleccion.getIntegrantes()) {
                filasIntegrantes.add(new FilaIntegrante(integrante));
            }
        }
    }

    /** Muestra los datos del integrante seleccionado: */
    private void integrantesMostrarIntegrante() {
        if (integranteSeleccion != null) {
            txtIntegrantesApellido.setText(integranteSeleccion.getApellido());
            txtIntegrantesNombre.setText(integranteSeleccion.getNombre());
            txtIntegrantesCargo.setText(integranteSeleccion.getCargo());
            txtIntegrantesInstitucion.setText(integranteSeleccion.getInstitucion());
            txtIntegrantesHoras.setText(String.valueOf(integranteSeleccion.getHorasSemanales()));
        }
    }

	/** Vacía los controles de datos del integrante */
	private void integrantesVaciarControles() {
	    txtIntegrantesApellido.clear();
	    txtIntegrantesNombre.clear();
	    txtIntegrantesCargo.clear();
	    txtIntegrantesInstitucion.clear();
	    txtIntegrantesHoras.clear();
	}

	private void integrantesModoModificar() {
	    if (this.permiso.getModificar()) {
	        btnIntegrantesGuardar.setVisible(true);
	        btnIntegrantesDescartar.setVisible(true);
	        btnIntegrantesEliminar.setVisible(true);

	        btnIntegrantesDocente.setVisible(true);
	        txtIntegrantesApellido.setEditable(true);
	        txtIntegrantesNombre.setEditable(true);
	        txtIntegrantesCargo.setEditable(true);
	        txtIntegrantesInstitucion.setEditable(true);
	        txtIntegrantesHoras.setEditable(true);
	    }
	}

	private void integrantesModoNuevo() {
	    if (this.permiso.getModificar()) {
	        integrantesModoModificar();
	        btnIntegrantesEliminar.setVisible(false);
	    }
	}

	private void integrantesModoVer() {
	    btnIntegrantesGuardar.setVisible(false);
        btnIntegrantesDescartar.setVisible(false);
        btnIntegrantesEliminar.setVisible(false);

        btnIntegrantesDocente.setVisible(false);
        txtIntegrantesApellido.setEditable(false);
        txtIntegrantesNombre.setEditable(false);
        txtIntegrantesCargo.setEditable(false);
        txtIntegrantesInstitucion.setEditable(false);
        txtIntegrantesHoras.setEditable(false);
	}

	@FXML void inicializarIntegrantes() {
	    inicializarTabla("Integrantes");
	    tblIntegrantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	            integranteSeleccion = newSelection.getInstanciaIntegrante();
                integrantesMostrarIntegrante();
                integrantesModoModificar();
	        }
	    });

	    integrantesActualizarTabla();
	}

	@FXML private Button btnIntegrantesNuevo;
    @FXML void nuevoIntegrante(ActionEvent event) {
        if (proyectoSeleccion != null) {
            integranteSeleccion = controlInvestigacion.getIIntegrante();
            integrantesVaciarControles();
            integrantesModoNuevo();
        }
    }

    @FXML private Button btnIntegrantesGuardar;
	@FXML void guardarIntegrante(ActionEvent event) {
	    if (proyectoSeleccion != null && integranteSeleccion != null) {
	        integranteSeleccion.setApellido(txtIntegrantesApellido.getText());
	        integranteSeleccion.setNombre(txtIntegrantesNombre.getText());
	        integranteSeleccion.setCargo(txtIntegrantesCargo.getText());
	        integranteSeleccion.setInstitucion(txtIntegrantesInstitucion.getText());
	        integranteSeleccion.setHorasSemanales(Integer.parseInt(txtIntegrantesHoras.getText()));

	        exitoGuardado(controlInvestigacion.guardarIntegrante(proyectoSeleccion, integranteSeleccion), TITULO, "Guardar Integrante");
            integrantesModoModificar();
	        integrantesActualizarTabla();
	    }
	}

	@FXML private Button btnIntegrantesDescartar;
	@FXML void descartarIntegrante(ActionEvent event) {
	    integrantesMostrarIntegrante();
	}

	@FXML private Button btnIntegrantesEliminar;
	@FXML void eliminarIntegrante(ActionEvent event) {
	    if (proyectoSeleccion != null && integranteSeleccion != null) {
	        if (exitoEliminar(controlInvestigacion.quitarIntegrante(proyectoSeleccion, integranteSeleccion), TITULO, "Eliminar Integrante")) {
	            integranteSeleccion = null;
	            integrantesModoVer();
                integrantesVaciarControles();
	        }
	        integrantesActualizarTabla();
	    }
	}

	@FXML private Button btnIntegrantesDocente;
	@FXML void buscarCargoDocente(ActionEvent event) {
	    /* TEST Integrantes: buscarCargoDocente * /
	    if (proyectoSeleccion != null &&
	        integranteSeleccion != null) {

    	    ControlDocente cd = new ControlDocente(this);
    	    List<ICargoDocente> cargosDocentes = cd.listarCargosDocente(null, null);
    	    Random random = new Random();
    	    int index = random.nextInt(cargosDocentes.size());
    	    integranteSeleccion.setCargoDocente(cargosDocentes.get(index));
	    }
	    //*/
	    /* TODO Integrantes: buscarCargoDocente */
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, true);
        args.put(Busqueda.KEY_TIPO, "CargoDocentes");
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_CARGODOCENTE);
        args.put(GestorPantalla.KEY_PADRE, Proyectos.TITULO);
        this.gestorPantalla.lanzarPantalla("BusquedaCargoDocente", args);
	    //*/
	}

	@FXML protected TableView<FilaIntegrante> tblIntegrantes;
    @FXML protected TableColumn<FilaIntegrante, String> colIntegrantesApellido;
    @FXML protected TableColumn<FilaIntegrante, String> colIntegrantesNombre;
    @FXML protected TableColumn<FilaIntegrante, String> colIntegrantesCargo;
    @FXML protected TableColumn<FilaIntegrante, String> colIntegrantesInstitucion;
    @FXML protected TableColumn<FilaIntegrante, Integer> colIntegrantesHoras;

	@FXML private TextField txtIntegrantesApellido;
	@FXML private TextField txtIntegrantesNombre;
	@FXML private TextField txtIntegrantesCargo;
	@FXML private TextField txtIntegrantesInstitucion;
	@FXML private TextField txtIntegrantesHoras;

// --------------------------- Pestaña Subsidios ---------------------------- //

	private ISubsidio subsidioSeleccion = null;
	protected ObservableList<FilaSubsidio> filasSubsidios = FXCollections.observableArrayList();

	public class FilaSubsidio {
	    private ISubsidio subsidio;

        public FilaSubsidio(ISubsidio subsidio) {
            this.subsidio = subsidio;
        }

        public int getAnio() {
            return this.subsidio.getFecha().getValue();
        }

        public float getMonto() {
            return this.subsidio.getMontoTotal();
        }

        public String getObservaciones() {
            return this.subsidio.getObservaciones();
        }

        public ISubsidio getInstanciaSubsidio() {
            return this.subsidio;
        }
    }

	/** Refresca la tabla de subsidios */
    private void subsidiosActualizarTabla() {
        filasSubsidios.clear();
        if (proyectoSeleccion != null) {
            List<ISubsidio> subsidios = proyectoSeleccion.getSubsidios();
            for (ISubsidio subsidio : subsidios) {
                filasSubsidios.add(new FilaSubsidio(subsidio));
            }
        }
    }

    /** Muestra los datos del subsidio seleccionado: */
    private void subsidiosMostrarSubsidio() {
        if (subsidioSeleccion != null) {
            txtSubsidiosAnio.setText(subsidioSeleccion.getFecha().toString());
            txtSubsidiosMonto.setText(String.valueOf(subsidioSeleccion.getMontoTotal()));
            txtSubsidiosDisp.setText(subsidioSeleccion.getDisposicion());
            txtaSubsidiosObservaciones.setText(subsidioSeleccion.getObservaciones());
        }
    }

    /** Vacía los controles de datos del subsidio */
	private void subsidiosVaciarControles() {
	    txtSubsidiosAnio.clear();
        txtSubsidiosMonto.clear();
        txtSubsidiosDisp.clear();
        txtaSubsidiosObservaciones.clear();
	}

	private void subsidiosModoModificar() {
	    if (this.permiso.getModificar()) {
            btnSubsidiosDescartar.setVisible(true);
            btnSubsidiosEliminar.setVisible(true);
            btnSubsidiosGuardar.setVisible(true);
            txtSubsidiosAnio.setEditable(true);
            txtSubsidiosDisp.setEditable(true);
            txtSubsidiosMonto.setEditable(true);
            txtaSubsidiosObservaciones.setEditable(true);
	    }
	}

	private void subsidiosModoNuevo() {
	    if (this.permiso.getModificar()) {
	        subsidiosModoModificar();
	        btnSubsidiosEliminar.setVisible(false);
	    }
	}

	private void subsidiosModoVer() {
        btnSubsidiosDescartar.setVisible(false);
        btnSubsidiosEliminar.setVisible(false);
        btnSubsidiosGuardar.setVisible(false);
        txtSubsidiosAnio.setEditable(false);
        txtSubsidiosDisp.setEditable(false);
        txtSubsidiosMonto.setEditable(false);
        txtaSubsidiosObservaciones.setEditable(false);
	}

	@FXML void inicializarSubsidios() {
	    inicializarTabla("Subsidios");
	    tblSubsidios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                subsidioSeleccion = newSelection.getInstanciaSubsidio();
                subsidiosMostrarSubsidio();
                subsidiosModoModificar();
            }
        });

	    subsidiosActualizarTabla();
	}

	@FXML private Button btnSubsidiosNuevo;
	@FXML void nuevoSubsidio(ActionEvent event) {
	    if (proyectoSeleccion != null) {
	        subsidioSeleccion = controlInvestigacion.getISubsidio();
	        subsidiosVaciarControles();
	        subsidiosModoNuevo();
	    }
	}

	@FXML private Button btnSubsidiosGuardar;
	@FXML void guardarSubsidio(ActionEvent event) {
	    if (proyectoSeleccion != null && subsidioSeleccion != null) {
	        subsidioSeleccion.setFecha(Year.of(Integer.parseInt(txtSubsidiosAnio.getText())));
	        subsidioSeleccion.setMontoTotal(Float.parseFloat(txtSubsidiosMonto.getText()));
	        subsidioSeleccion.setDisposicion(txtSubsidiosDisp.getText());
	        subsidioSeleccion.setObservaciones(txtaSubsidiosObservaciones.getText());

	        exitoGuardado(controlInvestigacion.guardarSubsidio(proyectoSeleccion, subsidioSeleccion), TITULO, "Guardar Subsidio");
	        subsidiosModoModificar();
	        subsidiosActualizarTabla();
	    }
    }

	@FXML private Button btnSubsidiosDescartar;
	@FXML void descartarSubsidio(ActionEvent event) {
	    subsidiosMostrarSubsidio();
    }

	@FXML private Button btnSubsidiosEliminar;
	@FXML void eliminarSubsidio(ActionEvent event) {
	    if (proyectoSeleccion != null && subsidioSeleccion != null) {
	        if (exitoEliminar(controlInvestigacion.quitarSubsidio(proyectoSeleccion, subsidioSeleccion), TITULO, "Eliminar Subsidio")) {
	            subsidioSeleccion = null;
	            subsidiosModoVer();
                subsidiosVaciarControles();
	        }
            subsidiosActualizarTabla();
        }
    }

	@FXML protected TableView<FilaSubsidio> tblSubsidios;
	@FXML protected TableColumn<FilaSubsidio, Integer> colSubsidiosAnio;
	@FXML protected TableColumn<FilaSubsidio, Float> colSubsidiosMonto;
	@FXML protected TableColumn<FilaSubsidio, String> colSubsidiosObservaciones;

	@FXML private TextField txtSubsidiosAnio;
	@FXML private TextField txtSubsidiosMonto;
	@FXML private TextField txtSubsidiosDisp;
	@FXML private TextArea txtaSubsidiosObservaciones;

// -------------------------- Pestaña Rendiciones --------------------------- //
	@FXML private ComboBox<Year> cmbRendicionesAnio;

	private IRendicion rendicionSeleccion = null;
	protected ObservableList<FilaRendicion> filasRendiciones = FXCollections.observableArrayList();

	public class FilaRendicion {
	    private IRendicion rendicion;

	    public FilaRendicion(IRendicion rendicion) {
            this.rendicion = rendicion;
        }

        public LocalDate getFecha() {
            return this.rendicion.getFecha();
        }

        public float getMonto() {
            return this.rendicion.getMonto();
        }

        public String getObservaciones() {
            return this.rendicion.getObservaciones();
        }

        public IRendicion getInstanciaRendicion() {
            return this.rendicion;
        }
    }

	/** Refresca la tabla de rendiciones */
    private void rendicionesActualizarTabla() {
        filasRendiciones.clear();
        if (proyectoSeleccion != null && subsidioSeleccion != null) {
            for (IRendicion rendicion : subsidioSeleccion.getRendiciones()) {
                filasRendiciones.add(new FilaRendicion(rendicion));
            }
        }
    }

	/** Muestra los datos de la rendición seleccionada: */
    private void rendicionesMostrarRendicion() {
        if (rendicionSeleccion != null) {
            cmbRendicionesAnio.getSelectionModel().select(subsidioSeleccion.getFecha());
            dtpRendicionesFecha.setValue(rendicionSeleccion.getFecha());
            txtRendicionesMonto.setText(String.valueOf(rendicionSeleccion.getMonto()));
            txtaRendicionesObservaciones.setText(rendicionSeleccion.getObservaciones());
        }
    }

	/** Vacía los controles de datos de la rendición */
	private void rendicionesVaciarControles() {
	    cmbRendicionesAnio.getSelectionModel().clearSelection();
	    dtpRendicionesFecha.setValue(null);
	    txtRendicionesMonto.clear();
	    txtaRendicionesObservaciones.clear();
	}

	private void rendicionesModoModificar() {
	    if (this.permiso.getCrear()) {
	        btnRendicionesGuardar.setVisible(true);
            btnRendicionesDescartar.setVisible(true);
            btnRendicionesEliminar.setVisible(true);

            cmbRendicionesAnio.setDisable(false);
            dtpRendicionesFecha.setEditable(true);
            txtRendicionesMonto.setEditable(true);
            txtaRendicionesObservaciones.setEditable(true);
	    }
	}

	private void rendicionesModoNuevo() {
	    if (this.permiso.getCrear()) {
	        rendicionesModoModificar();
	        btnRendicionesEliminar.setVisible(false);
	    }
	}

	private void rendicionesModoVer() {
	    btnRendicionesGuardar.setVisible(false);
        btnRendicionesDescartar.setVisible(false);
        btnRendicionesEliminar.setVisible(false);

        cmbRendicionesAnio.setDisable(true);
        dtpRendicionesFecha.setEditable(false);
        txtRendicionesMonto.setEditable(false);
        txtaRendicionesObservaciones.setEditable(false);
	}

	@FXML void inicializarRendiciones() {
	    inicializarTabla("Rendiciones");
	    tblRendiciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                rendicionSeleccion = newSelection.getInstanciaRendicion();
                rendicionesMostrarRendicion();
                rendicionesModoModificar();
            }
        });

	    if (proyectoSeleccion != null) {
	        cmbRendicionesAnio.setItems(
	            FXCollections.observableArrayList(
	                controlInvestigacion.fechasSubsidios(proyectoSeleccion)));
	    }

	    rendicionesActualizarTabla();
	}

	@FXML private Button btnRendicionesNueva;
	@FXML void nuevaRendicion(ActionEvent event) {
	    if (proyectoSeleccion != null && subsidioSeleccion != null) {
	        rendicionSeleccion = controlInvestigacion.getIRendicion();
	        rendicionesVaciarControles();
	        rendicionesModoNuevo();
	    }
	}

	@FXML private Button btnRendicionesGuardar;
	@FXML void guardarRendicion(ActionEvent event) {
	    if (proyectoSeleccion != null && subsidioSeleccion != null && rendicionSeleccion != null) {
	        try {
	            LocalDate fecha = dtpRendicionesFecha.getValue();
	            if (!subsidioSeleccion.getFecha().equals(Year.of(fecha.getYear()))) {
	                throw new IllegalArgumentException("El año no coincide con el subsidio seleccionado");
	            }

	            float monto = Float.parseFloat(txtRendicionesMonto.getText());

    	        rendicionSeleccion.setFecha(fecha);
    	        rendicionSeleccion.setMonto(monto);
    	        rendicionSeleccion.setObservaciones(txtaRendicionesObservaciones.getText());

    	        exitoGuardado(controlInvestigacion.guardarRendicion(proyectoSeleccion, subsidioSeleccion, rendicionSeleccion), TITULO, "Guardar Rendición");
    	        rendicionesModoModificar();
                rendicionesActualizarTabla();

	        } catch (NumberFormatException e) {
	            alertaError(TITULO, "Guardar Rendición", "El monto ingresado no es numérico.");
	        } catch (IllegalArgumentException e) {
	            alertaError(TITULO, "Guardar Rendición", e.getMessage());
	        }
	    }
	}

	@FXML private Button btnRendicionesDescartar;
	@FXML void descartarRendicion(ActionEvent event) {
	    rendicionesMostrarRendicion();
	}

    @FXML private Button btnRendicionesEliminar;
    @FXML void eliminarRendicion(ActionEvent event) {
        if (proyectoSeleccion != null && subsidioSeleccion != null && rendicionSeleccion != null) {
            if (exitoEliminar(controlInvestigacion.quitarRendicion(proyectoSeleccion, subsidioSeleccion, rendicionSeleccion), TITULO, "Eliminar Rendición")) {
                rendicionSeleccion = null;
                rendicionesModoVer();
                rendicionesVaciarControles();
            }
            rendicionesActualizarTabla();
        }
    }

	@FXML protected TableView<FilaRendicion> tblRendiciones;
	@FXML protected TableColumn<FilaRendicion, LocalDate> colRendicionesFecha;
	@FXML protected TableColumn<FilaRendicion, Float> colRendicionesMonto;
	@FXML protected TableColumn<FilaRendicion, String> colRendicionesObservaciones;

	@FXML private DatePicker dtpRendicionesFecha;
	@FXML private TextField txtRendicionesMonto;
	@FXML private TextArea txtaRendicionesObservaciones;

// --------------------------- Pestaña Prórrogas ---------------------------- //

	private IProrroga prorrogaSeleccion = null;
	protected ObservableList<FilaProrroga> filasProrrogas = FXCollections.observableArrayList();

	public class FilaProrroga {
        private IProrroga prorroga;

        public FilaProrroga(IProrroga prorroga) {
            this.prorroga = prorroga;
        }

        public LocalDate getFinalizacion() {
            return this.prorroga.getFechaFin();
        }

        public IProrroga getInstanciaProrroga() {
            return this.prorroga;
        }
    }

	private void prorrogasActualizarTabla() {
	    filasProrrogas.clear();
        if (proyectoSeleccion != null) {
            for (IProrroga prorroga : proyectoSeleccion.getProrrogas()) {
                filasProrrogas.add(new FilaProrroga(prorroga));
            }
        }
	}

	private void prorrogasMostrarProrroga() {
        if (prorrogaSeleccion != null) {
            dtpProrrogasFinalizacion.setValue(prorrogaSeleccion.getFechaFin());
            txtProrrogasDisp.setText(prorrogaSeleccion.getDisposicion());
        }
    }

    private void prorrogasVaciarControles() {
        dtpProrrogasFinalizacion.setValue(null);
        txtProrrogasDisp.clear();
    }

    private void prorrogasModoModificar() {
        if (this.permiso.getModificar()) {
            btnProrrogasGuardar.setVisible(true);
            btnProrrogasDescartar.setVisible(true);
            btnProrrogasEliminar.setVisible(true);

            dtpProrrogasFinalizacion.setEditable(true);
            txtProrrogasDisp.setEditable(true);
        }
    }

    private void prorrogasModoNuevo() {
        if (this.permiso.getModificar()) {
            prorrogasModoModificar();
            btnProrrogasEliminar.setVisible(false);
        }
    }

    private void prorrogasModoVer() {
        btnProrrogasGuardar.setVisible(false);
        btnProrrogasDescartar.setVisible(false);
        btnProrrogasEliminar.setVisible(false);

        dtpProrrogasFinalizacion.setEditable(false);
        txtProrrogasDisp.setEditable(false);
    }

    @FXML void inicializarProrrogas() {
        inicializarTabla("Prorrogas");
        tblProrrogas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                prorrogaSeleccion = newSelection.getInstanciaProrroga();
                prorrogasMostrarProrroga();
                prorrogasModoModificar();
            }
        });

        prorrogasActualizarTabla();
    }

	@FXML private Button btnProrrogasNueva;
    @FXML void nuevaProrroga(ActionEvent event) {
        if (proyectoSeleccion != null) {
            prorrogaSeleccion = controlInvestigacion.getIProrroga();
            prorrogasVaciarControles();
            prorrogasModoNuevo();
        }
    }

    @FXML private Button btnProrrogasGuardar;
    @FXML void guardarProrroga(ActionEvent event) {
        if (proyectoSeleccion != null && prorrogaSeleccion != null) {
            prorrogaSeleccion.setFechaFin(dtpProrrogasFinalizacion.getValue());
            prorrogaSeleccion.setDisposicion(txtProrrogasDisp.getText());

            exitoGuardado(controlInvestigacion.guardarProrroga(proyectoSeleccion, prorrogaSeleccion), TITULO, "Guardar Prórroga");
            prorrogasModoModificar();
            prorrogasActualizarTabla();
        }
    }

    @FXML private Button btnProrrogasDescartar;
    @FXML void descartarProrroga(ActionEvent event) {
        prorrogasMostrarProrroga();
    }

    @FXML private Button btnProrrogasEliminar;
    @FXML void eliminarProrroga(ActionEvent event) {
        if (proyectoSeleccion != null && prorrogaSeleccion != null) {
            if (exitoEliminar(controlInvestigacion.quitarProrroga(proyectoSeleccion, prorrogaSeleccion), TITULO, "Eliminar Prórroga")) {
                prorrogaSeleccion = null;
                prorrogasModoVer();
                prorrogasVaciarControles();
            }
            prorrogasActualizarTabla();
        }
    }

	@FXML protected TableView<FilaProrroga> tblProrrogas;
	@FXML protected TableColumn<FilaProrroga, LocalDate> colProrrogasFinalizacion;

	@FXML private DatePicker dtpProrrogasFinalizacion;
	@FXML private TextField txtProrrogasDisp;

// ---------------------------- Pestaña Resumen ----------------------------- //

	@FXML private void inicializarResumen() {
	    resumenMostrarResumen();
	}

	private void resumenMostrarResumen() {
	    if (proyectoSeleccion != null) {
            txtaResumen.setText(proyectoSeleccion.getResumen());
        }
	}

	private void resumenVaciarControles() {
	    txtaResumen.clear();
	}

//    @FXML private Button btnResumenGuardar;
//    @FXML void guardarResumen(ActionEvent event) {
//        if (proyectoSeleccion != null) {
//            proyectoSeleccion.setResumen(txtaResumen.getText());
//
//            exitoGuardado(controlInvestigacion.guardarProyecto(proyectoSeleccion), TITULO, "Guardar Resumen");
//        }
//    }
//
//    @FXML private Button btnResumenDescartar;
//    @FXML void descartarResumen(ActionEvent event) {
//        if (proyectoSeleccion != null) {
//            txtaResumen.setText(proyectoSeleccion.getResumen());
//        } else {
//            resumenVaciarControles();
//        }
//    }

    @FXML private TextArea txtaResumen;
}
