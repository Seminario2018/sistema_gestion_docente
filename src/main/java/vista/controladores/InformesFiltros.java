package vista.controladores;

import java.util.Map;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import modelo.auxiliares.Calculo;
import modelo.auxiliares.Filtro;
import modelo.informe.FiltroColumna;
import modelo.informe.ColumnaInforme;
import modelo.informe.ColumnaInforme.TipoColumna;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de set. de 2018
 */
public class InformesFiltros extends ControladorVista {

	public static final String TITULO = "InformesFiltros";
	public static final String COLUMNA = "columna";
	
	private ColumnaInforme columnaSeleccion = null;
	private ColumnaInforme columnaAnterior = null;

	@Override
	public void inicializar() {
		this.cmbFiltroFiltro.setItems(FXCollections.observableArrayList(Filtro.getLista()));
		this.cmbFiltroCalculo.setItems(FXCollections.observableArrayList(Calculo.getLista()));
		
		this.window.setTitle("Modificar columna");
	}
	
	@Override
	public void recibirParametros(Map<String, Object> args) {
		if (args != null) {
			if (args.containsKey(InformesFiltros.COLUMNA)) {
				Object o = args.get(InformesFiltros.COLUMNA);
				if (o instanceof ColumnaInforme) {
					setColumnaSeleccion((ColumnaInforme) o);
				}
			}
		}
	}
	
	public void setColumnaSeleccion(ColumnaInforme columna) {
		this.columnaSeleccion = columna;
		// Tener una copia sin cambiar
		this.columnaAnterior = columna.clone();

		this.txtFiltroNombre.clear();
		String nombre = columna.getNombre();
		if (nombre != null && !"".equals(nombre))
			this.txtFiltroNombre.setText(nombre);

		if (this.columnaSeleccion.getTipo() == TipoColumna.DATE) {
			this.txtFiltroCondicion.setVisible(false);
			this.dtpFiltroCondicion.setVisible(true);
			this.dtpFiltroCondicion.getEditor().clear();
		} else {
			this.dtpFiltroCondicion.setVisible(false);
			this.txtFiltroCondicion.setVisible(true);
			this.txtFiltroCondicion.clear();
		}

		this.cmbFiltroFiltro.getSelectionModel().clearSelection();
		FiltroColumna filtro = columna.getFiltro();
		if (filtro != null) {
			if (this.columnaSeleccion.getTipo() == TipoColumna.DATE) {
				this.dtpFiltroCondicion.getEditor().setText(filtro.getValor());
			} else {
				this.txtFiltroCondicion.setText(filtro.getValor());
			}
			this.cmbFiltroFiltro.setValue(filtro.getTipo());
		}

		this.cmbFiltroCalculo.getSelectionModel().clearSelection();
		Calculo calculo = columna.getCalculo();
		if (calculo != null) {
			this.cmbFiltroCalculo.setValue(calculo);
		}
	}

	
	@FXML
	protected TextField txtFiltroNombre;
	@FXML
	protected ComboBox<Filtro> cmbFiltroFiltro;
	@FXML
	protected DatePicker dtpFiltroCondicion;
	@FXML
	protected TextField txtFiltroCondicion;
	@FXML
	protected ComboBox<Calculo> cmbFiltroCalculo;
	@FXML
	protected Button btnAceptar;
	@FXML
	public void aceptar(ActionEvent event) {
		if (this.columnaSeleccion != null) {

			String nombre = this.txtFiltroNombre.getText();
			if (nombre != null && !"".equals(nombre))
				this.columnaSeleccion.setNombre(nombre);
// TODO múltiples filtros
			Filtro filtro = this.cmbFiltroFiltro.getSelectionModel().getSelectedItem();
			if (filtro != null) {
				String valor = (this.txtFiltroCondicion.isVisible()) ? this.txtFiltroCondicion.getText()
						: this.dtpFiltroCondicion.getValue().toString();
				if (valor != null && !"".equals(valor)) {
					FiltroColumna fc = new FiltroColumna(filtro, valor);
					this.columnaSeleccion.setFiltro(fc);
				}
			}

			Calculo calculo = this.cmbFiltroCalculo.getSelectionModel().getSelectedItem();
			if (calculo != null) {
				this.columnaSeleccion.setCalculo(calculo);
			}
		}
	}
	
	@FXML
	protected Button btnCancelar;
	@FXML
	public void cancelar(ActionEvent event) {
		this.columnaSeleccion = this.columnaAnterior;
		this.gestorPantalla.cerrarPantalla(TITULO);
	}
}
