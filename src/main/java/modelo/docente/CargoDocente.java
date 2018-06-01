package modelo.docente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.TipoCargo;
import modelo.cargo.Cargo;
import modelo.cargo.GestorCargo;
import modelo.cargo.ICargo;
import modelo.division.Area;
import modelo.division.GestorArea;
import modelo.division.IArea;
import persistencia.ManejoDatos;

public class CargoDocente implements ICargoDocente {
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
			Hashtable<String, String> reg = res.get(0);
			GestorArea ga = new GestorArea();
			IArea a = new Area();
			a.setCodigo(reg.get("Area"));
			a = ga.listarAreas(a).get(0);
			this.setArea(a);
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
			car = (Cargo) gc.listarCargo(car).get(0);
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
        return this.ultimoCosto;
    }

    @Override
    public void setUltimoCosto(float ultimoCosto) {
        this.ultimoCosto = ultimoCosto;
    }

    @Override
    public LocalDate getFechaUltCost() {
        return this.fechaUltCost;
    }

    @Override
    public void setFechaUltCost(LocalDate fechaUltCost) {
        this.fechaUltCost = fechaUltCost;
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

}