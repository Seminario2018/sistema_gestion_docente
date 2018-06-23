package vista.controladores;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import controlador.ControlBusqueda;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import utilidades.Utilidades;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class BusquedaCargoDocente extends ControladorVista implements Initializable {

    public static final String KEY_NUEVO = "nuevo";
    public static final String KEY_TIPO = "tipo";
    public static final String KEY_CONTROLADOR = "controlador";
    // Devuelve el tipo de dato seleccionado, por ejemplo "Area"
    public static final String KEY_SELECCION = "seleccion";
    public static final String KEY_TIPO_RESPUESTA = "tipo_respuesta";
    // Devuelve el dato seleccionado
    public static final String KEY_VALOR = "valor";
    public static final String TITULO = "Busqueda";

    private String tipo;
    private ControlBusqueda control = new ControlBusqueda(this);
    // Recibe la respuesta (selección)
    private ControladorVista controladorRespuesta;

    private String tipo_respuesta;

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtCargoDocenteCriterio.requestFocus();
            }
        });
	}

	@FXML private TextField txtCargoDocenteCriterio;

	@FXML private Button btnCargoDocenteDocente;
	@FXML public void nuevoDocente(ActionEvent event) {
	    this.gestorPantalla.lanzarPantalla("Docentes", null);
	}

	@FXML private Button btnCargoDocenteCargo;
	@FXML public void nuevoCargo(ActionEvent event) {
	    if (!tblCargoDocente.getSelectionModel().isEmpty()) {
	        Object fila = tblCargoDocente.getSelectionModel().getSelectedItem();
	        Object valor = this.control.seleccionar(fila);

	        if (valor != null && valor instanceof ICargoDocente) {
	            ICargoDocente cargoDocente = (ICargoDocente) valor;
	            IDocente docente = cargoDocente.getDocente();

	            Map<String, Object> args = new HashMap<String, Object>();
	            args.put(Docentes.KEY_TAB, Docentes.TAB_CARGOS);
	            args.put(Busqueda.KEY_SELECCION, "Docente");
	            args.put(Busqueda.KEY_VALOR, docente);
	            this.gestorPantalla.lanzarPantalla("Docentes", args);
	        }
	    }
	}

	@FXML private Button btnCargoDocenteSeleccionar;
	@FXML public void seleccionarCargo(ActionEvent event) {
	    // Borrar la "s" del final (DocenteS, PersonaS)
        String varName = this.tipo.substring(0, this.tipo.length()-1);
        if (!tblCargoDocente.getSelectionModel().isEmpty()) {
            Object fila = tblCargoDocente.getSelectionModel().getSelectedItem();
            try {
                Object valor = this.control.seleccionar(fila);
                if (valor != null) {
                    Map<String, Object> args = new HashMap<String, Object>();
                    args.put(Busqueda.KEY_SELECCION, varName);
                    args.put(Busqueda.KEY_VALOR, valor);
                    args.put(Busqueda.KEY_TIPO_RESPUESTA, this.tipo_respuesta);
                    this.controladorRespuesta.recibirParametros(args);
                    this.gestorPantalla.cerrarPantalla("BusquedaCargoDocente");
                } else {
                    System.out.printf("valor es null\n");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
//          TODO this.gestorPantalla.mensajeEstado("Debe seleccionar una fila de la grilla");
        }
	}

	class EditThread implements Runnable {
        private Busqueda b;
        public EditThread(Busqueda b) {
            this.b = b;
        }
        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            try {
                Thread.sleep(300);
                if (!Thread.interrupted()) {
                    b.actualizarLista();
                }
            } catch (InterruptedException e) {
                // Nada
            }
        }
    }

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

    @Override
    public void recibirParametros(Map<String, Object> args) {
        this.tipo = (String) args.get(KEY_TIPO);
        this.controladorRespuesta = (ControladorVista) args.get(KEY_CONTROLADOR);
        this.btnCargoDocenteCargo.setVisible((boolean) args.get(KEY_NUEVO));
        this.btnCargoDocenteDocente.setVisible((boolean) args.get(KEY_NUEVO));
        this.tipo_respuesta = (args.containsKey(KEY_TIPO_RESPUESTA)) ?
            (String) args.get(KEY_TIPO_RESPUESTA) :
            "";

        try {
            inicializar(Class.forName("modelo.busqueda.Busqueda" + this.tipo.substring(0, this.tipo.length()-1)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void inicializar(Class c) {
        inicializarTabla(c);
        actualizarLista();
        this.txtCargoDocenteCriterio.requestFocus();
    }

    public void inicializarTabla(Class fila) {
        this.tblCargoDocente.getColumns().clear();
        Field[] campos = fila.getDeclaredFields();
        for (int i = 0; i < campos.length; i++) {
            String varName = campos[i].getName();
            TableColumn columna = new TableColumn<>(Utilidades.primeraMayuscula(varName));
            columna.setCellValueFactory(new PropertyValueFactory(varName));
            this.tblCargoDocente.getColumns().add(columna);
        }
        this.tblCargoDocente.setItems(this.filasCargoDocente);
    }

    public void actualizarLista() {
        this.filasCargoDocente.clear();
        try {
            Method m = this.control.getClass().getDeclaredMethod("listar" + this.tipo, String.class);
            this.filasCargoDocente.addAll((List)
                    m.invoke(this.control, this.txtCargoDocenteCriterio.getText()));
            if (this.filasCargoDocente.size() > 0) {
                this.tblCargoDocente.getSelectionModel().select(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML protected ObservableList<?> filasCargoDocente = FXCollections.observableArrayList();
    @FXML private TableView tblCargoDocente;
//    @FXML private TableColumn<?, ?> colCargoDocenteLegajo;
//    @FXML private TableColumn<?, ?> colCargoDocenteNombre;
//    @FXML private TableColumn<?, ?> colCargoDocenteArea;
//    @FXML private TableColumn<?, ?> colCargoDocenteCodigo;
//    @FXML private TableColumn<?, ?> colCargoDocenteCargo;

}