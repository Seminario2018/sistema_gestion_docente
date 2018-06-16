package modelo.costeo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.FixMethodOrder;
import org.junit.Test;


public class CargosFaltantesTest {

	
	@Test
	public void agregarTest() {
		CargoFaltante cf = new CargoFaltante();
		cf.setLegajo(143112);
		cf.setCodigoCargo(1);
		cf.setUltimoCosto(2000);
		cf.setFechaUltimoCosto(LocalDate.now());
		cf.setTipo(false);
		
		GestorCargosFaltantes gc = new GestorCargosFaltantes();
		System.out.println(gc.agregarCargoFaltante(cf));
	}
	
	
	@Test
	public void deleteTest() {
		CargoFaltante cf = new CargoFaltante();
		cf.setLegajo(143112);
		cf.setCodigoCargo(1);
		cf.setUltimoCosto(2000);
		cf.setFechaUltimoCosto(LocalDate.now());
		cf.setTipo(false);
		
		GestorCargosFaltantes gc = new GestorCargosFaltantes();
		System.out.println(gc.eliminarCargoFaltante(cf));
	}
	
	
	
	@Test
	public void listarTest() {
		GestorCargosFaltantes gc = new GestorCargosFaltantes();
		System.out.println(gc.listarCargosFaltantes().get(0).getLegajo());
	}
	
	
	
	
	
	

}
