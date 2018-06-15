package modelo.docente;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.TipoCargo;
import modelo.cargo.Cargo;
import modelo.cargo.GestorCargo;
import modelo.cargo.ICargo;
import modelo.division.Area;
import modelo.division.GestorArea;
import modelo.division.IArea;
import persistencia.ManejoDatos;

public class CargoDocente implements ICargoDocente, ICargoDocenteg {
	private int id = -1;
	private IArea area;
	private ICargo cargo;
	private TipoCargo tipoCargo;
	private String disposicion;
	private LocalDate dispDesde;
	private LocalDate dispHasta;
	private float ultimoCosto;
	private LocalDate fechaUltCost;
	private String resolucion;
	private LocalDate resDesde;
	private LocalDate resHasta;
	private EstadoCargo estado;

	private List<Costo> costos;


	public CargoDocente() {
		this.id = -1;
		this.area = null;
		this.cargo = null;
		this.tipoCargo = null;
		this.disposicion = null;
		this.dispDesde = null;
		this.dispHasta = null;
		this.ultimoCosto = -1;
		this.fechaUltCost = null;
		this.resolucion = null;
		this.resDesde = null;
		this.resHasta = null;
		this.estado = null;
		this.costos = new ArrayList<Costo>();
	}

	public CargoDocente(int id, IArea area, ICargo cargo, TipoCargo tipoCargo,
			String disposicion, LocalDate dispDesde, LocalDate dispHasta,
			float ultimoCosto, LocalDate fechaUltCost, String resolucion,
			LocalDate resDesde, LocalDate resHasta, EstadoCargo estado) {

		this.id = id;
		this.area = area;
		this.cargo = cargo;
		this.tipoCargo = tipoCargo;
		this.disposicion = disposicion;
		this.dispDesde = dispDesde;
		this.dispHasta = dispHasta;
		this.ultimoCosto = ultimoCosto;
		this.fechaUltCost = fechaUltCost;
		this.resolucion = resolucion;
		this.resDesde = resDesde;
		this.resHasta = resHasta;
		this.estado = estado;
		this.costos = new ArrayList<Costo>();
	}

	@Override
	public ICargoDocente clone() {
		return (ICargoDocente) new CargoDocente(
				this.id,
				this.area,
				this.cargo,
				this.tipoCargo,
				this.disposicion,
				this.dispDesde,
				this.dispHasta,
				this.ultimoCosto,
				this.fechaUltCost,
				this.resolucion,
				this.resDesde,
				this.resHasta,
				this.estado
				);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public IArea getArea() {
		if (area == null) {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select("CargosDocentes", "Area", "Codigo = " + this.getId());
			if (!res.isEmpty()) {
				Hashtable<String, String> reg = res.get(0);
				GestorArea ga = new GestorArea();
				IArea a = new Area();
				a.setCodigo(reg.get("Area"));
				a = ga.listarAreas(a).get(0);
				this.setArea(a);
			}
		}
		return this.area;
	}

	@Override
	public void setArea(IArea area) {
		this.area = area;
	}

	@Override
	public ICargo getCargo() {
		if (cargo == null) {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select("CargosDocentes", "Cargo", "Codigo = " + this.getId());
			Hashtable<String, String> reg = res.get(0);
			GestorCargo gc = new GestorCargo();
			Cargo car = new Cargo(Integer.parseInt(reg.get("Cargo")), null, -1);
			car = (Cargo) gc.listarCargos(car).get(0);
			this.setCargo(car);
		}
		return this.cargo;
	}

	@Override
	public void setCargo(ICargo cargo) {
		this.cargo = cargo;
	}

	@Override
	public TipoCargo getTipoCargo() {
		if (this.tipoCargo == null) {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select("CargosDocentes", "TipoCargo", "Codigo = " + this.getId());
			Hashtable<String, String> reg = res.get(0);
			this.setTipoCargo(TipoCargo.getTipoCargo(new TipoCargo(Integer.parseInt(reg.get("TipoCargo")), "")));
		}
		return this.tipoCargo;
	}

	@Override
	public void setTipoCargo(TipoCargo tipoCargo) {
		this.tipoCargo = tipoCargo;
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
	public float getUltimoCosto() {
		if (costos == null || costos.isEmpty()) {
			this.obtenerCostos();
		}
		return this.costos.get(this.costos.size()-1).getCosto();
	}

	@Override
	public void setUltimoCosto(float ultimoCosto) {
		this.obtenerCostos();
		Costo costo = new Costo();
		costo.setCosto(ultimoCosto);
		costos.add(costo);
	}

	private void obtenerCostos() {
		if (this.id != -1) {
			costos = new ArrayList<Costo>();
			ManejoDatos md = new ManejoDatos();
			String tabla = "costos";
			String campos = "id, Costo, Fecha";
			String condicion = "CodigoCargo = " + this.getId();

			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
			for (Hashtable<String, String> reg : res) {
				Costo c = new Costo();
				c.setId(Integer.parseInt(reg.get("id")));
				c.setCosto(Float.parseFloat(reg.get("Costo")));

				if (!reg.get("Fecha").equals("")) {
					c.setFechaCosto(Date.valueOf(reg.get("Fecha")).toLocalDate());
				}
				this.costos.add(c);
			}
		}

	}

	@Override
	public LocalDate getFechaUltCost() {
		if (costos == null || costos.isEmpty()) {
			this.obtenerCostos();
		}
		return this.costos.get(this.costos.size()-1).getFechaCosto();
	}

	@Override
	public void setFechaUltCost(LocalDate fechaUltCost) {	
		if (costos == null || costos.isEmpty()) {
			this.obtenerCostos();
		}
		this.costos.get(costos.size() - 1).setFechaCosto(fechaUltCost);
	}

	@Override
	public String getResolucion() {
		return this.resolucion;
	}

	@Override
	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	@Override
	public LocalDate getResDesde() {
		return this.resDesde;
	}

	@Override
	public void setResDesde(LocalDate resDesde) {
		this.resDesde = resDesde;
	}

	@Override
	public LocalDate getResHasta() {
		return this.resHasta;
	}

	@Override
	public void setResHasta(LocalDate resHasta) {
		this.resHasta = resHasta;
	}

	@Override
	public EstadoCargo getEstado() {
		if (this.estado == null) {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select("CargosDocentes", "EstadoCargo", "Codigo = " + this.getId());
			Hashtable<String, String> reg = res.get(0);
			EstadoCargo estado = new EstadoCargo();
			estado.setId(Integer.parseInt(reg.get("EstadoCargo")));
			estado = EstadoCargo.getEstadoCargo(estado);
			this.setEstado(estado);
		}
		return this.estado;
	}

	@Override
	public void setEstado(EstadoCargo estado) {
		this.estado = estado;
	}

	@Override
	public IArea getArea2() {
		return this.area;
	}

	@Override
	public ICargo getCargo2() {
		return this.cargo;
	}

	@Override
	public TipoCargo getTipoCargo2() {
		return this.tipoCargo;
	}

	@Override
	public EstadoCargo getEstado2() {
		return this.estado;
	}

	public IDocente getDocente() {
		IDocente docente = new Docente(); 
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "CargosDocentes";
			String campos = "Legajo";
			String condicion = "Codigo = " + this.id;
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
			Hashtable<String, String> reg = res.get(0);
			docente.setLegajo(Integer.parseInt(reg.get("Legajo")));
			GestorDocente gd = new GestorDocente();
			docente = gd.listarDocentes(docente).get(0);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			docente = new Docente();
		}
		return docente;
	}
	
	public List<Costo> getCostos(){
		if (costos == null || costos.isEmpty()) {
			this.obtenerCostos();
		}
		return costos;
	}

}