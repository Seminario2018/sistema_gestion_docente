package vista.controladores;

import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modelo.costeo.FilaCostoComparar;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 21 de ago. de 2018
 */
public class ListaCosto extends ControladorVista implements Initializable {
	public static final String TITULO = "ListaCosto";
	public static final String LISTA = "lista";
	
	@Override
	public void inicializar() {
		inicializarTabla("Lista");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void recibirParametros(Map<String, Object> args) {
		Object o = args.containsKey(LISTA)
				? args.get(LISTA)
				: null;
		if (o != null && o instanceof List) {
			this.filasLista = FXCollections.observableArrayList((List<FilaCostoComparar>) o);
			this.tblLista.setItems(this.filasLista);
		}
	}

	@FXML private Button btnSalir;
	@FXML void salir(ActionEvent event) {
		this.gestorPantalla.cerrarPantalla(TITULO);
	}

	protected ObservableList<FilaCostoComparar> filasLista;
	
	@FXML protected TableView<FilaCostoComparar> tblLista;
	@FXML protected TableColumn<FilaCostoComparar, Integer> colListaLegajo;
	@FXML protected TableColumn<FilaCostoComparar, String> colListaApellido;
	@FXML protected TableColumn<FilaCostoComparar, String> colListaNombre;
	@FXML protected TableColumn<FilaCostoComparar, Integer> colListaCodigo;
	@FXML protected TableColumn<FilaCostoComparar, String> colListaCostoAnt;
	@FXML protected TableColumn<FilaCostoComparar, String> colListaFechaCostoAnt;
	@FXML protected TableColumn<FilaCostoComparar, String> colListaCostoAct;
	@FXML protected TableColumn<FilaCostoComparar, String> colListaFechaCostoAct;
}
