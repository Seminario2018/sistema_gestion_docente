package vista.controladores;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controlador.ControlImportarCosto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modelo.auxiliares.EstadoCargo;
import modelo.costeo.GestorImportarCosto.TipoAlta;
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
		this.dtpFecha.setValue(LocalDate.now());
		actualizarTablas();
	}
	
	private void actualizarTablas() {
		actualizarTablaCosteo();
		actualizarTablaSistema();
	}
	
	private void actualizarTablaCosteo() {
		this.filasFaltantesCosteo.clear();
		List<ICargoDocente> faltantesCosteo = this.control.getFaltantesCosteo();
		for (ICargoDocente cargo : faltantesCosteo) {
			FilaCosteo fc = new FilaCosteo(cargo);
			this.filasFaltantesCosteo.add(fc);
		}
	}
	
	private void actualizarTablaSistema() {
		this.filasFaltantesSistema.clear();
		List<ICargoFaltante> faltantesSistema = this.control.getFaltantesSistema();
		for (ICargoFaltante cargo : faltantesSistema) {
			FilaSistema fs = new FilaSistema(cargo);
			this.filasFaltantesSistema.add(fs);
		}
	}

    @FXML protected Button btnListar;
    @FXML public void listarCostos(ActionEvent event) {
    	Map<String, Object> args = new HashMap<String, Object>();
    	args.put(ListaCosto.LISTA, this.control.listarComparacion());
    	this.gestorPantalla.lanzarPantalla(ListaCosto.TITULO, args);
    }
    
    @FXML protected Button btnImportar;
    @FXML public void importar(ActionEvent event) {
    	LocalDate fechaImportada = this.dtpFecha.getValue();
    	if (this.control.importar(fechaImportada)) {
    		this.btnListar.setDisable(false);
    		this.btnGuardar.setDisable(false);
    		this.btnDescartar.setDisable(false);
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
	
	@FXML protected DatePicker dtpFecha;

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
    			this.gestorPantalla.mensajeEstado("Se ha actualizado el Cargo "
    					+ fc.getCodigo() + " al Estado " + ec.getDescripcion());
    			actualizarTablas();
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
    	String titulo = "Dar de alta cargo";
    	FilaSistema fs = this.tblFaltantesSistema.getSelectionModel().getSelectedItem();
    	if (fs != null) {
    		ICargoFaltante cargof = fs.getCargo();
    		TipoAlta ta = this.control.getTipoAlta(cargof);
    		switch (ta) {
    		case ESTADO:
    			// Cambiar el estado a activo
    			if (dialogoConfirmacion(titulo, 
    					"El cargo ya se encuentra en el sistema, pero está inactivo.\r\n"
    					+ "¿Desea cambiar su estado a Activo?", "")) {
    				if (this.control.altaEstado(cargof)) {
    					dialogoInformacion(titulo, 
    							"El estado del cargo se cambió a Activo.", "");
    				} else {
    					alertaError(titulo, "El estado del cargo no pudo "
    							+ "ser modificado.", "");
    				}
    			}
    			break;
    		case CARGO:
    			// El docente existe pero el cargo no, llevar a la pestaña Cargos
    			if (dialogoConfirmacion(titulo,
    					"El cargo no se encuentra en el sistema.\r\n"
    					+ "¿Desea dar de alta un nuevo cargo?", "")) {
    				Map<String, Object> args = new HashMap<String, Object>();
    				args.put(Docentes.REC_CARGO_DOCENTE, this.control.prepararCargo(cargof));
    				args.put(Docentes.KEY_TAB, Docentes.TAB_CARGOS);
    				this.gestorPantalla.lanzarPantalla(Docentes.TITULO + " ImportarCosto", args);
    			}
    			break; 
    		case DOCENTE:
    			// No existe ni el Docente ni el Cargo
    			if (dialogoConfirmacion(titulo,
    					"El docente no se encuentra en el sistema.\r\n"
    					+ "¿Desea dar de alta un nuevo docente?", "")) {
    				Map<String, Object> args = new HashMap<String, Object>();
    				args.put(Docentes.REC_CARGO_DOCENTE, this.control.prepararCargo(cargof));
    				args.put(Docentes.KEY_TAB, Docentes.TAB_DATOS);
    				this.gestorPantalla.lanzarPantalla(Docentes.TITULO + " ImportarCosto", args);
    			}
    			break;
    		default:
    			this.dialogoInformacion("Dar de alta cargo",
    					"Ocurrió un error al dar de alta", "");
    			break;
    		}
    	}
    }    
}