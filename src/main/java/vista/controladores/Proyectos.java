package vista.controladores;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.HashMap;
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
import javafx.scene.control.TabPane;
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
import vista.GestorPantalla;
/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Proyectos extends ControladorVista implements Initializable {

    public void setProyectoSeleccion(Object proyecto, String tipo) {
        if (proyecto instanceof IProyecto) {
            proyectoSeleccion = (IProyecto) proyecto;
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
            }
        }
    }

    private void setCodirectorSeleccion(Object docenteSeleccion) {
        if (docenteSeleccion instanceof IDocente) {
            codirectorSeleccion = (IDocente) docenteSeleccion;
            txtDatosCodirector.setText(String.valueOf(codirectorSeleccion.getLegajo()));
        }
    }

    private void setDirectorSeleccion(Object docenteSeleccion) {
        if (docenteSeleccion instanceof IDocente) {
            directorSeleccion = (IDocente) docenteSeleccion;
            txtDatosDirector.setText(String.valueOf(directorSeleccion.getLegajo()));
        }
    }

    public void setCargoDocenteSeleccion(Object cargoDocente, String tipo) {
        if (cargoDocente instanceof ICargoDocente) {
            cargoDocenteSeleccion = (ICargoDocente) cargoDocente;
        }
    }

	@FXML private ScrollPane mainPane;

	private static final String TITULO = "Proyectos";
	private ControlInvestigacion controlInvestigacion = new ControlInvestigacion();

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

// -------------------------------- General --------------------------------- //

	private IProyecto proyectoSeleccion = null;

	@FXML private TabPane tabpaneProyectos;
//	@FXML private TextField txtProyectosId;
	@FXML private TextField txtProyectosNombre;

	/** Muestra los datos del proyecto seleccionado en los controles generales */
    private void generalMostrarProyecto() {
        if (proyectoSeleccion != null) {
//            txtProyectosId.setText(String.valueOf(proyectoSeleccion.getId()));
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
//        txtProyectosId.clear();
        txtProyectosNombre.clear();

        // Vaciar controles de las pestañas:
        vaciarTablas();
        datosVaciarControles();
        subsidiosVaciarControles();
        rendicionesVaciarControles();
        prorrogasVaciarControles();
        resumenVaciarControles();
    }

	@FXML private Button btnProyectosBuscar;
	@FXML void buscarProyecto(ActionEvent event) {
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, false);
        args.put(Busqueda.KEY_TIPO, Proyectos.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(GestorPantalla.KEY_PADRE, Proyectos.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Proyectos.TITULO, args);
	}

	@FXML private Button btnProyectosNuevo;
	@FXML void nuevoProyecto(ActionEvent event) {
	    this.proyectoSeleccion = controlInvestigacion.getIProyecto();
        generalVaciarControles();
	}

	@FXML private Button btnProyectosEliminar;
    @FXML void eliminarProyecto(ActionEvent event) {
        if (proyectoSeleccion != null) {
            if (exitoEliminar(controlInvestigacion.eliminarProyecto(proyectoSeleccion), TITULO, "Eliminar Proyecto")) {
                proyectoSeleccion = null;
                generalVaciarControles();
            }
        }
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

    private IDocente directorSeleccion = null;
    private IDocente codirectorSeleccion = null;

    /** Muestra los datos del proyecto seleccionado */
    private void datosMostrarProyecto() {
        if (proyectoSeleccion != null) {
            txtDatosNombre.setText(proyectoSeleccion.getNombre());
            txtDatosDirector.setText(proyectoSeleccion.getDirector().getPersona().getNombreCompleto());
            txtDatosCodirector.setText(proyectoSeleccion.getCodirector().getPersona().getNombreCompleto());
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
        directorSeleccion = null;
        txtDatosCodirector.clear();
        codirectorSeleccion = null;
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

	@FXML private Button btnDatosGuardar;
	@FXML void guardarProyecto(ActionEvent event) {
	    if (proyectoSeleccion != null) {
	        proyectoSeleccion.setNombre(txtDatosNombre.getText());
	        proyectoSeleccion.setDirector(directorSeleccion);
	        proyectoSeleccion.setCodirector(codirectorSeleccion);
	        proyectoSeleccion.setEstado(cmbDatosEstado.getSelectionModel().getSelectedItem());
	        proyectoSeleccion.setFechaPresentacion(dtpDatosPresentacion.getValue());
	        proyectoSeleccion.setFechaAprobacion(dtpDatosAprobacion.getValue());
	        proyectoSeleccion.setFechaInicio(dtpDatosInicio.getValue());
	        proyectoSeleccion.setFechaFin(dtpDatosFinalizacion.getValue());

	        exitoGuardado(controlInvestigacion.guardarProyecto(proyectoSeleccion, null), TITULO, "Guardar Proyecto");
	    }
	}

	@FXML private Button btnDatosDescartar;
    @FXML void descartarProyecto(ActionEvent event) {
        datosMostrarProyecto();
    }

    @FXML private TextField txtDatosDirector;
    @FXML private Button btnDatosDirector;
	@FXML void buscarDirector(ActionEvent event) {
	    // TODO directorSeleccion = ???
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, true);
        args.put(Busqueda.KEY_TIPO, Docentes.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(GestorPantalla.KEY_PADRE, Proyectos.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Docentes.TITULO, args);
	}

	@FXML private TextField txtDatosCodirector;
	@FXML private Button btnDatosCodirector;
	@FXML void buscarCodirector(ActionEvent event) {
	    // TODO codirectorSeleccionado = ???
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, true);
        args.put(Busqueda.KEY_TIPO, Docentes.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
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
	private ICargoDocente cargoDocenteSeleccion = null;
	private ObservableList<FilaIntegrante> filasIntegrantes = FXCollections.observableArrayList();

	class FilaIntegrante {
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

        public String getUnidad() {
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

	@FXML void inicializarIntegrantes() {
	    inicializarTabla("Integrantes");
	    tblIntegrantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	            integranteSeleccion = newSelection.getInstanciaIntegrante();
                integrantesMostrarIntegrante();
	        }
	    });

	    integrantesActualizarTabla();
	}

	@FXML private Button btnIntegrantesNuevo;
    @FXML void nuevoIntegrante(ActionEvent event) {
        if (proyectoSeleccion != null) {
            integranteSeleccion = controlInvestigacion.getIIntegrante();
            integrantesVaciarControles();
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
            integrantesActualizarTabla();
	    }
	}

	@FXML private Button btnIntegrantesDescartar;
	@FXML void descartarIntegrante(ActionEvent event) {
	    integranteSeleccion = null;
	    integrantesVaciarControles();
	}

	@FXML private Button btnIntegrantesEliminar;
	@FXML void eliminarIntegrante(ActionEvent event) {
	    if (proyectoSeleccion != null && integranteSeleccion != null) {
	        if (exitoEliminar(controlInvestigacion.quitarIntegrante(proyectoSeleccion, integranteSeleccion), TITULO, "Eliminar Integrante")) {
	            integranteSeleccion = null;
                integrantesVaciarControles();
	        }
	        integrantesActualizarTabla();
	    }
	}

	@FXML private Button btnIntegrantesDocente;
	@FXML void buscarCargoDocente(ActionEvent event) {
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, true);
        args.put(Busqueda.KEY_TIPO, "CargoDocentes");
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_CARGODOCENTE);
        args.put(GestorPantalla.KEY_PADRE, Proyectos.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + "Cargos Docentes", args);
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

        public int getFecha() {
            return this.subsidio.getFecha().getValue();
        }

        public float getMontoTotal() {
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
            for (ISubsidio subsidio : proyectoSeleccion.getSubsidios()) {
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

	@FXML void inicializarSubsidios() {
	    inicializarTabla("Subsidios");
	    tblSubsidios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                subsidioSeleccion = newSelection.getInstanciaSubsidio();
                subsidiosMostrarSubsidio();
            }
        });

	    subsidiosActualizarTabla();
	}

	@FXML private Button btnSubsidiosNuevo;
	@FXML void nuevoSubsidio(ActionEvent event) {
	    if (proyectoSeleccion != null) {
	        subsidioSeleccion = controlInvestigacion.getISubsidio();
	        subsidiosVaciarControles();
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
	        subsidiosActualizarTabla();
	    }
    }

	@FXML private Button btnSubsidiosDescartar;
	@FXML void descartarSubsidio(ActionEvent event) {
	    subsidioSeleccion = null;
	    subsidiosVaciarControles();
    }

	@FXML private Button btnSubsidiosEliminar;
	@FXML void eliminarSubsidio(ActionEvent event) {
	    if (proyectoSeleccion != null && subsidioSeleccion != null) {
	        if (exitoEliminar(controlInvestigacion.quitarSubsidio(proyectoSeleccion, subsidioSeleccion), TITULO, "Eliminar Subsidio")) {
	            subsidioSeleccion = null;
                subsidiosVaciarControles();
	        }
            subsidiosActualizarTabla();
        }
    }

	@FXML private TableView<FilaSubsidio> tblSubsidios;
	@FXML private TableColumn<FilaSubsidio, Integer> colSubsidiosAnio;
	@FXML private TableColumn<FilaSubsidio, Float> colSubsidiosMonto;
	@FXML private TableColumn<FilaSubsidio, String> colSubsidiosObservaciones;

	@FXML private TextField txtSubsidiosAnio;
	@FXML private TextField txtSubsidiosMonto;
	@FXML private TextField txtSubsidiosDisp;
	@FXML private TextArea txtaSubsidiosObservaciones;

// -------------------------- Pestaña Rendiciones --------------------------- //
// TODO agregar Año de subsidio a los controles
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

	@FXML void inicializarRendiciones() {
	    inicializarTabla("Rendiciones");
	    tblRendiciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                rendicionSeleccion = newSelection.getInstanciaRendicion();
                rendicionesMostrarRendicion();
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
	    }
	}

	@FXML private Button btnRendicionesGuardar;
	@FXML void guardarRendicion(ActionEvent event) {
	    if (proyectoSeleccion != null && subsidioSeleccion != null && rendicionSeleccion != null) {
	        try {
	            float monto = Float.parseFloat(txtRendicionesMonto.getText());

    	        rendicionSeleccion.setFecha(dtpRendicionesFecha.getValue());
    	        rendicionSeleccion.setMonto(monto);
    	        rendicionSeleccion.setObservaciones(txtaRendicionesObservaciones.getText());

    	        exitoGuardado(controlInvestigacion.guardarRendicion(proyectoSeleccion, subsidioSeleccion, rendicionSeleccion), TITULO, "Guardar Rendición");
                rendicionesActualizarTabla();

	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	            alertaError(TITULO, "Guardar Rendición", "El monto ingresado no es numérico.");
	        }
	    }
	}

	@FXML private Button btnRendicionesDescartar;
	@FXML void descartarRendicion(ActionEvent event) {
	    rendicionSeleccion = null;
	    rendicionesVaciarControles();
	}

    @FXML private Button btnRendicionesEliminar;
    @FXML void eliminarRendicion(ActionEvent event) {
        if (proyectoSeleccion != null && subsidioSeleccion != null && rendicionSeleccion != null) {
            if (exitoEliminar(controlInvestigacion.quitarRendicion(proyectoSeleccion, subsidioSeleccion, rendicionSeleccion), TITULO, "Eliminar Rendición")) {
                rendicionSeleccion = null;
                rendicionesVaciarControles();
            }
            rendicionesActualizarTabla();
        }
    }

	@FXML private TableView<FilaRendicion> tblRendiciones;
	@FXML private TableColumn<FilaRendicion, LocalDate> colRendicionesFecha;
	@FXML private TableColumn<FilaRendicion, Float> colRendicionesMonto;
	@FXML private TableColumn<FilaRendicion, String> colRendicionesObservaciones;

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

        public LocalDate getFechaInicio() {
            // Prorrogas: Fila fecha inicio
            return null;
        }

        public LocalDate getfechaFin() {
            return this.prorroga.getFechaFin();
        }

        public IProrroga getInstanciaProrroga() {
            return this.prorroga;
        }
    }

	private void prorrogasActualizarTabla() {
	    filasProrrogas.clear();
        if (prorrogaSeleccion != null) {
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

    @FXML void inicializarProrrogas() {
        inicializarTabla("Prorrogas");
        tblProrrogas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                prorrogaSeleccion = newSelection.getInstanciaProrroga();
                prorrogasMostrarProrroga();
            }
        });

        prorrogasActualizarTabla();
    }

	@FXML private Button btnProrrogasNueva;
    @FXML void nuevaProrroga(ActionEvent event) {
        if (proyectoSeleccion != null) {
            prorrogaSeleccion = controlInvestigacion.getIProrroga();
            prorrogasVaciarControles();
        }
    }

    @FXML private Button btnProrrogasGuardar;
    @FXML void guardarProrroga(ActionEvent event) {
        if (proyectoSeleccion != null && prorrogaSeleccion != null) {
            // TODO Prorrogas: prorrogaSeleccion.setFechaInicio(dtpProrrogasInicio.getValue());
            prorrogaSeleccion.setFechaFin(dtpProrrogasFinalizacion.getValue());
            prorrogaSeleccion.setDisposicion(txtProrrogasDisp.getText());

            exitoGuardado(controlInvestigacion.guardarProrroga(proyectoSeleccion, prorrogaSeleccion), TITULO, "Guardar Prórroga");
            prorrogasActualizarTabla();
        }
    }

    @FXML private Button btnProrrogasDescartar;
    @FXML void descartarProrroga(ActionEvent event) {
        prorrogaSeleccion = null;
        prorrogasVaciarControles();
    }

    @FXML private Button btnProrrogasEliminar;
    @FXML void eliminarProrroga(ActionEvent event) {
        if (proyectoSeleccion != null && prorrogaSeleccion != null) {
            if (exitoEliminar(controlInvestigacion.quitarProrroga(proyectoSeleccion, prorrogaSeleccion), TITULO, "Eliminar Prórroga")) {
                prorrogaSeleccion = null;
                prorrogasVaciarControles();
            }
            prorrogasActualizarTabla();
        }
    }

	@FXML private TableView<FilaProrroga> tblProrrogas;
//	@FXML private TableColumn<FilaProrroga, LocalDate> colProrrogasInicio;
	@FXML private TableColumn<FilaProrroga, LocalDate> colProrrogasFinalización;

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

    @FXML private Button btnResumenGuardar;
    @FXML void guardarResumen(ActionEvent event) {
        if (proyectoSeleccion != null) {
            proyectoSeleccion.setResumen(txtaResumen.getText());

            exitoGuardado(controlInvestigacion.guardarProyecto(proyectoSeleccion, null), TITULO, "Guardar Resumen");
        }
    }

    @FXML private Button btnResumenDescartar;
    @FXML void descartarResumen(ActionEvent event) {
        if (proyectoSeleccion != null) {
            txtaResumen.setText(proyectoSeleccion.getResumen());
        } else {
            resumenVaciarControles();
        }
    }

    @FXML private TextArea txtaResumen;
}
