package vista.controladores;

import java.time.LocalDate;
import java.util.List;
import controlador.ControlImportarCosto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modelo.auxiliares.EstadoCargo;
import modelo.costeo.ICargoFaltante;
import modelo.docente.ICargoDocente;

public class ImportarCosto extends ControladorVista {
	
	public static final String TITULO = "ImportarCosto"; 
	
	private ControlImportarCosto control = new ControlImportarCosto(this);
	
	@Override
    public void inicializar() {
		inicializarTablas();
		this.cmbEstado.setItems(
				FXCollections.observableArrayList(EstadoCargo.getLista()));
		this.window.setTitle("Importar último costo");
		resetGeneral();
	}
	
	private void inicializarTablas() {
		inicializarTabla("FaltantesCosteo");
		inicializarTabla("FaltantesSistema");
	}
	
	@FXML protected Label lblUltima;
	
	private void resetGeneral() {
		String fecha = "Nunca";
		LocalDate ultima = this.control.getUltimaFecha();
		if (ultima != null)
			fecha = ultima.toString();
		this.lblUltima.setText("Última actualización: " + fecha);
		// TODO cargar las tablas con datos
	}
	
	private void actualizarTablas() {
		actualizarTablaCosteo();
		actualizarTablaSistema();
	}
	
	private void actualizarTablaCosteo() {
		List<ICargoDocente> faltantesCosteo = this.control.getFaltantesCosteo();
		for (ICargoDocente cargo : faltantesCosteo) {
			FilaCosteo fc = new FilaCosteo(cargo);
			this.filasFaltantesCosteo.add(fc);
		}
	}
	
	private void actualizarTablaSistema() {
		List<ICargoFaltante> faltantesSistema = this.control.getFaltantesSistema();
		for (ICargoFaltante cargo : faltantesSistema) {
			FilaSistema fs = new FilaSistema(cargo);
			this.filasFaltantesSistema.add(fs);
		}
	}

    @FXML protected Button btnListar;
    @FXML public void listarCostos(ActionEvent event) {
//    	this.gestorPantalla.lanzarPantalla(ListaCosto.TITULO, null);
    }
    
    @FXML protected Button btnImportar;
    @FXML public void importar(ActionEvent event) {
    	if (this.control.importar()) {
    		actualizarTablas();
    	}
    }
    
	@FXML protected Button btnGuardar;
	@FXML public void guardar(ActionEvent event) {
		this.control.guardar();
	}
	@FXML protected Button btnDescartar;
	@FXML public void descartar(ActionEvent event) {
		this.control.descartar();
		resetGeneral();
	}

	public class FilaCosteo {
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
	
	protected ObservableList<FilaCosteo> filasFaltantesCosteo;
	
    @FXML protected TableView<FilaCosteo> tblFaltantesCosteo;
    @FXML protected TableColumn<FilaCosteo, Integer> colFaltantesCosteoLegajo;
    @FXML protected TableColumn<FilaCosteo, Integer> colFaltantesCosteoCodigo;
    @FXML protected TableColumn<FilaCosteo, String> colFaltantesCosteoEstado;
    
    @FXML protected ComboBox<EstadoCargo> cmbEstado;
    @FXML protected Button btnModificarEstado;
    @FXML public void modificarEstado(ActionEvent event) {
    	FilaCosteo fc = this.tblFaltantesCosteo.getSelectionModel().getSelectedItem();
    	if (fc != null) {
    		EstadoCargo ec = this.cmbEstado.getValue();
    		if (ec != null) {
    			this.control.modificarEstado(fc.getCargo(), ec);
    		}
    	}
    }
    
    public class FilaSistema {
		private ICargoFaltante cargo;
		public FilaSistema(ICargoFaltante cargo) {
			this.cargo = cargo;
		}
		public ICargoFaltante getCargo() {
			return cargo;
		}
		public void setCargo(ICargoFaltante cargo) {
			this.cargo = cargo;
		}
		public int getLegajo() {
			return cargo.getLegajo();
		}
		public void setLegajo(int legajo) {
			this.cargo.setLegajo(legajo);
		}
		public String getApellido() {
			return cargo.getApellido();
		}
		public void setApellido(String apellido) {
			this.cargo.setApellido(apellido);
		} 
		public String getNombre() {
			return cargo.getNombre();
		}
		public void setNombre(String nombre) {
			this.cargo.setNombre(nombre);
		}
		public int getCodigo() {
			return cargo.getCodigoCargo();
		}
		public void setCodigo(int codigo) {
			this.cargo.setCodigoCargo(codigo);
		}
    }
    
    protected ObservableList<FilaSistema> filasFaltantesSistema;
    
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