package vista.controladores;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import controlador.ControlInforme;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modelo.informe.ITipoInforme;


/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class ListaInformes extends ControladorVista implements Initializable {

	public static final String TITULO = "ListaInformes";
	
	private ControlInforme control = new ControlInforme(this);
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	@Override
	public void inicializar() {
		deshabilitarBotones();
		inicializarTablaListaInformes();
		actualizarTabla();
	}
	
	private void actualizarTabla() {
		this.filasListaInformes.clear();
		for (ITipoInforme informe : this.control.listarInforme(null)) {
			FilaListaInformes fl = new FilaListaInformes(informe);
			this.filasListaInformes.add(fl);
		}
	}
	
	public class FilaListaInformes {
		private ITipoInforme informe;
		public FilaListaInformes(ITipoInforme informe) {
			this.informe = informe;
		}
		public String getNombre() {
			return this.informe.getNombre();
		}
		public ITipoInforme getInforme() {
			return this.informe;
		}
	}

	protected ObservableList<FilaListaInformes> filasListaInformes = FXCollections.observableArrayList();
    @FXML protected TableView<FilaListaInformes> tblListaInformes;
    @FXML protected TableColumn<FilaListaInformes, String> colListaInformesNombre;
    
    private ITipoInforme informeSeleccion;
    public ITipoInforme getInformeSeleccion() {
		return informeSeleccion;
	}
	public void setInformeSeleccion(ITipoInforme informeSeleccion) {
		this.informeSeleccion = informeSeleccion;
		if (informeSeleccion != null) {
			habilitarBotones();
		} else {
			deshabilitarBotones();
		}
	}

	private void inicializarTablaListaInformes() {
    	inicializarTabla("ListaInformes");
    	this.tblListaInformes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	setInformeSeleccion(newSelection.getInforme());
		    } else {
		    	setInformeSeleccion(null);
		    }
		});
    }


    @FXML private Button btnListaInformesNuevo;
    @FXML private void nuevo() {
    	if (this.informeSeleccion != null) {
    		this.informeSeleccion.setId(-1);
    		editar();
    	}
    }
    
    @FXML private Button btnListaInformesVerEditar;
    @FXML private void editar() {
    	if (this.informeSeleccion != null) {
    		Map<String, Object> args = new HashMap<String, Object>();
    		args.put(Informes.KEY_INFORME, this.informeSeleccion);
    		this.gestorPantalla.lanzarPantalla(Informes.TITULO, args);
    		this.gestorPantalla.cerrarPantalla(TITULO);
    	}
    }
    
    @FXML private Button btnListaInformesEliminar;
    @FXML private void eliminar() {
    	FilaListaInformes fl = this.tblListaInformes.getSelectionModel().getSelectedItem();
    	if (fl != null) {
    		this.control.eliminarInforme(fl.getInforme());
    	}
    }

    private void deshabilitarBotones() {
    	this.btnListaInformesNuevo.setDisable(true);
    	this.btnListaInformesVerEditar.setDisable(true);
    	this.btnListaInformesEliminar.setDisable(true);
    }
    
    private void habilitarBotones() {
    	if (this.informeSeleccion != null) {
    		this.btnListaInformesNuevo.setDisable(false);
    		this.btnListaInformesVerEditar.setDisable(false);
    		if (this.informeSeleccion.isEditable())
    			this.btnListaInformesEliminar.setDisable(false);
    	}
    }
    
}
