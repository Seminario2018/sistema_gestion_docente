package modelo.docente;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;

import org.junit.Test;

import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoDocente;
import modelo.auxiliares.EstadoPersona;
import modelo.auxiliares.TipoCargo;
import modelo.auxiliares.TipoDocumento;
import modelo.cargo.Cargo;
import modelo.division.Area;
import modelo.division.Division;
import modelo.division.IDivision;
import modelo.persona.Persona;
import modelo.persona.*;

public class DocenteTest {

	@Test
	public void guardarTodoTest() {
		TipoDocumento td = new TipoDocumento(0, "DNI");
		ArrayList<IDomicilio> doms = new ArrayList<IDomicilio>();
		ArrayList<IContacto> cont = new ArrayList<IContacto>();
		ArrayList<ITitulo> tits = new ArrayList<ITitulo>();
		EstadoPersona est = new EstadoPersona();
		est.setId(0);
		est.setDescripcion("Activa");
		
		Persona p = new Persona("Marazzo", "Jose Luis", LocalDate.of(1951, 1, 24), td, 8320478, doms, cont, tits, est);
		
		CategoriaInvestigacion cat = new CategoriaInvestigacion();
		cat.setId(0);
		
		EstadoDocente esta = new EstadoDocente();
		esta.setId(0);
		
		Incentivo i = new Incentivo();
		i.setFecha(Year.now());
		ArrayList<IIncentivo> is = new ArrayList<IIncentivo>();
	
		IDivision div = new Division("CO", "Computación", null, "DD 13/2012", LocalDate.parse("2006-02-14"), LocalDate.parse("2009-12-20"));
		
		Area a = new Area("CO.01.00", "Algoritmos y Lenguajes", div, null, null, null, null, null);
		
		Cargo cargo = new Cargo(213, "Profesor Titular Exclusiva", 40);
		
		TipoCargo tipoCargo = new TipoCargo(0, "Ordinario");
		
		EstadoCargo estado = new EstadoCargo();
		estado.setId(0);
		
		CargoDocente cd = new CargoDocente(3000, a, cargo, tipoCargo, null, null, null, 3000, null, null, null, null, estado);
		
		ArrayList<ICargoDocente> cargosDocentes = new ArrayList<ICargoDocente>();
		cargosDocentes.add(cd);
		
		Docente doc = new Docente(p, 100, "Prueba", cat, esta, is, cargosDocentes);
		
		GestorDocente gd = new GestorDocente();
		gd.guardarTodo(doc);
		
	}

}
