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
import modelo.auxiliares.EstadoProyecto;
import modelo.docente.IDocente;
import modelo.investigacion.IIntegrante;
import modelo.investigacion.IProyecto;
import modelo.investigacion.IRendicion;
import modelo.investigacion.ISubsidio;
import modelo.investigacion.Integrante;
import modelo.investigacion.Proyecto;
import modelo.investigacion.Rendicion;
import modelo.investigacion.Subsidio;
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

	private IProyecto proyectoSeleccionado = null;

	@FXML private TabPane tabpaneProyectos;
	@FXML private TextField txtProyectosId;
	@FXML private TextField txtProyectosNombre;

	@FXML private Button btnProyectosBuscar;
	@FXML void buscarProyecto(ActionEvent event) {
	    try {
	        int idProyecto = Integer.parseInt(txtProyectosId.getText());
	        IProyecto proyectoBusqueda = new Proyecto(idProyecto, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
	        List<IProyecto> proyectos = this.controlInvestigacion.listarProyecto(proyectoBusqueda);

	        switch (proyectos.size()) {
	            case 0:  // No se encontraron proyectos para tal id:
	                alertaError(TITULO, "Búsqueda de Proyectos", "No se encontró el proyecto para el id indicado.");
	                break;
	            case 1:  // Se encontró el proyecto:
	                proyectoSeleccionado = proyectos.get(0);
	                // Cambiar a pestaña datos:
	                tabpaneProyectos.getSelectionModel().select(0);
	                llenarCamposDatos();
	                break;
	            default: // Se encontró más de un proyecto (Error):
	                throw new RuntimeException("Se encontró más de un proyecto para un id");
	        }

	    } catch (NumberFormatException nfe) {
	        nfe.printStackTrace();
	        alertaError("Proyectos", "Error Id de proyecto", "El id ingresado no es válido");
	    }
	}

	@FXML private Button btnProyectosNuevo;
	@FXML void nuevoProyecto(ActionEvent event) {
	    // TODO Nuevo Proyecto
	}

	@FXML private Button btnProyectosEliminar;
    @FXML void eliminarProyecto(ActionEvent event) {
        this.controlInvestigacion.eliminarProyecto(proyectoSeleccionado);
        proyectoSeleccionado = null;
    }

// ----------------------------- Pestaña Datos ------------------------------ //

    private IDocente directorSeleccionado = null;
    private IDocente codirectorSeleccionado = null;

    private void llenarCamposDatos() {
        // Coloco los valores del proyecto en los controles:
        txtDatosNombre.setText(proyectoSeleccionado.getNombre());
        txtDatosDirector.setText(proyectoSeleccionado.getDirector().getPersona().getNombre());
        txtDatosCodirector.setText(proyectoSeleccionado.getCodirector().getPersona().getNombre());
        cmbDatosEstado.getSelectionModel().select(proyectoSeleccionado.getEstado());
        // TODO Disposición de proyecto?
        dtpDatosPresentacion.setValue(proyectoSeleccionado.getFechaPresentacion());
        dtpDatosAprobacion.setValue(proyectoSeleccionado.getFechaAprobacion());
        dtpDatosInicio.setValue(proyectoSeleccionado.getFechaInicio());
        dtpDatosFinalizacion.setValue(proyectoSeleccionado.getFechaFin());
    }

    @FXML private void inicializarDatos() {
        cmbDatosEstado.setItems(
                FXCollections.observableArrayList(
                        EstadoProyecto.getLista()));
    }

	@FXML private Button btnDatosGuardar;
	@FXML void guardarProyecto(ActionEvent event) {
	    // Actualizo valores del proyecto seleccionado:
	    proyectoSeleccionado.setNombre(txtDatosNombre.getText());
	    proyectoSeleccionado.setDirector(directorSeleccionado);
	    proyectoSeleccionado.setCodirector(codirectorSeleccionado);
	    proyectoSeleccionado.setEstado(cmbDatosEstado.getSelectionModel().getSelectedItem());
	    // TODO Disposición de proyecto?
	    proyectoSeleccionado.setFechaPresentacion(dtpDatosPresentacion.getValue());
	    proyectoSeleccionado.setFechaAprobacion(dtpDatosAprobacion.getValue());
	    proyectoSeleccionado.setFechaInicio(dtpDatosInicio.getValue());
	    proyectoSeleccionado.setFechaFin(dtpDatosFinalizacion.getValue());

	    this.controlInvestigacion.modificarProyecto(proyectoSeleccionado);
	}

	@FXML private Button btnDatosDescartar;
    @FXML void descartarProyecto(ActionEvent event) {
        llenarCamposDatos();
    }

	@FXML private TextField txtDatosNombre;

	@FXML private TextField txtDatosDirector;
	@FXML private Button btnDatosDirector;
	@FXML void buscarDirector(ActionEvent event) {
	    // TODO directorSeleccionado = ???
	}

	@FXML private TextField txtDatosCodirector;
	@FXML private Button btnDatosCodirector;
	@FXML void buscarCodirector(ActionEvent event) {
	    // TODO codirectorSeleccionado = ???
	}

	@FXML private ComboBox<EstadoProyecto> cmbDatosEstado;

	@FXML private TextField txtDatosDisp;

	@FXML private DatePicker dtpDatosPresentacion;
	@FXML private DatePicker dtpDatosAprobacion;
	@FXML private DatePicker dtpDatosInicio;
	@FXML private DatePicker dtpDatosFinalizacion;


// -------------------------- Pestaña Integrantes --------------------------- //

	private IIntegrante integranteSeleccionado = null;
	private ObservableList<FilaIntegrante> filasIntegrante = FXCollections.observableArrayList();

	private void llenarTablaIntegrantes() {
	    if (proyectoSeleccionado != null) {
	        List<IIntegrante> integrantes = proyectoSeleccionado.getIntegrantes();
	        filasIntegrante.clear();
	        for (IIntegrante integrante : integrantes) {
	            filasIntegrante.add(new FilaIntegrante(integrante));
	        }
	    }
	}

	private void limpiarCamposIntegrantes() {
	    txtIntegrantesApellido.clear();
	    txtIntegrantesNombre.clear();
	    txtIntegrantesCargo.clear();
	    txtIntegrantesInstitucion.clear();
	    txtIntegrantesHoras.clear();
	}

	private void llenarCamposIntegrantes() {
	    txtIntegrantesApellido.setText(integranteSeleccionado.getApellido());
        txtIntegrantesNombre.setText(integranteSeleccionado.getNombre());
        txtIntegrantesCargo.setText(integranteSeleccionado.getCargo());
        txtIntegrantesInstitucion.setText(integranteSeleccionado.getInstitucion());
        txtIntegrantesHoras.setText(String.valueOf(integranteSeleccionado.getHorasSemanales()));
	}

	class FilaIntegrante {
	    private String apellido;
	    private String nombre;
	    private String cargo;
	    private String unidad;
	    private int horas;
	    public FilaIntegrante(IIntegrante integrante) {
	        this.apellido = integrante.getApellido();
	        this.nombre = integrante.getNombre();
	        this.cargo = integrante.getCargo();
	        this.unidad = integrante.getInstitucion();
	        this.horas = integrante.getHorasSemanales();
	    }
	    public String getApellido() {
    	    return this.apellido;
        }
	    public String getNombre() {
    	    return this.nombre;
        }
	    public String getCargo() {
    	    return this.cargo;
        }
	    public String getUnidad() {
    	    return this.unidad;
        }
	    public int getHoras() {
    	    return this.horas;
        }
	}

	@FXML void inicializarIntegrantes() {
	    integranteSeleccionado = null;
	    filasIntegrante.clear();
	    limpiarCamposIntegrantes();

	    if (proyectoSeleccionado != null) {
	        for (IIntegrante integrante : proyectoSeleccionado.getIntegrantes()) {
	            filasIntegrante.add(new FilaIntegrante(integrante));
	        }
	    }
	}

	@FXML private Button btnIntegrantesNuevo;
    @FXML void nuevoIntegrante(ActionEvent event) {
        integranteSeleccionado = new Integrante(0, null, null, null, null, 0, null, 0);
        limpiarCamposIntegrantes();
    }

    @FXML private Button btnIntegrantesGuardar;
	@FXML void guardarIntegrante(ActionEvent event) {
	    // TODO Guardar cambios
	}

	@FXML private Button btnIntegrantesDescartar;
	@FXML void descartarIntegrante(ActionEvent event) {
	    llenarTablaIntegrantes();
	}

	@FXML private Button btnIntegrantesEliminar;
	@FXML void eliminarIntegrante(ActionEvent event) {
	    // TODO Eliminar integrante

	}

	@FXML private TableView<FilaIntegrante> tblIntegrantes;
	@FXML private TableColumn<FilaIntegrante, String> colIntegrantesApellido;
	@FXML private TableColumn<FilaIntegrante, String> colIntegrantesNombre;
	@FXML private TableColumn<FilaIntegrante, String> colIntegrantesCargo;
	@FXML private TableColumn<FilaIntegrante, String> colIntegrantesInstitucion;
	@FXML private TableColumn<FilaIntegrante, Integer> colIntegrantesHoras;

	@FXML private Button btnIntegrantesDocente;
	@FXML void buscarCargoDocente(ActionEvent event) {
	    // TODO Seleccionar docente
	}

	@FXML private TextField txtIntegrantesApellido;
	@FXML private TextField txtIntegrantesNombre;
	@FXML private TextField txtIntegrantesCargo;
	@FXML private TextField txtIntegrantesInstitucion;
	@FXML private TextField txtIntegrantesHoras;

// --------------------------- Pestaña Subsidios ---------------------------- //

	private ISubsidio subsidioSeleccionado;
	private ObservableList<FilaSubsidio> filasSubsidios = FXCollections.observableArrayList();

	private void limpiarCamposSubsidios() {
	    txtSubsidiosAnio.clear();
        txtSubsidiosMonto.clear();
        txtSubsidiosDisp.clear();
        txtaSubsidiosObservaciones.clear();
	}

	private void llenarCamposSubsidios() {
	    if (subsidioSeleccionado != null) {
	        txtSubsidiosAnio.setText(String.valueOf(subsidioSeleccionado.getFecha()));
	        txtSubsidiosMonto.setText(String.valueOf(subsidioSeleccionado.getMontoTotal()));
	        txtSubsidiosDisp.setText(subsidioSeleccionado.getDisposicion());
	        txtaSubsidiosObservaciones.setText(subsidioSeleccionado.getObservaciones());
	    }
	}

	class FilaSubsidio {
	    private int fecha;
	    private float monto;
	    private String observaciones;
	    public FilaSubsidio(ISubsidio subsidio) {
	        this.fecha = subsidio.getFecha().getValue();
	        this.monto = subsidio.getMontoTotal();
	        this.observaciones = subsidio.getObservaciones();
	    }
	    public int getFecha() {
    	    return this.fecha;
        }
	    public float getMontoTotal() {
    	    return this.monto;
        }
	    public String getObservaciones() {
    	    return this.observaciones;
        }
	    public ISubsidio getSubsidio() {
	        // TODO Subsidios: Regresar rendiciones
	        return new Subsidio(Year.of(fecha), observaciones, monto, observaciones, null);
	    }
	}

	@FXML void inicializarSubsidios() {
	    subsidioSeleccionado = null;
	    filasSubsidios.clear();
	    limpiarCamposSubsidios();

	    if (proyectoSeleccionado != null) {
	        for (ISubsidio subsidio : proyectoSeleccionado.getSubsidios()) {
	            filasSubsidios.add(new FilaSubsidio(subsidio));
	        }
	    }
	}

	@FXML private Button btnSubsidiosNuevo;
	@FXML void nuevoSubsidio(ActionEvent event) {
	    subsidioSeleccionado = new Subsidio(null, null, 0, null, null);
	    limpiarCamposSubsidios();
	}

	@FXML private Button btnSubsidiosGuardar;
	@FXML void guardarSubsidio(ActionEvent event) {
	    if (subsidioSeleccionado != null) {
	        // Actualizo los valores de los subsidios:
	        subsidioSeleccionado.setFecha(Year.of(Integer.parseInt(txtSubsidiosAnio.getText())));
	        subsidioSeleccionado.setMontoTotal(Float.parseFloat(txtSubsidiosMonto.getText()));
	        subsidioSeleccionado.setDisposicion(txtSubsidiosDisp.getText());
	        subsidioSeleccionado.setObservaciones(txtaSubsidiosObservaciones.getText());
	        // Agreo el subsidio al proyecto:
	        proyectoSeleccionado.agregarSubsidio(subsidioSeleccionado);
	        this.controlInvestigacion.agregarSubsidio(proyectoSeleccionado, subsidioSeleccionado);
	        // Agrego el subsidio a la tabla:
	        filasSubsidios.add(new FilaSubsidio(subsidioSeleccionado));
	    }
    }

	@FXML private Button btnSubsidiosDescartar;
	@FXML void descartarSubsidio(ActionEvent event) {
	    limpiarCamposSubsidios();
	    tblSubsidios.getSelectionModel().clearSelection();
	    subsidioSeleccionado = null;
    }

	@FXML private Button btnSubsidiosEliminar;
	@FXML void eliminarSubsidio(ActionEvent event) {
	    FilaSubsidio fs = tblSubsidios.getSelectionModel().getSelectedItem();
	    subsidioSeleccionado = fs.getSubsidio();
	    proyectoSeleccionado.quitarSubsidio(subsidioSeleccionado);

	    this.controlInvestigacion.quitarSubsidio(proyectoSeleccionado, subsidioSeleccionado);

	    dialogoConfirmacion(TITULO, "Quita de subsidio", "El subsidio ha sido eliminado");

	    limpiarCamposSubsidios();
	    tblSubsidios.getSelectionModel().clearSelection();
	    subsidioSeleccionado = null;
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

	private IRendicion rendicionSeleccionada = null;
	private ObservableList<FilaRendicion> filasRendiciones = FXCollections.observableArrayList();

	private void limpiarCamposRendiciones() {
	    dtpRendicionesFecha.setValue(null);
	    txtRendicionesMonto.clear();
	    txtaRendicionesObservaciones.clear();
	}

	private void llenarCamposRendiciones() {
	    if (rendicionSeleccionada != null) {
	        dtpRendicionesFecha.setValue(rendicionSeleccionada.getFecha());
	        txtRendicionesMonto.setText(String.valueOf(rendicionSeleccionada.getMonto()));
	        txtaRendicionesObservaciones.setText(rendicionSeleccionada.getObservaciones());
	    }
	}

	class FilaRendicion {
	    private LocalDate fecha;
	    public LocalDate getFecha() {
            return this.fecha;
        }

        public float getMonto() {
            return this.monto;
        }

        public String getObservaciones() {
            return this.observaciones;
        }

        private float monto;
	    private String observaciones;

	    public FilaRendicion(IRendicion rendicion) {
	        this.fecha = rendicion.getFecha();
	        this.monto = rendicion.getMonto();
	        this.observaciones = rendicion.getObservaciones();
	    }

	    public IRendicion getRendicion() {
	        return new Rendicion(0, this.fecha, this.monto, this.observaciones);
	    }

	}

	@FXML void inicializarRendiciones() {
	    rendicionSeleccionada = null;
	    filasRendiciones.clear();
	    limpiarCamposRendiciones();

	    if (subsidioSeleccionado != null) {
    	    for (IRendicion rendicion : subsidioSeleccionado.getRendiciones()) {
    	        filasRendiciones.add(new FilaRendicion(rendicion));
    	    }
	    }
	}

	@FXML private Button btnRendicionesNueva;
	@FXML void nuevaRendicion(ActionEvent event) {
	    rendicionSeleccionada = new Rendicion(0, null, 0, null);
	    limpiarCamposRendiciones();
	}

	@FXML private Button btnRendicionesGuardar;
	@FXML void guardarRendicion(ActionEvent event) {
	    if (rendicionSeleccionada != null) {
	        try {
    	        rendicionSeleccionada.setFecha(dtpRendicionesFecha.getValue());
    	        rendicionSeleccionada.setMonto(Float.parseFloat(txtRendicionesMonto.getText()));
    	        rendicionSeleccionada.setObservaciones(txtaRendicionesObservaciones.getText());

    	        subsidioSeleccionado.agregarRendicion(rendicionSeleccionada);
    	        // TODO Rendiciones: Agregar rendición a BD
	        } catch (NumberFormatException nfe) {
	            nfe.printStackTrace();
	            alertaError(TITULO, "Guardar rendición", "Alguno de los datos ingresados no es válido");
	        }
	    }
	}

	@FXML private Button btnRendicionesDescartar;
	@FXML void descartarRendicion(ActionEvent event) {
	    limpiarCamposRendiciones();
	    tblRendiciones.getSelectionModel().clearSelection();
	    rendicionSeleccionada = null;
	}

	@FXML private Button btnRendicionesEliminar;
	@FXML void eliminarRendicion(ActionEvent event) {
	    // Quitar fila de la tabla:
	    FilaRendicion fr = tblRendiciones.getSelectionModel().getSelectedItem();
	    filasRendiciones.remove(fr);

	    // Quitar rendición:
	    subsidioSeleccionado.quitarRendicion(rendicionSeleccionada);
	    // TODO Rendiciones: Quitar rendición en BD
	}

	@FXML private TableView<FilaRendicion> tblRendiciones;
	@FXML private TableColumn<FilaRendicion, LocalDate> colRendicionesFecha;
	@FXML private TableColumn<FilaRendicion, Float> colRendicionesMonto;
	@FXML private TableColumn<FilaRendicion, String> colRendicionesObservaciones;

	@FXML private DatePicker dtpRendicionesFecha;
	@FXML private TextField txtRendicionesMonto;
	@FXML private TextArea txtaRendicionesObservaciones;

// --------------------------- Pestaña Prórrogas ---------------------------- //

	@FXML private Button btnProrrogasNueva;
    @FXML void nuevaProrroga(ActionEvent event) {

    }

    @FXML private Button btnProrrogasGuardar;
    @FXML void guardarProrroga(ActionEvent event) {

    }

    @FXML private Button btnProrrogasDescartar;
    @FXML void descartarProrroga(ActionEvent event) {

    }

    @FXML private Button btnProrrogasEliminar;
    @FXML void eliminarProrroga(ActionEvent event) {

    }

	@FXML private TableView<?> tblProrrogas;
	@FXML private TableColumn<?, ?> colProrrogasInicio;
	@FXML private TableColumn<?, ?> colProrrogasFinalización;

	@FXML private DatePicker dtpProrrogasInicio;
	@FXML private DatePicker dtpProrrogasFinalizacion;
	@FXML private TextField txtProrrogasDisp;

// ---------------------------- Pestaña Resumen ----------------------------- //

    @FXML private Button btnResumenGuardar;
    @FXML void guardarResumen(ActionEvent event) {

    }

    @FXML private Button btnResumenDescartar;
    @FXML void descartarResumen(ActionEvent event) {

    }

    @FXML private TextArea txtaResumen;

}
