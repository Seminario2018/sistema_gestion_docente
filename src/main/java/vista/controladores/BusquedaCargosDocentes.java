package vista.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import controlador.ControlBusqueda;
import controlador.ControlDocente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import modelo.busqueda.BusquedaCargoDocente;
import modelo.docente.ICargoDocente;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
import modelo.usuario.Modulo;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class BusquedaCargosDocentes extends ControladorVista implements Initializable {

	public static final String TITULO = "BusquedaCargosDocentes";

	// Controlador al cual enviarle la respuesta
    public static final String KEY_CONTROLADOR = Busqueda.KEY_CONTROLADOR;
    // Devuelve el tipo de dato seleccionado, por ejemplo "Area"
    public static final String KEY_SELECCION = Busqueda.KEY_SELECCION;
	// Tipo de respuesta esperado en la pantalla respuesta
	public static final String KEY_TIPO_RESPUESTA = Busqueda.KEY_TIPO_RESPUESTA;
    // Devuelve el dato seleccionado
    public static final String KEY_VALOR = Busqueda.KEY_VALOR;
    
    private String tipo_respuesta;

    private ControlBusqueda control = new ControlBusqueda(this);
    private ControlDocente controlDocente = new ControlDocente(this);
    // Recibe la respuesta (selección)
    private ControladorVista controladorRespuesta;
	
	@Override
	public void inicializar() {
		// Mostrar botones si tiene permiso de crear docentes o cargos
		this.btnCargoDocenteCargo.setVisible(false);
		this.btnCargoDocenteDocente.setVisible(false);
		for (IRol r : this.usuario.getRoles()) {
			for (IPermiso p : r.getPermisos()) {
				if (p.getModulo() == Modulo.CARGOS) {
					if (p.getCrear()) {
						this.btnCargoDocenteCargo.setVisible(true);
					}
				}
				if (p.getModulo() == Modulo.DOCENTES) {
					if (p.getCrear()) {
						this.btnCargoDocenteDocente.setVisible(true);
					}
				}
			}
		}
		
		this.inicializarTabla("CargoDocente");
		this.actualizarLista();
		
		this.tblCargoDocente.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldSel, newSel) -> {
					if (newSel.getCodigo().equals("-")) {
						// Seleccionó un Docente sin cargo
						this.btnCargoDocenteSeleccionar.setDisable(true);
						this.btnCargoDocenteCargo.setDisable(false);
					} else {
						// Seleccionó un cargo
						this.btnCargoDocenteSeleccionar.setDisable(false);
						this.btnCargoDocenteCargo.setDisable(true);
					}
				});
		
		// Actualizar la lista cuando se muestra la pantalla
        this.window.focusedProperty().addListener(
        		(observable, oldValue, newValue) ->
        		{
        			if (newValue == true)
        				actualizarLista();
        		}
        );
        
        txtCargoDocenteCriterio.requestFocus();
	}

// -------------------------------- General --------------------------------- //
	@Override
	public void recibirParametros(Map<String, Object> args) {
		if (args.containsKey(KEY_CONTROLADOR)) {
			this.controladorRespuesta = (ControladorVista) args.get(KEY_CONTROLADOR);
		}
		if (args.containsKey(KEY_TIPO_RESPUESTA)) {
			this.tipo_respuesta = (String) args.get(KEY_TIPO_RESPUESTA);
		}
	}
	
// -------------------------------- Tablas ---------------------------------- //
    @FXML protected ObservableList<BusquedaCargoDocente> filasCargoDocente = FXCollections.observableArrayList();
    @FXML protected TableView<BusquedaCargoDocente> tblCargoDocente;
    @FXML protected TableColumn<BusquedaCargoDocente, Integer> colCargoDocenteLegajo;
    @FXML protected TableColumn<BusquedaCargoDocente, String> colCargoDocenteNombre;
    @FXML protected TableColumn<BusquedaCargoDocente, String> colCargoDocenteArea;
    @FXML protected TableColumn<BusquedaCargoDocente, String> colCargoDocenteCodigo;
    @FXML protected TableColumn<BusquedaCargoDocente, String> colCargoDocenteCargo;
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void actualizarLista() {
    	this.filasCargoDocente.addAll((List)
    			this.control.listarCargoDocentes(
    					this.txtCargoDocenteCriterio.getText()));
    	if (this.filasCargoDocente.size() > 0) {
    		this.tblCargoDocente.getSelectionModel().selectFirst();
    	}
    }
    
// -------------------------------- Botones --------------------------------- //
	@FXML private Button btnCargoDocenteDocente;
	@FXML public void nuevoDocente(ActionEvent event) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(Docentes.REC_DOCENTE, this.controlDocente.getIDocente());
	    this.gestorPantalla.lanzarPantalla(Docentes.TITULO, args);
	}

	@FXML private Button btnCargoDocenteCargo;
	@FXML public void nuevoCargo(ActionEvent event) {
	    if (!tblCargoDocente.getSelectionModel().isEmpty()) {
	        Object fila = tblCargoDocente.getSelectionModel().getSelectedItem();
	        Object valor = this.control.seleccionar(fila);

	        if (valor != null && valor instanceof ICargoDocente) {
	            ICargoDocente cargoDocente = (ICargoDocente) valor;
	            Map<String, Object> args = new HashMap<String, Object>();
	            args.put(Docentes.KEY_TAB, Docentes.TAB_CARGOS);
	            args.put(Docentes.REC_CARGO_DOCENTE, cargoDocente);
	            this.gestorPantalla.lanzarPantalla("Docentes", args);
	        }
	    }
	}

	@FXML private Button btnCargoDocenteSeleccionar;
	@FXML public void seleccionarCargo(ActionEvent event) {
		if (!tblCargoDocente.getSelectionModel().isEmpty()) {
			Object fila = tblCargoDocente.getSelectionModel().getSelectedItem();
			try {
				Object valor = this.control.seleccionar(fila);
				if (valor != null) {
					Map<String, Object> args = new HashMap<String, Object>();
					args.put(KEY_SELECCION, "CargoDocente");
					args.put(KEY_VALOR, valor);
					if (this.tipo_respuesta != null) {
						args.put(KEY_TIPO_RESPUESTA, this.tipo_respuesta);
					}
					
					this.controladorRespuesta.recibirParametros(args);
					this.gestorPantalla.cerrarPantalla(TITULO);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
// -------------------------------- Texto ----------------------------------- //
	@FXML protected TextField txtCargoDocenteCriterio;
	@FXML public void editarTexto() {
        actualizarLista();
    }

	/** Verificar si se apretó Enter */
    @FXML public void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                if (!this.tblCargoDocente.getSelectionModel().isEmpty()) {
                    seleccionarCargo(null);
                }
            default:
        }
    }
}