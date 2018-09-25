package vista.controladores;

import java.util.ArrayList;
import java.util.List;
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
import modelo.informe.ColumnaInforme.SepFiltro;
import modelo.informe.ColumnaInforme.TipoColumna;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de set. de 2018
 */
public class InformesFiltros extends ControladorVista {

	public static final String TITULO = "InformesFiltros";
	public static final String COLUMNA = "columna";
	
	private ColumnaInforme columnaSeleccion = null;

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
					this.window.setTitle(this.window.getTitle() + " " 
							+ this.columnaSeleccion.getAtributo());
				}
			}
		}
	}
	
	public void setColumnaSeleccion(ColumnaInforme columna) {
		this.columnaSeleccion = columna;

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
		
		if (this.columnaSeleccion.getFiltros() != null) {
			this.txtFiltro.setText(this.columnaSeleccion.stringFiltrosUI());
		}

		this.cmbFiltroFiltro.getSelectionModel().clearSelection();

		this.cmbFiltroCalculo.getSelectionModel().clearSelection();
		Calculo calculo = columna.getCalculo();
		if (calculo != null) {
			this.cmbFiltroCalculo.setValue(calculo);
		}
	}

	
	@FXML protected TextField txtFiltroNombre;
	
	@FXML protected TextField txtFiltro;
	@FXML protected ComboBox<Filtro> cmbFiltroFiltro;
	@FXML protected DatePicker dtpFiltroCondicion;
	@FXML protected TextField txtFiltroCondicion;
	@FXML protected Button btnFiltroAgregar;
	@FXML public void agregarFiltro(ActionEvent event) {
//		Filtro filtro = this.cmbFiltroFiltro.getSelectionModel().getSelectedItem();
//		if (filtro != null) {
//			String valor = (this.txtFiltroCondicion.isVisible()) ? this.txtFiltroCondicion.getText()
//					: this.dtpFiltroCondicion.getValue().toString();
//			if (valor != null && !"".equals(valor)) {
//				FiltroColumna fc = new FiltroColumna(filtro, valor);
//				this.columnaSeleccion.setFiltro(fc);
//			}
//		}
	}
	
	@FXML protected ComboBox<Calculo> cmbFiltroCalculo;
	@FXML protected Button btnAceptar;
	@FXML public void aceptar(ActionEvent event) {
		if (this.columnaSeleccion != null) {

			if (!this.parsearFiltro(this.txtFiltro.getText().trim(), this.columnaSeleccion)) {
				this.alertaError(this.window.getTitle(),
						"El formato del filtro no es correcto.",
						"Revise el formato de la condición a filtrar.");
				return;
			}
			
			String nombre = this.txtFiltroNombre.getText();
			if (nombre != null && !"".equals(nombre))
				this.columnaSeleccion.setNombre(nombre);

			Calculo calculo = this.cmbFiltroCalculo.getSelectionModel().getSelectedItem();
			if (calculo != null) {
				this.columnaSeleccion.setCalculo(calculo);
			}
			
			this.gestorPantalla.cerrarPantalla(TITULO);
		}
	}
	
	@FXML protected Button btnCancelar;
	@FXML public void cancelar(ActionEvent event) {
		this.gestorPantalla.cerrarPantalla(TITULO);
	}
	
	
	/**
	 * Convierte un filtro escrito en texto para asignarlo a una ColumnaInforme
	 * @param entrada la entrada a parsear.
	 * @param columna la ColumnaInforme a la cual se le asigna el filtro.
	 * @return <b>True</b> si se parseó con éxito, <b>False</b> en caso contrario.
	 */
	private boolean parsearFiltro(String entrada, ColumnaInforme columna) {
		if (columna == null || entrada == null) return false;
		if (entrada.length() == 0) return true;
		
		String texto = new String(entrada); 
		boolean finCadena = false;
		List<FiltroColumna> filtros = new ArrayList<FiltroColumna>();
		List<SepFiltro> sepFiltros = new ArrayList<SepFiltro>();
		
		while (!finCadena) {
			// Aislar la primer expresión entre paréntesis
			String exp = null;
			try {
				exp = texto.substring(1, texto.indexOf(")"));
			} catch (IndexOutOfBoundsException e) {
				return false;
			}
			
			FiltroColumna filtro = this.buscarFiltro(exp);
			// Error de sintaxis
			if (filtro == null) return false;
			
			// Añadir a la lista de columnas
			filtros.add(filtro);
			
			// Chequear que se haya acabado la cadena
			// La longitud de exp más los paréntesis
			if (texto.length() > exp.length() + 2) {
				texto = texto.substring(texto.indexOf(")") + 1, texto.length());
				
				String sep = null;
				try {
					sep = texto.substring(0, texto.indexOf("("));
				} catch (IndexOutOfBoundsException e) {
					return false;
				}
				
				SepFiltro sepFiltro = buscarSepFiltro(sep);
				// Error de sintaxis
				if (sepFiltro == null) return false;

				// Añadir a la lista de separadores
				sepFiltros.add(sepFiltro);
				
				// Continuar con el resto de la cadena
				try {
					texto = texto.substring(texto.indexOf("("), texto.length());
				} catch (IndexOutOfBoundsException e) {
					return false;
				} 
			} else {
				// Se terminó la cadena
				finCadena = true;
			}
		}
		
		columna.setFiltros(filtros);
		columna.setSepFiltros(sepFiltros);
		
		return true;
	}
	
	
	private FiltroColumna buscarFiltro(String s) {
		// Buscar el filtro
		Filtro filtro = null;
		Filtro[] fils = Filtro.getLista();
		int i = 0;
		boolean found = false;
		while (!found && i < fils.length) {
			if (s.contains(fils[i].getDescripcion())) {
				found = true;
				filtro = fils[i];
			}
			i++;
		}
		// Error de sintaxis
		if (!found) return null;
		
		// Quitar el nombre del filtro del texto y quedarse con el valor
		String valor = s.substring(
				s.indexOf(filtro.getDescripcion()) + filtro.getDescripcion().length() + 1,
				s.length());
		return new FiltroColumna(filtro, valor);	
	}
	
	private SepFiltro buscarSepFiltro(String s) {
		// Buscar el separador
		SepFiltro sepFiltro = null;
		SepFiltro[] seps = ColumnaInforme.SepFiltro.values();
		int i = 0;
		boolean found = false;
		while (!found && i < seps.length) {
			if (s.contains(seps[i].getDescripcion())) {
				found = true;
				sepFiltro = seps[i];
			}
			i++;
		}
		
		return sepFiltro;
	}
}
