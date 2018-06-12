package vista.controladores;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
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
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoProyecto;
import modelo.docente.IDocente;
import modelo.investigacion.IIntegrante;
import modelo.investigacion.IProrroga;
import modelo.investigacion.IProyecto;
import modelo.investigacion.IRendicion;
import modelo.investigacion.ISubsidio;
import modelo.investigacion.Proyecto;
/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Proyectos extends ControladorVista implements Initializable {

	@FXML private ScrollPane mainPane;

	private static final String TITULO = "Proyectos";
	private ControlInvestigacion controlInvestigacion = new ControlInvestigacion();

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
		// TODO esto no funciona así
		/*
	    try {
        int idProyecto = Integer.parseInt(txtProyectosId.getText());
	        IProyecto proyectoBusqueda = new Proyecto(idProyecto, null, null, null, null, null, null, null, null, null, null, null, null, null);
	        List<IProyecto> proyectos = this.controlInvestigacion.listarProyecto(proyectoBusqueda);

	        switch (proyectos.size()) {
	            case 0:  // No se encontraron proyectos para tal id:
	                alertaError(TITULO, "Búsqueda de Proyectos", "No se encontró el proyecto para el id indicado.");
	                break;
	            case 1:  // Se encontró el proyecto:
	                proyectoSeleccion = proyectos.get(0);
	                // Cambiar a pestaña datos:
	                tabpaneProyectos.getSelectionModel().select(0);
	                datosMostrarProyecto();
	                break;
	            default: // Se encontró más de un proyecto (Error):
	                throw new RuntimeException("Se encontró más de un proyecto para un id");
	        }

	    } catch (NumberFormatException nfe) {
	        nfe.printStackTrace();
	        alertaError("Proyectos", "Error Id de proyecto", "El id ingresado no es válido");
	    }
	    */
	}

	@FXML private Button btnProyectosNuevo;
	@FXML void nuevoProyecto(ActionEvent event) {
	    this.proyectoSeleccion = controlInvestigacion.getIProyecto();
        generalVaciarControles();
	}

	@FXML private Button btnProyectosEliminar;
    @FXML void eliminarProyecto(ActionEvent event) {
        if (proyectoSeleccion != null) {
            EstadoOperacion resultado = controlInvestigacion.eliminarProyecto(proyectoSeleccion);
            switch (resultado.getEstado()) {
                case DELETE_ERROR:
                    alertaError(TITULO, "Eliminar Proyecto", resultado.getMensaje());
                    break;
                case DELETE_OK:
                    proyectoSeleccion = null;
                    generalVaciarControles();
                    dialogoConfirmacion(TITULO, "Eliminar Proyecto", resultado.getMensaje());
                    break;
                default:
                    throw new RuntimeException("Estado de modificación no esperado: "
                        + resultado.getEstado().toString() + ": " + resultado.getMensaje());
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

	        EstadoOperacion resultado = controlInvestigacion.guardarProyecto(proyectoSeleccion, null);
	        switch (resultado.getEstado()) {
	            case INSERT_ERROR:
	            case UPDATE_ERROR:
	                alertaError(TITULO, "Guardar Proyecto", resultado.getMensaje());
	            case INSERT_OK:
                case UPDATE_OK:
                    dialogoConfirmacion(TITULO, "Guardar Proyecto", resultado.getMensaje());
                default:
                    throw new RuntimeException("Estado de modificación no esperado: "
                        + resultado.getEstado().toString() + ": " + resultado.getMensaje());
	        }
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
	}

	@FXML private TextField txtDatosCodirector;
	@FXML private Button btnDatosCodirector;
	@FXML void buscarCodirector(ActionEvent event) {
	    // TODO codirectorSeleccionado = ???
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

	        EstadoOperacion resultado = controlInvestigacion.guardarIntegrante(proyectoSeleccion, integranteSeleccion);
            switch (resultado.getEstado()) {
                case INSERT_ERROR:
                case UPDATE_ERROR:
                    alertaError(TITULO, "Guardar Integrante", resultado.getMensaje());
                    break;
                case INSERT_OK:
                case UPDATE_OK:
                    dialogoConfirmacion(TITULO, "Guardar Integrante", resultado.getMensaje());
                    break;
                default:
                    throw new RuntimeException("Estado de modificación no esperado: "
                            + resultado.getEstado().toString() + ": " + resultado.getMensaje());
            }
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
	        EstadoOperacion resultado = controlInvestigacion.quitarIntegrante(proyectoSeleccion, integranteSeleccion);
	        switch (resultado.getEstado()) {
                case DELETE_ERROR:
                    alertaError(TITULO, "Eliminar Integrante", resultado.getMensaje());
                    break;
                case DELETE_OK:
                    integranteSeleccion = null;
                    integrantesVaciarControles();
                    dialogoConfirmacion(TITULO, "Eliminar Integrante", resultado.getMensaje());
                    break;
                default:
                    throw new RuntimeException("Estado de eliminación no esperado: "
                        + resultado.getEstado().toString() + ": " + resultado.getMensaje());
	        }
	        integrantesActualizarTabla();
	    }
	}

	@FXML private Button btnIntegrantesDocente;
	@FXML void buscarCargoDocente(ActionEvent event) {
	    // TODO Integrantes: Seleccionar cargoDocente
	}

	@FXML private TableView<FilaIntegrante> tblIntegrantes;
    @FXML private TableColumn<FilaIntegrante, String> colIntegrantesApellido;
    @FXML private TableColumn<FilaIntegrante, String> colIntegrantesNombre;
    @FXML private TableColumn<FilaIntegrante, String> colIntegrantesCargo;
    @FXML private TableColumn<FilaIntegrante, String> colIntegrantesInstitucion;
    @FXML private TableColumn<FilaIntegrante, Integer> colIntegrantesHoras;

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

	        EstadoOperacion resultado = controlInvestigacion.guardarSubsidio(proyectoSeleccion, subsidioSeleccion);
	        switch (resultado.getEstado()) {
	            case INSERT_ERROR:
                case UPDATE_ERROR:
                    alertaError(TITULO, "Guardar Subsidio", resultado.getMensaje());
                    break;
                case INSERT_OK:
                case UPDATE_OK:
                    dialogoConfirmacion(TITULO, "Guardar Subsidio", resultado.getMensaje());
                    break;
                default:
                    throw new RuntimeException("Estado de modificación no esperado: "
                            + resultado.getEstado().toString() + ": " + resultado.getMensaje());
	        }

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
            EstadoOperacion resultado = controlInvestigacion.quitarSubsidio(proyectoSeleccion, subsidioSeleccion);
            switch (resultado.getEstado()) {
                case DELETE_ERROR:
                    alertaError(TITULO, "Eliminar Subsidio", resultado.getMensaje());
                    break;
                case DELETE_OK:
                    subsidioSeleccion = null;
                    subsidiosVaciarControles();
                    dialogoConfirmacion(TITULO, "Eliminar Subsidio", resultado.getMensaje());
                    break;
                default:
                    throw new RuntimeException("Estado de eliminación no esperado: "
                        + resultado.getEstado().toString() + ": " + resultado.getMensaje());
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
	@FXML private ComboBox cmbRendicionesAnio;  
	
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
            dtpRendicionesFecha.setValue(rendicionSeleccion.getFecha());
            txtRendicionesMonto.setText(String.valueOf(rendicionSeleccion.getMonto()));
            txtaRendicionesObservaciones.setText(rendicionSeleccion.getObservaciones());
        }
    }

	/** Vacía los controles de datos de la rendición */
	private void rendicionesVaciarControles() {
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

    	        EstadoOperacion resultado = controlInvestigacion.guardarRendicion(proyectoSeleccion, subsidioSeleccion, rendicionSeleccion);
    	        switch (resultado.getEstado()) {
                    case INSERT_ERROR:
                    case UPDATE_ERROR:
                        alertaError(TITULO, "Guardar Rendición", resultado.getMensaje());
                        break;
                    case INSERT_OK:
                    case UPDATE_OK:
                        dialogoConfirmacion(TITULO, "Guardar Rendición", resultado.getMensaje());
                        break;
                    default:
                        throw new RuntimeException("Estado de modificación no esperado: "
                                + resultado.getEstado().toString() + ": " + resultado.getMensaje());
                }

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
            EstadoOperacion resultado = controlInvestigacion.quitarRendicion(proyectoSeleccion, subsidioSeleccion, rendicionSeleccion);
            switch (resultado.getEstado()) {
                case DELETE_ERROR:
                    alertaError(TITULO, "Eliminar Rendición", resultado.getMensaje());
                    break;
                case DELETE_OK:
                    rendicionSeleccion = null;
                    rendicionesVaciarControles();
                    dialogoConfirmacion(TITULO, "Eliminar Rendición", resultado.getMensaje());
                    break;
                default:
                    throw new RuntimeException("Estado de eliminación no esperado: "
                        + resultado.getEstado().toString() + ": " + resultado.getMensaje());
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

            EstadoOperacion resultado = controlInvestigacion.guardarProrroga(proyectoSeleccion, prorrogaSeleccion);
            switch (resultado.getEstado()) {
                case INSERT_ERROR:
                case UPDATE_ERROR:
                    alertaError(TITULO, "Guardar Prórroga", resultado.getMensaje());
                    break;
                case INSERT_OK:
                case UPDATE_OK:
                    dialogoConfirmacion(TITULO, "Guardar Prórroga", resultado.getMensaje());
                    break;
                default:
                    throw new RuntimeException("Estado de modificación no esperado: "
                            + resultado.getEstado().toString() + ": " + resultado.getMensaje());
            }

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
            EstadoOperacion resultado = controlInvestigacion.quitarProrroga(proyectoSeleccion, prorrogaSeleccion);
            switch (resultado.getEstado()) {
                case DELETE_ERROR:
                    alertaError(TITULO, "Eliminar Prórroga", resultado.getMensaje());
                    break;
                case DELETE_OK:
                    prorrogaSeleccion = null;
                    prorrogasVaciarControles();
                    dialogoConfirmacion(TITULO, "Eliminar Prórroga", resultado.getMensaje());
                    break;
                default:
                    throw new RuntimeException("Estado de eliminación no esperado: "
                        + resultado.getEstado().toString() + ": " + resultado.getMensaje());
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

            EstadoOperacion resultado = controlInvestigacion.guardarProyecto(proyectoSeleccion, null);
            switch (resultado.getEstado()) {
                case INSERT_ERROR:
                case UPDATE_ERROR:
                    alertaError(TITULO, "Guardar Resumen", resultado.getMensaje());
                case INSERT_OK:
                case UPDATE_OK:
                    dialogoConfirmacion(TITULO, "Guardar Resumen", resultado.getMensaje());
                default:
                    throw new RuntimeException("Estado de modificación no esperado: "
                        + resultado.getEstado().toString() + ": " + resultado.getMensaje());
            }
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
