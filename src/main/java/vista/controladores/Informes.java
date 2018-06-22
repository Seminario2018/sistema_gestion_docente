package vista.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import controlador.ControlInforme;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.auxiliares.Calculo;
import modelo.auxiliares.Filtro;
import modelo.informe.ColumnaInforme;
import modelo.informe.FiltroColumna;
import modelo.informe.ITipoInforme;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
import modelo.usuario.Modulo;
import utilidades.Utilidades;


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
		
		this.cmbFiltroFiltro.setItems(FXCollections.observableArrayList(Filtro.getLista()));
		this.cmbFiltroCalculo.setItems(FXCollections.observableArrayList(Calculo.getLista()));
	}
	
	@Override
	public void recibirParametros(Map<String, Object> args) {
		ITipoInforme informeActual = (ITipoInforme) args.get(KEY_INFORME);
		this.control.setInformeActual(informeActual);
		if (!informeActual.isEditable())
			this.btnInformesGuardar.setVisible(false);
		
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
		
		for (ColumnaInforme col : informeActual.getColumnas()) {
			TableColumn<List<String>, String> columna = new TableColumn<>(col.getNombre());
			columna.setCellValueFactory(new PropertyValueFactory<List<String>, String>(col.getNombre()));
			this.tblInformes.getColumns().add(columna);
		}
		
		ObservableList<List<String>> vistaPrevia = FXCollections.observableArrayList(this.control.vistaPrevia());
		this.tblInformes.getItems().addAll(vistaPrevia);
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

    @FXML protected TableView<List<String>> tblInformes;
    
// -------------------------------- Filtros --------------------------------- //
    
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
    @FXML protected ComboBox<Filtro> cmbFiltroFiltro;
    @FXML protected TextField txtFiltroCondicion;
    @FXML protected ComboBox<Calculo> cmbFiltroCalculo;

    @FXML protected Button btnFiltroAplicar;
    @FXML public void aplicarFiltro(ActionEvent event) {
    	
    }
    @FXML protected Button btnFiltroLimpiar;
    @FXML public void limpiarFiltro(ActionEvent event) {
    	
    }
    
    
    // No se me ocurrió otra cosa
 	class FilaColumna {
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
 			return this.col.getFiltro().getTipo().getDescripcion() + " " 
 					+ this.col.getFiltro().getValor();
 		}
 		public void setFiltro(FiltroColumna filtro) {
 			this.col.setFiltro(filtro);
 		}
 		public String getCalculo() {
 			return this.col.getCalculo().getDescripcion();
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
 		
 		String nombre = columna.getNombre();
 		if (nombre != null && !"".equals(nombre))
 			this.txtFiltroNombre.setText(nombre);
 		
 		FiltroColumna filtro = columna.getFiltro(); 
 		if (filtro != null) {
 			this.txtFiltroCondicion.setText(filtro.getValor());
 			this.cmbFiltroFiltro.setValue(filtro.getTipo());
 		}
 		
 		Calculo calculo = columna.getCalculo();
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
 		this.txtFiltroNombre.setEditable(false);
 		this.cmbFiltroFiltro.setDisable(true);
 		this.txtFiltroCondicion.setEditable(false);
 		this.cmbFiltroCalculo.setDisable(true);
 		this.btnFiltroAplicar.setDisable(true);
 		this.btnFiltroLimpiar.setDisable(true);
 	}
 	
 	private void habilitarFiltro() {
 		this.btnFiltroVer.setDisable(false);
 		this.btnFiltroOrdenar.setDisable(false);
 		this.btnFiltroSubir.setDisable(false);
 		this.btnFiltroBajar.setDisable(false);
 		this.txtFiltroNombre.setEditable(true);
 		this.cmbFiltroFiltro.setDisable(false);
 		this.txtFiltroCondicion.setEditable(true);
 		this.cmbFiltroCalculo.setDisable(false);
 		this.btnFiltroAplicar.setDisable(false);
 		this.btnFiltroLimpiar.setDisable(false);
 	}
}
