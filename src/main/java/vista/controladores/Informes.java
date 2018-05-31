package vista.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Informes extends ControladorVista implements Initializable {
	
	public static final String TITULO = "Informes";
	
    /* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	@FXML protected Button btnInformesVolver;
	@FXML public void volver(ActionEvent event) {
		this.gestorPantalla.lanzarPantalla(ListaInformes.TITULO, null);
		this.gestorPantalla.cerrarPantalla(Informes.TITULO);
	}
	
	@FXML protected Button btnInformesGuardar;
	@FXML public void guardar(ActionEvent event) {
		
	}
	
	@FXML protected Button btnInformesGuardarComo;
	@FXML public void guardarComo(ActionEvent event) {
		
	}
	
	@FXML protected Button btnInformesExcel;
	@FXML public void exportar(ActionEvent event) {
		
	}

// -------------------------------- Informe --------------------------------- //
	
	@FXML protected TextField txtInformesNombre;
    @FXML protected TextField txtInformesDescripcion;

    @FXML protected TableView<?> tblInformes;
    
// -------------------------------- Filtros --------------------------------- //

    @FXML protected Button btnFiltroDuplicar;
    @FXML public void duplicarColumna(ActionEvent event) {

    }
    
    @FXML protected Button btnFiltroVer;
    @FXML public void verColumna(ActionEvent event) {
    	
    }
    
    @FXML protected Button btnFiltroOrdenar;
    @FXML public void ordenarColumna(ActionEvent event) {

    }
    
    @FXML protected Button btnFiltroSubir;
    @FXML public void subirColumna(ActionEvent event) {

    }

    @FXML protected Button btnFiltroBajar;
    @FXML public void bajarColumna(ActionEvent event) {
        
    }

    @FXML protected TextField txtFiltroNombre;
    @FXML protected ComboBox<?> cmbFiltroFiltro;
    @FXML protected TextField txtFiltroCondicion;
    @FXML protected ComboBox<?> cmbFiltroCalculo;

    @FXML protected Button btnFiltroAplicar;
    @FXML public void aplicarFiltro(ActionEvent event) {
    	
    }
    @FXML protected Button btnFiltroLimpiar;
    @FXML public void limpiarFiltro(ActionEvent event) {
    	
    }
    
    
    // No se me ocurrió otra cosa
 	class FilaColumna {
 		private String columna;
 		private String nombre;
 		private String visible;
 		private String ordenar;
 		private String filtro;
 		private String calculo;
 		public String getColumna() {
 			return columna;
 		}
 		public void setColumna(String columna) {
 			this.columna = columna;
 		}
 		public String getNombre() {
 			return nombre;
 		}
 		public void setNombre(String nombre) {
 			this.nombre = nombre;
 		}
 		public String getVisible() {
 			return visible;
 		}
 		public void setVisible(String visible) {
 			this.visible = visible;
 		}
 		public String getOrdenar() {
 			return ordenar;
 		}
 		public void setOrdenar(String ordenar) {
 			this.ordenar = ordenar;
 		}
 		public String getFiltro() {
 			return filtro;
 		}
 		public void setFiltro(String filtro) {
 			this.filtro = filtro;
 		}
 		public String getCalculo() {
 			return calculo;
 		}
 		public void setCalculo(String calculo) {
 			this.calculo = calculo;
 		}
 	}
 	
 	@FXML protected TableView<FilaColumna> tblFiltro;
 	@FXML protected TableColumn<FilaColumna, String> colFiltroColumna;
 	@FXML protected TableColumn<FilaColumna, String> colFiltroNombre;
 	@FXML protected TableColumn<FilaColumna, String> colFiltroVisible;
 	@FXML protected TableColumn<FilaColumna, String> colFiltroOrdenar;
 	@FXML protected TableColumn<FilaColumna, String> colFiltroFiltro;
 	@FXML protected TableColumn<FilaColumna, String> colFiltroCalculo;
}
