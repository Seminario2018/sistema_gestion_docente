package vista.controladores;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 15 de set. de 2018
 */
public class ConfigInf extends ControladorVista implements Initializable {

	public static final String TITULO = "ConfigInf";
	
	@Override
	public void inicializar() {
		this.window.setTitle("Configuración de informes");
	}
	
	@FXML
	private ComboBox<?> cmbHoraFor;

	@FXML
	private TextField txtNombre;

	@FXML
	private ComboBox<?> cmbFechaFor;

	@FXML
	private Button btnAceptar;

	@FXML
	private TextField txtDirectorio;

	@FXML
	private Button btnCancelar;

	@FXML
	private ComboBox<?> cmbHoraSep;

	@FXML
	private Button btnBuscar;

	@FXML
	private ComboBox<?> cmbFechaSep;

	@FXML
	void buscarDirectorio(ActionEvent event) {

	}

	@FXML
	void aceptar(ActionEvent event) {

	}

	@FXML
	void cancelar(ActionEvent event) {

	}

}
