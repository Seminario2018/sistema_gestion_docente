package vista.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class BusquedaCargoDocente implements Initializable {

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	@FXML private TextField txtCargoDocenteCriterio;

	@FXML private Button btnCargoDocenteDocente;
	@FXML public void nuevoDocente(ActionEvent event) {
		
	}
	
	@FXML private Button btnCargoDocenteCargo;
	@FXML public void nuevoCargo(ActionEvent event) {
		
	}

	@FXML private Button btnCargoDocenteSeleccionar;
	@FXML public void seleccionarCargo(ActionEvent event) {
		
	}
	
	
	@FXML private TableView<?> tblCargoDocente;
	@FXML private TableColumn<?, ?> colCargoDocenteLegajo;
	@FXML private TableColumn<?, ?> colCargoDocenteNombre;
	@FXML private TableColumn<?, ?> colCargoDocenteArea;
	@FXML private TableColumn<?, ?> colCargoDocenteCargo;

}