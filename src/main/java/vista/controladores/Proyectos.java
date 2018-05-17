package vista.controladores;
import java.net.URL;import java.util.ResourceBundle;import javafx.fxml.Initializable;import javafx.event.ActionEvent;import javafx.fxml.FXML;import javafx.scene.control.Button;import javafx.scene.control.ComboBox;import javafx.scene.control.DatePicker;import javafx.scene.control.ScrollPane;import javafx.scene.control.TableColumn;import javafx.scene.control.TableView;import javafx.scene.control.TextArea;import javafx.scene.control.TextField;
/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Proyectos extends ControladorVista implements Initializable {
	
	@FXML private ScrollPane mainPane;
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
// -------------------------------- General --------------------------------- //
	
	@FXML private TextField txtProyectosId;
	@FXML private TextField txtProyectosNombre;
	
	@FXML private Button btnProyectosBuscar;
	@FXML void buscarProyecto(ActionEvent event) {
		
	}
	
	@FXML private Button btnProyectosNuevo;
	@FXML void nuevoProyecto(ActionEvent event) {
		
	}

	@FXML private Button btnProyectosEliminar;
    @FXML void eliminarProyecto(ActionEvent event) {

    }
	
// ----------------------------- Pestaña Datos ------------------------------ //
	
	@FXML private Button btnDatosGuardar;
	@FXML void guardarProyecto(ActionEvent event) {
		
	}

	@FXML private Button btnDatosDescartar;	
    @FXML void descartarProyecto(ActionEvent event) {

    }

	@FXML private TextField txtDatosNombre;
	
	@FXML private TextField txtDatosDirector;
	@FXML private Button btnDatosDirector;
	@FXML void buscarDirector(ActionEvent event) {
		
	}

	@FXML private TextField txtDatosCodirector;
	@FXML private Button btnDatosCodirector;
	@FXML void buscarCodirector(ActionEvent event) {
		
	}
	
	@FXML private ComboBox<?> cmbDatosEstado;

	@FXML private TextField txtDatosDisp;

	@FXML private DatePicker dtpDatosPresentacion;
	@FXML private DatePicker dtpDatosAprobacion;
	@FXML private DatePicker dtpDatosInicio;
	@FXML private DatePicker dtpDatosFinalizacion;

	
// -------------------------- Pestaña Integrantes --------------------------- //
	
	@FXML private Button btnIntegrantesNuevo;
    @FXML void nuevoIntegrante(ActionEvent event) {

    }

    @FXML private Button btnIntegrantesGuardar;
	@FXML void guardarIntegrante(ActionEvent event) {
		
	}

	@FXML private Button btnIntegrantesDescartar;
	@FXML void descartarIntegrante(ActionEvent event) {
		
	}
	
	@FXML private Button btnIntegrantesEliminar;
	@FXML void eliminarIntegrante(ActionEvent event) {
		
	}
	
	@FXML private TableView<?> tblIntegrantes;
	@FXML private TableColumn<?, ?> colIntegrantesApellido;
	@FXML private TableColumn<?, ?> colIntegrantesNombre;
	@FXML private TableColumn<?, ?> colIntegrantesCargo;
	@FXML private TableColumn<?, ?> colIntegrantesInstitucion;
	@FXML private TableColumn<?, ?> colIntegrantesHoras;

	@FXML private Button btnIntegrantesDocente;	
	@FXML void buscarCargoDocente(ActionEvent event) {
		
	}

	@FXML private TextField txtIntegrantesApellido;
	@FXML private TextField txtIntegrantesNombre;
	@FXML private TextField txtIntegrantesCargo;
	@FXML private TextField txtIntegrantesInstitucion;
	@FXML private TextField txtIntegrantesHoras;
	
// --------------------------- Pestaña Subsidios ---------------------------- //
	
	@FXML private Button btnSubsidiosNuevo;
	@FXML void nuevoSubsidio(ActionEvent event) {
		
	}

	@FXML private Button btnSubsidiosGuardar;
	@FXML void guardarSubsidio(ActionEvent event) {
    	
    }
    
	@FXML private Button btnSubsidiosDescartar;
	@FXML void descartarSubsidio(ActionEvent event) {

    }
    
	@FXML private Button btnSubsidiosEliminar;
	@FXML void eliminarSubsidio(ActionEvent event) {

    }
	
	@FXML private TableView<?> tblSubsidios;
	@FXML private TableColumn<?, ?> colSubsidiosAnio;
	@FXML private TableColumn<?, ?> colSubsidiosMonto;
	@FXML private TableColumn<?, ?> colSubsidiosObservaciones;
	
	@FXML private TextField txtSubsidiosAnio;
	@FXML private TextField txtSubsidiosMonto;
	@FXML private TextField txtSubsidiosDisp;
	@FXML private TextArea txtaSubsidiosObservaciones;
	
// -------------------------- Pestaña Rendiciones --------------------------- //
	
	@FXML private Button btnRendicionesNueva;
	@FXML void nuevaRendicion(ActionEvent event) {
		
	}

	@FXML private Button btnRendicionesGuardar;
	@FXML void guardarRendicion(ActionEvent event) {
		
	}

	@FXML private Button btnRendicionesDescartar;
	@FXML void descartarRendicion(ActionEvent event) {
		
	}
	
	@FXML private Button btnRendicionesEliminar;
	@FXML void eliminarRendicion(ActionEvent event) {
		
	}
	
	@FXML private TableView<?> tblRendiciones;
	@FXML private TableColumn<?, ?> colRendicionesFecha;
	@FXML private TableColumn<?, ?> colRendicionesMonto;
	@FXML private TableColumn<?, ?> colRendicionesObservaciones;
	
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
