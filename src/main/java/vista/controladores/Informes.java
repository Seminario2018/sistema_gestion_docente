package vista.controladores;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import controlador.ControlInforme;
import javafx.fxml.Initializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import modelo.auxiliares.Calculo;
import modelo.informe.ColumnaInforme;
import modelo.informe.FiltroColumna;
import modelo.informe.ITipoInforme;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
import modelo.usuario.Modulo;


/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Informes extends ControladorVista implements Initializable {
	
	public static final String TITULO = "Informes";
	
	public static final String KEY_INFORME = "informe";
	
	private ControlInforme control = new ControlInforme(this);
	
    /* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	@Override
	public void inicializar() {
		for (IRol rol : this.usuario.getRoles()) {
			int i = 1;
			IPermiso p = rol.getPermisos().get(0);
			while (p.getModulo() != Modulo.INFORMES && i < rol.getPermisos().size()) {
				p = rol.getPermisos().get(i);
				i++;
			}
			if (p.getModulo() == Modulo.INFORMES) {
				this.permiso = p;
			}
		}
		
		this.btnInformesGuardar.setVisible(false);
		this.btnInformesGuardarComo.setVisible(false);
		
		if (this.permiso != null) {
			if (this.permiso.getModificar())
				this.btnInformesGuardarComo.setVisible(true);
				this.btnInformesGuardar.setVisible(true);
		}
		
		inicializarTablaFiltro();
	}
	
	@Override
	public void recibirParametros(Map<String, Object> args) {
		ITipoInforme informeActual = (ITipoInforme) args.get(KEY_INFORME);
		this.control.setInformeActual(informeActual);
		
		actualizarTodo();
	}
	
	private void actualizarTodo() {
		ITipoInforme informeActual = this.control.getInformeActual();
		this.btnInformesGuardar.setVisible(informeActual.isEditable());
		this.txtInformesNombre.setText(informeActual.getNombre());
		this.txtInformesDescripcion.setText(informeActual.getDescripcion());
		actualizarTablas();
	}
	
	
	private void actualizarTablas() {
		actualizarVistaPrevia();
		actualizarTablaFiltros();
	}
	
	
	private void actualizarVistaPrevia() {
		ITipoInforme informeActual = this.control.getInformeActual();
		this.tblInformes.getColumns().clear();
		this.tblInformes.getItems().clear();
		
		int i = 0; 
		for (ColumnaInforme col : informeActual.getColumnas()) {
			if (col.isVisible()) {
				TableColumn<List<String>, String> columna = new TableColumn<>(col.getNombre());
				final int colNo = i;
				columna.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
	                @Override
	                public ObservableValue<String> call(CellDataFeatures<List<String>, String> p) {
	                	if (colNo < p.getValue().size())
	                		return new SimpleStringProperty(p.getValue().get(colNo));
	                	else
	                		return new SimpleStringProperty("");
	                }
	            });
				columna.setSortable(false);
				this.tblInformes.getColumns().add(columna);
				i++;
			}
		}
		
		ObservableList<List<String>> vistaPrevia = FXCollections.observableArrayList(this.control.vistaPrevia());
		this.tblInformes.setItems(vistaPrevia);
	}
	
	
	private void actualizarTablaFiltros() {
		ITipoInforme informeActual = this.control.getInformeActual();
		this.filasFiltro.clear();
		
		for (ColumnaInforme col : informeActual.getColumnas()) {
			FilaColumna fc = new FilaColumna(col);
			this.filasFiltro.add(fc);
		}
	}
	
	
// --------------------------------- General -------------------------------- // 	
	
	@FXML protected Button btnInformesVolver;
	@FXML public void volver(ActionEvent event) {
		this.gestorPantalla.lanzarPantalla(ListaInformes.TITULO, null);
		this.gestorPantalla.cerrarPantalla(Informes.TITULO);
	}
	
	@FXML protected Button btnInformesGuardar;
	@FXML public void guardar(ActionEvent event) {
		ITipoInforme informe = this.control.getInformeActual();
		informe.setNombre(this.txtInformesNombre.getText());
		informe.setDescripcion(this.txtInformesDescripcion.getText());
		this.control.guardar(informe);
	}
	
	@FXML protected Button btnInformesGuardarComo;
	@FXML public void guardarComo(ActionEvent event) {
		ITipoInforme informe = this.control.getInformeActual();
		informe.setNombre(this.txtInformesNombre.getText());
		informe.setDescripcion(this.txtInformesDescripcion.getText());
		this.control.guardarComo(informe);
		actualizarTodo();
	}
	
	@FXML protected Button btnInformesExcel;
	@FXML public void exportar(ActionEvent event) {
		// TODO Exportar usando el XML
	}
	
	@FXML protected Button btnInformesExcelComo;
	@FXML public void exportarComo(ActionEvent event) {
		List<String> extensiones = new ArrayList<String>();
    	extensiones.add("*.xls");
    	extensiones.add("*.xlsx");
        File archivo = elegirRuta("Elija la ubicación del archivo", "Hojas de cálculo", extensiones);
		this.control.exportar(archivo);
	}
	
	

// -------------------------------- Informe --------------------------------- //
	
	@FXML protected TextField txtInformesNombre;
    @FXML protected TextField txtInformesDescripcion;

    @FXML protected TableView<List<String>> tblInformes;
    
// -------------------------------- Filtros --------------------------------- //
    
    @FXML protected Button btnFiltroModificar;
    @FXML public void modificarColumna(ActionEvent event) {
    	Map<String, Object> args = new HashMap<String, Object>();
    	args.put(InformesFiltros.COLUMNA, this.columnaSeleccion);
    	this.gestorPantalla.lanzarPantalla(InformesFiltros.TITULO, args);
    }
    
    @FXML protected Button btnFiltroVer;
    @FXML public void verColumna(ActionEvent event) {
    	if (this.columnaSeleccion != null) {
    		this.columnaSeleccion.setVisible(!this.columnaSeleccion.isVisible());
    		actualizarColumna();
    	}
    }
    
    @FXML protected Button btnFiltroOrdenar;
    @FXML public void ordenarColumna(ActionEvent event) {
    	if (this.columnaSeleccion != null) {
    		this.columnaSeleccion.setOrdenar(
    				(this.columnaSeleccion.getOrdenar() + 1) % 3);
    		actualizarColumna();
    	}
    }
    
    @FXML protected Button btnFiltroSubir;
    @FXML public void subirColumna(ActionEvent event) {
    	swapColumna(-1);
    }

    @FXML protected Button btnFiltroBajar;
    @FXML public void bajarColumna(ActionEvent event) {
        swapColumna(1);
    }
    
    private void actualizarColumna() {
    	if (this.columnaSeleccion != null) {
	    	this.control.actualizarColumna(this.columnaSeleccion);
			int i = this.columnaSeleccion.getPosicion();
			actualizarTablas();
			this.tblFiltro.getSelectionModel().select(i);
    	}
    }
    
    private void swapColumna(int cambio) {
    	if (this.columnaSeleccion != null) {
    		int a = this.columnaSeleccion.getPosicion();
    		int b = a + cambio;
    		this.control.swapColumna(a, b);
    		actualizarTablas();
    		this.tblFiltro.getSelectionModel().select(b);
    	}
    }    
    
//    @FXML protected Button btnFiltroLimpiar;
//    @FXML public void limpiarFiltro(ActionEvent event) {
//    	if (this.columnaSeleccion != null) {
//    		this.columnaSeleccion.setFiltro(null);
//    		this.columnaSeleccion.setCalculo(null);
//    		actualizarColumna();
//    	}
//    }
    
    
    // No se me ocurrió otra cosa
 	public class FilaColumna {
 		private ColumnaInforme col;
 		public FilaColumna(ColumnaInforme col) {
 			this.col = col;
 		}
 		public ColumnaInforme getCol() {
 			return this.col;
 		}
 		public void setCol(ColumnaInforme col) {
 			this.col = col;
 		}
 		public String getColumna() {
 			return this.col.getAtributo();
 		}
 		public void setColumna(String columna) {
 			this.col.setAtributo(columna);
 		}
 		public String getNombre() {
 			return this.col.getNombre();
 		}
 		public void setNombre(String nombre) {
 			this.col.setNombre(nombre);
 		}
 		public String getVisible() {
 			return this.col.isVisible() ? "Sí" : "No";
 		}
 		public void setVisible(String visible) {
 			this.col.setVisible(visible.equals("No") ? false : true);
 		}
 		public String getOrdenar() {
 			switch (this.col.getOrdenar()) {
 			case ColumnaInforme.ASCENDENTE:
 				return "Ascendente";
 			case ColumnaInforme.DESCENDENTE:
 				return "Descendente";
 			default:
 				return "Sin orden";
 			}
 		}
 		public void setOrdenar(String ordenar) {
 			int ord = ColumnaInforme.SIN_ORDEN;
 			switch (ordenar) {
 			case "Ascendente":
 				ord = ColumnaInforme.ASCENDENTE;
 				break;
 			case "Descendente":
 				ord = ColumnaInforme.DESCENDENTE;
 				break;
 			default:
 				break;
 			}
 			this.col.setOrdenar(ord);
 		}
 		public String getFiltro() {
 			if (this.col.getFiltro() != null) {
 				return this.col.getFiltro().getTipo().getDescripcion() + " " 
 						+ this.col.getFiltro().getValor();
 			} else {
 				return "";
 			}
 		}
 		public void setFiltro(FiltroColumna filtro) {
 			this.col.setFiltro(filtro);
 		}
 		public String getCalculo() {
 			if (this.col.getCalculo() != null)
 				return this.col.getCalculo().getDescripcion();
 			else
 				return "";
 		}
 		public void setCalculo(Calculo calculo) {
 			this.col.setCalculo(calculo);
 		}
 	}
 	
 	protected ObservableList<FilaColumna> filasFiltro = FXCollections.observableArrayList();
 	@FXML protected TableView<FilaColumna> tblFiltro;
 	@FXML protected TableColumn<FilaColumna, String> colFiltroColumna;
 	@FXML protected TableColumn<FilaColumna, String> colFiltroNombre;
 	@FXML protected TableColumn<FilaColumna, String> colFiltroVisible;
 	@FXML protected TableColumn<FilaColumna, String> colFiltroOrdenar;
 	@FXML protected TableColumn<FilaColumna, String> colFiltroFiltro;
 	@FXML protected TableColumn<FilaColumna, String> colFiltroCalculo;
 	
 	private ColumnaInforme columnaSeleccion;
 	public void setColumnaSeleccion(ColumnaInforme columna) {
 		this.columnaSeleccion = columna;
 		habilitarFiltro();
 		
 		// La primer columna no se puede subir
 		if (columna.getPosicion() == 0)
 			this.btnFiltroSubir.setDisable(true);
 		// La última columna no se puede bajar
 		if (columna.getPosicion() >= this.filasFiltro.size() - 1)
 			this.btnFiltroBajar.setDisable(true);
 	}
 	
 	private void inicializarTablaFiltro() {
 		inicializarTabla("Filtro");
 		this.tblFiltro.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	setColumnaSeleccion(newSelection.getCol());
		    } else {
		    	deshabilitarFiltro();
		    }
		});
 		deshabilitarFiltro();
 	}
 	
 	private void deshabilitarFiltro() {
 		this.btnFiltroVer.setDisable(true);
 		this.btnFiltroOrdenar.setDisable(true);
 		this.btnFiltroSubir.setDisable(true);
 		this.btnFiltroBajar.setDisable(true);
 	}
 	
 	private void habilitarFiltro() {
 		this.btnFiltroVer.setDisable(false);
 		this.btnFiltroOrdenar.setDisable(false);
 		this.btnFiltroSubir.setDisable(false);
 		this.btnFiltroBajar.setDisable(false);
 	}
}