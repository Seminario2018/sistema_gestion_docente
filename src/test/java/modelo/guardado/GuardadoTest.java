package modelo.guardado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoDocente;
import modelo.auxiliares.EstadoPersona;
import modelo.auxiliares.TipoCargo;
import modelo.auxiliares.TipoContacto;
import modelo.auxiliares.TipoDocumento;
import modelo.cargo.Cargo;
import modelo.division.Area;
import modelo.division.Division;
import modelo.division.GestorArea;
import modelo.division.GestorDivision;
import modelo.docente.CargoDocente;
import modelo.docente.Docente;
import modelo.docente.GestorDocente;
import modelo.persona.*;

public class GuardadoTest {

	@Test
	public void test() {
		EstadoPersona ep = new EstadoPersona();
		ep.setId(0);
		ep = EstadoPersona.getEstado(ep);
		
		
		TipoDocumento td = new TipoDocumento();
		td.setId(0);
		td = TipoDocumento.getTipo(td);
		
		Persona p = new Persona();
		p.setApellido("Marazzo");
		p.setNombre("Leonardo José");
		p.setFechaNacimiento(LocalDate.of(1997, 06, 22));
		p.setTipoDocumento(td);
		p.setNroDocumento(40455634);
		
		List<ITitulo> titulos = new ArrayList<ITitulo>();
		titulos.add(new Titulo(-1, "Licenciado en sistemas", true));
		
		p.setTitulos(titulos);
		
		List<IDomicilio> domicilios = new ArrayList<IDomicilio>();
		domicilios.add(new Domicilio(-1, "Buenos Aires", "Lujan", "67000", "Martin fierro 1111"));
		
		p.setDomicilios(domicilios);
		
		List<IContacto> contactos = new ArrayList<IContacto>();
		TipoContacto tc = new TipoContacto();
		tc.setId(-1);
		tc.setDescripcion("Mail");
		
		contactos.add(new Contacto(-1, tc, "leonardojmarazzo@gmail.com"));
		p.setContactos(contactos);
		p.setEstado(ep);
		
		GestorPersona gp = new GestorPersona();
		System.out.println(gp.nuevaPersona(p).getMensaje());
		
		CategoriaInvestigacion ci = new CategoriaInvestigacion();
		ci.setId(1);
		ci.setDescripcion("I");
		
		EstadoDocente estadodoc = new EstadoDocente();
		estadodoc.setId(0);
		estadodoc.setDescripcion("ACTIVO");
		
		
		Docente doc = new Docente(p, 143112, "Leonardo genio", ci, estadodoc, null, null);
		GestorDocente gd = new GestorDocente();
		System.out.println(gd.nuevoDocente(doc).getMensaje());
		
		Division div = new Division();
		div.setCodigo("LE");
		div.setDescripcion("Division leonardo");
		div.setDisposicion("DISP-0002323");
		div.setDispDesde(LocalDate.of(2018, 5, 23));
		div.setDispHasta(LocalDate.of(2019, 5, 23));
		div.setJefe(doc);
		
		GestorDivision gdiv = new GestorDivision();
		System.out.println(gdiv.nuevaDivision(div).getMensaje());
		
		Area area = new Area();
		area.setCodigo("LE.01");
		area.setDescripcion("Area de tomas");
		area.setDisposicion("DISP-0001");
		area.setDispDesde(LocalDate.of(2018, 5, 23));
		area.setDispHasta(LocalDate.of(2019, 5, 23));
		area.setDivision(div);
		area.setDocenteResponsable(new Docente(null, 143191, null, null, null, null, null));
		area.setAreaDe(null);
		
		GestorArea ga = new GestorArea();
		System.out.println(ga.nuevaArea(area));
		
		Cargo car = new Cargo(-1, "Titular", 20);
		
		CargoDocente cd = new CargoDocente();
		cd.setId(-1);
		cd.setArea(area);
		cd.setCargo(car);
		cd.setDisposicion("DISP-003");
		cd.setDispDesde(LocalDate.of(2018, 5, 23));
		cd.setDispHasta(LocalDate.of(2019, 5, 23));
		cd.setResolucion("RES-001");
		cd.setResDesde(LocalDate.of(2018, 5, 23));
		cd.setResHasta(LocalDate.of(2019, 5, 23));
		cd.setEstado(new EstadoCargo(0,"ACTIVO"));
		cd.setFechaUltCost(LocalDate.of(2018, 5, 23));
		cd.setTipoCargo(new TipoCargo(0, "INTERINO"));
		cd.setUltimoCosto(2000);
			
		
		System.out.println(gd.agregarCargoDocente(doc, cd));		

	}

}
