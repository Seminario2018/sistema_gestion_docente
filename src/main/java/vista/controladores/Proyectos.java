package vista.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Proyectos extends ControladorVista implements Initializable {
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
    @FXML
    private Button btnRendicionesEliminar;

    @FXML
    private Button btnProrrogasDescartar;

    @FXML
    private Button btnDatosDirector;

    @FXML
    private Button btnResumenDescartar;

    @FXML
    private TableColumn<?, ?> colSubsidiosObservaciones;

    @FXML
    private TextField txtSubsidiosDisp;

    @FXML
    private Button btnSubsidiosGuardar;

    @FXML
    private TextField txtIntegrantesHoras;

    @FXML
    private Button btnSubsidiosNuevo;

    @FXML
    private TextField txtIntegrantesApellido;

    @FXML
    private Button btnProrrogasEliminar;

    @FXML
    private Button btnProyectosBuscar;

    @FXML
    private Button btnDatosCodirector;

    @FXML
    private TextField txtSubsidiosAnio;

    @FXML
    private Button btnIntegrantesEliminar;

    @FXML
    private DatePicker dtpRendicionesFecha;

    @FXML
    private Button btnDatosGuardar;

    @FXML
    private TextField txtDatosDirector;

    @FXML
    private TextField txtIntegrantesNombre;

    @FXML
    private TextField txtSubsidiosMonto;

    @FXML
    private Button btnIntegrantesDocente;

    @FXML
    private TableView<?> tblProrrogas;

    @FXML
    private Button btnIntegrantesDescartar;

    @FXML
    private TextField txtDatosNombre;

    @FXML
    private TableColumn<?, ?> colRendicionesMonto;

    @FXML
    private TableColumn<?, ?> colIntegrantesInstitucion;

    @FXML
    private DatePicker dtpDatosFinalizacion;

    @FXML
    private DatePicker dtpDatosInicio;

    @FXML
    private TextField txtProyectosId;

    @FXML
    private DatePicker dtpProrrogasInicio;

    @FXML
    private TextArea txtaResumen;

    @FXML
    private TableColumn<?, ?> colIntegrantesHoras;

    @FXML
    private TableColumn<?, ?> colIntegrantesNombre;

    @FXML
    private ScrollPane mainPane;

    @FXML
    private Button btnIntegrantesGuardar;

    @FXML
    private TableColumn<?, ?> colRendicionesFecha;

    @FXML
    private DatePicker dtpDatosPresentacion;

    @FXML
    private TextField txtDatosDisp;

    @FXML
    private Button btnProrrogasGuardar;

    @FXML
    private TextField txtIntegrantesInstitucion;

    @FXML
    private Button btnProrrogasNueva;

    @FXML
    private TableColumn<?, ?> colIntegrantesApellido;

    @FXML
    private ComboBox<?> cmbDatosEstado;

    @FXML
    private Button btnIntegrantesNuevo;

    @FXML
    private TextField txtDatosCodirector;

    @FXML
    private TableView<?> tblSubsidios;

    @FXML
    private Button btnRendicionesNueva;

    @FXML
    private Button btnRendicionesGuardar;

    @FXML
    private Button btnRendicionesDescartar;

    @FXML
    private TextField txtIntegrantesCargo;

    @FXML
    private TableColumn<?, ?> colProrrogasFinalización;

    @FXML
    private DatePicker dtpDatosAprobacion;

    @FXML
    private Button btnSubsidiosDescartar;

    @FXML
    private TableColumn<?, ?> colRendicionesObservaciones;

    @FXML
    private TableColumn<?, ?> colSubsidiosMonto;

    @FXML
    private DatePicker dtpProrrogasFinalizacion;

    @FXML
    private TextField txtProyectosNombre;

    @FXML
    private Button btnDatosDescartar;

    @FXML
    private TableColumn<?, ?> colIntegrantesCargo;

    @FXML
    private Button btnSubsidiosEliminar;

    @FXML
    private TextArea txtaRendicionesObservaciones;

    @FXML
    private TextField txtRendicionesMonto;

    @FXML
    private Button btnProyectosEliminar;

    @FXML
    private TableColumn<?, ?> colSubsidiosAnio;

    @FXML
    private TableView<?> tblIntegrantes;

    @FXML
    private Button btnResumenGuardar;

    @FXML
    private TableView<?> tblRendiciones;

    @FXML
    private Button btnProyectosNuevo;

    @FXML
    private TableColumn<?, ?> colProrrogasInicio;

    @FXML
    private TextField txtProrrogasDisp;

    @FXML
    void buscarProyecto(ActionEvent event) {

    }

    @FXML
    void nuevoProyecto(ActionEvent event) {

    }

    @FXML
    void eliminarProyecto(ActionEvent event) {

    }

    @FXML
    void guardarProyecto(ActionEvent event) {

    }

    @FXML
    void mostrarProyecto(ActionEvent event) {

    }

    @FXML
    void buscarDirector(ActionEvent event) {

    }

    @FXML
    void buscarCodirector(ActionEvent event) {

    }

    @FXML
    void buscarCargoDocente(ActionEvent event) {

    }

    @FXML
    void nuevoIntegrante(ActionEvent event) {

    }

    @FXML
    void guardarIntegrante(ActionEvent event) {

    }

    @FXML
    void mostrarIntegrante(ActionEvent event) {

    }

    @FXML
    void eliminarIntegrante(ActionEvent event) {

    }

    @FXML
    void nuevoSubsidio(ActionEvent event) {

    }

    @FXML
    void guardarSubsidio(ActionEvent event) {

    }

    @FXML
    void mostrarSubsidio(ActionEvent event) {

    }

    @FXML
    void eliminarSubsidio(ActionEvent event) {

    }

    @FXML
    void nuevaRendicion(ActionEvent event) {

    }

    @FXML
    void guardarRendicion(ActionEvent event) {

    }

    @FXML
    void mostrarRendicion(ActionEvent event) {

    }

    @FXML
    void eliminarRendicion(ActionEvent event) {

    }

    @FXML
    void nuevaProrroga(ActionEvent event) {

    }

    @FXML
    void guardarProrroga(ActionEvent event) {

    }

    @FXML
    void mostrarProrroga(ActionEvent event) {

    }

    @FXML
    void eliminarProrroga(ActionEvent event) {

    }

    @FXML
    void guardarResumen(ActionEvent event) {

    }

    @FXML
    void mostrarResumen(ActionEvent event) {

    }

}
