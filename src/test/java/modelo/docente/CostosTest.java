package modelo.docente;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.TipoCargo;
import modelo.cargo.Cargo;
import modelo.division.Area;

public class CostosTest {

	
	/*
	 * Crea un cargo Docente con nuevos costos y lo guarda
	 */
	@Before
	public void testGuardado() {
		Docente d = new Docente();
		d.setLegajo(81);
		CargoDocente cd = new CargoDocente();
		Area a = new Area();
		a.setCodigo("BI.01.00");
		cd.setArea(a);
		Cargo c = new Cargo();
		c.setCodigo(213);
		cd.setCargo(c);
		TipoCargo tc = new TipoCargo();
		tc.setId(0);
		tc.setDescripcion("Ordinario");
		cd.setTipoCargo(tc);
		EstadoCargo e = new EstadoCargo();
		e.setId(0);
		e.setDescripcion("Activo");
		cd.setEstado(e);
		
		cd.setUltimoCosto(2000);
		cd.setFechaUltCost(LocalDate.of(2017, 6, 14));
		
		GestorDocente gd = new GestorDocente();
		gd.agregarCargoDocente(d, cd);
		
	}
	
	
	/*
	 * Ingresa nuevo costo al docente
	 */
	@Test
	public void testAgregar() {
		Docente d = new Docente();
		d.setLegajo(81);
		CargoDocente cd = new CargoDocente();
		cd.setId(1);
		Area a = new Area();
		a.setCodigo("BI.01.00");
		cd.setArea(a);
		Cargo c = new Cargo();
		c.setCodigo(213);
		cd.setCargo(c);
		TipoCargo tc = new TipoCargo();
		tc.setId(0);
		tc.setDescripcion("Ordinario");
		cd.setTipoCargo(tc);
		EstadoCargo e = new EstadoCargo();
		e.setId(0);
		e.setDescripcion("Activo");
		cd.setEstado(e);
		
		cd.setUltimoCosto(4000);
		cd.setFechaUltCost(LocalDate.now());
		
		GestorDocente gd = new GestorDocente();
		gd.modificarCargoDocente(d, cd);
	}
	
	@Test
	public void testRecuperar() {
		CargoDocente cd = new CargoDocente();
		cd.setId(1);
		GestorDocente gd = new GestorDocente();
		cd = (CargoDocente) gd.listarCargo(null, cd).get(0);
		System.out.println(cd.getUltimoCosto() + " ---- " + cd.getFechaUltCost());
	}
	
	
	
	

}
