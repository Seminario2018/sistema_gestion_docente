package vista.controladores;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Busqueda extends ControladorVista implements Initializable {
	
	public static final String KEY_TIPO = "tipo";
	
	private String tipo;

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
// -------------------------------- General --------------------------------- //
	
	@FXML private TableView<?> tblBusqueda;
	
	@FXML private TextField txtBusquedaCriterio;
	
	@FXML private Button btnBusquedaNuevo;
	@FXML public void nuevo(ActionEvent event) {
		this.gestorPantalla.lanzarPantalla(this.tipo, null);
	}
	
	@FXML private Button btnBusquedaSeleccionar;
	@FXML public void seleccionar(ActionEvent event) {
		Object fila = tblBusqueda.getSelectionModel().getSelectedItem();
		try {
			Method m = this.getClass().getDeclaredMethod("inicializar" + this.tipo, Object.class);
			m.invoke(this, fila);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void recibirParametros(Map<String, Object> args) {
		this.tipo = (String) args.get(KEY_TIPO);
		try {
			Method m = this.getClass().getDeclaredMethod("inicializar" + this.tipo);
			m.invoke(this);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

// ------------------------------- Específico ------------------------------- //
	
	

}
