package vista.controladores;

import javafx.fxml.Initializable;
import controlador.ControlInforme;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 15 de set. de 2018
 */
public class ConfigInf extends ControladorVista implements Initializable {

	public static final String TITULO = "ConfigInf";
	
	public static final String INFORMES_SEPARADOR = ControlInforme.INFORMES_SEPARADOR;
	
	public enum FormatoFecha {
		d_M_aa("d/M/aa", "d M yy"),
		dd_MM_aa("dd/MM/aa", "dd MM yy"),
		dd_MM_aaaa("dd/MM/aaaa", "dd MM yyyy"),
		aaaa_MM_dd("aaaa/MM/dd", "yyyy MM dd");
		
		private String descripcion;
		private String formato;
		
		private FormatoFecha(String descripcion, String formato) {
			this.descripcion = descripcion;
			this.formato = formato;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public String getFormato() {
			return formato;
		}
		public void setFormato(String formato) {
			this.formato = formato;
		}
	}
	
	public enum FormatoHora {
		HH_mm_ss("HH:mm:ss", "HH mm ss"),
		H_mm_ss("H:mm:ss", "H mm ss");
		
		private String descripcion;
		private String formato;
		
		private FormatoHora(String descripcion, String formato) {
			this.descripcion = descripcion;
			this.formato = formato;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public String getFormato() {
			return formato;
		}
		public void setFormato(String formato) {
			this.formato = formato;
		}
	}
	
	
	@Override
	public void inicializar() {
		this.window.setTitle("Configuración de informes");
	}
	
	@FXML
	private ComboBox<?> cmbHoraFor;

	@FXML
	private TextField txtNombre;

	@FXML
	private ComboBox<?> cmbFechaFor;

	@FXML
	private Button btnAceptar;

	@FXML
	private TextField txtDirectorio;

	@FXML
	private Button btnCancelar;

	@FXML
	private ComboBox<?> cmbHoraSep;

	@FXML
	private Button btnBuscar;

	@FXML
	private ComboBox<?> cmbFechaSep;

	@FXML
	void buscarDirectorio(ActionEvent event) {

	}

	@FXML
	void aceptar(ActionEvent event) {

	}

	@FXML
	void cancelar(ActionEvent event) {

	}

}
