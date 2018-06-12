package vista.controladores;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import excel.Costeo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modelo.auxiliares.EstadoCargo;
import modelo.docente.ICargoDocente;

public class ImportarCosto extends ControladorVista {
	
	public static final String TITULO = "ImportarCosto"; 
	
	@Override
    public void initialize(URL arg0, ResourceBundle arg1) {
		inicializarTablas();
		this.cmbEstado.setItems(
				FXCollections.observableArrayList(EstadoCargo.getLista()));
		String fecha = "Nunca";
		// TODO obtener la fecha de última actualización
		this.lblUltima.setText("Última actualización: " + fecha);
	}
	
	private void inicializarTablas() {
		inicializarTabla("FaltantesCosteo");
		inicializarTabla("FaltantesSistema");
	}	

    @FXML protected Button btnListar;
    @FXML public void listarCostos(ActionEvent event) {
    	
    }
    @FXML protected Button btnImportar;
    @FXML public void importar(ActionEvent event) {
    	String titulo = "Elegir la planilla a importar";
    	String error = "Error al importar el archivo";
	    try {
	    	List<String> extensiones = new ArrayList<String>();
	    	extensiones.add("xls");
	    	extensiones.add("xlsx");
	        File archivo = elegirArchivo(titulo, "Hojas de cálculo", extensiones);
	        if (archivo != null) {
	        	Costeo.importar(archivo);
                // TODO llevar a la capa de abajo
	        }
        } catch (EncryptedDocumentException e) {
            alertaError(TITULO, error,
                "El archivo está protegido por contraseña");
        } catch (InvalidFormatException e) {
            alertaError(TITULO, error,
                "El archivo no tiene el formato correcto.");
        } catch (IOException e) {
            alertaError(TITULO, error,
                "Error de apertura de archivo: " + e.getMessage());
        }
    }
	@FXML protected Button btnGuardar;
	@FXML public void guardar(ActionEvent event) {
		
	}
	@FXML protected Button btnDescartar;
	@FXML public void descartar(ActionEvent event) {

	}
	
	@FXML protected Label lblUltima;

	class FilaCosteo {
		private ICargoDocente cargo;
		public FilaCosteo(ICargoDocente cargo) {
			this.cargo = cargo;
		}
		public ICargoDocente getCargo() {
			return cargo;
		}
		public void setCargo(ICargoDocente cargo) {
			this.cargo = cargo;
		}
		public int getLegajo() {
			return cargo.getDocente().getLegajo();
		}
		public void setLegajo(int legajo) {
			this.cargo.getDocente().setLegajo(legajo);
		}
		public int getCodigo() {
			return cargo.getId();
		}
		public void setCodigo(int codigo) {
			this.cargo.setId(codigo);
		}
		public String getEstado() {
			return cargo.getEstado().getDescripcion();
		}
		public void setEstado(EstadoCargo estado) {
			this.cargo.setEstado(estado);
		}
	}
	
    @FXML protected TableView<FilaSistema> tblFaltantesCosteo;
    @FXML protected TableColumn<FilaSistema, Integer> colFaltantesCosteoLegajo;
    @FXML protected TableColumn<FilaSistema, Integer> colFaltantesCosteoCodigo;
    @FXML protected TableColumn<FilaSistema, String> colFaltantesCosteoEstado;
    
    @FXML protected ComboBox<EstadoCargo> cmbEstado;
    @FXML protected Button btnModificarEstado;
    @FXML public void modificarEstado(ActionEvent event) {
    	
    }
    
    
    class FilaSistema {
		private ICargoDocente cargo;
		public FilaSistema(ICargoDocente cargo) {
			this.cargo = cargo;
		}
		public ICargoDocente getCargo() {
			return cargo;
		}
		public void setCargo(ICargoDocente cargo) {
			this.cargo = cargo;
		}
		public int getLegajo() {
			return cargo.getDocente().getLegajo();
		}
		public void setLegajo(int legajo) {
			this.cargo.getDocente().setLegajo(legajo);
		}
		public String getApellido() {
			return cargo.getDocente().getPersona().getApellido();
		}
		public void setApellido(String apellido) {
			this.cargo.getDocente().getPersona().setApellido(apellido);
		} 
		public String getNombre() {
			return cargo.getDocente().getPersona().getNombre();
		}
		public void setNombre(String nombre) {
			this.cargo.getDocente().getPersona().setNombre(nombre);
		}
		public String getEstado() {
			return cargo.getEstado().getDescripcion();
		}
		public void setEstado(EstadoCargo estado) {
			this.cargo.setEstado(estado);
		}
    }
    
    @FXML protected TableView<FilaSistema> tblFaltantesSistema;
    @FXML protected TableColumn<FilaSistema, Integer> colFaltantesSistemaLegajo;
    @FXML protected TableColumn<FilaSistema, String> colFaltantesSistemaApellido;
    @FXML protected TableColumn<FilaSistema, String> colFaltantesSistemaNombre;
    @FXML protected TableColumn<FilaSistema, Integer> colFaltantesSistemaCodigo;
    
    @FXML protected Button btnAltaCargo;
    @FXML public void altaCargo(ActionEvent event) {
//		TODO Lanzar pantalla Personas, luego Docentes e ir a la pestaña Cargos,
//    	a menos que el docente ya esté dado de alta
    	FilaSistema fs = this.tblFaltantesSistema.getSelectionModel().getSelectedItem();
    	if (fs != null) {
//    		this.gestorPantalla.lanzarPantalla(nombre, args);
    	}
    }    
}