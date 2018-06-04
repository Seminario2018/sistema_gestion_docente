package modelo.division;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

import modelo.docente.Docente;
import modelo.docente.GestorDocente;
import modelo.docente.IDocente;
import persistencia.ManejoDatos;

public class Area implements IArea, IAreag {

	private String codigo;
	private String descripcion;
	private IDivision division;
	private IDocente responsable;
	private String disposicion;
	private LocalDate dispDesde;
	private LocalDate dispHasta;
	private IArea subAreaDe;


	@Override
	public IArea clone() {
		return (IArea) new Area(
				this.codigo,
				this.descripcion,
				this.division,
				this.responsable,
				this.disposicion,
				this.dispDesde,
				this.dispHasta,
				this.subAreaDe);
	}

	public Area(){
		this.codigo = "";
		this.descripcion = "";
		this.disposicion = "";
	}


	public Area(String codigo, String descripcion, IDivision division,
			IDocente responsable, String disposicion, LocalDate dispDesde,
			LocalDate dispHasta, IArea subAreaDe) {

		this.codigo = codigo;
		this.descripcion = descripcion;
		this.division = division;
		this.responsable = responsable;
		this.disposicion = disposicion;
		this.dispDesde = dispDesde;
		this.dispHasta = dispHasta;
		this.subAreaDe = subAreaDe;
	}

	@Override
	public String getCodigo() {
		return this.codigo;
	}

	@Override
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getDescripcion() {
		return this.descripcion;
	}

	@Override
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public IDivision getDivision() {
		if (this.division == null) {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res =  
					md.select("Areas", "Division", "Codigo = '" + this.getCodigo() + "'");
			Hashtable<String, String> reg = res.get(0);
			if (!reg.get("Division").equals("")) {
				Division d = new Division();
				d.setCodigo(reg.get("Division"));	
				GestorDivision gestorDivision = new GestorDivision();
				this.division = (Division) gestorDivision.listarDivision(d).get(0);
			}
		}
		
		return this.division;
	}

	@Override
	public void setDivision(IDivision division) {
		this.division = division;
	}

	@Override
	public IDocente getDocenteResponsable() {
		if (this.responsable == null) {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res =  
					md.select("Areas", "Responsable", "Codigo = '" + this.getCodigo() + "'");
			Hashtable<String, String> reg = res.get(0);
			if (!reg.get("Responsable").equals("")) {
				Docente responsable = new Docente(null, Integer.parseInt(reg.get("Responsable")), null, null, null, null, null);
				GestorDocente gd = new GestorDocente();
				responsable = (Docente) gd.listarDocentes(responsable).get(0);
			}
		}
		return this.responsable;
	}

	@Override
	public void setDocenteResponsable(IDocente responsable) {
		this.responsable = responsable;
	}

	@Override
	public String getDisposicion() {
		return this.disposicion;
	}

	@Override
	public void setDisposicion(String disposicion) {
		this.disposicion = disposicion;
	}

	@Override
	public LocalDate getDispDesde() {
		return this.dispDesde;
	}

	@Override
	public void setDispDesde(LocalDate dispDesde) {
		this.dispDesde = dispDesde;
	}

	@Override
	public LocalDate getDispHasta() {
		return this.dispHasta;
	}

	@Override
	public void setDispHasta(LocalDate dispHasta) {
		this.dispHasta = dispHasta;
	}

	@Override
	public IArea getAreaDe() {
		if (this.subAreaDe == null) {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res =  
					md.select("Areas", "SubAreaDe", "Codigo = '" + this.getCodigo() + "'");
			Hashtable<String, String> reg = res.get(0);
			if (!reg.get("SubAreaDe").equals("")) {
				Area a = new Area();
				a.setCodigo(reg.get("SubAreaDe"));
				GestorArea ga = new GestorArea();
				a = (Area) ga.listarAreas(a).get(0);
				this.setAreaDe(a);
			}
		}
		return this.subAreaDe;
	}

	@Override
	public void setAreaDe(IArea subAreaDe) {
		this.subAreaDe = subAreaDe;
	}

	@Override
	public IDivision getDivision2() {
		return this.division;
	}

	@Override
	public IDocente getDocenteResponsable2() {
		return this.responsable;
	}

	@Override
	public IArea getAreaDe2() {
		return this.subAreaDe;
	}

}